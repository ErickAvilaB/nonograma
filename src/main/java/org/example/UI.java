package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Nonograma
 * Interfaz gráfica del juego, usa Java Swing.
 */
public class UI {

    private static final int ANCHO_VENTANA = 250;
    private static final int ALTO_VENTANA = 300;

    public static void main(String[] args) {
        UI ui = new UI();
        ui.createAndShowUI();
    }

    /**
     * Configura y muestra la interfaz principal.
     */
    private void createAndShowUI() {
        JFrame frame = setupFrame();
        Tablero tablero = inicializarTablero(5); // Crear tablero de tamaño 5x5

        configurarComponentes(frame, tablero);
        frame.setVisible(true);
    }

    /**
     * Configura el marco principal.
     * 
     * @return JFrame configurado.
     */
    private JFrame setupFrame() {
        JFrame frame = new JFrame("Nonograma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ANCHO_VENTANA, ALTO_VENTANA);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    /**
     * Inicializa el tablero lógico del Nonograma.
     * 
     * @param tamano Tamaño del tablero.
     * @return Tablero inicializado.
     */
    private Tablero inicializarTablero(int tamano) {
        Nonograma nonograma = new Nonograma(tamano);
        return new Tablero(nonograma);
    }

    /**
     * Configura los componentes principales en el marco.
     * 
     * @param frame   Marco principal.
     * @param tablero Tablero lógico.
     */
    private void configurarComponentes(JFrame frame, Tablero tablero) {
        frame.add(crearTitulo(), BorderLayout.NORTH);
        frame.add(crearPanelSeleccion(frame), BorderLayout.CENTER);
        frame.add(crearTableroPanel(tablero), BorderLayout.SOUTH);
    }

    /**
     * Crea un JLabel con el título.
     * 
     * @return JLabel con el mensaje de bienvenida.
     */
    private JLabel crearTitulo() {
        return new JLabel("Selecciona el nivel de dificultad:", SwingConstants.CENTER);
    }

    /**
     * Crea un panel para seleccionar el nivel de dificultad.
     * 
     * @param frame Marco principal.
     * @return JPanel configurado.
     */
    private JPanel crearPanelSeleccion(JFrame frame) {
        JPanel panel = new JPanel();
        JComboBox<String> comboBox = crearComboBox();
        JButton botonConfirmar = crearBotonConfirmar(frame, comboBox);

        panel.add(comboBox);
        panel.add(botonConfirmar);
        return panel;
    }

    /**
     * Crea un JComboBox con los niveles de dificultad.
     * 
     * @return JComboBox configurado.
     */
    private JComboBox<String> crearComboBox() {
        String[] niveles = { "Principiante", "Fácil", "Medio", "Intermedio", "Difícil" };
        return new JComboBox<>(niveles);
    }

    /**
     * Crea un botón para confirmar la selección.
     * 
     * @param frame    Marco principal.
     * @param comboBox JComboBox para obtener la selección.
     * @return JButton configurado.
     */
    private JButton crearBotonConfirmar(JFrame frame, JComboBox<String> comboBox) {
        JButton boton = new JButton("Confirmar");
        boton.addActionListener(e -> manejarConfirmacion(frame, comboBox));
        return boton;
    }

    /**
     * Maneja la acción de confirmar la selección.
     * 
     * @param frame    Marco principal.
     * @param comboBox JComboBox con la selección.
     */
    private void manejarConfirmacion(JFrame frame, JComboBox<String> comboBox) {
        String nivelSeleccionado = (String) comboBox.getSelectedItem();
        JOptionPane.showMessageDialog(frame, "Has seleccionado: " + nivelSeleccionado);
        // Aquí puedes integrar la lógica del nivel de dificultad
    }

    /**
     * Crea un panel que representa el tablero gráfico.
     * 
     * @param tablero Tablero lógico.
     * @return JPanel configurado.
     */
    private JPanel crearTableroPanel(Tablero tablero) {
        JPanel tableroPanel = new JPanel(new BorderLayout());

        tableroPanel.add(crearPistasFilas(tablero), BorderLayout.WEST);
        tableroPanel.add(crearPistasColumnas(tablero), BorderLayout.NORTH);
        tableroPanel.add(crearCasillas(tablero), BorderLayout.CENTER);

        return tableroPanel;
    }

    /**
     * Crea un panel con las pistas de las filas.
     * 
     * @param tablero Tablero lógico.
     * @return JPanel configurado.
     */
    private JPanel crearPistasFilas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(tamano, 1));

        for (List<Integer> pistaFila : tablero.getPistasFilas()) {
            JLabel label = new JLabel(formatearPistas(pistaFila), SwingConstants.RIGHT);
            panel.add(label);
        }
        return panel;
    }

    /**
     * Crea un panel con las pistas de las columnas.
     * 
     * @param tablero Tablero lógico.
     * @return JPanel configurado.
     */
    private JPanel crearPistasColumnas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(1, tamano));

        for (List<Integer> pistaColumna : tablero.getPistasColumnas()) {
            JPanel subPanel = new JPanel(new GridLayout(pistaColumna.size(), 1));
            for (Integer pista : pistaColumna) {
                JLabel label = new JLabel(String.valueOf(pista), SwingConstants.CENTER);
                subPanel.add(label);
            }
            panel.add(subPanel);
        }
        return panel;
    }

    /**
     * Crea un panel con las casillas interactivas del tablero.
     * 
     * @param tablero Tablero lógico.
     * @return JPanel configurado.
     */
    private JPanel crearCasillas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(tamano, tamano));
        JButton[][] botones = new JButton[tamano][tamano];

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                botones[i][j] = crearBotonCasilla(j, i, tablero, botones);
                panel.add(botones[i][j]);
            }
        }
        return panel;
    }

    /**
     * Crea un botón interactivo para una casilla.
     * 
     * @param x       Coordenada x.
     * @param y       Coordenada y.
     * @param tablero Tablero lógico.
     * @param botones Matriz de botones.
     * @return JButton configurado.
     */
    private JButton crearBotonCasilla(int x, int y, Tablero tablero, JButton[][] botones) {
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(30, 30));
        boton.setBackground(Color.WHITE);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                manejarClicCasilla(e, x, y, tablero, botones);
            }
        });

        return boton;
    }

    /**
     * Maneja el clic en una casilla.
     * 
     * @param e       Evento del clic.
     * @param x       Coordenada x.
     * @param y       Coordenada y.
     * @param tablero Tablero lógico.
     * @param botones Matriz de botones.
     */
    private void manejarClicCasilla(java.awt.event.MouseEvent e, int x, int y, Tablero tablero, JButton[][] botones) {
        try {
            if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                tablero.marcarCasilla(x, y, 1);
            } else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                tablero.marcarCasilla(x, y, 0);
            }
            actualizarTablero(botones, tablero);

            if (tablero.completo()) {
                JOptionPane.showMessageDialog(null, "¡Ganaste!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Formatea las pistas como una cadena para mostrarlas.
     * 
     * @param pistas Lista de pistas.
     * @return Cadena formateada.
     */
    private String formatearPistas(List<Integer> pistas) {
        return String.join(" ", pistas.stream().map(String::valueOf).toArray(String[]::new));
    }

    /**
     * Actualiza los colores de las casillas según el estado del tablero.
     * 
     * @param botones Matriz de botones.
     * @param tablero Tablero lógico.
     */
    private void actualizarTablero(JButton[][] botones, Tablero tablero) {
        int tamano = tablero.getTamano();
        int[][] casillas = tablero.getCasillas();

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                Color color = switch (casillas[i][j]) {
                    case 1 -> Color.BLACK; // Marcado
                    case 0 -> Color.LIGHT_GRAY; // Vacío
                    default -> Color.WHITE; // Sin marcar
                };
                botones[i][j].setBackground(color);
            }
        }
    }
}
