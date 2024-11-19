package org.example;

import java.util.Scanner;

/**
 * Singleton para obtener una única instancia de Scanner.
 */
public class InputScanner {

    // Instancia única del Scanner
    private static Scanner instance;

    // Constructor privado para evitar instanciación directa
    private InputScanner() {
    }

    /**
     * Devuelve la única instancia del Scanner.
     * Si aún no está creada, la crea.
     * 
     * @return Instancia única del Scanner.
     */
    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }

    /**
     * Método para cerrar el Scanner.
     * Debe ser llamado al final del programa para liberar el recurso.
     */
    public static void close() {
        if (instance != null) {
            instance.close();
            instance = null; // Asegurarse de que no se pueda reutilizar
        }
    }

}
