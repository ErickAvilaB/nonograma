package org.example;

public class Jugador {

    public int vidas;
    public int pistas;

    public Jugador() {
        this.vidas = 3;
        this.pistas = 3;
    }

    public void restarVida() {
        if (this.vidas > 0)
            this.vidas--;
    }

    public void restarPista() {
        if (this.pistas > 0)
            this.pistas--;
    }

    public boolean vivo() {
        return this.vidas > 0;
    }

    public boolean tienePistas() {
        return this.pistas > 0;
    }

    public int vidas() {
        return this.vidas;
    }

    public int pistas() {
        return this.pistas;
    }

}
