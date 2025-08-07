package prontuario.gui;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.PacienteDAO;
import prontuario.model.Paciente;

public class JanelaLocalizarPaciente extends JDialog {

    private JRadioButton radioNome;
    private JRadioButton radioCpf;
    private ButtonGroup grupoRadio;
    private JTextField txtBusca;
    private JButton btnPesquisar;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private PacienteDAO pacienteDAO;

    public JanelaLocalizarPaciente(JFrame owner) {
        super(owner, "Localizar Paciente", true);

        this.pacienteDAO = new PacienteDAO();
        
        setSize(500, 350);
        setLayout(null);
        setLocationRelativeTo(owner);
        
        radioNome = new JRadioButton("Buscar por Nome", true);
        radioNome.setBounds(20, 20, 150, 25);
        add(radioNome);
        
        radioCpf = new JRadioButton("Buscar por CPF");
        radioCpf.setBounds(180, 20, 150, 25);
        add(radioCpf);
        
        grupoRadio = new ButtonGroup();
        grupoRadio.add(radioNome);
        grupoRadio.add(radioCpf);
        
        txtBusca = new JTextField();
        txtBusca.setBounds(20, 50, 340, 25);
        add(txtBusca);
        
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(370, 50, 100, 25);
        add(btnPesquisar);
        
        String[] colunas = {"ID", "Nome", "CPF", "Data Nasc."};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaResultados = new JTable(modeloTabela);
        
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setBounds(20, 90, 450, 200);
        add(scrollPane);
        
        btnPesquisar.addActionListener(e -> {
            String termoBusca = txtBusca.getText();
            List<Paciente> resultado;

            if (radioNome.isSelected()) {
                resultado = pacienteDAO.buscarPorNome(termoBusca);
            } else {
                resultado = pacienteDAO.buscarPorCpf(termoBusca);
            }
            
            atualizarTabela(resultado);
        });
    }
    
    private void atualizarTabela(List<Paciente> pacientes) {
        modeloTabela.setRowCount(0);

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