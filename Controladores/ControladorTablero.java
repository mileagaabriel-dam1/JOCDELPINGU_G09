package Controladores;

import Modelo.Tablero;
import Modelo.Casilla;

public class ControladorTablero {
    private Tablero tablero;
    
    public ControladorTablero() {
        this.tablero = new Tablero();
    }
    //Creamos el controlador para los métodos del tablero
    
    public Casilla getCasilla(int posicion) {
        return tablero.getCasilla(posicion);
    }
    //Método para obtener las casillas en el tablero según su posición.
    
    public void mostrarTablero() {
        tablero.mostrarTablero();
    }
    //Creamos método para mostrar el tablero por pantalla
    
    public int getTamanoTablero() {
        return tablero.getTamano();
    }
    //Se le asigna un tamaño al tablero, para que no ocupe toda la pantalla
    
    public Tablero getTablero() {
        return tablero;
        //Y aqui se muestra el tablero por pantalla.
    }
}