package prontuario.main;

import javax.swing.SwingUtilities;
import prontuario.gui.TelaPrincipal;
import prontuario.util.DatabaseSetup;

public class Main {
    public static void main(String[] args) {
        DatabaseSetup.verificarEInstanciarBanco();

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}