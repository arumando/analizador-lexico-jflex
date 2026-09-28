package analizador;

/** Un token reconocido: su tipo, el texto original y dónde apareció. */
public record Token(TipoToken tipo, String lexema, int linea, int columna) {

    @Override
    public String toString() {
        return String.format("%-7s %-22s %s", linea + ":" + columna, tipo.getDescripcion(), lexema);
    }
}
