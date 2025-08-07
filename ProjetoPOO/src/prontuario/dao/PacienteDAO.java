package prontuario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prontuario.model.Paciente;

public class PacienteDAO {

    public void salvar(Paciente paciente) {
        String sql = "INSERT INTO PACIENTES (nome, cpf, data_nascimento) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getCpf());
            pstmt.setDate(3, java.sql.Date.valueOf(paciente.getDataNascimento()));

            pstmt.executeUpdate();
            System.out.println("Paciente '" + paciente.getNome() + "' salvo com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o paciente: " + e.getMessage());
            e.printStackTrace();
        }
    } 

    public List<Paciente> listarTodos() {
        String sql = "SELECT * FROM PACIENTES";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getLong("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return pacientes;
    }

    public void atualizar(Paciente paciente) {
        String sql = "UPDATE PACIENTES SET nome = ?, cpf = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getCpf());
            pstmt.setDate(3, java.sql.Date.valueOf(paciente.getDataNascimento()));
            pstmt.setLong(4, paciente.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(long id) {
        String sql = "DELETE FROM PACIENTES WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
    
    }

public List<Paciente> buscarPorNome(String nome) {
 String sql = "SELECT * FROM PACIENTES WHERE nome LIKE ?";
 List<Paciente> pacientes = new ArrayList<>();

 try (Connection conn = ConnectionFactory.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
     
     pstmt.setString(1, "%" + nome + "%");
     ResultSet rs = pstmt.executeQuery();

     while (rs.next()) {
         Paciente paciente = new Paciente();
         paciente.setId(rs.getLong("id"));
         paciente.setNome(rs.getString("nome"));
         paciente.setCpf(rs.getString("cpf"));
         paciente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
         pacientes.add(paciente);
     }
 } catch (SQLException e) {
     e.printStackTrace();
 }
 return pacientes;
}

public List<Paciente> buscarPorCpf(String cpf) {
 String sql = "SELECT * FROM PACIENTES WHERE cpf = ?";
 List<Paciente> pacientes = new ArrayList<>();

 try (Connection conn = ConnectionFactory.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
     
     pstmt.setString(1, cpf);
     ResultSet rs = pstmt.executeQuery();

     if (rs.next()) {
         Paciente paciente = new Paciente();
         paciente.setId(rs.getLong("id"));
         paciente.setNome(rs.getString("nome"));
         paciente.setCpf(rs.getString("cpf"));
         paciente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
         pacientes.add(paciente);
     }
 } catch (SQLException e) {
     e.printStackTrace();
 }
 return pacientes;
	}
}   
