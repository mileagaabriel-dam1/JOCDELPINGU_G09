package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private List<Casilla> casillas;
    private static final int TAMANO = 50;
    
    public Tablero() {
        casillas = new ArrayList<>();
        generarTablero();
    }
    
    private void generarTablero() {
        casillas.add(new Casilla(0, TipoCasilla.PINGUINO));
        
        TipoCasilla[] tipos = TipoCasilla.values();
        for(int i = 1; i < TAMANO; i++) {
            int aleatorio = (int)(Math.random() * tipos.length);
            casillas.add(new Casilla(i, tipos[aleatorio]));
        }
    }
    
    public Casilla getCasilla(int index) {
        return casillas.get(index);
    }
    
    public int getTamano() { return TAMANO; }
    
    public void mostrarTablero() {
        System.out.println("\n--- TABLERO (50 casillas) ---");
        for(int i = 0; i < TAMANO; i++) {
            if(i % 10 == 0 && i > 0) System.out.println();
            System.out.print(i + ":" + casillas.get(i).getSimbolo() + " ");
        }
        System.out.println("\n");
    }
    
    public List<Casilla> getCasillas() {
        return casillas;
    }
}