package br.ufrn.imd.carrinho.servico;

import br.ufrn.imd.carrinho.dominio.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarrinhoServicoTest {


//    @Mock
//    private Item item1;
//
//    @Mock
//    private Item item2;
//
//    @Mock
//    private Item item3;


    @ParameterizedTest
    @CsvSource({
            "0,0.00,AC,SIM,0.00",
            "0,0.00,RS,NÃO,0.00",
            "0,500.00,MS,SIM,450.00",
            "0,500.00,AC,NÃO,450.00",
            "0,1000.00,RS,SIM,800.00",
            "0,1000.00,MS,NÃO,800.00",
            "10,0.00,AC,SIM,5.50",
            "10,0.00,RS,NÃO,5.50",
            "10,500.00,MS,SIM,455.50",
            "10,500.00,AC,NÃO,455.50",
            "10,1000.00,RS,SIM,805.50",
            "10,1000.00,MS,NÃO,805.50",
            "40,0.00,AC,SIM,33.00",
            "40,0.00,RS,NÃO,33.00",
            "40,500.00,MS,SIM,483.00",
            "40,500.00,AC,NÃO,483.00",
            "40,1000.00,RS,SIM,833.00",
            "40,1000.00,MS,NÃO,833.00",
            "100,0.00,AC,SIM,110.00",
            "100,0.00,RS,NÃO,110.00",
            "100,500.00,MS,SIM,560.00",
            "100,500.00,AC,NÃO,560.00",
            "100,1000.00,RS,SIM,910.00",
            "100,1000.00,MS,NÃO,910.00",
            "0,0.00,SP,SIM,0.00",
            "0,0.00,RN,NÃO,0.00",
            "0,500.00,SP,SIM,450.00",
            "0,500.00,RN,NÃO,450.00",
            "0,1000.00,SP,SIM,800.00",
            "0,1000.00,RN,NÃO,800.00",
            "10,0.00,SP,SIM,5.00",
            "10,0.00,RN,NÃO,5.00",
            "10,500.00,SP,SIM,455.00",
            "10,500.00,RN,NÃO,455.00",
            "10,1000.00,SP,SIM,805.00",
            "10,1000.00,RN,NÃO,805.00",
            "40,0.00,SP,SIM,30.00",
            "40,0.00,RN,NÃO,30.00",
            "40,500.00,SP,SIM,480.00",
            "40,500.00,RN,NÃO,480.00",
            "40,1000.00,SP,SIM,830.00",
            "40,1000.00,RN,NÃO,830.00",
            "100,0.00,SP,SIM,100.00",
            "100,0.00,RN,NÃO,100.00",
            "100,500.00,SP,SIM,550.00",
            "100,500.00,RN,NÃO,550.00",
            "100,1000.00,SP,SIM,900.00",
            "100,1000.00,RN,NÃO,900.00"
    })
    public void test(BigDecimal peso, BigDecimal preco, Estado estado, Boolean possuiIguais, BigDecimal valorFinal) {

        Item item1 = Mockito.mock(Item.class);
        Item item2 = Mockito.mock(Item.class);
        Item item3 = Mockito.mock(Item.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Endereco endereco = Mockito.mock(Endereco.class);

        when(item1.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item2.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item3.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));

        when(item1.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item2.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item3.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));

        when(usuario.getEndereco()).thenReturn(endereco);
        when(endereco.getEstado()).thenReturn(estado);

        if (possuiIguais) {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item3.getTipo()).thenReturn(ItemTipo.COZINHA);
        } else {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.CASA);
            when(item3.getTipo()).thenReturn(ItemTipo.LIVROS);
        }
        LocalDate checkoutDate = LocalDate.now();

        CarrinhoServico carrinhoServico = new CarrinhoServico();

        List<Item> itens = Arrays.asList(item1, item2, item3);
        Pedido pedido = carrinhoServico.finalizar(usuario, itens, checkoutDate);
        System.out.println(pedido.getPrecoTotal());
        assertEquals(valorFinal.subtract(pedido.getPrecoTotal()).abs().compareTo(BigDecimal.valueOf(0.01)) < 0, true);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0.00,AC,SIM,0.00",
            "0,0.00,RS,NÃO,0.00",
            "0,500.00,MS,SIM,450.00",
            "0,500.00,AC,NÃO,450.00",
            "0,1000.00,RS,SIM,800.00",
            "0,1000.00,MS,NÃO,800.00",
            "10,0.00,AC,SIM,5.50",
            "10,0.00,RS,NÃO,5.50",
            "10,500.00,MS,SIM,455.50",
            "10,500.00,AC,NÃO,455.50",
            "10,1000.00,RS,SIM,805.50",
            "10,1000.00,MS,NÃO,805.50",
            "40,0.00,AC,SIM,33.00",
            "40,0.00,RS,NÃO,33.00",
            "40,500.00,MS,SIM,483.00",
            "40,500.00,AC,NÃO,483.00",
            "40,1000.00,RS,SIM,833.00",
            "40,1000.00,MS,NÃO,833.00",
            "100,0.00,AC,SIM,110.00",
            "100,0.00,RS,NÃO,110.00",
            "100,500.00,MS,SIM,560.00",
            "100,500.00,AC,NÃO,560.00",
            "100,1000.00,RS,SIM,910.00",
            "100,1000.00,MS,NÃO,910.00",
            "0,0.00,SP,SIM,0.00",
            "0,0.00,RN,NÃO,0.00",
            "0,500.00,SP,SIM,450.00",
            "0,500.00,RN,NÃO,450.00",
            "0,1000.00,SP,SIM,800.00",
            "0,1000.00,RN,NÃO,800.00",
            "10,0.00,SP,SIM,5.00",
            "10,0.00,RN,NÃO,5.00",
            "10,500.00,SP,SIM,455.00",
            "10,500.00,RN,NÃO,455.00",
            "10,1000.00,SP,SIM,805.00",
            "10,1000.00,RN,NÃO,805.00",
            "40,0.00,SP,SIM,30.00",
            "40,0.00,RN,NÃO,30.00",
            "40,500.00,SP,SIM,480.00",
            "40,500.00,RN,NÃO,480.00",
            "40,1000.00,SP,SIM,830.00",
            "40,1000.00,RN,NÃO,830.00",
            "100,0.00,SP,SIM,100.00",
            "100,0.00,RN,NÃO,100.00",
            "100,500.00,SP,SIM,550.00",
            "100,500.00,RN,NÃO,550.00",
            "100,1000.00,SP,SIM,900.00",
            "100,1000.00,RN,NÃO,900.00"
    })
    public void testLimite(BigDecimal peso, BigDecimal preco, Estado estado, Boolean possuiIguais, BigDecimal valorFinal) {

        Item item1 = Mockito.mock(Item.class);
        Item item2 = Mockito.mock(Item.class);
        Item item3 = Mockito.mock(Item.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Endereco endereco = Mockito.mock(Endereco.class);

        when(item1.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item2.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item3.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));

        when(item1.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item2.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item3.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));

        when(usuario.getEndereco()).thenReturn(endereco);
        when(endereco.getEstado()).thenReturn(estado);

        if (possuiIguais) {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item3.getTipo()).thenReturn(ItemTipo.COZINHA);
        } else {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.CASA);
            when(item3.getTipo()).thenReturn(ItemTipo.LIVROS);
        }
        LocalDate checkoutDate = LocalDate.now();

        CarrinhoServico carrinhoServico = new CarrinhoServico();

        List<Item> itens = Arrays.asList(item1, item2, item3);
        Pedido pedido = carrinhoServico.finalizar(usuario, itens, checkoutDate);
        System.out.println(pedido.getPrecoTotal());
        assertEquals(valorFinal.subtract(pedido.getPrecoTotal()).abs().compareTo(BigDecimal.valueOf(0.01)) < 0, true);
    }

    @ParameterizedTest
    @CsvSource({
            "5,250.00,AC,true,237.50",
            "5,250.00,RS,false,250.00",
            "5,750.00,MS,true,675.00",
            "5,750.00,AC,false,675.00",
            "5,1500.00,RS,true,1200.00",
            "5,1500.00,MS,false,1200.00",
            "15,250.00,AC,true,245.75",
            "15,250.00,RS,false,258.25",
            "15,750.00,MS,true,683.25",
            "15,750.00,AC,false,683.25",
            "15,1500.00,RS,true,1208.25",
            "15,1500.00,MS,false,1208.25",
            "70,250.00,AC,true,295.25",
            "70,250.00,RS,false,307.75",
            "70,750.00,MS,true,732.75",
            "70,750.00,AC,false,732.75",
            "70,1500.00,RS,true,1257.75",
            "70,1500.00,MS,false,1257.75",
            "150,250.00,AC,true,402.50",
            "150,250.00,RS,false,415.00",
            "150,750.00,MS,true,840.00",
            "150,750.00,AC,false,840.00",
            "150,1500.00,RS,true,1365.00",
            "150,1500.00,MS,false,1365.00",
            "5,250.00,SP,true,237.50",
            "5,250.00,RN,false,250.00",
            "5,750.00,SP,true,675.00",
            "5,750.00,RN,false,675.00",
            "5,1500.00,SP,true,1200.00",
            "5,1500.00,RN,false,1200.00",
            "15,250.00,SP,true,245.00",
            "15,250.00,RN,false,257.50",
            "15,750.00,SP,true,682.50",
            "15,750.00,RN,false,682.50",
            "15,1500.00,SP,true,1207.50",
            "15,1500.00,RN,false,1207.50",
            "70,250.00,SP,true,290.00",
            "70,250.00,RN,false,302.50",
            "70,750.00,SP,true,727.50",
            "70,750.00,RN,false,727.50",
            "70,1500.00,SP,true,1252.50",
            "70,1500.00,RN,false,1252.50",
            "150,250.00,SP,true,387.50",
            "150,250.00,RN,false,400.00",
            "150,750.00,SP,true,825.00",
            "150,750.00,RN,false,825.00",
            "150,1500.00,SP,true,1350.00",
            "150,1500.00,RN,false,1350.00"

    })
    public void testMedio(BigDecimal peso, BigDecimal preco, Estado estado, Boolean possuiIguais, BigDecimal valorFinal) {

        Item item1 = Mockito.mock(Item.class);
        Item item2 = Mockito.mock(Item.class);
        Item item3 = Mockito.mock(Item.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Endereco endereco = Mockito.mock(Endereco.class);

        when(item1.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item2.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));
        when(item3.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_EVEN));

        when(item1.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item2.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));
        when(item3.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_EVEN));

        when(usuario.getEndereco()).thenReturn(endereco);
        when(endereco.getEstado()).thenReturn(estado);

        if (possuiIguais) {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item3.getTipo()).thenReturn(ItemTipo.COZINHA);
        } else {
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item2.getTipo()).thenReturn(ItemTipo.CASA);
            when(item3.getTipo()).thenReturn(ItemTipo.LIVROS);
        }
        LocalDate checkoutDate = LocalDate.now();

        CarrinhoServico carrinhoServico = new CarrinhoServico();

        List<Item> itens = Arrays.asList(item1, item2, item3);
        Pedido pedido = carrinhoServico.finalizar(usuario, itens, checkoutDate);
        System.out.println(pedido.getPrecoTotal());
        assertEquals(valorFinal.subtract(pedido.getPrecoTotal()).abs().compareTo(BigDecimal.valueOf(0.01)) < 0, true);
        
    }
}
