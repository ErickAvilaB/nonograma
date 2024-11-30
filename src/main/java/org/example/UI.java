package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Nonograma
 * Interfaz gráfica del juego, usa Java Swing.
 */
public class UI {

    private int anchoVentana = 250;
    private int altoVentana = 300;
    private int vidas = 3;

    public static void main(String[] args) {
        // Instanciar y mostrar la interfaz
        UI ui = new UI();
        ui.createAndShowUI();
    }

    /**
     * Método principal para configurar y mostrar la interfaz.
     */
    private void createAndShowUI() {
        JFrame frame = setupFrame(); // Configurar marco principal
        Nonograma nonograma = new Nonograma(5); // Crear un nonograma de tamaño 5
        Tablero tablero = new Tablero(nonograma); // Crear un tablero a partir del nonograma
        addComponents(frame); // Añadir los componentes necesarios
        frame.add(createGameBoard(tablero), BorderLayout.SOUTH); // Añadir el tablero
        frame.setVisible(true); // Mostrar la ventana
    }

    /**
     * Configura el marco principal.
     * 
     * @return JFrame configurado.
     */
    private JFrame setupFrame() {
        JFrame frame = new JFrame("Nonograma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(anchoVentana, altoVentana);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    /**
     * Añade los componentes principales al marco.
     * 
     * @param frame El JFrame al que se añadirán los componentes.
     */
    private void addComponents(JFrame frame) {
        frame.add(createTitleLabel(), BorderLayout.NORTH); // Título
        frame.add(createSelectionPanel(frame), BorderLayout.CENTER); // Selección de dificultad
    }

    /**
     * Crea un JLabel con el título de la ventana.
     * 
     * @return JLabel con el mensaje de bienvenida.
     */
    private JLabel createTitleLabel() {
        return new JLabel("Selecciona el nivel de dificultad:", SwingConstants.CENTER);
    }

    /**
     * Crea un panel con un menú desplegable y un botón de confirmación.
     * 
     * @param frame El JFrame para asociar los eventos.
     * @return JPanel con los controles de selección.
     */
    private JPanel createSelectionPanel(JFrame frame) {
        JPanel panel = new JPanel();

        // Opciones de dificultad
        String[] niveles = { "Principiante", "Fácil", "Medio", "Intermedio", "Difícil" };
        JComboBox<String> comboBox = new JComboBox<>(niveles);

        // Botón de confirmación
        JButton confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleConfirmAction(frame, comboBox);
            }
        });

        // Añadir componentes al panel
        panel.add(comboBox);
        panel.add(confirmar);

        return panel;
    }

    /**
     * Maneja la acción de confirmar la selección del nivel.
     * 
     * @param frame    El JFrame para mostrar mensajes.
     * @param comboBox El JComboBox para obtener la selección.
     */
    private void handleConfirmAction(JFrame frame, JComboBox<String> comboBox) {
        String nivelSeleccionado = (String) comboBox.getSelectedItem();
        JOptionPane.showMessageDialog(frame, "Has seleccionado: " + nivelSeleccionado);

        // Aquí puedes conectar con la lógica del juego.
    }

    /**
     * Crea un panel que representa el tablero del Nonograma con pistas.
     *
     * @param tablero El tablero a graficar.
     * @return JPanel que contiene la representación gráfica del tablero.
     */
    private JPanel createGameBoard(Tablero tablero) {
        int tamano = tablero.getTamano();
        List<List<Integer>> pistasFilas = tablero.getPistasFilas();
        List<List<Integer>> pistasColumnas = tablero.getPistasColumnas();

        // Crear el panel principal que combina pistas y tablero
        JPanel tableroPanel = new JPanel(new BorderLayout());

        // Panel de pistas de las filas
        JPanel pistasIzquierda = new JPanel(new GridLayout(tamano, 1));
        for (List<Integer> pistaFila : pistasFilas) {
            JLabel label = new JLabel(formatPistas(pistaFila), SwingConstants.RIGHT);
            pistasIzquierda.add(label);
        }

        // Panel de pistas de las columnas
        JPanel pistasSuperior = new JPanel(new GridLayout(1, tamano));
        for (List<Integer> pistaColumna : pistasColumnas) {
            JPanel columnaPanel = new JPanel(new GridLayout(pistaColumna.size(), 1)); // Subpanel para pistas
            for (Integer pista : pistaColumna) {
                JLabel label = new JLabel(String.valueOf(pista), SwingConstants.CENTER);
                columnaPanel.add(label);
            }
            pistasSuperior.add(columnaPanel);
        }

        // Panel para el tablero de casillas
        JPanel casillasPanel = new JPanel(new GridLayout(tamano, tamano));
        JButton[][] botonesCasillas = new JButton[tamano][tamano]; // Matriz para guardar los botones

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                JButton casilla = new JButton();
                casilla.setPreferredSize(new Dimension(30, 30));
                casilla.setBackground(Color.WHITE);

                // Manejo de clics
                int x = j, y = i; // Coordenadas para pasar al método marcarCasilla
                casilla.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        try {
                            if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                                // Clic izquierdo: marcar como lleno (negro)
                                tablero.marcarCasilla(x, y, 1);
                                casilla.setBackground(Color.BLACK);
                            } else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                                // Clic derecho: marcar como vacío (gris)
                                tablero.marcarCasilla(x, y, 0);
                                casilla.setBackground(Color.LIGHT_GRAY);
                            }
                            actualizarTablero(botonesCasillas, tablero);
                            if (tablero.completo()) {
                                JOptionPane.showMessageDialog(null, "Ganaste!!!", "Ganador",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (NoSuchElementException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            System.out.println("Vida Menos");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                botonesCasillas[i][j] = casilla; // Guardar referencia del botón
                casillasPanel.add(casilla);
            }
        }

        // Ensamblar las secciones
        tableroPanel.add(pistasIzquierda, BorderLayout.WEST);
        tableroPanel.add(pistasSuperior, BorderLayout.NORTH);
        tableroPanel.add(casillasPanel, BorderLayout.CENTER);

        return tableroPanel;
    }

    /**
     * Formatea las pistas como una cadena para mostrarlas en la interfaz.
     *
     * @param pistas Lista de pistas.
     * @return Cadena formateada.
     */
    private String formatPistas(List<Integer> pistas) {
        StringBuilder sb = new StringBuilder();
        for (Integer pista : pistas) {
            sb.append(pista).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Actualiza los colores de los botones según el estado de la matriz de
     * casillas.
     *
     * @param botonesCasillas Matriz de botones representando el tablero gráfico.
     * @param tablero         El tablero lógico que contiene la matriz casillas.
     */
    private void actualizarTablero(JButton[][] botonesCasillas, Tablero tablero) {
        int tamano = tablero.getTamano();
        int[][] casillas = tablero.getCasillas(); // Obtener la matriz lógica

        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                if (casillas[i][j] == 1)
                    botonesCasillas[i][j].setBackground(Color.BLACK); // Lleno
                else if (casillas[i][j] == 0)
                    botonesCasillas[i][j].setBackground(Color.LIGHT_GRAY); // Vacío
                else
                    botonesCasillas[i][j].setBackground(Color.WHITE); // No marcado
    }

}
