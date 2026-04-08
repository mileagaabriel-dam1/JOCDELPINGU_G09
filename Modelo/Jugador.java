package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private String color;
    private TipoJugador tipo;
    private int posicion;
    private Inventario inventario;
    private List<String> historial;
    
    public Jugador(String nombre, String color, TipoJugador tipo) {
        this.nombre = nombre;
        this.color = color;
        this.tipo = tipo;
        this.posicion = 0;
        this.inventario = new Inventario();
        this.historial = new ArrayList<>();
    }
    
    public int lanzarDado() {
        int resultado = (int)(Math.random() * 6) + 1;
        registrarAccion("Ha sacado un " + resultado);
        return resultado;
    }
    
    public void registrarAccion(String accion) {
        historial.add(accion);
    }
    
    public String getNombre() { return nombre; }
    public String getColor() { return color; }
    public TipoJugador getTipo() { return tipo; }
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { 
        this.posicion = Math.max(0, Math.min(49, posicion)); 
    }
    public Inventario getInventario() { return inventario; }
    public List<String> getHistorial() { return historial; }
    
    public boolean esIA() {
        return tipo == TipoJugador.IA;
    }
}