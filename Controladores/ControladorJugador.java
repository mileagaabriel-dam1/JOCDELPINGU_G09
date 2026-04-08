package Controladores; 

import Modelo.Jugador; 
import Modelo.TipoJugador; 
import java.util.ArrayList;
import java.util.List;

public class ControladorJugador { 
	//Esta clase se encarga de todo lo relacionado con los jugadores
	
    private List<Jugador> jugadores; 
    //Aquí guardamos la lista con todos los jugadores de la partida
    
    public ControladorJugador() { 
    	//Cuando se crea este controlador
    	
        this.jugadores = new ArrayList<>(); 
        //Aarrancamos con una lista de jugadores vacía
    }
    
    public void inicializarJugadores(int numHumanos, boolean incluirIA) { 
    	//Este método prepara a los jugadores antes de empezar la partida
    	
        jugadores.clear(); 
        //Borramos cualquier jugador que hubiera de antes, empezamos de cero
        
        for(int i = 1; i <= numHumanos; i++) { 
        	//Repetimos esto una vez por cada jugador humano
        	
            jugadores.add(new Jugador("Jugador " + i, "Azul", TipoJugador.HUMANO)); 
            //Creamos un jugador humano y lo añadimos a la lista
        }
        
        if(incluirIA) { // Si se pidió que hubiera IA en la partida
        	
            jugadores.add(new Jugador("IA-Pingu", "Rojo", TipoJugador.IA)); 
            //creamos el jugador IA y lo añadimos a la lista
        }
    }
    
    public List<Jugador> getJugadores() { 
    	//Método para que otros puedan ver la lista de jugadores
    	
        return jugadores; 
        //Devolvemos la lista con todos los jugadores
    }
    
    public void registrarVictoria(Jugador ganador) { 
    	//Este método se llama cuando alguien gana la partida
    	
        System.out.println("¡" + ganador.getNombre() + " ha ganado!"); 
        //Mostramos por pantalla el nombre del ganador
    }
}