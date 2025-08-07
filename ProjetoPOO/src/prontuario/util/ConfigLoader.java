package prontuario.util;

import java.io.IOException;
import java.io.InputStream; // Importação diferente
import java.util.Properties;


public final class ConfigLoader {

    private static final Properties properties = new Properties();

    static {
      
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

    private ConfigLoader() {
    }

       public static String get(String key) {
        return properties.getProperty(key);
    }
}