package org.example;

/**
 * Representa el tablero con el que interactúa el usuario.
 */
public class Tablero extends LayOutNonograma {

    int[][] solucion;

    public Tablero(Nonograma nonograma) {
        this.solucion = nonograma.getCasillas();
        this.tamano = nonograma.getTamano();
        this.casillas = new int[tamano][tamano];
        this.pistasFilas = nonograma.getPistasFilas();
        this.pistasColumnas = nonograma.getPistasColumnas();
        inicializarCasillas();
    }

    public void inicializarCasillas() {
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                casillas[i][j] = -1;
    }

    public void marcarCasilla(int x, int y, int valor) {
        if (x < 0 || x >= tamano || y < 0 || y >= tamano)
            throw new IllegalArgumentException("Coordenadas fuera de rango");
        if (valor < -1 || valor > 1)
            throw new IllegalArgumentException("Valor no válido");
        int solucion = this.solucion[y][x];
        if (solucion == valor)
            casillas[y][x] = valor;
        else
            throw new IllegalArgumentException("Valor incorrecto");
    }

    public boolean completo() {
        return primeraOcurencia(casillas, -1)[0] == -1;
    }

    public int[] primerVacio() {
        return primeraOcurencia(solucion, 0);
    }

    public int[] pista() {
        // Busca el primer -1 y asigna su valore real. Regresa las coordenadas y el
        // valor
        int[] primerVacio = primeraOcurencia(casillas, -1);
        int[] coordenadas = new int[] { primerVacio[0], primerVacio[1] };
        int valor = solucion[coordenadas[1]][coordenadas[0]];
        marcarCasilla(coordenadas[0], coordenadas[1], valor);
        return new int[] { coordenadas[0], coordenadas[1], valor };
    }

    private int[] primeraOcurencia(int[][] matriz, int valor) {
        for (int i = 0; i < tamano; i++)
            for (int j = 0; j < tamano; j++)
                if (matriz[i][j] == valor)
                    return new int[] { j, i };
        return new int[] { -1, -1 };
    }
}
