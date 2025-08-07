package prontuario.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.PacienteDAO;
import prontuario.model.Paciente;

public class JanelaEditarPaciente extends JDialog {

    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JButton btnAtualizar;
    private PacienteDAO pacienteDAO;
    private long idPacienteSelecionado;

    public JanelaEditarPaciente(JFrame owner) {
        super(owner, "Editar Paciente", true);
        
        this.pacienteDAO = new PacienteDAO();
        
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(owner);

        String[] colunas = {"ID", "Nome", "CPF", "Data Nasc."};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaPacientes = new JTable(modeloTabela);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
        scrollPane.setBounds(20, 20, 540, 150);
        add(scrollPane);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 190, 80, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 190, 410, 25);
        add(txtNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(20, 230, 80, 25);
        add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(150, 230, 410, 25);
        add(txtCpf);
        
        JLabel lblDataNascimento = new JLabel("Data Nascimento:");
        lblDataNascimento.setBounds(20, 270, 120, 25);
        add(lblDataNascimento);

        txtDataNascimento = new JTextField();
        txtDataNascimento.setBounds(150, 270, 410, 25);
        add(txtDataNascimento);
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(240, 320, 100, 30);
        add(btnAtualizar);
        
        carregarDadosTabela();

        tabelaPacientes.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabelaPacientes.getSelectedRow();
            if (selectedRow != -1) {
                idPacienteSelecionado = (long) modeloTabela.getValueAt(selectedRow, 0);
                txtNome.setText((String) modeloTabela.getValueAt(selectedRow, 1));
                txtCpf.setText((String) modeloTabela.getValueAt(selectedRow, 2));
                
                LocalDate data = (LocalDate) modeloTabela.getValueAt(selectedRow, 3);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtDataNascimento.setText(data.format(formatter));
            }
        });

        btnAtualizar.addActionListener(e -> {
            if (idPacienteSelecionado == 0) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um paciente na tabela.", "Nenhum Paciente Selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Paciente paciente = new Paciente();
                paciente.setId(idPacienteSelecionado);
                paciente.setNome(txtNome.getText());
                paciente.setCpf(txtCpf.getText());
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                paciente.setDataNascimento(LocalDate.parse(txtDataNascimento.getText(), formatter));
                
                pacienteDAO.atualizar(paciente);
                
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                carregarDadosTabela();
                
                txtNome.setText("");
                txtCpf.setText("");
                txtDataNascimento.setText("");
                idPacienteSelecionado = 0;

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void carregarDadosTabela() {
        modeloTabela.setRowCount(0);
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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