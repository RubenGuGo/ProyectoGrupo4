package es.cic.grupo.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.grupo.Backend.model.Obra;
import es.cic.grupo.Backend.repository.ObraRepositorio;

@Service
public class ObraServicio {

    @Autowired
    private ObraRepositorio obraRepositorio;

    public Obra addObra(Obra obra) {
        return obraRepositorio.save(obra);
    }

    public List<Obra> getAllObras() {
        return obraRepositorio.findAll();
    }

    public Obra getObraByNombre(String nombre) {
        return obraRepositorio.findByName(nombre);
    }

    public Obra updateObra(Obra obra) {
        return obraRepositorio.save(obra);
    }

    public boolean existsById(Long id) {
        return obraRepositorio.existsById(id);
    }

    public Obra getObraById(Long id) {
        return obraRepositorio.findById(id).orElse(null);
    }

    public void deleteObraById(Long id) {
        obraRepositorio.deleteById(id);
    }
}