package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;
    //Armazena a conexão com o banco de dados. É estática para que seja compartilhada entre todas as instâncias da classe.

    private ConnectionFactory(Connection connection) {
//a princípio, o construtor não está sendo usado em lugar nenhum do código
//O construtor é privado para garantir que somente a própria classe ConnectionFactory possa criar instâncias dela mesma
//assim, outras partes do código não poderão criar novas instâncias da ConnectionFactory diretamente
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = initConnection();
            return connection;
//Se connection for null (inexistente), chama initConnection() para criar uma nova conexão e a retorna.
        } else if (connection.isClosed()) {
            connection = initConnection();
            return connection;
//Se a conexão existente estiver fechada (connection.isClosed()), ele chama initConnection() para criar uma nova conexão e a retorna.
        } else {
            return connection;
//Se a conexão existente não for nula e não estiver fechada, ele simplesmente retorna a conexão existente.
        }

    }

    private static Connection initConnection() { //método q estabelece uma conexão com o banco de dados
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/vendas_online_2", "postgres", "Nascimento");
            //getConnection é um método do DriverManager
            //getConnection é usado para estabelecer uma conexão com um banco de dados
            //Ele aceita parâmetros como a URL do banco de dados, o nome de usuário e a senha, e retorna um objeto Connection que representa uma conexão ativa com o banco de dados especificado.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Se ocorrer alguma exceção no bloco try, o catch e éxecutado

        //'SQLException e' captura uma exceção genérica "e" >> exemplo, se o banco de dados estiver inacessível ou as credenciais estiverem incorretas

        //throw new RuntimeException(e) permite que a exceção seja propagada para o nível superior do código
    }

}


