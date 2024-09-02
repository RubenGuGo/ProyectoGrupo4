package es.cic.grupo.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.grupo.Backend.model.Obra;

public interface ObraRepositorio extends JpaRepository<Obra, Long>{
    Obra findByName(String nombre);

}
