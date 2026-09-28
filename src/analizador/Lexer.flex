/*
 * Especificación del analizador léxico para JFlex.
 * Para regenerar Lexer.java: java -cp "lib/*" herramientas/GenerarLexer.java
 */
package analizador;

%%

%public
%class Lexer
%type Token
%unicode
%line
%column

%{
    /** Crea un token con el texto reconocido y su posición (empezando en 1). */
    private Token token(TipoToken tipo) {
        return new Token(tipo, yytext(), yyline + 1, yycolumn + 1);
    }
%}

LETRA      = [a-zA-Z_áéíóúÁÉÍÓÚñÑ]
DIGITO     = [0-9]
ESPACIO    = [ \t\r\n\f]+
COMENTARIO_LINEA  = "//" [^\r\n]*
COMENTARIO_BLOQUE = "/*" ~"*/"

%%

/* Espacios y comentarios: se ignoran */
{ESPACIO}            { }
{COMENTARIO_LINEA}   { }
{COMENTARIO_BLOQUE}  { }

/* Palabras reservadas (van antes que los identificadores) */
"int" | "float" | "double" | "char" | "boolean" | "String" |
"if" | "else" | "while" | "for" | "do" | "return" |
"true" | "false"     { return token(TipoToken.RESERVADA); }

/* Identificadores y literales */
{LETRA}({LETRA}|{DIGITO})*       { return token(TipoToken.IDENTIFICADOR); }
{DIGITO}+ ("." {DIGITO}+)?       { return token(TipoToken.NUMERO); }
\" [^\"\r\n]* \"                  { return token(TipoToken.CADENA); }

/* Operadores. El signo "-" siempre es resta: el analizador léxico no decide
   si un número es negativo, eso le toca al analizador sintáctico. */
"==" | "!=" | "<=" | ">=" | "<" | ">"   { return token(TipoToken.OPERADOR_RELACIONAL); }
"&&" | "||" | "!"                       { return token(TipoToken.OPERADOR_LOGICO); }
"="   { return token(TipoToken.ASIGNACION); }
"+"   { return token(TipoToken.SUMA); }
"-"   { return token(TipoToken.RESTA); }
"*"   { return token(TipoToken.MULTIPLICACION); }
"/"   { return token(TipoToken.DIVISION); }

/* Signos de agrupación y puntuación */
"("   { return token(TipoToken.PARENTESIS_ABRE); }
")"   { return token(TipoToken.PARENTESIS_CIERRA); }
"{"   { return token(TipoToken.LLAVE_ABRE); }
"}"   { return token(TipoToken.LLAVE_CIERRA); }
";"   { return token(TipoToken.PUNTO_Y_COMA); }
","   { return token(TipoToken.COMA); }

/* Cualquier otro carácter es un error léxico */
[^]   { return token(TipoToken.ERROR); }
