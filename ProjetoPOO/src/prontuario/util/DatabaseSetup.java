package prontuario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class DatabaseSetup {

   
    private DatabaseSetup() {
    }
    public static void verificarEInstanciarBanco() {
        System.out.println("Iniciando verificação do banco de dados...");

        String dbName = ConfigLoader.get("DBNAME");
        String dbUser = ConfigLoader.get("DBUSER");
        String dbPassword = ConfigLoader.get("DBPASSWORD");
        String dbAddress = ConfigLoader.get("DBADDRESS");
        String dbPort = ConfigLoader.get("DBPORT");

        if (dbName == null || dbUser == null || dbPassword == null || dbAddress == null || dbPort == null) {
            System.err.println("FATAL: As configurações do banco de dados não foram carregadas corretamente. Verifique o arquivo 'config.properties'.");
            return;
        }
        
        String urlServidor = "jdbc:mysql://" + dbAddress + ":" + dbPort + "/?useTimezone=true&serverTimezone=UTC";
        try (Connection conn = DriverManager.getConnection(urlServidor, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Banco de dados '" + dbName + "' verificado/criado com sucesso.");

        } catch (SQLException e) {
            System.err.println("ERRO CRÍTICO ao tentar criar o banco de dados: " + e.getMessage());
            e.printStackTrace();
            return; 
        }
        String urlBanco = "jdbc:mysql://" + dbAddress + ":" + dbPort + "/" + dbName + "?useTimezone=true&serverTimezone=UTC";

        
        String sqlTabelaPacientes = "CREATE TABLE IF NOT EXISTS PACIENTES ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "cpf VARCHAR(14) UNIQUE NOT NULL,"
                + "nome VARCHAR(255) NOT NULL,"
                + "data_nascimento DATE NOT NULL"
                + ")";

        String sqlTabelaExames = "CREATE TABLE IF NOT EXISTS EXAMES ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "descricao VARCHAR(255) NOT NULL,"
                + "data_exame DATE NOT NULL,"
                + "paciente_id BIGINT NOT NULL,"
                + "FOREIGN KEY (paciente_id) REFERENCES PACIENTES(id) ON DELETE CASCADE" // Adicionado ON DELETE CASCADE para integridade
                + ")";

        try (Connection conn = DriverManager.getConnection(urlBanco, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sqlTabelaPacientes);
            System.out.println("Tabela 'PACIENTES' verificada/criada com sucesso.");

            stmt.execute(sqlTabelaExames);
            System.out.println("Tabela 'EXAMES' verificada/criada com sucesso.");

        } catch (SQLException e) {
            System.err.println("ERRO CRÍTICO ao tentar criar as tabelas: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Setup do banco de dados finalizado com sucesso.");
    }
}