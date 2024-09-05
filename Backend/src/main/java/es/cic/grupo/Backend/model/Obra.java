package es.cic.grupo.Backend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Obra")
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;

    @Size(max = 200)
    @Column(name = "AUTOR")
    private String autor;

    @Size(max = 100)
    @Column(name = "FECHA")
    private String fecha;

    @Size(max = 200)
    @Column(name = "LOCALIZACION")
    private String localizacion;

    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @ManyToOne
    @JsonIgnoreProperties("obras")
    @JoinColumn(name = "TIPO_ID")
    private Tipo tipo;

    public Obra() {
    }

    public Obra(Long id, String nombre, String autor, String fecha, String localizacion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.fecha = fecha;
        this.localizacion = localizacion;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}