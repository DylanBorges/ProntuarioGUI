package prontuario.gui;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.ExameDAO;
import prontuario.model.Exame;
import prontuario.model.Paciente;

public class JanelaDeletarExame extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTable tabelaExames;
    private DefaultTableModel modeloTabela;
    private JButton btnDeletar;
    private ExameDAO exameDAO;
    
    public JanelaDeletarExame(JFrame owner) {
        super(owner, "Deletar Exame", true);
        
        this.exameDAO = new ExameDAO();
        
        setSize(500, 300);
        setLayout(null);
        setLocationRelativeTo(owner);

        String[] colunas = {"ID Exame", "Paciente", "Data", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaExames = new JTable(modeloTabela);
        
        JScrollPane scrollPane = new JScrollPane(tabelaExames);
        scrollPane.setBounds(20, 20, 440, 180);
        add(scrollPane);
        
        btnDeletar = new JButton("Deletar Selecionado");
        btnDeletar.setBounds(150, 220, 180, 30);
        add(btnDeletar);
        
        carregarDadosTabela();

        btnDeletar.addActionListener(e -> {
            int selectedRow = tabelaExames.getSelectedRow();
            
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um exame na tabela para deletar.", "Nenhum Exame Selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            long idParaDeletar = (long) modeloTabela.getValueAt(selectedRow, 0);
            String nomePaciente = modeloTabela.getValueAt(selectedRow, 1).toString();
            
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja deletar o exame do paciente: " + nomePaciente + "?",
                    "Confirmação de Deleção",
                    JOptionPane.YES_NO_OPTION
            );
            
            if (resposta == JOptionPane.YES_OPTION) {
                exameDAO.deletar(idParaDeletar);
                JOptionPane.showMessageDialog(this, "Exame deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDadosTabela();
            }
        });
    }

    private void carregarDadosTabela() {
        modeloTabela.setRowCount(0);
        List<Exame> exames = exameDAO.listarTodos();

        for (Exame ex : exames) {
            Object[] rowData = {
                ex.getId(),
                ex.getPaciente(),
                ex.getDataExame(),
                ex.getDescricao()
            };
            modeloTabela.addRow(rowData);
        }
    }
}