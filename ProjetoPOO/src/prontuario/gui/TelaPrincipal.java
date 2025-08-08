package prontuario.gui;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        super("Sistema de Prontuário Médico");

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuPaciente = new JMenu("Pacientes");
        JMenuItem itemNovoPaciente = new JMenuItem("Novo");
        JMenuItem itemEditarPaciente = new JMenuItem("Editar");
        JMenuItem itemLocalizarPaciente = new JMenuItem("Localizar");
        JMenuItem itemExcluirPaciente = new JMenuItem("Excluir");

        menuPaciente.add(itemNovoPaciente);
        menuPaciente.add(itemEditarPaciente);
        menuPaciente.add(itemLocalizarPaciente);
        menuPaciente.add(itemExcluirPaciente);

        JMenu menuExame = new JMenu("Exames");
        JMenuItem itemNovoExame = new JMenuItem("Novo");
        JMenuItem itemEditarExame = new JMenuItem("Editar");
        JMenuItem itemLocalizarExame = new JMenuItem("Localizar");
        JMenuItem itemExcluirExame = new JMenuItem("Excluir");

        menuExame.add(itemNovoExame);
        menuExame.add(itemEditarExame);
        menuExame.add(itemLocalizarExame);
        menuExame.add(itemExcluirExame);

        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair da Aplicação");
        menuSair.add(itemSair);

        menuBar.add(menuPaciente);
        menuBar.add(menuExame);
        menuBar.add(menuSair);
        this.setJMenuBar(menuBar);

        JPanel painelCentral = new JPanel(new BorderLayout());
        JLabel lblMensagem = new JLabel("Bem-vindo ao Sistema de Prontuário Médico", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 20));
        painelCentral.add(lblMensagem, BorderLayout.CENTER);
        this.add(painelCentral, BorderLayout.CENTER);

        itemNovoPaciente.addActionListener(e -> new JanelaNovoPaciente(this).setVisible(true));
        itemEditarPaciente.addActionListener(e -> new JanelaEditarPaciente(this).setVisible(true));
        itemLocalizarPaciente.addActionListener(e -> new JanelaLocalizarPaciente(this).setVisible(true));
        itemExcluirPaciente.addActionListener(e -> new JanelaDeletarPaciente(this).setVisible(true));

        itemNovoExame.addActionListener(e -> new JanelaNovoExame(this).setVisible(true));
        itemEditarExame.addActionListener(e -> new JanelaEditarExame(this).setVisible(true));
        itemLocalizarExame.addActionListener(e -> new JanelaLocalizarExame(this).setVisible(true));
        itemExcluirExame.addActionListener(e -> new JanelaDeletarExame(this).setVisible(true));

        itemSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair do sistema?",
                    "Confirmação de Saída",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
