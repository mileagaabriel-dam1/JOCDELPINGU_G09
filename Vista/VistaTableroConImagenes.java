package Vista;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import Controladores.*;
import Modelo.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VistaTableroConImagenes {
    
    private VistaJavaFX principal;
    private GridPane tableroGrid; 
    // La cuadrícula donde van las casillas
    
    private VBox vista;
    private Map<Integer, StackPane> casillasMap; 
    // Relaciona número de casilla con su panel visual
    
    private Map<String, Image> cacheImagenes; 
    // Guardamos las imágenes ya cargadas para no cargarlas dos veces
    
    private Timeline animacionTurno; 
    // La animación que resalta la casilla del jugador activo
    
    // Posibles rutas donde pueden estar las imágenes
    private static final String[] RUTAS_IMAGENES = {
        "F:/JOCPINGU_09-DAM1/JOCPINGU_09-DAMI/Resources/images/",
        "F:/JOCPINGU_09-DAM1/Resources/images/",
        "./Resources/images/",
        "../Resources/images/",
        "Resources/images/"
    };
    
    private String rutaImagenes = null; 
    // La ruta que funcione, empieza como null
    
    public VistaTableroConImagenes(VistaJavaFX principal) {
        this.principal = principal;
        this.casillasMap = new HashMap<>();
        this.cacheImagenes = new HashMap<>();
        buscarRutaImagenes(); 
        // Buscamos dónde están las imágenes antes de nada
        
        crearVista();
        cargarTodasLasImagenes();
    }
    
    private void buscarRutaImagenes() {
        // Probamos cada ruta hasta encontrar una que exista
        for (String ruta : RUTAS_IMAGENES) {
            File carpeta = new File(ruta);
            if (carpeta.exists() && carpeta.isDirectory()) {
                rutaImagenes = ruta;
                System.out.println("✅ Ruta de imágenes encontrada: " + ruta);
                return;
            }
        }
        System.out.println("⚠️ No se encontró la carpeta de imágenes - Usando emojis");
    }
    
    private void crearVista() {
        // Panel principal azul degradado con borde oscuro
    	
        vista = new VBox(15);
        vista.setPadding(new Insets(20));
        vista.setStyle("-fx-background-color: linear-gradient(to bottom, #90caf9, #64b5f6); -fx-background-radius: 20; -fx-border-color: #0d47a1; -fx-border-width: 4; -fx-border-radius: 18;");
        vista.setAlignment(Pos.CENTER);
        
        HBox tituloBox = new HBox(15);
        tituloBox.setAlignment(Pos.CENTER);
        tituloBox.setPadding(new Insets(5, 0, 15, 0));
        
        Label iconoIzq = new Label("🐧");
        iconoIzq.setFont(Font.font("Segoe UI Emoji", 30));
        
        Label titulo = new Label("TABLERO DE HIELO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setStyle("-fx-text-fill: #01579b; -fx-effect: dropshadow(gaussian, white, 5, 0.7, 2, 2);");
        
        Label iconoDer = new Label("🐧");
        iconoDer.setFont(Font.font("Segoe UI Emoji", 30));
        
        tituloBox.getChildren().addAll(iconoIzq, titulo, iconoDer);
        
        tableroGrid = new GridPane();
        tableroGrid.setHgap(10);
        tableroGrid.setVgap(10);
        tableroGrid.setAlignment(Pos.CENTER);
        tableroGrid.setStyle("-fx-background-color: #b3e5fc; -fx-padding: 20; -fx-background-radius: 20; -fx-border-color: #0288d1; -fx-border-width: 3; -fx-border-radius: 18;");
        
        // Leyenda con los tipos de casilla
        HBox leyenda = new HBox(20);
        leyenda.setAlignment(Pos.CENTER);
        leyenda.setPadding(new Insets(15, 0, 5, 0));
        leyenda.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-background-radius: 15; -fx-padding: 10;");
        
        leyenda.getChildren().addAll(
            crearItemLeyenda("🐧", "Pingüino", "#b3e5fc"),
            crearItemLeyenda("🐻", "Oso", "#ffccbc"),
            crearItemLeyenda("🕳️", "Agujero", "#bcaaa4"),
            crearItemLeyenda("🛷", "Trineo", "#fff9c4"),
            crearItemLeyenda("❓", "Misterio", "#e1bee7")
        );
        
        vista.getChildren().addAll(tituloBox, tableroGrid, leyenda);
    }
    
    private HBox crearItemLeyenda(String emoji, String texto, String color) {
        // Cada ítem de la leyenda es una cajita con el emoji y el nombre del tipo
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle("-fx-background-color: " + color + "; -fx-padding: 5 12; -fx-background-radius: 20; -fx-border-color: #555; -fx-border-radius: 20;");
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font("Segoe UI Emoji", 16));
        
        Label textoLabel = new Label(texto);
        textoLabel.setFont(Font.font("Arial", 12));
        
        item.getChildren().addAll(emojiLabel, textoLabel);
        return item;
    }
    
    private void cargarTodasLasImagenes() {
        if (rutaImagenes == null) return; 
        // Si no hay ruta no intentamos cargar nada
        
        cargarImagen("pinguino", "pinguino.png", "🐧");
        cargarImagen("oso", "oso.png", "🐻");
        cargarImagen("agujero", "agujero.png", "🕳️");
        cargarImagen("trineo", "trineo.png", "🛷");
        cargarImagen("interrogante", "interrogante.png", "❓");
        cargarImagen("foca", "foca.png", "🦭");
    }
    
    private void cargarImagen(String nombre, String archivo, String emojiAlternativo) {
        try {
            File file = new File(rutaImagenes + archivo);
            if (file.exists()) {
                Image img = new Image(file.toURI().toString());
                cacheImagenes.put(nombre, img); 
                // Guardamos la imagen en caché
                
                System.out.println("✅ Imagen cargada: " + nombre);
            } else {
                System.out.println("⚠️ No se encuentra " + archivo + " - Usando emoji: " + emojiAlternativo);
                cacheImagenes.put(nombre, null); 
                // Guardamos null para saber que no hay imagen
            }
        } catch (Exception e) {
            System.out.println("❌ Error cargando " + nombre + ": " + e.getMessage());
            cacheImagenes.put(nombre, null);
        }
    }
    
    public void inicializarTablero(ControladorTablero controladorTablero) {
        tableroGrid.getChildren().clear();
        casillasMap.clear();
        
        for (int i = 0; i < 50; i++) {
            int fila = i / 10; 
            // Cada 10 casillas cambiamos de fila
            
            int columna = i % 10;
            
            Casilla casilla = controladorTablero.getCasilla(i);
            
            StackPane casillaPane = new StackPane();
            casillaPane.setPrefSize(80, 80);
            casillaPane.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #0d47a1; -fx-border-width: 2; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.5, 3, 3);");
            
            String tipoImg = "";
            String emojiAlternativo = "";
            String colorFondo = "";
            
            // Según el tipo de casilla, asignamos imagen, emoji y color de fondo
            switch(casilla.getTipo()) {
                case PINGUINO:
                    tipoImg = "pinguino";
                    emojiAlternativo = "🐧";
                    colorFondo = "#b3e5fc";
                    break;
                case OSO:
                    tipoImg = "oso";
                    emojiAlternativo = "🐻";
                    colorFondo = "#ffccbc";
                    break;
                case AGUJERO:
                    tipoImg = "agujero";
                    emojiAlternativo = "🕳️";
                    colorFondo = "#bcaaa4";
                    break;
                case TRINEO:
                    tipoImg = "trineo";
                    emojiAlternativo = "🛷";
                    colorFondo = "#fff9c4";
                    break;
                case INTERROGANTE:
                    tipoImg = "interrogante";
                    emojiAlternativo = "❓";
                    colorFondo = "#e1bee7";
                    break;
                default:
                    emojiAlternativo = "⬜";
                    colorFondo = "#e0e0e0";
                    break;
            }
            
            casillaPane.setStyle(casillaPane.getStyle() + "; -fx-background-color: " + colorFondo + ";");
            
            // Si tenemos la imagen la mostramos, si no usamos el emoji
            if (cacheImagenes.containsKey(tipoImg) && cacheImagenes.get(tipoImg) != null) {
                ImageView imgView = new ImageView(cacheImagenes.get(tipoImg));
                imgView.setFitWidth(60);
                imgView.setFitHeight(60);
                imgView.setPreserveRatio(true);
                casillaPane.getChildren().add(imgView);
            } else {
                Label emojiLabel = new Label(emojiAlternativo);
                emojiLabel.setFont(Font.font("Segoe UI Emoji", 40));
                casillaPane.getChildren().add(emojiLabel);
            }
            
            // Número de casilla en la esquina superior izquierda
            Label numeroLabel = new Label(String.valueOf(i));
            numeroLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            numeroLabel.setStyle("-fx-text-fill: #333; -fx-background-color: rgba(255,255,255,0.9); -fx-padding: 3 6; -fx-background-radius: 8; -fx-border-color: #999; -fx-border-radius: 6;");
            StackPane.setAlignment(numeroLabel, Pos.TOP_LEFT);
            StackPane.setMargin(numeroLabel, new Insets(3, 0, 0, 3));
            casillaPane.getChildren().add(numeroLabel);
            
            // Tooltip que aparece al pasar el ratón por encima
            String tooltipText = "🏷️ Casilla " + i + "\n" +
                                "📌 Tipo: " + casilla.getTipo() + "\n" +
                                "📝 " + casilla.getDescripcion();
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setFont(Font.font(12));
            tooltip.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-padding: 8;");
            Tooltip.install(casillaPane, tooltip);
            
            // Animación de aparición al cargar el tablero
            casillaPane.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(300), casillaPane);
            ft.setToValue(1);
            ft.setDelay(Duration.millis(i * 20)); 
            // Cada casilla aparece un poco después que la anterior
            ft.play();
            
            tableroGrid.add(casillaPane, columna, fila);
            casillasMap.put(i, casillaPane);
        }
    }
    
    public void actualizarPosiciones(ControladorJugador controladorJugador, ControladorTurnos controladorTurnos) {
        // Quitamos las fichas de todas las casillas antes de redibujarlas
        for (StackPane casilla : casillasMap.values()) {
            casilla.getChildren().removeIf(node -> 
                node instanceof Label && 
                !((Label)node).getText().matches("\\d+") && 
                !((Label)node).getText().matches("[🐧🐻🕳️🛷❓⬜]")
            );
        }
        
        String[] colores = {"#f44336", "#2196f3", "#4caf50", "#9c27b0", "#ff9800"}; // Un color por jugador
        String[] iconos = {"①", "②", "③", "④", "⑤"}; 
        // Un icono por jugador
        
        for (int idx = 0; idx < controladorJugador.getJugadores().size(); idx++) {
            Jugador jugador = controladorJugador.getJugadores().get(idx);
            int pos = jugador.getPosicion();
            StackPane casilla = casillasMap.get(pos);
            
            if (casilla != null) {
                Label fichaLabel = new Label(iconos[idx % iconos.length]);
                fichaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                fichaLabel.setStyle("-fx-background-color: " + colores[idx % colores.length] + 
                                   "; -fx-background-radius: 20; -fx-padding: 8; -fx-text-fill: white; " +
                                   "-fx-min-width: 35; -fx-min-height: 35; -fx-alignment: center; " +
                                   "-fx-effect: dropshadow(gaussian, black, 5, 0.7, 2, 2); " +
                                   "-fx-border-color: gold; -fx-border-width: 2; -fx-border-radius: 20;");
                
                String tooltipText = "👤 " + jugador.getNombre() + (jugador.esIA() ? " (IA)" : "") + 
                                    "\n📍 Posición: " + (jugador.getPosicion() + 1) +
                                    "\n" + jugador.getInventario().obtenerResumen();
                Tooltip fichaTooltip = new Tooltip(tooltipText);
                fichaTooltip.setFont(Font.font(11));
                Tooltip.install(fichaLabel, fichaTooltip);
                
                StackPane.setAlignment(fichaLabel, Pos.BOTTOM_RIGHT);
                StackPane.setMargin(fichaLabel, new Insets(0, 3, 3, 0));
                casilla.getChildren().add(fichaLabel);
                
                // Animación de aparición de la ficha
                ScaleTransition st = new ScaleTransition(Duration.millis(300), fichaLabel);
                st.setFromX(0);
                st.setFromY(0);
                st.setToX(1);
                st.setToY(1);
                st.play();
            }
        }
        
        if (animacionTurno != null) {
            animacionTurno.stop(); 
            // Paramos la animación anterior antes de crear una nueva
        }
        
        // Animación de brillo dorado en la casilla del jugador activo
        Jugador actual = controladorTurnos.getJugadorActual();
        if (actual != null) {
            StackPane casillaActual = casillasMap.get(actual.getPosicion());
            if (casillaActual != null) {
                animacionTurno = new Timeline(
                    new KeyFrame(Duration.ZERO, e -> 
                        casillaActual.setStyle(casillaActual.getStyle() + "-fx-effect: dropshadow(gaussian, gold, 25, 0.9, 0, 0);")),
                    new KeyFrame(Duration.seconds(0.5), e -> 
                        casillaActual.setStyle(casillaActual.getStyle().replace("-fx-effect: dropshadow(gaussian, gold, 25, 0.9, 0, 0);", ""))),
                    new KeyFrame(Duration.seconds(1))
                );
                animacionTurno.setCycleCount(Animation.INDEFINITE); 
                // Se repite sin parar
                
                animacionTurno.play();
            }
        }
    }
    
    public void animarMovimiento(Jugador jugador, int casillaOrigen, int casillaDestino, Runnable callback) {
        if (animacionTurno != null) {
            animacionTurno.stop();
        }
        
        Timeline timeline = new Timeline();
        
        // Vamos resaltando cada casilla por la que pasa el jugador una a una
        for (int i = casillaOrigen; i <= casillaDestino; i++) {
            final int paso = i;
            KeyFrame kf = new KeyFrame(Duration.millis(300 * (i - casillaOrigen + 1)), e -> {
                StackPane casilla = casillasMap.get(paso);
                if (casilla != null) {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), casilla);
                    st.setToX(1.15);
                    st.setToY(1.15);
                    st.setAutoReverse(true);
                    st.setCycleCount(2);
                    st.play();
                    
                    casilla.setStyle(casilla.getStyle() + "-fx-effect: dropshadow(gaussian, #FFD700, 30, 0.9, 0, 0);");
                    
                    // Quitamos el brillo de la casilla anterior
                    if (paso > casillaOrigen) {
                        StackPane anterior = casillasMap.get(paso - 1);
                        if (anterior != null) {
                            anterior.setStyle(anterior.getStyle().replace("-fx-effect: dropshadow(gaussian, #FFD700, 30, 0.9, 0, 0);", ""));
                        }
                    }
                }
            });
            timeline.getKeyFrames().add(kf);
        }
        
        // Cuando termina la animación, limpiamos los brillos y ejecutamos el callback
        timeline.setOnFinished(e -> {
            for (int i = casillaOrigen; i <= casillaDestino; i++) {
                StackPane casilla = casillasMap.get(i);
                if (casilla != null) {
                    casilla.setStyle(casilla.getStyle().replace("-fx-effect: dropshadow(gaussian, #FFD700, 30, 0.9, 0, 0);", ""));
                }
            }
            if (callback != null) callback.run();
        });
        
        timeline.play();
    }
    
    public void limpiar() {
        if (animacionTurno != null) {
            animacionTurno.stop(); 
            // Paramos las animaciones antes de limpiar
        }
        tableroGrid.getChildren().clear();
        casillasMap.clear();
    }
    
    public VBox getVista() { return vista; }
}