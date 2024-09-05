package es.cic.grupo.Backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tipo")

public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;

    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "tipo",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Obra> obras;
    
    
    public Tipo(Long id, String nombre, String descripcion, ArrayList<Obra> obras) {
    
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.obras = obras;
    }

    public Tipo(Long id, String nombre, String descripcion) {
    
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.obras = new ArrayList<>();
    }

    public Tipo() {
    this.obras = new ArrayList<>();
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

    public void addObra(Obra obra) {
        this.obras.add(obra);
        obra.setTipo(this);
    }

}
