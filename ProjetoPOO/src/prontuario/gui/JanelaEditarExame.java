package prontuario.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prontuario.dao.ExameDAO;
import prontuario.dao.PacienteDAO;
import prontuario.model.Exame;
import prontuario.model.Paciente;

public class JanelaEditarExame extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTable tabelaExames;
    private DefaultTableModel modeloTabela;
    private JComboBox<Paciente> comboPacientes;
    private JTextField txtDataExame;
    private JTextArea areaDescricao;
    private JButton btnAtualizar;
    
    private PacienteDAO pacienteDAO;
    private ExameDAO exameDAO;
    private long idExameSelecionado;

    public JanelaEditarExame(JFrame owner) {
        super(owner, "Editar Exame", true);

        this.pacienteDAO = new PacienteDAO();
        this.exameDAO = new ExameDAO();
        
        setSize(700, 500);
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
        
        JScrollPane scrollPaneTabela = new JScrollPane(tabelaExames);
        scrollPaneTabela.setBounds(20, 20, 640, 180);
        add(scrollPaneTabela);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 220, 80, 25);
        add(lblPaciente);

        comboPacientes = new JComboBox<>();
        comboPacientes.setBounds(150, 220, 510, 25);
        add(comboPacientes);

        JLabel lblDataExame = new JLabel("Data do Exame:");
        lblDataExame.setBounds(20, 260, 120, 25);
        add(lblDataExame);

        txtDataExame = new JTextField();
        txtDataExame.setBounds(150, 260, 510, 25);
        add(txtDataExame);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20, 300, 80, 25);
        add(lblDescricao);

        areaDescricao = new JTextArea();
        JScrollPane scrollPaneDescricao = new JScrollPane(areaDescricao);
        scrollPaneDescricao.setBounds(150, 300, 510, 80);
        add(scrollPaneDescricao);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(290, 400, 100, 30);
        add(btnAtualizar);

        carregarPacientesCombo();
        carregarDadosTabela();

        tabelaExames.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabelaExames.getSelectedRow();
            if (selectedRow != -1) {
                idExameSelecionado = (long) modeloTabela.getValueAt(selectedRow, 0);
                
                Paciente pacienteDoExame = (Paciente) modeloTabela.getValueAt(selectedRow, 1);
                
                for (int i = 0; i < comboPacientes.getItemCount(); i++) {
                    if (comboPacientes.getItemAt(i).getId() == pacienteDoExame.getId()) {
                        comboPacientes.setSelectedIndex(i);
                        break;
                    }
                }
                
                LocalDate data = (LocalDate) modeloTabela.getValueAt(selectedRow, 2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtDataExame.setText(data.format(formatter));
                
                areaDescricao.setText((String) modeloTabela.getValueAt(selectedRow, 3));
            }
        });

        btnAtualizar.addActionListener(e -> {
            if (idExameSelecionado == 0) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um exame na tabela.", "Nenhum Exame Selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Exame exame = new Exame();
                exame.setId(idExameSelecionado);
                exame.setPaciente((Paciente) comboPacientes.getSelectedItem());
                exame.setDescricao(areaDescricao.getText());
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                exame.setDataExame(LocalDate.parse(txtDataExame.getText(), formatter));
                
                exameDAO.atualizar(exame);
                
                JOptionPane.showMessageDialog(this, "Exame atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                carregarDadosTabela();
                
                txtDataExame.setText("");
                areaDescricao.setText("");
                comboPacientes.setSelectedIndex(-1);
                idExameSelecionado = 0;

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void carregarPacientesCombo() {
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for (Paciente p : pacientes) {
            comboPacientes.addItem(p);
        }
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