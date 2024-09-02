package es.cic.grupo.Backend.model;

import java.util.ArrayList;
import java.util.List;

public class Tipo {
    
    private long id;
    private String nombre;
    private String descripcion;
    private List<Obra> obras;
    
    
    public Tipo(Long id, String nombre, String descripcion, ArrayList<Obra> obras) {
    
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.obras = obras;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<Obra> getObras() {
        return obras;
    }
    
    public void setObras(ArrayList<Obra> obras) {
        this.obras = obras;
    }

}
