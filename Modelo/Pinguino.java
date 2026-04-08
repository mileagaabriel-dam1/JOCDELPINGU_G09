package Modelo;

public class Pinguino implements Entidad {

    @Override
    public String getNombre() { 
        return "Pingüino"; 
    }
    
    @Override
    public String getSimbolo() { 
        return "🐧"; 
    }

    @Override
    public String getDescripcion() { 
        return "Punto de salida"; 
    }
    
    @Override
    public String interactuar(Jugador jugador) {
        return "  🐧 Estás en la casilla de los pingüinos.";
    }
}