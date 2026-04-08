package Modelo;

public class Casilla {
    private int posicion;
    private TipoCasilla tipo;
    private Entidad entidad;
    
    public Casilla(int posicion, TipoCasilla tipo) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.entidad = crearEntidad();
    }
    
    private Entidad crearEntidad() {
        switch(tipo) {
            case PINGUINO: return new Pinguino();
            case OSO: return new Oso();
            case AGUJERO: return new AgujeroHielo();
            case TRINEO: return new Trineo();
            case INTERROGANTE: return null;
            default: return null;
        }
    }

    public String getSimbolo() {
        if (entidad != null) {
            return entidad.getSimbolo();
        }
        return "❓";
    }

    public String getDescripcion() {
        if (entidad != null) {
            return entidad.getDescripcion();
        }
        return "Casilla misteriosa";
    }
    
    public TipoCasilla getTipo() { 
        return tipo; 
    }
    
    public int getPosicion() { 
        return posicion; 
    }
    
    public Entidad getEntidad() {
        return entidad;
    }
}