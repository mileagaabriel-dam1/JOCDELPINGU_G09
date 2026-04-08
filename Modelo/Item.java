package Modelo;

public class Item {
    public enum TipoItem {
        PEZ, BOLA_NIEVE, DADO
    }
    
    private TipoItem tipo;
    private String nombre;
    
    public Item(TipoItem tipo) {
        this.tipo = tipo;
        switch(tipo) {
            case PEZ: this.nombre = "Pez"; break;
            case BOLA_NIEVE: this.nombre = "Bola de nieve"; break;
            case DADO: this.nombre = "Dado extra"; break;
        }
    }
    
    public String getSimbolo() {
        switch(tipo) {
            case PEZ: return "🐟";
            case BOLA_NIEVE: return "❄️";
            case DADO: return "⚡";
            default: return "📦";
        }
    }
    
    public TipoItem getTipo() { return tipo; }
    public String getNombre() { return nombre; }
}