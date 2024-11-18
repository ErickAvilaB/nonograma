package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        System.out.println("Bienvenido a Nonograma!");

        int nivel = main.solicitaNivel();

        Tablero tablero = main.crearTablero(nivel);

        System.out.println(tablero.graficar());

        int[] pista = tablero.pista();
        int[] pista1 = tablero.pista();
        int[] pista2 = tablero.pista();
        int[] pista3 = tablero.pista();

        System.out.println(tablero.graficar());
    }

    private Tablero crearTablero(int nivel) {
        Nonograma nonograma = null;
        switch (nivel) {
            case 1, 2:
                nonograma = new Nonograma(5);
                break;
            case 3, 4:
                nonograma = new Nonograma(10);
                break;
            case 5:
                nonograma = new Nonograma(15);
                break;
            default:
                nonograma = new Nonograma(5);
        }
        Tablero tablero = new Tablero(nonograma);
        if (nivel == 1 || nivel == 4) {
            int[] primerVacio = tablero.primerVacio();
            tablero.marcarCasilla(primerVacio[0], primerVacio[1], 0);
        }
        return tablero;
    }

    private int solicitaNivel() {
        System.out.println("Niveles:");
        System.out.println("1. Principiante (5x5) - señala una casilla vacia");
        System.out.println("2. Fácil (5x5)");
        System.out.println("3. Medio (10x10)");
        System.out.println("4. Intermedio (10x10) - señala una casilla vacia");
        System.out.println("5. Difícil (15x15)");
        System.out.println("Ingresa el número del nivel que deseas jugar:");

        Scanner scanner = new Scanner(System.in);
        int nivel = 0;

        try {
            nivel = scanner.nextInt();
        } catch (Exception e) {
            scanner.close();
            throw new RuntimeException("Ingresa un número válido");
        }
        scanner.close();

        if (nivel < 1 || nivel > 5) {
            throw new RuntimeException("Nivel inválido");
        }
        return nivel;
    }

}