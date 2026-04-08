package Modelo;

public class Oso implements Entidad {

    @Override
    public String getNombre() { 
        return "Oso"; 
    }
    
    @Override
    public String getSimbolo() { 
        return "🐻"; 
    }

    @Override
    public String getDescripcion() { 
        return "El oso te atacará, vuelves al inicio"; 
    }

    @Override
    public String interactuar(Jugador jugador) {
        jugador.setPosicion(0);
        return "  🐻 ¡EL OSO TE HA ATRAPADO! Volvemos al inicio";
    }
}