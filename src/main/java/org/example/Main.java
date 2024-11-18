package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tamaño del nonograma (5-15): ");
        int tamano = scanner.nextInt();
        long inicio = System.currentTimeMillis();
        Nonograma nonograma = new Nonograma(tamano);
        System.out.println(nonograma);
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");
    }

}