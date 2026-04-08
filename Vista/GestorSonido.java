package Vista;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class GestorSonido {
    
    // Esta clase se encarga de manejar todos los sonidos del juego (efectos y música de fondo)
    // Usa un patrón "singleton" (solo se crea una única instancia) para que no haya duplicados.
    
    private static GestorSonido instancia;
    private Map<String, MediaPlayer> reproductores;  
    // Guarda los sonidos cargados, identificados por un nombre
    
    private MediaPlayer musicaFondo;              
    // Reproductor especial para la música del juego
    
    private boolean sonidoActivado = true;           
    // Controla si los efectos están activos o apagados
    
    private boolean musicaActivada = true;           
    // Controla si la música está activa o no
    
    
    // Rutas posibles donde puede estar la carpeta con los archivos de sonido (por si cambia de sitio)
    private static final String[] RUTAS_POSIBLES = {
        "F:/JOCPINGU_09-DAM1/JOCPINGU_09-DAMI/Resources/audio/",
        "F:/JOCPINGU_09-DAM1/Resources/audio/",
        "./Resources/audio/",
        "../Resources/audio/",
        "Resources/audio/"
    };
    
    private String rutaEncontrada = null; 
    // Aquí se guarda la ruta que realmente se encuentre
    
    
    private GestorSonido() {
        reproductores = new HashMap<>();
        buscarRutaAudio();       
        // Busca en las rutas posibles la carpeta de sonidos
        
        cargarTodosLosSonidos(); 
        // Una vez encontrada, carga los sonidos del juego
    }
    
    // Método para obtener la única instancia de la clase
    public static GestorSonido getInstancia() {
        if (instancia == null) {
            instancia = new GestorSonido();
        }
        return instancia;
    }
    
    // Revisa las diferentes rutas posibles hasta encontrar la carpeta de audio
    private void buscarRutaAudio() {
        for (String ruta : RUTAS_POSIBLES) {
            File carpeta = new File(ruta);
            if (carpeta.exists() && carpeta.isDirectory()) {
                rutaEncontrada = ruta;
                System.out.println("✅ Ruta de audio encontrada: " + ruta);
                return;
            }
        }
        System.out.println("❌ No se encontró la carpeta de audio");
    }
    
    // Carga todos los sonidos que el juego usa (efectos y música)
    private void cargarTodosLosSonidos() {
        if (rutaEncontrada == null) return; 
        // Si no hay ruta, no intenta nada
        
        // Efectos cortos como tirar el dado, caer, sonidos de casillas, etc.
        cargarSonido("dado_lanzar", "dado_lanzar.mp3");
        cargarSonido("dado_caer", "dado_caer.mp3");
        cargarSonido("victoria", "victoria.mp3");
        cargarSonido("oso", "casilla_oso.mp3");
        cargarSonido("trineo", "casilla_trineo.mp3");
        cargarSonido("agujero", "casilla_agujero.mp3");
        cargarSonido("item", "item_recogido.mp3");
        cargarSonido("foca", "foca_atrapa.mp3");
        
        // Cargar la música de fondo que se escucha continuamente
        try {
            File file = new File(rutaEncontrada + "musica_fondo.mp3");
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                musicaFondo = new MediaPlayer(media);
                musicaFondo.setCycleCount(MediaPlayer.INDEFINITE); 
                // Se repite sin parar
                
                musicaFondo.setVolume(0.3); 
                // Volumen más bajo (para no molestar)
                
                System.out.println("✅ Música de fondo cargada");
            } else {
                System.out.println("⚠️ No se encuentra musica_fondo.mp3 en: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("❌ Error cargando música: " + e.getMessage());
        }
    }
    
    // Carga un sonido individualmente y lo guarda en el mapa
    private void cargarSonido(String nombre, String archivo) {
        try {
            File file = new File(rutaEncontrada + archivo);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                reproductores.put(nombre, player);
                System.out.println("✅ Sonido cargado: " + nombre + " - " + file.getAbsolutePath());
            } else {
                System.out.println("⚠️ No se encuentra " + archivo + " en: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("❌ Error cargando " + nombre + ": " + e.getMessage());
        }
    }
    
    // Reproduce un efecto de sonido por su nombre
    public void reproducirEfecto(String nombre) {
        if (!sonidoActivado) { 
        	// Si los sonidos están desactivados, no hace nada
        	
            System.out.println("🔇 Sonido desactivado, no se reproduce: " + nombre);
            return;
        }
        
        MediaPlayer player = reproductores.get(nombre);
        if (player != null) {
            player.stop(); 
            // Por si ya estaba sonando, lo reinicia
            
            player.play(); 
            // Lo reproduce
            
            System.out.println("🔊 Reproduciendo: " + nombre);
        } else {
            System.out.println("❌ No se puede reproducir: " + nombre + " - archivo no cargado");
        }
    }
    
    // Reproduce los sonidos del dado, con un pequeño retraso entre lanzar y caer
    public void reproducirEfectoDado(int resultado) {
        reproducirEfecto("dado_lanzar");
        
        new Thread(() -> {
            try { Thread.sleep(400); } catch (Exception e) {}
            javafx.application.Platform.runLater(() -> {
                reproducirEfecto("dado_caer");
            });
        }).start();
    }
    
    // Inicia la música de fondo (si está activada)
    public void iniciarMusicaFondo() {
        if (!musicaActivada || musicaFondo == null) {
            System.out.println("🔇 No se puede iniciar música");
            return;
        }
        
        musicaFondo.play();
        System.out.println("🎵 Música de fondo iniciada");
    }
    
    // Detiene completamente la música
    public void detenerMusicaFondo() {
        if (musicaFondo != null) {
            musicaFondo.stop();
        }
    }
    
    // Pausa la música sin reiniciarla
    public void pausarMusicaFondo() {
        if (musicaFondo != null) {
            musicaFondo.pause();
        }
    }
    
    // Permite ajustar el volumen de la música
    public void setVolumenMusica(double volumen) {
        if (musicaFondo != null) {
            musicaFondo.setVolume(volumen);
        }
    }
    
    // Activa o desactiva los efectos de sonido
    public void setSonidoActivado(boolean activado) {
        this.sonidoActivado = activado;
        System.out.println(activado ? "🔊 Sonido activado" : "🔇 Sonido desactivado");
    }
    
    // Activa o pausa la música según el valor que se pase
    public void setMusicaActivada(boolean activada) {
        this.musicaActivada = activada;
        if (activada) {
            iniciarMusicaFondo();
        } else {
            pausarMusicaFondo();
        }
    }
    
    // Comprueba si hay sonidos cargados en el mapa
    public boolean haySonidosCargados() {
        return !reproductores.isEmpty();
    }
}
