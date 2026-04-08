package Vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controladores.*;

public class VistaJavaFX extends Application {
    
    private ControladorJuego controladorJuego;
    private ControladorTablero controladorTablero;
    private ControladorJugador controladorJugador;
    private ControladorTurnos controladorTurnos;
    
    private BorderPane root; 
    // El layout principal que divide la ventana en zonas
    
    // Subvistas
    private VistaMenu vistaMenu;
    private JuegoConSonido vistaJuego;
    private VistaTableroConImagenes vistaTablero;
    private VistaJugador vistaJugador;
    private VistaEventos vistaEventos;
    private GestorSonido gestorSonido;
    
    @Override
    public void start(Stage primaryStage) {
        gestorSonido = GestorSonido.getInstancia();
        
        // Avisamos por consola si los sonidos se cargaron bien o no
        if (gestorSonido.haySonidosCargados()) {
            System.out.println("✅ Sonidos cargados correctamente");
        } else {
            System.out.println("⚠️ No se cargaron sonidos - Verificar carpeta Resources/audio/");
        }
        
        // Creamos todos los controladores
        controladorJuego = new ControladorJuego();
        controladorTablero = controladorJuego.getControladorTablero();
        controladorJugador = controladorJuego.getControladorJugador();
        controladorTurnos = controladorJuego.getControladorTurnos();
        
        // Creamos todas las subvistas
        vistaMenu = new VistaMenu(this);
        vistaJuego = new JuegoConSonido(this);
        vistaTablero = new VistaTableroConImagenes(this);
        vistaJugador = new VistaJugador(this);
        vistaEventos = new VistaEventos(this);
        
        primaryStage.setTitle("🐧 JOC DEL PINGÜ - CON SONIDO 🐧");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(850);
        
        root = new BorderPane();
        root.setStyle("-fx-background-color: #e0f7fa;");
        
        // Colocamos cada vista en su zona de la ventana
        root.setTop(vistaMenu.getVista());
        root.setCenter(vistaTablero.getVista());
        
        // Panel derecho con la info del jugador y el dado
        VBox panelDerecho = new VBox(20);
        panelDerecho.setPadding(new Insets(20));
        panelDerecho.setStyle("-fx-background-color: #bbdefb; -fx-background-radius: 15;");
        panelDerecho.setPrefWidth(300);
        panelDerecho.getChildren().add(vistaJugador.getVista());
        panelDerecho.getChildren().add(vistaJuego.getVista());
        
        root.setRight(panelDerecho);
        root.setBottom(vistaEventos.getVista()); 
        // Los eventos van abajo del todo
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        mostrarMenuPrincipal();
    }
    
    public void mostrarMenuPrincipal() {
        // Mostramos el menú y limpiamos todo lo demás
        vistaMenu.mostrar();
        vistaJuego.ocultar();
        vistaTablero.limpiar();
        vistaJugador.limpiar();
        vistaEventos.limpiar();
        gestorSonido.pausarMusicaFondo();
    }
    
    public void iniciarPartida(int numHumanos, boolean incluirIA) {
        controladorJugador.inicializarJugadores(numHumanos, incluirIA);
        controladorTurnos.setJugadores(controladorJugador.getJugadores());
        
        vistaTablero.inicializarTablero(controladorTablero);
        vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);
        
        vistaJugador.actualizar(controladorTurnos);
        vistaEventos.agregarEvento("🎮 ¡COMIENZA LA PARTIDA!");
        vistaEventos.agregarEvento("👥 Jugadores: " + controladorJugador.getJugadores().size());
        
        vistaJuego.mostrar();
        
        // Si el primer turno es de la IA, lanzamos el dado automáticamente
        if (controladorTurnos.getJugadorActual() != null && 
            controladorTurnos.getJugadorActual().esIA()) {
            vistaJuego.lanzarDadoIA();
        }
    }
    
    public void lanzarDado() {
        vistaJuego.lanzarDado();
    }
    
    // Getters para que otras clases puedan acceder a los controladores y vistas
    public ControladorJuego getControladorJuego() { return controladorJuego; }
    public ControladorTablero getControladorTablero() { return controladorTablero; }
    public ControladorJugador getControladorJugador() { return controladorJugador; }
    public ControladorTurnos getControladorTurnos() { return controladorTurnos; }
    public VistaEventos getVistaEventos() { return vistaEventos; }
    public VistaTableroConImagenes getVistaTablero() { return vistaTablero; }
    public VistaJugador getVistaJugador() { return vistaJugador; }
    
    @Override
    public void stop() {
        gestorSonido.detenerMusicaFondo(); 
        // Paramos la música cuando se cierra la ventana
    }
}