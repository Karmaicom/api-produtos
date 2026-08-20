package br.com.coti.apiprodutos.factories;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe para criar conexões com o banco de dados
 */
@Component
public class ConnectionFactory {

    // String de conexão com o endereço e o nome do banco de dados
    private final String connectionString = "jdbc:postgresql://localhost:5432/db-api-produtos";
    private final String user = "coti";
    private final String password = "Coti@2026";

    // Metodo para retornar uma conexao ativa com o banco de dados
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(connectionString,  user, password);
    }

}
