package Controladores; 

// Aquí decimos en qué "carpeta lógica" esta este archivo

import Modelo.*;
import Vista.GestorSonido;

public class ControladorEventos { 
	
	// Creamos la clase que se encarga de gestionar lo que pasa en cada casilla

    public String procesarCasilla(Jugador jugador, Casilla casilla) { 
    	
    	// Este método recibe al jugador y la casilla donde cayó, y nos dice que paso
    	
        Entidad entidad = null; 
        
        // De momento no hay ningún personaje/cosa con quien interactuar
        String mensaje = ""; 
        
        // El mensaje que le mostraremos al jugador empieza vacío

        switch(casilla.getTipo()) { 
        
        // Miramos que tipo de casilla es
        
            case PINGUINO: 
            	
            	// Si hay un pingüino en la casilla
            	
                entidad = new Pinguino(); 
                
                // creamos un pingüino
                break; 
                

            case OSO: 
            	
            	// Si hay un oso en la casilla
            	
                entidad = new Oso();  
                //creamos un oso
                
                GestorSonido.getInstancia().reproducirEfecto("oso"); 
                //ponemos el sonido del oso
                
                break;
                
            case AGUJERO: 
            	//Si hay un agujero en la casilla
            	
                entidad = new AgujeroHielo(); 
                //creamos el agujero
                
                GestorSonido.getInstancia().reproducirEfecto("agujero"); 
                //ponemos el sonido de caer en él
                
                break;
                
            case TRINEO: 
            	//Si hay un trineo en la casilla
            	
                entidad = new Trineo(); 
                //creamos el trineo
                
                GestorSonido.getInstancia().reproducirEfecto("trineo"); 
                //ponemos el sonido del trineo
                
                break; 
                
            case INTERROGANTE: 
            	//Si la casilla es de sorpresa
            	
                return eventoAleatorio(jugador); 
                //llamamos al método de eventos aleatorios y devolvemos lo que pase
        }

        if (entidad != null) { 
        	//Si al final tenemos algún personaje/cosa
        	
            mensaje = entidad.interactuar(jugador); 
            //hacemos que interactúe con el jugador y guardamos lo que nos diga
        }

        return mensaje; 
        //Devolvemos el mensaje para que se muestre en pantalla
    }

    private String eventoAleatorio(Jugador jugador) { 
    	//Este método decide qué evento sorpresa le toca al jugador
    	
        int evento = (int)(Math.random() * 5); 
        //Sacamos un número del 0 al 4 al azar para ver qué evento sale
        
        String mensaje = "  ❓ "; 
        //El mensaje empieza con el símbolo de interrogación

        switch(evento) { 
        //Dependiendo del número que salió
        
            case 0: //si salió 0, toca pez
            	
                mensaje += "¡Evento: Encuentras un pez!"; 
                //Añadimos al mensaje que encontró un pez
                
                jugador.getInventario().agregarPez(); 
                //Le metemos el pez en el inventario
                
                GestorSonido.getInstancia().reproducirEfecto("item"); 
                //Sonido de recoger algo
                
                break;
                
            case 1: //si salió 1, toca bola de nieve
            	
                mensaje += "¡Evento: Encuentras bolas de nieve!"; 
                //Añadimos al mensaje que encontró bolas de nieve
                
                jugador.getInventario().agregarBolaNieve(); 
                //Le metemos la bola de nieve en el inventario
                
                GestorSonido.getInstancia().reproducirEfecto("item"); 
                //Sonido de recoger algo
                
                break;
                
            case 2: //si salió 2, toca dado extra
            	
                mensaje += "¡Evento: Ganas un dado extra!"; 
                //Añadimos al mensaje que ganó un dado
                
                jugador.getInventario().agregarDado(); 
                //Le damos un dado más al jugador
                
                GestorSonido.getInstancia().reproducirEfecto("item"); 
                //Sonido de recoger algo
                
                break;
                
            case 3: //si salió 3, puede que pierda un dado
            	
                if (jugador.getInventario().getDados() > 1) { 
                	//Miramos si tiene más de un dado para no dejarlo sin ninguno
                	
                    mensaje += "¡Evento: Pierdes un dado!"; 
                    //Le decimos que pierde un dado
                    
                    jugador.getInventario().quitarDado(); 
                    //Le quitamos el dado
                    
                } else { 
                	//Si solo tiene uno, no se lo quitamos
                	
                    mensaje += "¡Evento: Casi pierdes un dado!"; 
                    //Le avisamos de que se salvó por los pelos
                }
                
                break;
                
            case 4: //si salió 4, aparece una foca
            	
                Foca foca = new Foca(); 
                //Creamos la foca
                
                mensaje = foca.interactuar(jugador); 
                //Hacemos que la foca haga lo suyo con el jugador
                
                break;
        }

        return mensaje; 
        //Devolvemos el mensaje con lo que pasó
    }
}