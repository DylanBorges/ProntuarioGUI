package prontuario.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import prontuario.dao.PacienteDAO;
import prontuario.model.Paciente;
import prontuario.util.ValidacaoUtil;

public class JanelaNovoPaciente extends JDialog {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;
    private PacienteDAO pacienteDAO;

    public JanelaNovoPaciente(JFrame owner) {
        super(owner, "Novo Paciente", true);

        this.pacienteDAO = new PacienteDAO();
        
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(owner);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 80, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 20, 200, 25);
        add(txtNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(20, 60, 80, 25);
        add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(150, 60, 200, 25);
        add(txtCpf);
        
        JLabel lblDataNascimento = new JLabel("Data Nascimento:");
        lblDataNascimento.setBounds(20, 100, 120, 25);
        add(lblDataNascimento);

        txtDataNascimento = new JTextField();
        txtDataNascimento.setBounds(150, 100, 200, 25);
        add(txtDataNascimento);
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 160, 80, 30);
        add(btnSalvar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(150, 160, 80, 30);
        add(btnLimpar);
        
        btnSair = new JButton("Sair");
        btnSair.setBounds(250, 160, 80, 30);
        add(btnSair);

        btnSair.addActionListener(e -> dispose());
        
        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtCpf.setText("");
            txtDataNascimento.setText("");
        });

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String dataStr = txtDataNascimento.getText();

            if (!ValidacaoUtil.validarNome(nome) || !ValidacaoUtil.validarCPF(cpf)) {
                JOptionPane.showMessageDialog(this, "Nome ou CPF inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Paciente paciente = new Paciente();
                paciente.setNome(nome);
                paciente.setCpf(cpf);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                paciente.setDataNascimento(LocalDate.parse(dataStr, formatter));

                pacienteDAO.salvar(paciente);
                
                JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}