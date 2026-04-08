package Modelo;

public class AgujeroHielo implements Entidad {

    @Override
    public String getNombre() { 
        return "Agujero de Hielo"; 
    }
    
    @Override
    public String getSimbolo() { 
        return "🕳️"; 
    }

    @Override
    public String getDescripcion() {
        return " Has caído en un agujero, retrocedes 3 casillas";
    }

    @Override
    public String interactuar(Jugador jugador) {
        int nuevaPos = Math.max(0, jugador.getPosicion() - 3);
        jugador.setPosicion(nuevaPos);
        return "  Te has caido en un agujero, Retrocedes 3 casillas";
    }
}