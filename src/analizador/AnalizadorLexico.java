package analizador;

import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Punto de entrada del análisis: recibe un texto y devuelve sus tokens.
 *
 * No depende de la interfaz gráfica, así que se puede usar desde la
 * ventana, desde la consola o desde las pruebas.
 */
public final class AnalizadorLexico {

    private AnalizadorLexico() {
    }

    public static List<Token> analizar(String codigo) {
        Lexer lexer = new Lexer(new StringReader(codigo));
        List<Token> tokens = new ArrayList<>();
        try {
            Token token;
            while ((token = lexer.yylex()) != null) {
                tokens.add(token);
            }
        } catch (IOException e) {
            // No debería ocurrir: se lee de un String en memoria
            throw new UncheckedIOException(e);
        }
        return tokens;
    }

    /** Uso desde consola: java -cp out analizador.AnalizadorLexico "int x = 5;" */
    public static void main(String[] args) {
        String codigo = args.length > 0 ? String.join(" ", args) : "int x = 6 - 7;";
        System.out.println("Entrada: " + codigo + "\n");
        analizar(codigo).forEach(System.out::println);
    }
}
