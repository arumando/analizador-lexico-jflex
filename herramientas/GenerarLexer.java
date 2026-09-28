// Se ejecuta con el "source launcher" de Java, sin compilar aparte.

/**
 * Regenera Lexer.java a partir de Lexer.flex.
 * Solo hace falta si se modifica Lexer.flex. Requiere jflex-full-1.9.1.jar en lib/.
 *
 * Uso (desde la carpeta del proyecto): java -cp "lib/*" herramientas/GenerarLexer.java
 */
public class GenerarLexer {

    public static void main(String[] args) throws Exception {
        String ruta = "src/analizador/Lexer.flex";
        jflex.Main.generate(new String[] {"--nobak", ruta});
        System.out.println("Lexer generado correctamente a partir de " + ruta);
    }
}
