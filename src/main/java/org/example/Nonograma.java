package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nonograma {

    private int tamano;
    private final int[][] casillas;
    private final List<List<Integer>> pistasFilas;
    private final List<List<Integer>> pistasColumnas;

    public Nonograma(int tamano) {
        validarTamano(tamano);
        this.casillas = new int[tamano][tamano];
        this.pistasFilas = new ArrayList<>();
        this.pistasColumnas = new ArrayList<>();
        generarCasillas();
        calcularPistas();
    }

    private void validarTamano(int tamano) {
        if (tamano < 5 || tamano > 15)
            throw new IllegalArgumentException("El tamaño debe estar entre 5 y 20");
        this.tamano = tamano;
    }

    private void generarCasillas() {
        Random random = new Random();
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                casillas[i][j] = random.nextInt(2);
    }

    private List<Integer> calcularPistas(int[] fila) {
        List<Integer> pistas = new ArrayList<>();
        int contador = 0;
        for (int j : fila)
            if (j == 1)
                contador++;
            else if (contador > 0) {
                pistas.add(contador);
                contador = 0;
            }
        if (contador > 0)
            pistas.add(contador);
        return pistas;
    }

    private void calcularPistas() {
        for (int i = 0; i < tamano; i++) {
            pistasFilas.add(calcularPistas(casillas[i]));
            pistasColumnas.add(calcularPistas(getColumna(i)));
        }
    }

    private int[] getColumna(int j) {
        int[] columna = new int[tamano];
        for (int i = 0; i < tamano; i++)
            columna[i] = casillas[i][j];
        return columna;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Calcular la longitud máxima de las pistas de las filas
        int maxPistasFila = pistasFilas.stream()
                .mapToInt(pistas -> pistas.stream().mapToInt(i -> i.toString().length()).sum() + pistas.size() - 1)
                .max()
                .orElse(0);

        // Calcular las pistas de las columnas como cadenas
        List<StringBuilder> pistasColumnasStr = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            StringBuilder colPistas = new StringBuilder();
            List<Integer> pista = pistasColumnas.get(i);
            for (int p : pista)
                colPistas.append(p).append(" ");
            pistasColumnasStr.add(colPistas);
        }

        // Crear cabecera de pistas de las columnas
        int maxAlturaPistasColumnas = pistasColumnas.stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        for (int nivel = 0; nivel < maxAlturaPistasColumnas; nivel++) {
            builder.append(" ".repeat(maxPistasFila)).append(" ");
            for (int i = 0; i < tamano; i++) {
                List<Integer> columna = pistasColumnas.get(i);
                if (nivel < maxAlturaPistasColumnas - columna.size())
                    builder.append("  ");
                else
                    builder.append(
                            String.format("%2d", columna.get(nivel - (maxAlturaPistasColumnas - columna.size()))));
            }
            builder.append("\n");
        }

        // Imprimir filas con pistas a la izquierda
        for (int i = 0; i < tamano; i++) {
            // Pistas de la fila
            List<Integer> filaPistas = pistasFilas.get(i);
            String filaPistasStr = filaPistas.stream()
                    .map(Object::toString)
                    .reduce((a, b) -> a + " " + b)
                    .orElse("");
            builder.append(String.format("%-" + maxPistasFila + "s ", filaPistasStr));

            // Casillas del tablero
            for (int j = 0; j < tamano; j++) {
                builder.append(casillas[i][j] == 1 ? "■" : "□").append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

}