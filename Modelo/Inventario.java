package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<Item.TipoItem, Integer> items;
    
    private static final int MAX_PECES = 2;
    private static final int MAX_BOLAS = 6;
    private static final int MAX_DADOS = 3;
    
    public Inventario() {
        items = new HashMap<>();
        items.put(Item.TipoItem.PEZ, 2);
        items.put(Item.TipoItem.BOLA_NIEVE, 0);
        items.put(Item.TipoItem.DADO, 1);
    }
    
    public void agregarPez() {
        int actual = items.get(Item.TipoItem.PEZ);
        if (actual < MAX_PECES) {
            items.put(Item.TipoItem.PEZ, actual + 1);
        }
    }
    
    public boolean quitarPez() {
        int actual = items.get(Item.TipoItem.PEZ);
        if (actual > 0) {
            items.put(Item.TipoItem.PEZ, actual - 1);
            return true;
        }
        return false;
    }
    
    public void agregarBolaNieve() {
        int actual = items.get(Item.TipoItem.BOLA_NIEVE);
        if (actual < MAX_BOLAS) {
            items.put(Item.TipoItem.BOLA_NIEVE, actual + 1);
        }
    }
    
    public boolean quitarBolaNieve() {
        int actual = items.get(Item.TipoItem.BOLA_NIEVE);
        if (actual > 0) {
            items.put(Item.TipoItem.BOLA_NIEVE, actual - 1);
            return true;
        }
        return false;
    }
    
    public void agregarDado() {
        int actual = items.get(Item.TipoItem.DADO);
        if (actual < MAX_DADOS) {
            items.put(Item.TipoItem.DADO, actual + 1);
        }
    }
    
    public boolean quitarDado() {
        int actual = items.get(Item.TipoItem.DADO);
        if (actual > 0) {
            items.put(Item.TipoItem.DADO, actual - 1);
            return true;
        }
        return false;
    }
    
    public int getPeces() { return items.get(Item.TipoItem.PEZ); }
    public int getBolasNieve() { return items.get(Item.TipoItem.BOLA_NIEVE); }
    public int getDados() { return items.get(Item.TipoItem.DADO); }
    
    public String obtenerResumen() {
        return String.format("🐟 Peces: %d/2 | ❄️ Bolas: %d/6 | ⚡ Dados: %d/3",
            getPeces(), getBolasNieve(), getDados());
    }
}