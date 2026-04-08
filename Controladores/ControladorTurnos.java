package Controladores;

import Modelo.Jugador;
import java.util.List;

public class ControladorTurnos {
    private List<Jugador> jugadores;
    private int turnoActual;
    
    public ControladorTurnos() {
        this.turnoActual = 0;
        //Método para los turnos del jugador e IA
    }
    
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
    }
    //Método para saber el turno actual, de quien es, y que turno es del jugador
    
    public Jugador getJugadorActual() {
        if (jugadores != null && !jugadores.isEmpty()) {
            return jugadores.get(turnoActual);
        }
        //Segun el turno, depende del jugador que acaba de tener el turno, cambiar al otro jugador
        
        return null;
    }
    
    public void siguienteTurno() {
        if (jugadores != null && !jugadores.isEmpty()) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        }
        //Aqui es donde se hace el cambio de turno
    }
    
    public List<Jugador> getJugadores() {
        return jugadores;
        //Y aqui el método para ver los jugadores
    }
}