package es.cic.grupo.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.grupo.Backend.model.Obra;
import es.cic.grupo.Backend.service.ObraServicio;

@RestController
@RequestMapping("/api/obras")
public class ObraControlador {

    @Autowired
    private ObraServicio obraServicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Obra> addObra(@RequestBody Obra obra) {
        Obra nuevaObra = obraServicio.addObra(obra);
        return ResponseEntity.ok(nuevaObra);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> getAllObras() {
        List<Obra> obras = obraServicio.getAllObras();
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> getObraById(@PathVariable Long id) {
        Obra obra = obraServicio.getObraById(id);
        if (obra != null) {
            return ResponseEntity.ok(obra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Obra> getObraByNombre(@PathVariable String nombre) {
        Obra obra = obraServicio.getObraByNombre(nombre);
        if (obra != null) {
            return ResponseEntity.ok(obra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Obra> updateObra(@PathVariable Long id, @RequestBody Obra obra) {
        if (obraServicio.existsById(id)) {
            obra.setId(id);
            Obra obraActualizada = obraServicio.updateObra(obra);
            return ResponseEntity.ok(obraActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObraById(@PathVariable Long id) {
        if (obraServicio.existsById(id)) {
            obraServicio.deleteObraById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}