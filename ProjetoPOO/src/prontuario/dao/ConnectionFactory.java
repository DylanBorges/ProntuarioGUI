package prontuario.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import prontuario.util.ConfigLoader; 

public class ConnectionFactory {

    
    public static Connection getConnection() {
        String dbName = ConfigLoader.get("DBNAME");
        String dbUser = ConfigLoader.get("DBUSER");
        String dbPassword = ConfigLoader.get("DBPASSWORD");
        String dbAddress = ConfigLoader.get("DBADDRESS");
        String dbPort = ConfigLoader.get("DBPORT");

                String url = "jdbc:mysql://" + dbAddress + ":" + dbPort + "/" + dbName + "?useTimezone=true&serverTimezone=UTC";

        try {
           
            return DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
           
            return null;
        }
    }
    
}