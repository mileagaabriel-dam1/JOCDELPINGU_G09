package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class VistaMenu {
    
    private VistaJavaFX principal;
    private HBox vista; 
    // La barra del menú superior
    
    private Button btnNuevaPartida;
    private Button btnSalir;
    
    public VistaMenu(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }
    
    private void crearVista() {
        // Barra azul degradada en la parte superior
        vista = new HBox(20);
        vista.setAlignment(Pos.CENTER_LEFT);
        vista.setPadding(new Insets(15, 25, 15, 25));
        vista.setStyle("-fx-background-color: linear-gradient(to right, #0277bd, #039be5);");
        vista.setPrefHeight(80);
        
        Label titulo = new Label("🐧 JOC DEL PINGÜ 🐧");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setTextFill(Color.WHITE);
        titulo.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0.5, 2, 2);");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); 
        // El spacer empuja los botones hacia la derecha
        
        btnNuevaPartida = new Button("🎮 Nueva Partida");
        btnNuevaPartida.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, black, 3, 0.3, 1, 1);");
        btnNuevaPartida.setOnAction(e -> mostrarDialogoNuevaPartida());
        
        btnSalir = new Button("❌ Salir");
        btnSalir.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, black, 3, 0.3, 1, 1);");
        btnSalir.setOnAction(e -> System.exit(0)); 
        // Cierra la aplicación directamente
        
        // Cambia el color del botón cuando el ratón pasa por encima
        btnNuevaPartida.setOnMouseEntered(e -> 
            btnNuevaPartida.setStyle("-fx-font-size: 14px; -fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, black, 5, 0.5, 2, 2);")
        );
        btnNuevaPartida.setOnMouseExited(e -> 
            btnNuevaPartida.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, black, 3, 0.3, 1, 1);")
        );
        
        HBox botones = new HBox(15, btnNuevaPartida, btnSalir);
        
        vista.getChildren().addAll(titulo, spacer, botones);
    }
    
    private void mostrarDialogoNuevaPartida() {
        // Creamos una ventanita emergente para configurar la partida
    	
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nueva Partida");
        dialog.setHeaderText("🐧 Configuración de la partida");
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField numHumanosField = new TextField();
        numHumanosField.setPromptText("1-3"); 
        // Texto gris de ayuda dentro del campo
        
        CheckBox incluirIACheck = new CheckBox("Incluir IA");
        
        grid.add(new Label("¿Cuántos jugadores humanos?:"), 0, 0);
        grid.add(numHumanosField, 1, 0);
        grid.add(incluirIACheck, 0, 1, 2, 1);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int numHumanos = Integer.parseInt(numHumanosField.getText());
                    boolean incluirIA = incluirIACheck.isSelected();
                    
                    if (numHumanos >= 1 && numHumanos <= 3) {
                        principal.iniciarPartida(numHumanos, incluirIA); 
                        // Arrancamos la partida con los datos introducidos
                    } else {
                        principal.getVistaEventos().agregarEvento("❌ Error: Número de jugadores debe ser 1-3");
                    }
                } catch (NumberFormatException e) {
                    principal.getVistaEventos().agregarEvento("❌ Error: Introduce un número válido"); 
                    // Si no escribió un número, avisamos
                }
            }
        });
    }
    
    public HBox getVista() { return vista; }
    public void mostrar() { vista.setVisible(true); }
    public void ocultar() { vista.setVisible(false); }
}