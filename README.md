# 🔤 Analizador léxico con JFlex y Java

Analizador léxico (*lexer*) que lee código fuente parecido a Java o C y lo separa en **tokens**: palabras reservadas, identificadores, números, cadenas, operadores y signos de puntuación. Indica la **línea y columna** de cada token y detecta los símbolos no válidos. Tiene **interfaz gráfica** con Swing y un modo de consola.

> Proyecto en equipo de la materia **Paradigmas de Programación**.
> Equipo: [PENDIENTE: nombres de tus compañeros]

## ✨ Qué reconoce

| Tipo | Ejemplos |
|---|---|
| Palabra reservada | `int` `float` `double` `char` `boolean` `String` `if` `else` `while` `for` `do` `return` `true` `false` |
| Identificador | `x` `total` `año` `mi_variable2` |
| Número | `42` `3.14` |
| Cadena de texto | `"hola mundo"` |
| Operadores | `=` `+` `-` `*` `/` · relacionales `==` `!=` `<` `<=` `>` `>=` · lógicos `&&` `\|\|` `!` |
| Agrupación y puntuación | `(` `)` `{` `}` `;` `,` |
| Se ignoran | espacios, comentarios `// ...` y `/* ... */` |
| Error | cualquier otro símbolo, como `@` o `#` |

Ejemplo en consola:

```
$ java -cp out analizador.AnalizadorLexico "if (a >= 2) {"
1:1     Palabra reservada      if
1:4     Paréntesis que abre    (
1:5     Identificador          a
1:7     Operador relacional    >=
1:10    Número                 2
1:11    Paréntesis que cierra  )
1:13    Llave que abre         {
```

## 🔧 Mejoras respecto a la versión original

El primer commit del repositorio contiene la versión original, así que se puede comparar.

| Antes | Ahora |
|---|---|
| `x-7` se leía como `x` y `-7` (número negativo), sin la resta | `-` siempre es resta; decidir si un número es negativo le corresponde al analizador sintáctico |
| `(` `)` `{` `}` `;` `<` `>` salían como error | Se reconocen, junto con operadores relacionales y lógicos, cadenas y decimales |
| No se sabía dónde estaba cada token | Cada token incluye línea y columna |
| El texto se escribía en `archivo.txt` y se volvía a leer | Se analiza directamente desde memoria (`StringReader`) |
| La lógica estaba dentro del botón de la ventana | `AnalizadorLexico.analizar()` es independiente de la interfaz y tiene pruebas |
| La ruta de JFlex apuntaba a una carpeta de otra computadora | JFlex solo se necesita para regenerar el lexer y se busca en `lib/` |

## 🛠️ Tecnologías

- Java 17 o superior
- [JFlex 1.9.1](https://jflex.de/) (generador de analizadores léxicos)
- Swing (interfaz gráfica)

## ▶️ Cómo ejecutarlo

`Lexer.java` ya viene generado, así que **no necesitas JFlex para ejecutarlo**.

```bash
git clone https://github.com/arumando/analizador-lexico-jflex.git
cd analizador-lexico-jflex
```

**Compilar** (Git Bash, Linux o macOS):

```bash
javac -encoding UTF-8 -d out $(find src test -name "*.java")
```

**Compilar** (PowerShell en Windows):

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src, test).FullName
```

**Ejecutar:**

```bash
java -cp out analizador.VentanaPrincipal                        # interfaz gráfica
java -cp out analizador.AnalizadorLexico "int x = 6 - 7;"       # consola
java -cp out pruebas.Pruebas                                    # 13 pruebas automáticas
```

### Modificar las reglas del lexer

1. Edita `src/analizador/Lexer.flex`.
2. Descarga [JFlex 1.9.1](https://jflex.de/download.html) y copia `jflex-full-1.9.1.jar` a la carpeta `lib/`.
3. Regenera `Lexer.java`:

```bash
java -cp "lib/*" herramientas/GenerarLexer.java
```

## 📸 Capturas

[PENDIENTE: captura de la ventana con la tabla de tokens]

## 📚 Qué aprendí

<!-- Revisa esta lista y escríbela con tus propias palabras. -->
- Cómo funciona la primera fase de un compilador: convertir texto en tokens.
- Escribir expresiones regulares para JFlex y entender la regla de "la coincidencia más larga gana" (por eso `>=` es un solo token y no `>` y `=`).
- Por qué el orden de las reglas importa: las palabras reservadas van antes que los identificadores.
- Separar la lógica de la interfaz gráfica para poder probarla.

## 👤 Autor

**José Armando García Bandera** — [github.com/arumando](https://github.com/arumando) · Proyecto en equipo: [PENDIENTE: nombres]
