package Vista;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Controladores.*;
import Modelo.*;

public class VistaJugador {
    
    private VistaJavaFX principal;
    private VBox vista;
    private Label jugadorLabel; 
    // Muestra el nombre del jugador actual
    
    private Label posicionLabel; 
    // Muestra en qué casilla está
    
    private Label inventarioLabel; 
    // Muestra lo que lleva encima
    
    private Label turnoLabel; 
    // Muestra de quién es el turno
    
    public VistaJugador(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }
    
    private void crearVista() {
        // Panel blanco con sombra suave
        vista = new VBox(15);
        vista.setAlignment(Pos.TOP_LEFT);
        vista.setPadding(new Insets(20));
        vista.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        
        Label titulo = new Label("👤 INFORMACIÓN");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-text-fill: #01579b;");
        
        turnoLabel = new Label("⏳ Turno: -");
        turnoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        turnoLabel.setStyle("-fx-text-fill: #f57c00;");
        
        jugadorLabel = new Label("🎮 Jugador: -");
        jugadorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        jugadorLabel.setWrapText(true);
        
        posicionLabel = new Label("📍 Posición: -");
        posicionLabel.setFont(Font.font("Arial", 14));
        
        inventarioLabel = new Label("📦 Inventario: -");
        inventarioLabel.setFont(Font.font("Arial", 13));
        inventarioLabel.setWrapText(true); 
        // Si el texto es largo, salta de línea
        
        Label separador = new Label("───────────");
        separador.setStyle("-fx-text-fill: #90a4ae;");
        
        vista.getChildren().addAll(titulo, turnoLabel, separador, jugadorLabel, posicionLabel, inventarioLabel);
    }
    
    public void actualizar(ControladorTurnos controladorTurnos) {
        Jugador actual = controladorTurnos.getJugadorActual();
        if (actual != null) {
            String icono = actual.esIA() ? "🤖" : "👤"; 
            // Ponemos un icono distinto si es IA o humano
            
            turnoLabel.setText("⏳ Turno: " + actual.getNombre());
            jugadorLabel.setText(icono + " Jugador: " + actual.getNombre() + (actual.esIA() ? " (IA)" : ""));
            posicionLabel.setText("📍 Posición: " + (actual.getPosicion() + 1) + "/50");
            inventarioLabel.setText("📦 " + actual.getInventario().obtenerResumen());
        }
    }
    
    public void limpiar() {
        // Reseteamos todas las etiquetas a su estado inicial
        turnoLabel.setText("⏳ Turno: -");
        jugadorLabel.setText("🎮 Jugador: -");
        posicionLabel.setText("📍 Posición: -");
        inventarioLabel.setText("📦 Inventario: -");
    }
    
    public VBox getVista() { return vista; }
}