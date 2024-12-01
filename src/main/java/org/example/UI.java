package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Nonograma
 * Interfaz gráfica del juego, usa Java Swing.
 */
public class UI {

    private static final int ANCHO_VENTANA = 700;
    private static final int ALTO_VENTANA = 600;

    private JLabel labelVidas;
    private JLabel labelPistas;
    private int vidas = 3;
    private int pistas = 3;
    private JButton[][] botones;

    public static void main(String[] args) {
        UI ui = new UI();
        ui.createAndShowUI();
    }

    /**
     * Configura y muestra la interfaz principal.
     */
    private void createAndShowUI() {
        JFrame frame = setupFrame();

        configurarComponentes(frame);
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

    private void configurarComponentes(JFrame frame, Tablero tablero) {
        // Barra superior con título y selección de nivel
        frame.add(crearTitulo(), BorderLayout.NORTH);
        frame.add(crearPanelSeleccion(frame), BorderLayout.NORTH);

        // Panel central donde estará el tablero y botones
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());

        // Panel para el tablero (Centro de la pantalla)
        centralPanel.add(crearTableroPanel(tablero), BorderLayout.CENTER);

        // Panel lateral de botones (Este)
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.add(crearPanelVidas());
        botonesPanel.add(crearPanelPistas());
        botonesPanel.add(crearPanelBotones(tablero));

        centralPanel.add(botonesPanel, BorderLayout.EAST);

        // Configura los componentes y coloca los paneles
        frame.add(centralPanel, BorderLayout.CENTER);
    }

    private void configurarComponentes(JFrame frame) {
        frame.add(crearTitulo(), BorderLayout.NORTH);
        frame.add(crearPanelSeleccion(frame), BorderLayout.CENTER);
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

    private void manejarConfirmacion(JFrame frame, JComboBox<String> comboBox) {
        vidas = 3;
        pistas = 3;
        String nivelSeleccionado = (String) comboBox.getSelectedItem();
        int tamanoTablero;

        // Definir el tamaño o la complejidad según el nivel seleccionado
        switch (nivelSeleccionado) {
            case "Principiante":
                tamanoTablero = 5;
                break;
            case "Fácil":
                tamanoTablero = 5;
                break;
            case "Medio":
                tamanoTablero = 10;
                break;
            case "Intermedio":
                tamanoTablero = 10;
                break;
            case "Difícil":
                tamanoTablero = 15;
                break;
            default:
                tamanoTablero = 5;
        }

        // Crear un nuevo tablero con el tamaño seleccionado
        Tablero nuevoTablero = inicializarTablero(tamanoTablero);

        if (nivelSeleccionado == "Principiante" || nivelSeleccionado == "Intermedio") {
            nuevoTablero.pistaInicio();
        }

        // Aquí, en lugar de eliminar todo, solo actualizamos la interfaz para mostrar
        // el juego
        // Quitamos el panel de selección de nivel
        frame.getContentPane().removeAll();

        // Añadimos el panel del juego
        configurarComponentes(frame, nuevoTablero); // Asegúrate de pasar el tablero para configurar los componentes

        // Añadimos los elementos gráficos de la interfaz
        frame.revalidate();
        frame.repaint();

    }

    private JPanel crearPanelVidas() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Cambiado para mayor control de tamaño
        JLabel tituloVidas = new JLabel("Vidas restantes:");
        tituloVidas.setHorizontalAlignment(SwingConstants.CENTER);

        labelVidas = new JLabel(String.valueOf(vidas), SwingConstants.CENTER);
        labelVidas.setFont(new Font("Arial", Font.BOLD, 18));
        labelVidas.setForeground(Color.MAGENTA);

        panel.add(tituloVidas);
        panel.add(labelVidas);
        return panel;
    }

    private JPanel crearPanelPistas() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Cambiado para mayor control de tamaño
        JLabel tituloPistas = new JLabel("Pistas restantes:");
        tituloPistas.setHorizontalAlignment(SwingConstants.CENTER);

        labelPistas = new JLabel(String.valueOf(pistas), SwingConstants.CENTER);
        labelPistas.setFont(new Font("Arial", Font.BOLD, 18));
        labelPistas.setForeground(Color.MAGENTA);

        panel.add(tituloPistas);
        panel.add(labelPistas);
        return panel;
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

    private JPanel crearTableroPanel(Tablero tablero) {
        JPanel tableroPanel = new JPanel();
        tableroPanel.setLayout(new BorderLayout());

        // Pistas verticales a la izquierda
        tableroPanel.add(crearPistasFilas(tablero), BorderLayout.WEST);

        // Pistas horizontales en la parte superior
        tableroPanel.add(crearPistasColumnas(tablero), BorderLayout.NORTH);

        // Casillas del tablero en el centro
        tableroPanel.add(crearCasillas(tablero), BorderLayout.CENTER);

        return tableroPanel;
    }

    private JPanel crearPanelBotones(Tablero tablero) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1)); // Acomoda el panel de botones en una fila

        // Botón de usar pista
        JButton botonPista = new JButton("Pista");
        botonPista.addActionListener(e -> manejarPista(tablero, (JFrame) SwingUtilities.getWindowAncestor(panel)));

        panel.add(botonPista);
        actualizarTablero(tablero); // Actualiza el tablero al inicio
        return panel;
    }

    private void manejarPista(Tablero tablero, JFrame frame) {
        if (vidas <= 0) {
            JOptionPane.showMessageDialog(null, "No puedes usar pistas sin vidas.", "Sin vidas",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pistas <= 0) {
            JOptionPane.showMessageDialog(null, "No puedes usar más pistas.", "Sin pistas",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Restar una pista
        pistas--;

        // Actualizar el texto del JLabel de pistas
        labelPistas.setText(String.valueOf(pistas));

        // Asegúrate de que la interfaz se actualice
        frame.revalidate(); // Refresca los componentes del contenedor
        frame.repaint(); // Redibuja el contenedor con los nuevos datos

        int[] pista = tablero.pista(); // Obtener la pista del tablero
        if (pista[0] == -1 && pista[1] == -1) {
            JOptionPane.showMessageDialog(null, "No hay más pistas disponibles.", "Sin pistas",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTablero(tablero);
        if (tablero.completo()) {
            JOptionPane.showMessageDialog(null, "¡Ganaste!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel crearPistasFilas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(tamano, 1));
        panel.setPreferredSize(new Dimension(60, tamano * 30)); // Asegura un buen tamaño para las pistas

        for (List<Integer> pistaFila : tablero.getPistasFilas()) {
            JLabel label = new JLabel(formatearPistas(pistaFila), SwingConstants.RIGHT);
            panel.add(label);
        }
        return panel;
    }

    private JPanel crearPistasColumnas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(1, tamano));
        panel.add(new JPanel()); // Espacio en blanco para la esquina superior izquierda

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

    private JPanel crearCasillas(Tablero tablero) {
        int tamano = tablero.getTamano();
        JPanel panel = new JPanel(new GridLayout(tamano, tamano));
        panel.setPreferredSize(new Dimension(400, 400)); // Tamaño fijo para las casillas

        botones = new JButton[tamano][tamano];

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
            if (vidas <= 0) {
                JOptionPane.showMessageDialog(null, "No tienes vidas", "Vidas", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                tablero.marcarCasilla(x, y, 1);
            } else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                tablero.marcarCasilla(x, y, 0);
            }
            if (tablero.completo()) {
                JOptionPane.showMessageDialog(null, "¡Ganaste!", "Victoria", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (NoSuchElementException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            restarVida();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            actualizarTablero(tablero);
        }
    }

    private void restarVida() {
        vidas--;
        labelVidas.setText(String.valueOf(vidas));

        if (vidas <= 0) {
            JOptionPane.showMessageDialog(null, "¡Has perdido! No te quedan vidas.", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
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
    private void actualizarTablero(Tablero tablero) {
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
