package analizador;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/** Ventana para escribir código y ver la tabla de tokens reconocidos. */
public class VentanaPrincipal extends JFrame {

    private static final String EJEMPLO = """
            // Escribe tu código aquí y presiona "Analizar"
            int x = 6 - 7;
            if (x >= 0 && y != 2) {
                x = x + 1.5;
            }
            """;

    private final JTextArea entrada = new JTextArea(EJEMPLO, 10, 40);
    private final DefaultTableModel modelo =
            new DefaultTableModel(new Object[] {"Línea", "Columna", "Tipo", "Lexema"}, 0) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
    private final JLabel resumen = new JLabel(" ");

    public VentanaPrincipal() {
        super("Analizador léxico");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        entrada.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        entrada.setTabSize(4);

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(70);
        tabla.getColumnModel().getColumn(1).setMaxWidth(80);

        JButton analizar = new JButton("Analizar");
        analizar.addActionListener(e -> analizar());

        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(e -> {
            entrada.setText("");
            modelo.setRowCount(0);
            resumen.setText(" ");
        });

        JPanel botones = new JPanel();
        botones.add(analizar);
        botones.add(limpiar);

        JPanel superior = new JPanel(new BorderLayout());
        superior.setBorder(BorderFactory.createTitledBorder("Código"));
        superior.add(new JScrollPane(entrada), BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setBorder(BorderFactory.createTitledBorder("Tokens"));
        inferior.add(new JScrollPane(tabla), BorderLayout.CENTER);
        inferior.add(resumen, BorderLayout.SOUTH);

        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, superior, inferior), BorderLayout.CENTER);
        setSize(720, 640);
        setLocationRelativeTo(null);
    }

    private void analizar() {
        List<Token> tokens = AnalizadorLexico.analizar(entrada.getText());
        modelo.setRowCount(0);
        long errores = 0;
        for (Token t : tokens) {
            modelo.addRow(new Object[] {t.linea(), t.columna(), t.tipo().getDescripcion(), t.lexema()});
            if (t.tipo() == TipoToken.ERROR) {
                errores++;
            }
        }
        resumen.setText(tokens.size() + " tokens encontrados, " + errores + " con error.");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException e) {
            // Si Nimbus no está disponible se usa el aspecto por defecto
        }
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
