package prontuario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prontuario.model.Exame;
import prontuario.model.Paciente;


public class ExameDAO {

    
    public void salvar(Exame exame) {
        String sql = "INSERT INTO EXAMES (descricao, data_exame, paciente_id) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, exame.getDescricao());
            pstmt.setDate(2, java.sql.Date.valueOf(exame.getDataExame()));
            pstmt.setLong(3, exame.getPaciente().getId());

            pstmt.executeUpdate();
            System.out.println("Exame '" + exame.getDescricao() + "' salvo com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o exame: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
    public void atualizar(Exame exame) {
        String sql = "UPDATE EXAMES SET descricao = ?, data_exame = ?, paciente_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, exame.getDescricao());
            pstmt.setDate(2, java.sql.Date.valueOf(exame.getDataExame()));
            pstmt.setLong(3, exame.getPaciente().getId());
            pstmt.setLong(4, exame.getId());

            pstmt.executeUpdate();
            System.out.println("Exame com id=" + exame.getId() + " atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o exame: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    public void deletar(long id) {
        String sql = "DELETE FROM EXAMES WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Exame com id=" + id + " deletado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar o exame: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    public List<Exame> listarTodos() {
        String sql = "SELECT e.*, p.nome as nome_paciente, p.cpf as cpf_paciente "
                   + "FROM EXAMES e JOIN PACIENTES p ON e.paciente_id = p.id";
        
        List<Exame> exames = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getLong("paciente_id"));                  
                paciente.setNome(rs.getString("nome_paciente"));
                paciente.setCpf(rs.getString("cpf_paciente"));
                
                Exame exame = new Exame();
                exame.setId(rs.getLong("id")); 
                exame.setDescricao(rs.getString("descricao"));
                exame.setDataExame(rs.getDate("data_exame").toLocalDate());
                
                exame.setPaciente(paciente);
                
                exames.add(exame);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os exames: " + e.getMessage());
            e.printStackTrace();
        }
        return exames;
    }
 
    public List<Exame> buscarPorNomePaciente(String nomePaciente) {
        String sql = "SELECT e.*, p.nome as nome_paciente, p.cpf as cpf_paciente "
                   + "FROM EXAMES e JOIN PACIENTES p ON e.paciente_id = p.id "
                   + "WHERE p.nome LIKE ?";
        
        List<Exame> exames = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nomePaciente + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getLong("paciente_id"));
                paciente.setNome(rs.getString("nome_paciente"));
                paciente.setCpf(rs.getString("cpf_paciente"));
                
                Exame exame = new Exame();
                exame.setId(rs.getLong("id"));
                exame.setDescricao(rs.getString("descricao"));
                exame.setDataExame(rs.getDate("data_exame").toLocalDate());
                exame.setPaciente(paciente);
                
                exames.add(exame);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exames;
    }
}