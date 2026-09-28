package analizador;

/** Categorías de tokens que reconoce el analizador léxico. */
public enum TipoToken {
    RESERVADA("Palabra reservada"),
    IDENTIFICADOR("Identificador"),
    NUMERO("Número"),
    CADENA("Cadena de texto"),
    ASIGNACION("Asignación"),
    SUMA("Suma"),
    RESTA("Resta"),
    MULTIPLICACION("Multiplicación"),
    DIVISION("División"),
    OPERADOR_RELACIONAL("Operador relacional"),
    OPERADOR_LOGICO("Operador lógico"),
    PARENTESIS_ABRE("Paréntesis que abre"),
    PARENTESIS_CIERRA("Paréntesis que cierra"),
    LLAVE_ABRE("Llave que abre"),
    LLAVE_CIERRA("Llave que cierra"),
    PUNTO_Y_COMA("Punto y coma"),
    COMA("Coma"),
    ERROR("Símbolo no reconocido");

    private final String descripcion;

    TipoToken(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
