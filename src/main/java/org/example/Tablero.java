package org.example;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Representa el tablero con el que interactúa el usuario.
 */
public class Tablero extends LayOutNonograma {

    private final int[][] solucion;

    public Tablero(Nonograma nonograma) {
        this.solucion = nonograma.getCasillas();
        this.tamano = nonograma.getTamano();
        this.casillas = new int[tamano][tamano];
        this.pistasFilas = nonograma.getPistasFilas();
        this.pistasColumnas = nonograma.getPistasColumnas();
        inicializarCasillas();
    }

    /**
     * Inicializa todas las casillas del tablero con -1 (sin marcar).
     */
    public void inicializarCasillas() {
        for (int[] fila : casillas)
            java.util.Arrays.fill(fila, -1);
    }

    /**
     * Marca una casilla en las coordenadas dadas con el valor proporcionado.
     * 
     * @param x     Coordenada x (columna).
     * @param y     Coordenada y (fila).
     * @param valor Valor a marcar (-1, 0, 1).
     * @throws IllegalArgumentException Si las coordenadas o el valor son inválidos.
     */
    public void marcarCasilla(int x, int y, int valor) {
        validarCoordenadas(x, y);
        if (valor < -1 || valor > 1)
            throw new IllegalArgumentException("Valor invalido. Debe ser 0 o 1.");
        if (casillas[y][x] != -1)
            throw new IllegalArgumentException("Casilla ya marcada.");

        if (solucion[y][x] == valor) {
            casillas[y][x] = valor;

            if (esFilaCompleta(y))
                rellenarFilaConVacios(y);

            if (esColumnaCompleta(x))
                rellenarColumnaConVacios(x);

        } else
            throw new NoSuchElementException("Valor incorrecto.");
    }

    /**
     * Verifica si todas las casillas llenas de la fila están marcadas.
     *
     * @param fila Índice de la fila a verificar.
     * @return true si todas las casillas llenas están marcadas, false en caso
     *         contrario.
     */
    private boolean esFilaCompleta(int fila) {
        for (int i = 0; i < tamano; i++)
            if (solucion[fila][i] == 1 && casillas[fila][i] != 1)
                return false;
        return true;
    }

    /**
     * Verifica si todas las casillas llenas de la columna están marcadas.
     *
     * @param columna Índice de la columna a verificar.
     * @return true si todas las casillas llenas están marcadas, false en caso
     *         contrario.
     */
    private boolean esColumnaCompleta(int columna) {
        for (int i = 0; i < tamano; i++)
            if (solucion[i][columna] == 1 && casillas[i][columna] != 1)
                return false;
        return true;
    }

    /**
     * Rellena con ceros todas las casillas vacías en una fila.
     *
     * @param fila Índice de la fila a rellenar.
     */
    private void rellenarFilaConVacios(int fila) {
        for (int i = 0; i < tamano; i++)
            if (casillas[fila][i] == -1)
                casillas[fila][i] = 0; // Marcar como vacío
    }

    /**
     * Rellena con ceros todas las casillas vacías en una columna.
     *
     * @param columna Índice de la columna a rellenar.
     */
    private void rellenarColumnaConVacios(int columna) {
        for (int i = 0; i < tamano; i++)
            if (casillas[i][columna] == -1)
                casillas[i][columna] = 0; // Marcar como vacío
    }

    /**
     * Verifica si todas las casillas han sido completadas correctamente.
     * 
     * @return true si el tablero está completo, false en caso contrario.
     */
    public boolean completo() {
        return buscarPrimeraOcurrencia(casillas, -1).isEmpty();
    }

    /**
     * Proporciona una pista marcando la primera casilla sin marcar en el tablero
     * con el valor correcto de la solución.
     * 
     * @return Un array con las coordenadas [x, y, valor] de la casilla marcada.
     */
    public int[] pista() {
        Optional<int[]> primeraNoMarcada = buscarPrimeraOcurrencia(casillas, -1);
        if (primeraNoMarcada.isPresent()) {
            int x = primeraNoMarcada.get()[0];
            int y = primeraNoMarcada.get()[1];
            int valor = solucion[y][x];
            marcarCasilla(x, y, valor);
            return new int[] { x, y, valor };
        }
        return new int[] { -1, -1, -1 };
    }

    /**
     * Marca la primera casilla vacía en la solución como 0.
     */
    public void pistaInicio() {
        int[] pista = primerVacio();
        if (pista[0] != -1) {
            casillas[pista[1]][pista[0]] = 0;
        }
    }

    private int[] primerVacio() {
        return buscarPrimeraOcurrencia(solucion, 0).orElse(new int[] { -1, -1 });
    }

    /**
     * Busca la primera ocurrencia de un valor en una matriz.
     * 
     * @param matriz La matriz en la que buscar.
     * @param valor  El valor a buscar.
     * @return Un Optional que contiene un array con las coordenadas [x, y] si se
     *         encuentra el valor, o un Optional vacío si no se encuentra.
     */
    private Optional<int[]> buscarPrimeraOcurrencia(int[][] matriz, int valor) {
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                if (matriz[i][j] == valor)
                    return Optional.of(new int[] { j, i });
        return Optional.empty();
    }

    /**
     * Valida que las coordenadas proporcionadas estén dentro del rango del tablero.
     * 
     * @param x Coordenada x (columna).
     * @param y Coordenada y (fila).
     * @throws IllegalArgumentException Si las coordenadas están fuera del rango.
     */
    private void validarCoordenadas(int x, int y) {
        if (x < 0 || x >= tamano || y < 0 || y >= tamano)
            throw new IllegalArgumentException("Coordenadas fuera de rango.");
    }

}
