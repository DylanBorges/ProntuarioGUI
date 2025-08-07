package prontuario.gui;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		super("Sistema de Prontuário Médico");

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		itemNovoPaciente.addActionListener(e -> {
			JanelaNovoPaciente janelaNovo = new JanelaNovoPaciente(this);
			janelaNovo.setVisible(true);

			itemEditarPaciente.addActionListener(e1 -> {
				JanelaEditarPaciente janelaEditar = new JanelaEditarPaciente(this);
				janelaEditar.setVisible(true);

				itemLocalizarPaciente.addActionListener(e2 -> {
					JanelaLocalizarPaciente janelaLocalizar = new JanelaLocalizarPaciente(this);
					janelaLocalizar.setVisible(true);

					itemExcluirPaciente.addActionListener(e3 -> {
						JanelaDeletarPaciente janelaDeletar = new JanelaDeletarPaciente(this);
						janelaDeletar.setVisible(true);

					});
				});
			});
		});

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

		itemEditarExame.addActionListener(e5 -> {
			JanelaEditarExame janelaEditarExame = new JanelaEditarExame(this);
			janelaEditarExame.setVisible(true);
		});

		itemNovoExame.addActionListener(e4 -> {
			JanelaNovoExame janelaNovoExame = new JanelaNovoExame(this);
			janelaNovoExame.setVisible(true);

			itemLocalizarExame.addActionListener(e6 -> {
				JanelaLocalizarExame janelaLocalizar = new JanelaLocalizarExame(this);
				janelaLocalizar.setVisible(true);

				

				itemExcluirExame.addActionListener(e7 -> {
					JanelaDeletarExame janelaDeletar = new JanelaDeletarExame(this);
					janelaDeletar.setVisible(true);
				});
			});
		});

		itemSair.addActionListener(e -> {
			int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema?",
					"Confirmação de Saída", JOptionPane.YES_NO_OPTION);

			if (resposta == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
	}
}