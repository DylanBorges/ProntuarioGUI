package prontuario.util;

import java.io.IOException;
import java.io.InputStream; // Importação diferente
import java.util.Properties;

/**
 * Classe utilitária para carregar as configurações do arquivo config.properties.
 * Esta versão usa getResourceAsStream para uma busca de arquivo mais robusta.
 */
public final class ConfigLoader {

    private static final Properties properties = new Properties();

    // Bloco estático que é executado uma única vez quando a classe é carregada.
    static {
        // Usa ClassLoader para encontrar o arquivo como um recurso no classpath.
        // Esta é a forma mais garantida de encontrar o arquivo.
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("FATAL: Arquivo 'config.properties' não encontrado no classpath.");
            } else {
                properties.load(input);
            }

        } catch (IOException ex) {
            System.err.println("FATAL: Erro de I/O ao carregar o arquivo 'config.properties'.");
            ex.printStackTrace();
        }
    }

    /**
     * Construtor privado para impedir a criação de instâncias.
     */
    private ConfigLoader() {
    }

    /**
     * Retorna o valor associado a uma chave do arquivo de configuração.
     * @param key A chave da propriedade (ex: "DBNAME").
     * @return O valor da propriedade como String.
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}