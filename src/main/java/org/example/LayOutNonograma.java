package org.example;

import java.util.List;

/**
 * Clase que define los atributos y la representación gráfica de un Nonograma.
 */
public class LayOutNonograma {

    protected int tamano;
    protected int[][] casillas;
    protected List<List<Integer>> pistasFilas;
    protected List<List<Integer>> pistasColumnas;

    public int getTamano() {
        return tamano;
    }

    public int[][] getCasillas() {
        return casillas;
    }

    public List<List<Integer>> getPistasFilas() {
        return pistasFilas;
    }

    public List<List<Integer>> getPistasColumnas() {
        return pistasColumnas;
    }

    /**
     * Genera una representación gráfica del Nonograma en texto.
     *
     * @return String con la representación del Nonograma.
     */
    public String graficar() {
        StringBuilder builder = new StringBuilder();

        // Espacio necesario para las pistas de las filas
        int maxAnchoPistasFila = maxAnchoPistasFila();

        // Altura máxima de las pistas de las columnas
        int maxAlturaPistasColumnas = maxPistasColumnas();

        // Agregar pistas de las columnas como cabecera
        agregarPistasColumnas(builder, maxAnchoPistasFila, maxAlturaPistasColumnas);

        // Agregar filas con pistas de las filas y casillas del tablero
        agregarFilas(builder, maxAnchoPistasFila);

        // Agregar índices de las columnas debajo del tablero
        agregarIndicesColumnas(builder, maxAnchoPistasFila);

        // Agregar leyenda de simbología
        agregarLeyenda(builder);

        return builder.toString();
    }

    // Devuelve el ancho máximo de las pistas de las filas
    private int maxAnchoPistasFila() {
        return pistasFilas.stream()
                .mapToInt(pistas -> pistas.size() * 2 - 1) // Cada número más espacio intermedio
                .max().orElse(0);
    }

    // Devuelve la altura máxima de las pistas de las columnas
    private int maxPistasColumnas() {
        return pistasColumnas.stream()
                .mapToInt(List::size)
                .max().orElse(0);
    }

    // Método auxiliar para agregar las pistas de las columnas
    private void agregarPistasColumnas(StringBuilder builder, int maxAnchoPistasFila, int maxAlturaPistasColumnas) {
        builder.append("\n");
        for (int nivel = 0; nivel < maxAlturaPistasColumnas; nivel++) {
            builder.append(" ".repeat(maxAnchoPistasFila));
            for (int col = 0; col < tamano; col++) {
                List<Integer> pistas = pistasColumnas.get(col);
                int indicePista = nivel - (maxAlturaPistasColumnas - pistas.size());
                if (indicePista >= 0)
                    builder.append(String.format("%2d", pistas.get(indicePista)));
                else
                    builder.append("  "); // Espacio vacío si no hay pista en este nivel
            }
            builder.append("\n");
        }
    }

    // Método auxiliar para agregar las filas con pistas y casillas
    private void agregarFilas(StringBuilder builder, int maxAnchoPistasFila) {
        for (int fila = 0; fila < tamano; fila++) {
            // Pistas de la fila
            String pistasFila = String.join(" ", pistasFilas.get(fila).stream()
                    .map(String::valueOf)
                    .toArray(String[]::new));
            builder.append(String.format("%-" + maxAnchoPistasFila + "s ", pistasFila));

            // Casillas del tablero
            for (int col = 0; col < tamano; col++)
                builder.append(simbologiaCasilla(casillas[fila][col])).append(" ");

            // Índice de la fila
            builder.append(String.format("  %2d", fila)).append("\n");
        }
        builder.append("\n");
    }

    // Método auxiliar para agregar los índices de las columnas
    private void agregarIndicesColumnas(StringBuilder builder, int maxAnchoPistasFila) {
        builder.append(" ".repeat(maxAnchoPistasFila));
        for (int col = 0; col < tamano; col++) {
            int digito = col % 10;
            builder.append(String.format("%2d", digito));
        }
        builder.append("\n\n");
    }

    // Método auxiliar para agregar la leyenda de simbología
    private void agregarLeyenda(StringBuilder builder) {
        builder.append("■ = llena, ");
        builder.append("□ = vacio, ");
        builder.append("X = sin marcar\n");
    }

    // Método auxiliar para determinar el símbolo de una casilla
    private String simbologiaCasilla(int valor) {
        return switch (valor) {
            case 0 -> "□"; // Vacía
            case 1 -> "■"; // Llena
            case -1 -> "X"; // Sin marcar
            default -> "?"; // Valor desconocido
        };
    }

}
