package prontuario.gui;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.PacienteDAO;
import prontuario.model.Paciente;

public class JanelaEditarPaciente extends JDialog {

    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;
    private JButton btnDeletar;
    private JButton btnSair;
    private PacienteDAO pacienteDAO;
    
    public JanelaEditarPaciente(JFrame owner) {
        super(owner, "Deletar Paciente", true);
        
        this.pacienteDAO = new PacienteDAO();
        
        setSize(500, 300);
        setLayout(null);
        setLocationRelativeTo(owner);

        String[] colunas = {"ID", "Nome", "CPF", "Data Nasc."};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaPacientes = new JTable(modeloTabela);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
        scrollPane.setBounds(20, 20, 440, 180);
        add(scrollPane);
        
        btnDeletar = new JButton("Deletar Selecionado");
        btnDeletar.setBounds(150, 220, 180, 30);
        add(btnDeletar);
        
        btnSair = new JButton("Sair");
        btnSair.setBounds(340, 220, 80, 30);
        add(btnSair);
        btnSair.addActionListener(e -> dispose());
        
        carregarDadosTabela();

        btnDeletar.addActionListener(e -> {
            int selectedRow = tabelaPacientes.getSelectedRow();
            
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um paciente na tabela para deletar.", "Nenhum Paciente Selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            long idParaDeletar = (long) modeloTabela.getValueAt(selectedRow, 0);
            String nomePaciente = (String) modeloTabela.getValueAt(selectedRow, 1);
            
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja deletar o paciente: " + nomePaciente + "?",
                    "Confirmação de Deleção",
                    JOptionPane.YES_NO_OPTION
            );
            
            if (resposta == JOptionPane.YES_OPTION) {
                pacienteDAO.deletar(idParaDeletar);
                JOptionPane.showMessageDialog(this, "Paciente deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDadosTabela();             }
        });
    }

    private void carregarDadosTabela() {
        modeloTabela.setRowCount(0);
        List<Paciente> pacientes = pacienteDAO.listarTodos();

        for (Paciente p : pacientes) {
            Object[] rowData = {
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getDataNascimento()
            };
            modeloTabela.addRow(rowData);
        }
    }
}