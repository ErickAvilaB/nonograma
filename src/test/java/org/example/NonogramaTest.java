package org.example;

import java.util.List;
import java.util.Random;

import org.junit.Test;

public class NonogramaTest {
    // Dadas todas las casillas, cuenta el total de casillas llenas
    private int cuentaLlenos(int[][] tablero) {
        int cuenta = 0;
        for (int[] fila : tablero)
            for (int casilla : fila)
                if (casilla == 1)
                    cuenta++;
        return cuenta;
    }

    // Dadas las pistas de las filas, cuenta el total de pistas
    private int cuentaPistasFilas(List<List<Integer>> pistasFilas) {
        int cuenta = 0;
        for (List<Integer> pistas : pistasFilas)
            for (int pista : pistas)
                cuenta += pista;
        return cuenta;
    }

    // Dadas las pistas de las columnas, cuenta el total de pistas llenas
    private int cuentaPistasColumnas(List<List<Integer>> pistasColumnas) {
        int cuenta = 0;
        for (List<Integer> pistas : pistasColumnas)
            for (int pista : pistas)
                cuenta += pista;
        return cuenta;
    }

    // Prueba que el nonograma tenga solución. Deben coincidir el total de casillas,
    // pistas de filas y columnas
    @Test
    public void tieneSolucion() {
        Random random = new Random();
        // numero entre 5 y 15
        int tamano = random.nextInt(11) + 5;
        for (int i = 0; i < 100; i++) {
            Nonograma nonograma = new Nonograma(tamano);
            int[][] tablero = nonograma.getCasillas();
            List<List<Integer>> pistasFilas = nonograma.getPistasFilas();
            List<List<Integer>> pistasColumnas = nonograma.getPistasColumnas();
            int llenos = cuentaLlenos(tablero);
            int pistasFilasTotal = cuentaPistasFilas(pistasFilas);
            int pistasColumnasTotal = cuentaPistasColumnas(pistasColumnas);
            // El número de casillas llenas debe ser igual al total de pistas de filas y
            // columnas
            assert llenos == pistasFilasTotal;
            assert llenos == pistasColumnasTotal;
        }
    }
}