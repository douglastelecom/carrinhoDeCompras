package br.ufrn.imd.carrinho.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.ufrn.imd.carrinho.dominio.*;

public class CarrinhoServico {

    public Pedido finalizar(Usuario usuario, List<Item> itens, LocalDate checkoutDate) {

        BigDecimal somaPrecoItens = somarPreco(itens);
        BigDecimal taxaDesconto = encontrarDesconto(somaPrecoItens, itens);
        BigDecimal precoItensFinal = calcularPrecoItensFinal(somaPrecoItens, taxaDesconto, itens);

        Regiao regiao = encontrarRegiao(usuario.getEndereco().getEstado());
        BigDecimal taxaAdicional = encontrarTaxaAdicional(regiao);
        BigDecimal somaPeso = somarPeso(itens);
        BigDecimal taxaFrete = encontrarTaxaFrete(somaPeso);
        BigDecimal freteFinal = calcularFreteFinal(somaPeso, taxaFrete, taxaAdicional);

        BigDecimal precoTotal = precoItensFinal.add(freteFinal);

        return new Pedido(usuario, itens, precoTotal, precoItensFinal, freteFinal, checkoutDate);
    }

    public BigDecimal somarPreco(List<Item> itens) {
        BigDecimal somaPreco = itens.stream().map(Item::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
        return somaPreco;
    }

    public BigDecimal encontrarDesconto(BigDecimal somaPreco, List<Item> itens) {
        somaPreco = somaPreco.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal desconto = BigDecimal.ZERO;
        System.out.println("A soma do preco é: "+somaPreco);
        if ((somaPreco.compareTo(BigDecimal.ZERO) >= 0) && (somaPreco.compareTo(BigDecimal.valueOf(500)) < 0)) {
                desconto = BigDecimal.ZERO;
        } else if ((somaPreco.compareTo(BigDecimal.valueOf(500)) >= 0) && (somaPreco.compareTo(BigDecimal.valueOf(1000)) < 0)) {
            desconto = BigDecimal.valueOf(0.1);
        } else if ((somaPreco.compareTo(BigDecimal.valueOf(1000)) >= 0)) {
            desconto = BigDecimal.valueOf(0.2);
        }
        System.out.println("O desconto foi de: "+desconto);
        return desconto;
    }

    public BigDecimal calcularPrecoItensFinal(BigDecimal somaPreco, BigDecimal desconto, List<Item> itens) {
        BigDecimal precoItensFinal= BigDecimal.ZERO;
        if(desconto.equals(BigDecimal.ZERO)){
            for (ItemTipo tipo : ItemTipo.values()){
                int j = 0;
                for(Item item: itens) {
                    if (item.getTipo().equals(tipo)) {
                        j++;
                    }
                }
                if(j > 2){
                    for (Item item2: itens) {
                        if(item2.getTipo().equals(tipo)){
                            precoItensFinal = precoItensFinal.add(aplicarDesconto(item2.getPreco(), BigDecimal.valueOf(0.05)));
                        }
                    }
                }
                else {
                    for (Item item2: itens) {
                        if(item2.getTipo().equals(tipo)){
                            precoItensFinal = precoItensFinal.add(item2.getPreco());
                        }
                    }
                }

            }

        return precoItensFinal.setScale(2, RoundingMode.HALF_EVEN);
        }
        else {
            return aplicarDesconto(somaPreco, desconto).setScale(2, RoundingMode.HALF_EVEN);
        }
    }

    public Regiao encontrarRegiao(Estado e) {
        Regiao regiao = null;
        switch (e) {
            case RS:
            case SC:
            case PR:
                regiao = Regiao.SUL;
                break;
            case SP:
            case RJ:
            case ES:
            case MG:
                regiao = Regiao.SUDESTE;
                break;
            case MS:
            case GO:
            case DF:
            case MT:
                regiao = Regiao.CENTRO_OESTE;
                break;
            case BA:
            case SE:
            case AL:
            case PE:
            case PB:
            case RN:
            case CE:
            case PI:
            case MA:
                regiao = Regiao.NORDESTE;
                break;
            case TO:
            case RO:
            case AC:
            case PA:
            case AM:
            case AP:
            case RR:
                regiao = Regiao.NORTE;
                break;
        }
        return regiao;
    }

    public BigDecimal encontrarTaxaAdicional(Regiao regiao) {
        BigDecimal taxaAdicional = BigDecimal.ZERO;
        switch (regiao) {
            case SUDESTE:
            case NORDESTE:
                taxaAdicional = BigDecimal.ZERO;
                break;
            case NORTE:
            case SUL:
            case CENTRO_OESTE:
                taxaAdicional = BigDecimal.valueOf(0.1);
                break;
        }
        return taxaAdicional;
    }


    public BigDecimal somarPeso(List<Item> itens) {
        BigDecimal somaPeso = itens.stream().map(Item::getPeso).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(1, RoundingMode.HALF_UP);
        return somaPeso;
    }

    public BigDecimal encontrarTaxaFrete(BigDecimal somaPeso) {
        BigDecimal taxaFrete = null;
        if ((somaPeso.compareTo(BigDecimal.ZERO) >= 0) && (somaPeso.compareTo(BigDecimal.valueOf(10)) < 0)) {
            taxaFrete = BigDecimal.ZERO;
        } else if ((somaPeso.compareTo(BigDecimal.valueOf(10)) >= 0) && (somaPeso.compareTo(BigDecimal.valueOf(40)) < 0)) {
            taxaFrete = BigDecimal.valueOf(0.5);
        } else if ((somaPeso.compareTo(BigDecimal.valueOf(40)) >= 0) && (somaPeso.compareTo(BigDecimal.valueOf(100)) < 0)) {
            taxaFrete = BigDecimal.valueOf(0.75);
        } else if (somaPeso.compareTo(BigDecimal.valueOf(100)) >= 0) {
            taxaFrete = BigDecimal.valueOf(1);
        }
        System.out.println("A soma do peso é"+somaPeso);
        System.out.println("A taxa do frete é"+taxaFrete);

        return taxaFrete;

    }

    public BigDecimal calcularFreteFinal(BigDecimal somaPeso, BigDecimal taxaFrete, BigDecimal taxaFreteAdicional) {
        BigDecimal freteFinalSemAdicional = somaPeso.multiply(taxaFrete);
        BigDecimal freteFinalComAdicional = aplicarAcrescimo(freteFinalSemAdicional, taxaFreteAdicional);
        return freteFinalComAdicional.setScale(2,RoundingMode.HALF_EVEN);
    }

    public BigDecimal aplicarDesconto(BigDecimal valor, BigDecimal desconto) {
        BigDecimal valorDescontado = valor.multiply(BigDecimal.ONE.subtract(desconto));
        return valorDescontado;
    }

    public BigDecimal aplicarAcrescimo(BigDecimal valor, BigDecimal acrescimo) {
        BigDecimal valorAcrescido = valor.multiply(acrescimo.add(BigDecimal.ONE));
        return valorAcrescido;
    }


}
