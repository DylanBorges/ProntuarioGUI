package prontuario.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import prontuario.dao.ExameDAO;
import prontuario.dao.PacienteDAO;
import prontuario.model.Exame;
import prontuario.model.Paciente;

public class JanelaNovoExame extends JDialog {

    private JComboBox<Paciente> comboPacientes;
    private JTextField txtDataExame;
    private JTextArea areaDescricao;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    private PacienteDAO pacienteDAO;
    private ExameDAO exameDAO;

    public JanelaNovoExame(JFrame owner) {
        super(owner, "Novo Exame", true);

        this.pacienteDAO = new PacienteDAO();
        this.exameDAO = new ExameDAO();

        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(owner);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 25);
        add(lblPaciente);

        comboPacientes = new JComboBox<>();
        comboPacientes.setBounds(150, 20, 250, 25);
        add(comboPacientes);

        JLabel lblDataExame = new JLabel("Data do Exame:");
        lblDataExame.setBounds(20, 60, 120, 25);
        add(lblDataExame);

        txtDataExame = new JTextField();
        txtDataExame.setBounds(150, 60, 250, 25);
        add(txtDataExame);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20, 100, 80, 25);
        add(lblDescricao);

        areaDescricao = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaDescricao);
        scrollPane.setBounds(150, 100, 250, 100);
        add(scrollPane);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(70, 250, 80, 30);
        add(btnSalvar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(180, 250, 80, 30);
        add(btnLimpar);

        btnSair = new JButton("Sair");
        btnSair.setBounds(290, 250, 80, 30);
        add(btnSair);

        carregarPacientes();

        btnSair.addActionListener(e -> dispose());

        btnLimpar.addActionListener(e -> {
            txtDataExame.setText("");
            areaDescricao.setText("");
            comboPacientes.setSelectedIndex(0);
        });

        btnSalvar.addActionListener(e -> {
            Paciente pacienteSelecionado = (Paciente) comboPacientes.getSelectedItem();
            String dataStr = txtDataExame.getText();
            String descricao = areaDescricao.getText();
            
            if (pacienteSelecionado == null || descricao.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente e preencha a descrição.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Exame exame = new Exame();
                exame.setPaciente(pacienteSelecionado);
                exame.setDescricao(descricao);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                exame.setDataExame(LocalDate.parse(dataStr, formatter));

                exameDAO.salvar(exame);
                
                JOptionPane.showMessageDialog(this, "Exame salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void carregarPacientes() {
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for (Paciente p : pacientes) {
            comboPacientes.addItem(p);
        }
    }
}