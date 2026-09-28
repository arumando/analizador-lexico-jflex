package pruebas;

import analizador.AnalizadorLexico;
import analizador.TipoToken;
import analizador.Token;
import java.util.List;

/**
 * Pruebas automáticas sin librerías externas.
 * Termina con código 1 si alguna prueba falla.
 */
public class Pruebas {

    private static int total = 0;
    private static int fallidas = 0;

    /** Verifica que el código produzca exactamente estos tipos de token, en orden. */
    static void tipos(String nombre, String codigo, TipoToken... esperados) {
        List<TipoToken> obtenidos = AnalizadorLexico.analizar(codigo).stream().map(Token::tipo).toList();
        comprobar(nombre, obtenidos.equals(List.of(esperados)), "se obtuvo " + obtenidos);
    }

    static void comprobar(String nombre, boolean correcto, String detalle) {
        total++;
        if (correcto) {
            System.out.println("  OK     " + nombre);
        } else {
            fallidas++;
            System.out.println("  FALLÓ  " + nombre + " -> " + detalle);
        }
    }

    public static void main(String[] args) {
        tipos("declaración con resta", "int x = 6 - 7",
                TipoToken.RESERVADA, TipoToken.IDENTIFICADOR, TipoToken.ASIGNACION,
                TipoToken.NUMERO, TipoToken.RESTA, TipoToken.NUMERO);

        // Error de la versión original: "x-7" daba Identificador y Numero(-7)
        tipos("x-7 sin espacios es una resta", "x-7",
                TipoToken.IDENTIFICADOR, TipoToken.RESTA, TipoToken.NUMERO);

        tipos("números decimales", "3.14",
                TipoToken.NUMERO);

        tipos("operadores relacionales de dos caracteres", "a >= b != c",
                TipoToken.IDENTIFICADOR, TipoToken.OPERADOR_RELACIONAL, TipoToken.IDENTIFICADOR,
                TipoToken.OPERADOR_RELACIONAL, TipoToken.IDENTIFICADOR);

        tipos("== no se confunde con dos asignaciones", "a == b",
                TipoToken.IDENTIFICADOR, TipoToken.OPERADOR_RELACIONAL, TipoToken.IDENTIFICADOR);

        tipos("if con paréntesis, llaves y punto y coma", "if (x) { y = 1; }",
                TipoToken.RESERVADA, TipoToken.PARENTESIS_ABRE, TipoToken.IDENTIFICADOR,
                TipoToken.PARENTESIS_CIERRA, TipoToken.LLAVE_ABRE, TipoToken.IDENTIFICADOR,
                TipoToken.ASIGNACION, TipoToken.NUMERO, TipoToken.PUNTO_Y_COMA, TipoToken.LLAVE_CIERRA);

        tipos("palabra que empieza como reservada es identificador", "integer iff",
                TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR);

        tipos("cadenas de texto", "String s = \"hola mundo\";",
                TipoToken.RESERVADA, TipoToken.IDENTIFICADOR, TipoToken.ASIGNACION,
                TipoToken.CADENA, TipoToken.PUNTO_Y_COMA);

        tipos("comentarios de línea y de bloque se ignoran", "x // nota\n/* varias\nlíneas */ y",
                TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR);

        tipos("símbolo desconocido es error", "x @ y",
                TipoToken.IDENTIFICADOR, TipoToken.ERROR, TipoToken.IDENTIFICADOR);

        tipos("identificadores con acentos y ñ", "año = número",
                TipoToken.IDENTIFICADOR, TipoToken.ASIGNACION, TipoToken.IDENTIFICADOR);

        List<Token> tokens = AnalizadorLexico.analizar("int a;\n  b = 2;");
        Token b = tokens.get(3);
        comprobar("línea y columna correctas", b.lexema().equals("b") && b.linea() == 2 && b.columna() == 3,
                "se obtuvo " + b);

        comprobar("texto vacío no produce tokens", AnalizadorLexico.analizar("").isEmpty(), "no vacío");

        System.out.println("\n" + (total - fallidas) + " de " + total + " pruebas correctas");
        if (fallidas > 0) {
            System.exit(1);
        }
    }
}
