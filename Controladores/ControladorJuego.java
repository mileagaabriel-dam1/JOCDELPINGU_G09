package Controladores; 
//Aquí decimos en qué "carpeta lógica" esta este archivo

public class ControladorJuego { 
	//Esta clase es como el "jefe" que tiene a todos los demás controladores
	
    private ControladorTablero controladorTablero;
//Guardamos aquí al controlador que maneja el tablero
    
    private ControladorJugador controladorJugador; 
    //Guardamos aquí al controlador que maneja a los jugadores
    
    private ControladorTurnos controladorTurnos; 
    //Guardamos aquí al controlador que maneja los turnos
    
    private ControladorEventos controladorEventos; 
    //Guardamos aquí al controlador que maneja los eventos de las casillas

    public ControladorJuego() { 
    	//Cuando se crea el juego, creamos también todos los controladores que necesita
    	
        this.controladorTablero = new ControladorTablero(); 
        //Creamos el controlador del tablero
        
        this.controladorJugador = new ControladorJugador(); 
        //Creamos el controlador de los jugadores
        
        this.controladorTurnos = new ControladorTurnos(); 
        //Creamos el controlador de los turnos
        
        this.controladorEventos = new ControladorEventos(); 
        //Creamos el controlador de los eventos
    }

    public ControladorTablero getControladorTablero() { 
    	//Método para que otros puedan obtener el controlador del tablero
    	
        return controladorTablero; 
        //Devolvemos el controlador del tablero
    }

    public ControladorJugador getControladorJugador() { 
    	//Método para que otros puedan obtener el controlador de jugadores
    	
        return controladorJugador; 
        //Devolvemos el controlador de jugadores
    }

    public ControladorTurnos getControladorTurnos() { 
    	//Método para que otros puedan obtener el controlador de turnos
    	
        return controladorTurnos; 
        //Devolvemos el controlador de turnos
    }

    public ControladorEventos getControladorEventos() { 
    	//Método para que otros puedan obtener el controlador de eventos
    	
        return controladorEventos; 
        //Devolvemos el controlador de eventos
    }
}