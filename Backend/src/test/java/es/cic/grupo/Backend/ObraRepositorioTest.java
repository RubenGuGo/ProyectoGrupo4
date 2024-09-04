package es.cic.grupo.Backend;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import es.cic.grupo.Backend.model.Obra;
import es.cic.grupo.Backend.repository.ObraRepositorio;
import es.cic.grupo.Backend.service.ObraServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ObraRepositorioTest {

    @Mock
    private ObraRepositorio obraRepositorio;

    @InjectMocks
    private ObraServicio obraServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNombre() {
        // Arrange
        String nombre = "La Mona Lisa";
        Obra obra = new Obra();
        obra.setNombre(nombre);

        when(obraRepositorio.findByNombre(nombre)).thenReturn(obra);

        // Act
        Obra result = obraServicio.getObraByNombre(nombre);

        // Assert
        assertNotNull(result);
        assertEquals(nombre, result.getNombre());
        verify(obraRepositorio, times(1)).findByNombre(nombre);
    }
    @Test
    public void testSaveObra() {
        // Arrange
        Obra obra = new Obra();
        obra.setNombre("Nueva Obra");

        when(obraRepositorio.save(obra)).thenReturn(obra);

        // Act
        Obra result = obraServicio.addObra(obra);

        // Assert
        assertNotNull(result);
        assertEquals("Nueva Obra", result.getNombre());
        verify(obraRepositorio, times(1)).save(obra);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Obra obra1 = new Obra();
        obra1.setNombre("Obra 1");
        Obra obra2 = new Obra();
        obra2.setNombre("Obra 2");

        List<Obra> obras = Arrays.asList(obra1, obra2);

        when(obraRepositorio.findAll()).thenReturn(obras);

        // Act
        List<Obra> result = obraServicio.getAllObras();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(obraRepositorio, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long id = 1L;
        doNothing().when(obraRepositorio).deleteById(id);

        // Act
        obraServicio.deleteObraById(id);

        // Assert
        verify(obraRepositorio, times(1)).deleteById(id);
    }
}