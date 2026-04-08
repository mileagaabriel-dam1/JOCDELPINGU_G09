package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;
import Controladores.*;
import Modelo.*;

public class JuegoConSonido {
    
    private VistaJavaFX principal;
    private VBox vista;
    private Button btnLanzarDado;
    private Button btnSilenciar;
    private Label dadoLabel;
    private boolean juegoActivo = false; 
    // El juego empieza parado
    
    private GestorSonido gestorSonido;
    private boolean silenciado = false; 
    // El sonido empieza activado
    
    public JuegoConSonido(VistaJavaFX principal) {
        this.principal = principal;
        this.gestorSonido = GestorSonido.getInstancia(); 
        // Cogemos el manejador de sonidos que ya existe
        
        crearVista();
    }
    
    private void crearVista() {
        // Creamos el panel principal con estilo naranja y sombra
        vista = new VBox(20);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(20));
        vista.setStyle("-fx-background-color: #ffb74d; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        vista.setPrefWidth(280);
        
        Label titulo = new Label("🎲 LANZAMIENTO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-text-fill: white;");
        
        dadoLabel = new Label("🎲"); 
        // Empieza con el dado genérico
        
        dadoLabel.setFont(Font.font("Segoe UI Emoji", 70));
        dadoLabel.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(gaussian, black, 5, 0.5, 2, 2);");
        
        btnLanzarDado = new Button("LANZAR DADO");
        btnLanzarDado.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        btnLanzarDado.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-padding: 15 20; -fx-background-radius: 30; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");
        btnLanzarDado.setPrefWidth(200);
        btnLanzarDado.setDisable(true); 
        // Desactivado hasta que empiece el juego
        
        btnLanzarDado.setOnAction(e -> lanzarDado());
        
        // Cambia el color del botón cuando el ratón pasa por encima
        btnLanzarDado.setOnMouseEntered(e -> 
            btnLanzarDado.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; -fx-padding: 15 20; -fx-background-radius: 30; -fx-cursor: hand;")
        );
        btnLanzarDado.setOnMouseExited(e -> 
            btnLanzarDado.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-padding: 15 20; -fx-background-radius: 30; -fx-cursor: hand;")
        );
        
        btnSilenciar = new Button("🔊");
        btnSilenciar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnSilenciar.setStyle("-fx-background-color: #607d8b; -fx-text-fill: white; -fx-padding: 8 12; -fx-background-radius: 20; -fx-cursor: hand;");
        btnSilenciar.setOnAction(e -> toggleSilencio());
        
        // Ponemos el botón del dado y el de silencio juntos en una fila
        HBox botonera = new HBox(10, btnLanzarDado, btnSilenciar);
        botonera.setAlignment(Pos.CENTER);
        
        Label estadoSonido = new Label("🎵 Sonido activado");
        estadoSonido.setFont(Font.font("Arial", 10));
        estadoSonido.setStyle("-fx-text-fill: white;");
        
        vista.getChildren().addAll(titulo, dadoLabel, botonera, estadoSonido);
    }
    
    private void toggleSilencio() {
        silenciado = !silenciado; 
        // Cambiamos el estado al contrario de lo que estaba
        
        gestorSonido.setSonidoActivado(!silenciado);
        gestorSonido.setMusicaActivada(!silenciado);
        btnSilenciar.setText(silenciado ? "🔇" : "🔊"); 
        // Cambiamos el emoji según el estado
    }
    
    public void lanzarDado() {
        if (!juegoActivo) return; 
        // Si el juego no está activo no hacemos nada
        
        ControladorTurnos controladorTurnos = principal.getControladorTurnos();
        ControladorEventos controladorEventos = principal.getControladorJuego().getControladorEventos();
        Jugador jugadorActual = controladorTurnos.getJugadorActual();
        
        if (jugadorActual == null) return; 
        // Si no hay jugador tampoco hacemos nada
        
        btnLanzarDado.setDisable(true); 
        // Desactivamos el botón mientras se anima el dado
        
        animarDado(() -> {
            int dado = (int)(Math.random() * 6) + 1; 
            // Número aleatorio entre 1 y 6
            
            dadoLabel.setText(getDadoEmoji(dado));
            gestorSonido.reproducirEfectoDado(dado);
            
            int posOrigen = jugadorActual.getPosicion();
            int nuevaPos = Math.min(posOrigen + dado, 49); 
            // No dejamos que pase de la casilla 49
            
            principal.getVistaEventos().agregarEvento("🎲 " + jugadorActual.getNombre() + " lanza: " + dado);
            
            VistaTableroConImagenes tablero = principal.getVistaTablero();
            tablero.animarMovimiento(jugadorActual, posOrigen, nuevaPos, () -> {
                
                jugadorActual.setPosicion(nuevaPos);
                principal.getVistaEventos().agregarEvento("📍 Posición: " + (nuevaPos + 1));
                
                // Procesamos lo que pasa en la casilla donde ha caido el jugador
                Casilla casilla = principal.getControladorTablero().getCasilla(nuevaPos);
                String mensaje = controladorEventos.procesarCasilla(jugadorActual, casilla);
                
                if (mensaje != null && !mensaje.isEmpty()) {
                    principal.getVistaEventos().agregarEvento(mensaje);
                }
                
                tablero.actualizarPosiciones(principal.getControladorJugador(), controladorTurnos);
                principal.getVistaJugador().actualizar(controladorTurnos);
                
                // Comprobamos si el jugador ha llegado al final y ha ganado
                if (jugadorActual.getPosicion() >= 49) {
                    principal.getVistaEventos().agregarEvento("🎉 ¡" + jugadorActual.getNombre() + " GANA! 🎉");
                    gestorSonido.reproducirEfecto("victoria");
                    juegoActivo = false;
                    btnLanzarDado.setDisable(true);
                    return;
                }
                
                controladorTurnos.siguienteTurno();
                principal.getVistaJugador().actualizar(controladorTurnos);
                
                btnLanzarDado.setDisable(false);
                
                // Si el siguiente jugador es la IA, lanzamos el dado automáticamente
                Jugador siguiente = controladorTurnos.getJugadorActual();
                if (siguiente != null && siguiente.esIA()) {
                    lanzarDadoIA();
                }
            });
        });
    }
    
    private void animarDado(Runnable callback) {
        // Creamos un hilo aparte para que la animación no congele la pantalla
    	
        Thread animacion = new Thread(() -> {
            for (int i = 0; i < 15; i++) { 
            	// Cambiamos el dado 15 veces rapidito para simular que rueda
            	
                final int num = (int)(Math.random() * 6) + 1;
                Platform.runLater(() -> dadoLabel.setText(getDadoEmoji(num)));
                try { Thread.sleep(70); } catch (Exception e) {}
            }
            Platform.runLater(() -> {
                if (callback != null) callback.run();
            });
        });
        animacion.start();
    }
    
    private String getDadoEmoji(int num) {
        // Devolvemos el emoji que corresponde a cada número del dado
        switch(num) {
            case 1: return "⚀";
            case 2: return "⚁";
            case 3: return "⚂";
            case 4: return "⚃";
            case 5: return "⚄";
            case 6: return "⚅";
            default: return "🎲";
        }
    }
    
    public void lanzarDadoIA() {
        // Esperamos 2 segundos antes de que la IA tire para que no sea instantáneo
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (Exception e) {}
            Platform.runLater(() -> {
                if (juegoActivo) {
                    lanzarDado();
                }
            });
        }).start();
    }
    
    public VBox getVista() { return vista; }
    
    public void mostrar() {
        juegoActivo = true;
        btnLanzarDado.setDisable(false);
        dadoLabel.setText("🎲");
        vista.setVisible(true);
        gestorSonido.iniciarMusicaFondo();
        // Arrancamos la música al mostrar el panel
    }
    
    public void ocultar() {
        juegoActivo = false;
        btnLanzarDado.setDisable(true);
        vista.setVisible(false);
        gestorSonido.pausarMusicaFondo(); 
        // Pausamos la música al ocultar el panel
    }
}