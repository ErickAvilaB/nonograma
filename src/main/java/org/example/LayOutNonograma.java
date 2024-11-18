package org.example;

import java.util.List;

/**
 * Clase que define los atributos de un nonogrma.
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

    public String graficar() {
        StringBuilder builder = new StringBuilder();

        // Calcular el espacio necesario para las pistas de las filas
        int maxPistasFila = pistasFilas.stream().mapToInt(pistas -> pistas.size() * 2 - 1) // Cada número + espacio
                .max().orElse(0);

        // Preparar las pistas de las columnas
        int maxAlturaPistasColumnas = pistasColumnas.stream().mapToInt(List::size).max().orElse(0);

        // Crear cabecera con pistas de las columnas
        for (int nivel = 0; nivel < maxAlturaPistasColumnas; nivel++) {
            builder.append(" ".repeat(maxPistasFila)).append(" ");
            for (int col = 0; col < tamano; col++) {
                List<Integer> pistas = pistasColumnas.get(col);
                int indicePista = nivel - (maxAlturaPistasColumnas - pistas.size());
                if (indicePista >= 0) {
                    builder.append(String.format("%2d", pistas.get(indicePista)));
                } else {
                    builder.append("  "); // Espacio vacío
                }
            }
            builder.append("\n");
        }

        // Agregar filas con pistas y casillas
        for (int fila = 0; fila < tamano; fila++) {
            // Pistas de la fila
            List<Integer> pistas = pistasFilas.get(fila);
            String pistasFila = pistas.stream().map(Object::toString).reduce((a, b) -> a + " " + b).orElse("");
            builder.append(String.format("%-" + maxPistasFila + "s ", pistasFila));

            // Casillas del tablero
            for (int col = 0; col < tamano; col++) {
                String simbolo = "?";
                int valorCasilla = casillas[fila][col];
                simbolo = switch (valorCasilla) {
                    case 0 -> "□";
                    case 1 -> "■";
                    case -1 -> "X";
                    default -> simbolo;
                };
                builder.append(simbolo).append(" ");
            }

            // Índice de la fila (basado en 0)
            builder.append(String.format("%2d", fila)).append("\n");
        }

        // Agregar índices de las columnas debajo del tablero
        builder.append(" ".repeat(maxPistasFila)).append(" ");
        for (int col = 0; col < tamano; col++) {
            builder.append(String.format("%2d", col));
        }
        builder.append("\n");

        // simbologia
        builder.append("■ = Casilla llena\n");
        builder.append("□ = Casilla vacía\n");
        builder.append("X = Casilla sin marcar");

        return builder.toString();
    }

}
