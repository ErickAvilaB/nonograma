package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new Main().iniciarJuego();
    }

    private void iniciarJuego() {
        try (Scanner scanner = InputScanner.getInstance()) {
            System.out.println();
            System.out.println("Bienvenido a Nonograma!\n");
            System.out.println("Presiona Ctrl+C en cualquier momento para salir.\n");

            // Solicitar nivel al usuario
            int nivel;
            try {
                nivel = solicitaNivel(scanner);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            Tablero tablero = crearTablero(nivel);
            jugar(tablero, scanner);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    private void jugar(Tablero tablero, Scanner scanner) {
        int vidas = 3;
        int pistas = 3;

        System.out.println(tablero.graficar());

        while (!tablero.completo() && vidas > 0) {
            System.out.println("Vidas: " + vidas + " - Pistas: " + pistas + "\n");
            System.out.print("Ingresa 'x y valor' para marcar o '-1 -1 -1' para una pista: ");

            int[] entrada = obtenerEntrada(scanner);
            if (entrada == null) {
                System.out.println("Entrada inválida.");
                System.out.println(tablero.graficar());
                continue;
            }

            int x = entrada[0], y = entrada[1], valor = entrada[2];

            // Si el usuario pide una pista
            if (x == -1 && y == -1 && valor == -1) {
                if (pistas <= 0) {
                    System.out.println("No tienes pistas disponibles");
                    continue;
                }
                pistas--;
                mostrarPista(tablero);
                continue;
            }

            // Intentar marcar una casilla en el tablero
            if (marcarCasilla(tablero, x, y, valor) == 0) {
                vidas--;
            }

            System.out.println(tablero.graficar());
        }

        mostrarResultado(vidas);
    }

    private void mostrarPista(Tablero tablero) {
        int[] pista = tablero.pista();
        System.out.println("Pista: " + pista[0] + " " + pista[1] + " " + pista[2]);
        System.out.println(tablero.graficar());
    }

    private int marcarCasilla(Tablero tablero, int x, int y, int valor) {
        // 0: incorrecto, 1: correcto, 2: error
        try {
            tablero.marcarCasilla(x, y, valor);
            return 1;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return 2;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private void mostrarResultado(int vidas) {
        if (vidas == 0) {
            System.out.println("Perdiste! Te quedaste sin vidas");
        } else {
            System.out.println("Felicidades! Has completado el nonograma");
        }
    }

    private int[] obtenerEntrada(Scanner scanner) {
        try {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int valor = scanner.nextInt();
            return new int[] { x, y, valor };
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar el buffer de entrada
            return null;
        }
    }

    private Tablero crearTablero(int nivel) {
        Nonograma nonograma = switch (nivel) {
            case 1, 2 -> new Nonograma(5);
            case 3, 4 -> new Nonograma(10);
            case 5 -> new Nonograma(15);
            default -> throw new IllegalArgumentException("Nivel inválido");
        };

        Tablero tablero = new Tablero(nonograma);

        // Marcar una casilla vacía para los niveles 1 y 4
        if (nivel == 1 || nivel == 4) {
            int[] primerVacio = tablero.primerVacio();
            tablero.marcarCasilla(primerVacio[0], primerVacio[1], 0);
        }

        return tablero;
    }

    private int solicitaNivel(Scanner scanner) {
        System.out.println("Niveles:");
        System.out.println("  1. Principiante (5x5) - señala una casilla vacía");
        System.out.println("  2. Fácil (5x5)");
        System.out.println("  3. Medio (10x10)");
        System.out.println("  4. Intermedio (10x10) - señala una casilla vacía");
        System.out.println("  5. Difícil (15x15)\n");
        System.out.print("Ingresa el número del nivel que deseas jugar: ");

        int nivel;
        try {
            nivel = scanner.nextInt();
        } catch (Exception e) {
            throw new RuntimeException("Ingrese un número válido");
        }

        if (nivel < 1 || nivel > 5) {
            throw new RuntimeException("Nivel inválido");
        }

        return nivel;
    }
}