package prontuario.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.InputMismatchException; 
public final class ValidacaoUtil {

    private ValidacaoUtil() {
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cpfLimpo = cpf.replaceAll("[.\\-]", "");

        if (cpfLimpo.equals("00000000000") || cpfLimpo.equals("11111111111") ||
            cpfLimpo.equals("22222222222") || cpfLimpo.equals("33333333333") ||
            cpfLimpo.equals("44444444444") || cpfLimpo.equals("55555555555") ||
            cpfLimpo.equals("66666666666") || cpfLimpo.equals("77777777777") ||
            cpfLimpo.equals("88888888888") || cpfLimpo.equals("99999999999") ||
            (cpfLimpo.length() != 11)) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpfLimpo.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpfLimpo.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            return (dig10 == cpfLimpo.charAt(9)) && (dig11 == cpfLimpo.charAt(10));
        } catch (InputMismatchException erro) {
            return false;
        }
    }

    public static boolean validarData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                                             .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(data, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3 || nome.trim().length() > 100) {
            return false;
        }
        return nome.matches("^[\\p{L} .'-]+$");
    }
}