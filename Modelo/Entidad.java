package Modelo;

public interface Entidad {
    String getNombre();
    String getSimbolo();
    String getDescripcion();
    String interactuar(Jugador jugador);
}