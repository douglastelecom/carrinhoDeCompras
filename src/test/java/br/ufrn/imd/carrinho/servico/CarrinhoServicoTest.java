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
    @CsvSource({"10, 500, RO, false, 455.5"})
    public void test(BigDecimal peso, BigDecimal preco, Estado estado, Boolean possuiIguais, BigDecimal valorFinal) {

        Item item1 = Mockito.mock(Item.class);
        Item item2 = Mockito.mock(Item.class);
        Item item3 = Mockito.mock(Item.class);
        Usuario usuario = Mockito.mock(Usuario.class);

        when(item1.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));
        when(item2.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));
        when(item3.getPeso()).thenReturn(peso.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));

        when(item1.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));
        when(item2.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));
        when(item3.getPreco()).thenReturn(preco.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_EVEN));

        when(usuario.getEndereco().getEstado()).thenReturn(estado);

        if(possuiIguais){
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
        }
        else{
            when(item1.getTipo()).thenReturn(ItemTipo.COZINHA);
            when(item1.getTipo()).thenReturn(ItemTipo.CASA);
            when(item1.getTipo()).thenReturn(ItemTipo.LIVROS);
        }
        LocalDate checkoutDate = LocalDate.now();

        CarrinhoServico carrinhoServico = new CarrinhoServico();

        List<Item> itens = Arrays.asList(item1, item2, item3);
        Pedido pedido = carrinhoServico.finalizar(usuario, itens, checkoutDate);
        assertEquals(BigDecimal.valueOf(455.5).subtract(pedido.getPrecoTotal()).compareTo(BigDecimal.valueOf(0.01))<=0, true);
    }
}
