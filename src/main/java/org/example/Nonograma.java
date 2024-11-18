package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Genera un nonograma aleatorio contiene su solución y las pistas de las filas
 * y columnas.
 */
public class Nonograma extends LayOutNonograma {

    private final long semilla;

    public Nonograma(int tamano) {
        this.semilla = System.currentTimeMillis();
        build(tamano);
    }

    public Nonograma(int tamano, long semilla) {
        this.semilla = semilla;
        build(tamano);
    }

    private void build(int tamano) {
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
        Random random = new Random(semilla);
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                casillas[i][j] = random.nextInt(2);
    }

    private void calcularPistas() {
        for (int i = 0; i < tamano; i++) {
            pistasFilas.add(calcularPistasLinea(casillas[i]));
            pistasColumnas.add(calcularPistasLinea(getColumna(i)));
        }
    }

    private List<Integer> calcularPistasLinea(int[] fila) {
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

    private int[] getColumna(int j) {
        int[] columna = new int[tamano];
        for (int i = 0; i < tamano; i++)
            columna[i] = casillas[i][j];
        return columna;
    }

}