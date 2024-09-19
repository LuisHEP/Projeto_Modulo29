package dao;

import domain.Cliente;
import jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO {

    @Override
    public Integer cadastrar(Cliente cliente) throws SQLException {
        Connection connection = null;
        //é uma interface da biblioteca JDBC, será utilizada para estabelecer a CONEXÃO COM O BANCO DE DADOS
        PreparedStatement stm = null;
        //PreparedStatement é uma interface na biblioteca JDBC Usada para executar comandos SQL pré-compilados no banco de dados.
        //Declara uma variável stm do tipo PreparedStatement q será utilizada para preparar e EXECUTAR INSTRUÇÕES SQL no banco de dados.

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "INSERT INTO TB_CLIENTE_2 (ID, CODIGO, NOME) VALUES (nextval('SQ_CLIENTE_2'),?,?)"; //comando SQL
            stm = connection.prepareStatement(sql); //Prepara o comando SQL para ser executado no banco de dados.
            stm.setString(1, cliente.getCodigo()); //setString é um método usado com PreparedStatement para definir valores de parâmetros na instrução SQL.
            stm.setString(2, cliente.getNome());
            //Define o valor de um parâmetro ? na instrução SQL com um valor de texto (String).
            //get pega os valores de nome e codigo e setString os coloca nos "?" da instrução SQL
            return stm.executeUpdate();  //executa e realiza a alteração no banco de dados conforme a instrução SQL preparada e retorna quantas linhas foram modificadas.
        } catch (Exception e) {
            throw e;
            //Exception é uma classe Java que representa um tipo de erro / é usada para capturar e lidar com erros que podem ocorrer durante a execução do código
// A palavra-chave throw em Java é usada para lançar explicitamente uma exceção e passa-la para outro código que saiba como lidar com essa situação.
            //"e" >> é apenas o nome da exceção genérica
// O throw e; indica que queremos lançar novamente a exceção para o método ou bloco de código que chamou o método atual.
        } finally {
            if (stm != null && !stm.isClosed()) {
                stm.close(); //comando que fecha o stm
                //stm É um objeto do tipo PreparedStatement, que representa uma instrução SQL pré-compilada que pode ser executada no banco de dados.
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();  //connection: Representa a conexão com o banco de dados.
                //fecha a conexão com o banco de dados
            }
        }

    }

    @Override
    public Cliente consultar(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
//ResultSet é uma interface em Java usada para armazenar e manipular os resultados de uma consulta a um banco de dados.
        Cliente cliente = null;

        try {
            connection = ConnectionFactory.getConnection();  // ConnectionFactory.getConnection(): Método que obtém ou cria uma conexão com o banco de dados.
            // connection é a variável que irá armazenar a conexão com o banco de dados.
            String sql = "select * from tb_cliente_2 where codigo = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, codigo);
            rs = stm.executeQuery();
            // enquanto executeUpdate() é apenas para modificar dados (INSERT, UPDATE, DELETE)
            // executeQuery() é Usado para consultar dados (com SELECT)-> executeQuery() sempre retorna um obj ResultSet

            if (rs.next()) {  //O comando next() em ResultSet move o cursor para a próxima linha de resultados. Ele retorna true se houver uma próxima linha e false se não houver.
                cliente = new Cliente(); // Se rs.next() retornar true, significa que há dados. Um novo objeto Cliente é criado para armazenar esses dados.
                cliente.setId(rs.getLong("id"));  // Obtém o valor da coluna "id" da linha atual do ResultSet e define esse valor no objeto Cliente.
                cliente.setCodigo(rs.getString("codigo"));
                cliente.setNome(rs.getString("nome"));

            }
            return cliente;
        } catch (Exception e) {
            throw e;
        } finally {
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "DELETE FROM TB_CLIENTE_2 WHERE CODIGO = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, cliente.getCodigo());
            return stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM tb_cliente_2";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setCodigo(rs.getString("codigo"));
                cliente.setNome(rs.getString("nome"));
                clientes.add(cliente);
            }
            return clientes;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Integer alterar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "UPDATE tb_cliente_2 SET nome = ? WHERE codigo = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getCodigo());
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
