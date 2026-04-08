package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;
import Controladores.*;
import Modelo.*;

public class VistaJuego {
    
    private VistaJavaFX principal;
    private VBox vista;
    private Button btnLanzarDado;
    private Label dadoLabel; 
    // El emoji que muestra el dado
    
    private boolean juegoActivo = false; 
    // El juego empieza parado
    
    public VistaJuego(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }
    
    private void crearVista() {
        // Panel naranja con sombra
        vista = new VBox(20);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(20));
        vista.setStyle("-fx-background-color: #ffb74d; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        vista.setPrefWidth(280);
        
        Label titulo = new Label("🎲 LANZAMIENTO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-text-fill: white;");
        
        dadoLabel = new Label("🎲");
        dadoLabel.setFont(Font.font("Segoe UI Emoji", 60));
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
        
        vista.getChildren().addAll(titulo, dadoLabel, btnLanzarDado);
    }
    
    public void lanzarDado() {
        if (!juegoActivo) return; 
        // Si el juego no está activo no hacemos nada
        
        ControladorTurnos controladorTurnos = principal.getControladorTurnos();
        ControladorEventos controladorEventos = principal.getControladorJuego().getControladorEventos();
        Jugador jugadorActual = controladorTurnos.getJugadorActual();
        
        if (jugadorActual == null) return;
        
        btnLanzarDado.setDisable(true); 
        // Desactivamos el botón mientras se anima el dado
        
        animarDado(() -> {
            int dado = (int)(Math.random() * 6) + 1; 
            // Número aleatorio entre 1 y 6
            
            dadoLabel.setText(getDadoEmoji(dado));
            
            int posOrigen = jugadorActual.getPosicion();
            int nuevaPos = Math.min(posOrigen + dado, 49); 
            // No dejamos que pase de la casilla 49
            
            principal.getVistaEventos().agregarEvento("🎲 " + jugadorActual.getNombre() + " lanza: " + dado);
            
            VistaTableroConImagenes tablero = principal.getVistaTablero();
            tablero.animarMovimiento(jugadorActual, posOrigen, nuevaPos, () -> {
                
                jugadorActual.setPosicion(nuevaPos);
                principal.getVistaEventos().agregarEvento("📍 Posición: " + (nuevaPos + 1));
                
                // Procesamos lo que pasa en la casilla donde cayó el jugador
                
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
                    juegoActivo = false;
                    btnLanzarDado.setDisable(true);
                    return;
                }
                
                controladorTurnos.siguienteTurno();
                principal.getVistaJugador().actualizar(controladorTurnos);
                
                btnLanzarDado.setDisable(false);
                
                // Si le toca a la IA, lanzamos el dado automáticamente
                Jugador siguiente = controladorTurnos.getJugadorActual();
                if (siguiente != null && siguiente.esIA()) {
                    lanzarDadoIA();
                }
            });
        });
    }
    
    private void animarDado(Runnable callback) {
        // Usamos un hilo aparte para que la animación no congele la pantalla
    	
        Thread animacion = new Thread(() -> {
            for (int i = 0; i < 10; i++) { 
            	// Cambiamos el dado 10 veces para simular que rueda
            	
                final int num = (int)(Math.random() * 6) + 1;
                Platform.runLater(() -> dadoLabel.setText(getDadoEmoji(num)));
                try { Thread.sleep(100); } catch (Exception e) {}
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
    }
    
    public void ocultar() {
        juegoActivo = false;
        btnLanzarDado.setDisable(true);
        vista.setVisible(false);
    }
}