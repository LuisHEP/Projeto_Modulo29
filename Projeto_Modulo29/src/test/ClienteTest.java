package test;

import dao.ClienteDAO;
import dao.IClienteDAO;
import dao.IProdutoDAO;
import dao.ProdutoDAO;
import domain.Cliente;
import domain.Produto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void cadastrarClienteTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("01");
        cliente.setNome("Rodrigo Pires");

        Integer qtd = dao.cadastrar(cliente);
        assertTrue(qtd == 1);

        Cliente clienteBD = dao.consultar(cliente.getCodigo());
        assertNotNull(clienteBD);
        assertNotNull(clienteBD.getId());
        assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
        assertEquals(cliente.getNome(), clienteBD.getNome());

        Integer qtdDel = dao.excluir(clienteBD);
        assertNotNull(qtdDel);
    }

    @Test
    public void cadastrarProdutoTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("P001");
        produto.setNome("Produto A");
        produto.setPreco(50.0);

        Integer qtd = dao.cadastrar(produto);
        assertTrue(qtd == 1);

        Produto produtoBD = dao.consultar(produto.getCodigo());
        assertNotNull(produtoBD);
        assertNotNull(produtoBD.getId());
        assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        assertEquals(produto.getNome(), produtoBD.getNome());
        assertEquals(produto.getPreco(), produtoBD.getPreco(), 0.01); // Comparação de double com tolerância de 0.01

        Integer qtdDel = dao.excluir(produtoBD);
        assertNotNull(qtdDel);
    }

    @Test
    public void buscarTodosClientesTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();

        Cliente cliente1 = new Cliente();
        cliente1.setCodigo("01");
        cliente1.setNome("Rodrigo Pires");

        Cliente cliente2 = new Cliente();
        cliente2.setCodigo("02");
        cliente2.setNome("Ana Silva");

        dao.cadastrar(cliente1);
        dao.cadastrar(cliente2);

        List<Cliente> clientes = dao.buscarTodos();
        assertNotNull(clientes);
        assertTrue(clientes.size() >= 2);

        dao.excluir(cliente1);
        dao.excluir(cliente2);
    }

    @Test
    public void buscarTodosProdutosTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();

        Produto produto1 = new Produto();
        produto1.setCodigo("P001");
        produto1.setNome("Produto A");
        produto1.setPreco(50.0);

        Produto produto2 = new Produto();
        produto2.setCodigo("P002");
        produto2.setNome("Produto B");
        produto2.setPreco(100.0);

        dao.cadastrar(produto1);
        dao.cadastrar(produto2);

        List<Produto> produtos = dao.buscarTodos();
        assertNotNull(produtos);
        assertTrue(produtos.size() >= 2);

        dao.excluir(produto1);
        dao.excluir(produto2);
    }

    @Test
    public void alterarProdutoTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("P001");
        produto.setNome("Produto A");
        produto.setPreco(50.0);

        dao.cadastrar(produto);

        Produto produtoBD = dao.consultar(produto.getCodigo());
        assertNotNull(produtoBD);
        assertEquals("Produto A", produtoBD.getNome());
        assertEquals(50.0, produtoBD.getPreco(), 0.01);

        produtoBD.setNome("Produto A Alterado");
        produtoBD.setPreco(75.0);
        dao.alterar(produtoBD);

        Produto produtoBDAlterado = dao.consultar(produtoBD.getCodigo());
        assertNotNull(produtoBDAlterado);
        assertEquals("Produto A Alterado", produtoBDAlterado.getNome());
        assertEquals(75.0, produtoBDAlterado.getPreco(), 0.01);

        dao.excluir(produtoBDAlterado);
    }
}
