package es.cic.grupo.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.grupo.Backend.model.Tipo;
import es.cic.grupo.Backend.repository.TipoRepositorio;

@Service
public class TipoServicio {

    @Autowired
    private TipoRepositorio tipoRepositorio;

    public Tipo addTipo(Tipo tipo) {
        return tipoRepositorio.save(tipo);
    }

    public List<Tipo> getAllTipos() {
        return tipoRepositorio.findAll();
    }

    public Tipo getTipoByNombre(String nombre) {
        return tipoRepositorio.findByNombre(nombre);
    }

    public Tipo updateTipo(Tipo tipo) {
        return tipoRepositorio.save(tipo);
    }

    public boolean existsById(Long id) {
        return tipoRepositorio.existsById(id);
    }

    public Tipo getTipoById(Long id) {
        return tipoRepositorio.findById(id).orElse(null);
    }

    public void deleteTipoById(Long id) {
        tipoRepositorio.deleteById(id);
    }
}