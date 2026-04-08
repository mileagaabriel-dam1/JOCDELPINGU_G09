package Modelo;

import Vista.GestorSonido;

public class Foca implements Entidad {

    @Override
    public String getNombre() { 
        return "Foca"; 
    }

    @Override
    public String getSimbolo() { 
        return "🦭"; 
    }
    
    @Override
    public String getDescripcion() { 
        return "Te ha encontrado una Foca, cuidado con ella"; 
    }
    
    @Override
    public String interactuar(Jugador jugador) {
        // Reproducir sonido de foca
        GestorSonido.getInstancia().reproducirEfecto("foca");
        
        String mensaje = "  Te ha atrapado una foca ";
        
        int accion = (int)(Math.random() * 4);
        
        switch(accion) {
            case 0:
                mensaje += "  Te ha quitado un pez ";
                jugador.getInventario().quitarPez();
                break;
            case 1:
                mensaje += "  Te ha quitado una bola de nieve ";
                jugador.getInventario().quitarBolaNieve();
                break;
            case 2:
                mensaje += "  Te hace retroceder 2 casillas";
                jugador.setPosicion(Math.max(0, jugador.getPosicion() - 2));
                break;
            case 3:
                mensaje += "  Te has salvado esta vez, cuidado con la siguiente. ";
                break;
        }
        
        return mensaje;
    }
}