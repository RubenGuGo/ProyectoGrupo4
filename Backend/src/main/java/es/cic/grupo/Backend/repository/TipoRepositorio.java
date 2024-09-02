package es.cic.grupo.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.grupo.Backend.model.Tipo;

public interface TipoRepositorio extends JpaRepository<Tipo, Long>{
    Tipo findByNombre(String nombre);
}