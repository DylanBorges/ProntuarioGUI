package prontuario.gui;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.ExameDAO;
import prontuario.model.Exame;
import prontuario.model.Paciente;

public class JanelaLocalizarExame extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtBusca;
    private JButton btnPesquisar;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private ExameDAO exameDAO;

    public JanelaLocalizarExame(JFrame owner) {
        super(owner, "Localizar Exame", true);

        this.exameDAO = new ExameDAO();
        
        setSize(500, 380);
        setLayout(null);
        setLocationRelativeTo(owner);
        
        JLabel lblTermo = new JLabel("Nome do Paciente:");
        lblTermo.setBounds(20, 20, 120, 25);
        add(lblTermo);
        
        txtBusca = new JTextField();
        txtBusca.setBounds(150, 20, 210, 25);
        add(txtBusca);
        
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(370, 20, 100, 25);
        add(btnPesquisar);
        
        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(370, 300, 100, 30); 
        add(btnSair);

        btnSair.addActionListener(e -> dispose());

        
        String[] colunas = {"ID Exame", "Paciente", "Data", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaResultados = new JTable(modeloTabela);
        
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setBounds(20, 60, 450, 230);
        add(scrollPane);
        
        btnPesquisar.addActionListener(e -> {
            String termoBusca = txtBusca.getText();
            List<Exame> resultado = exameDAO.buscarPorNomePaciente(termoBusca);
            atualizarTabela(resultado);
        });
    }
    
    private void atualizarTabela(List<Exame> exames) {
        modeloTabela.setRowCount(0);

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