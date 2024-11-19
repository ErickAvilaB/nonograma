package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Genera un nonograma aleatorio que contiene su solución y las pistas de las
 * filas y columnas.
 */
public class Nonograma extends LayOutNonograma {

    private final long semilla;

    // Constructor que utiliza la semilla del sistema actual
    public Nonograma(int tamano) {
        this(System.currentTimeMillis(), tamano);
    }

    // Constructor con semilla personalizada
    public Nonograma(long semilla, int tamano) {
        this.semilla = semilla;
        build(tamano);
    }

    /**
     * Inicializa el nonograma generando casillas y pistas.
     *
     * @param tamano Tamaño del nonograma.
     */
    private void build(int tamano) {
        validarTamano(tamano);
        this.casillas = new int[tamano][tamano];
        this.pistasFilas = new ArrayList<>();
        this.pistasColumnas = new ArrayList<>();
        generarCasillas();
        calcularPistas();
    }

    /**
     * Valida que el tamaño del nonograma esté en el rango permitido.
     *
     * @param tamano Tamaño a validar.
     */
    private void validarTamano(int tamano) {
        if (tamano < 5 || tamano > 15)
            throw new IllegalArgumentException("El tamaño debe estar entre 5 y 15.");
        this.tamano = tamano;
    }

    /*
     * NOTA
     * El algoritmo de generación de los nonogramas es parcialmente aleatorio.
     * Utiliza random para rellenar las casillas, pero las pistas se calculan
     * a partir de las casillas generadas. Por lo que las pistas son siempre
     * correctas y no se generan aleatoriamente. Esto garantiza que el nonograma
     * siempre se pueda resolver.
     * 
     * Observese el test NonogramaTest.java donde se verifica que los nonogramas
     * generados siempre tienen una solución.
     */

    /**
     * Genera un tablero de casillas aleatorias (0 o 1) utilizando la semilla.
     */
    private void generarCasillas() {
        Random random = new Random(semilla);
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                casillas[i][j] = random.nextInt(2);
    }

    /**
     * Calcula las pistas para filas y columnas basándose en las casillas generadas.
     */
    private void calcularPistas() {
        // Calcular pistas para filas
        casillasToPistas(pistasFilas, true);

        // Calcular pistas para columnas
        casillasToPistas(pistasColumnas, false);
    }

    /**
     * Genera las pistas a partir de filas o columnas.
     */
    private void casillasToPistas(List<List<Integer>> pistas, boolean esFila) {
        for (int i = 0; i < tamano; i++) {
            int[] linea = esFila ? casillas[i] : getColumna(i);
            pistas.add(calcularPistasLinea(linea));
        }
    }

    /**
     * Calcula las pistas para una línea (fila o columna).
     *
     * @param linea Array que representa una fila o columna.
     * @return Lista de pistas para la línea.
     */
    private List<Integer> calcularPistasLinea(int[] linea) {
        List<Integer> pistas = new ArrayList<>();
        int contador = 0;

        for (int casilla : linea)
            if (casilla == 1)
                contador++;
            else if (contador > 0) {
                pistas.add(contador);
                contador = 0;
            }

        // Agregar última secuencia si existe
        if (contador > 0)
            pistas.add(contador);
        return pistas;
    }

    /**
     * Obtiene una columna del tablero.
     *
     * @param indiceColumna Índice de la columna a obtener.
     * @return Array que representa la columna.
     */
    private int[] getColumna(int indiceColumna) {
        return IntStream.range(0, tamano)
                .map(i -> casillas[i][indiceColumna])
                .toArray();
    }

}
