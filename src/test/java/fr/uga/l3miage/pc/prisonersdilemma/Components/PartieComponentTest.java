package fr.uga.l3miage.pc.prisonersdilemma.Components;

import fr.uga.l3miage.pc.components.PartieComponent;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundPartieEntityException;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PartieComponentTest {

    @Mock
    private PartieRepository partieRepository;

    @InjectMocks
    private PartieComponent partieComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPartieByIdSuccess() throws NotFoundPartieEntityException {
        // Given
        Long id = 1L;
        PartieEntity partie = new PartieEntity();
        partie.setId(id);
        partie.setStatus("En cours");
        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));

        // When
        PartieEntity result = partieComponent.getPartieById(id);

        // Then
        assertEquals(partie, result);
        assertEquals("En cours", result.getStatus());
        verify(partieRepository, times(1)).findById(id);
    }

    @Test
    void testGetPartieByIdNotFound() {
        // Given
        Long id = 1L;
        when(partieRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundPartieEntityException.class, () -> partieComponent.getPartieById(id));
        verify(partieRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStatusSuccess() throws NotFoundPartieEntityException, BadRequestException {
        // Given
        Long id = 1L;
        String nouveauStatus = "Terminé";
        PartieEntity partie = new PartieEntity();
        partie.setId(id);
        partie.setStatus("En cours");
        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));
        when(partieRepository.save(partie)).thenReturn(partie);

        // When
        PartieEntity result = partieComponent.updateStatus(id, nouveauStatus);

        // Then
        assertNotNull(result);
        assertEquals(nouveauStatus, result.getStatus());
        verify(partieRepository, times(1)).findById(id);
        verify(partieRepository, times(1)).save(partie);
    }

    @Test
    void testUpdateStatusInvalid() {
        // Given
        Long id = 1L;
        String nouveauStatus = "";
        PartieEntity partie = new PartieEntity();
        partie.setId(id);
        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));

        // When/Then
        assertThrows(BadRequestException.class, () -> partieComponent.updateStatus(id, nouveauStatus));
        verify(partieRepository, times(1)).findById(id);
        verify(partieRepository, times(0)).save(partie);
    }

    @Test
    void testDeletePartieSuccess() throws NotFoundPartieEntityException {
        // Given
        Long id = 1L;
        PartieEntity partie = new PartieEntity();
        partie.setId(id);
        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));

        // When
        partieComponent.deletePartie(id);

        // Then
        verify(partieRepository, times(1)).findById(id);
        verify(partieRepository, times(1)).delete(partie);
    }

    @Test
    void testDeletePartieNotFound() {
        // Given
        Long id = 1L;
        when(partieRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundPartieEntityException.class, () -> partieComponent.deletePartie(id));
        verify(partieRepository, times(1)).findById(id);
        verify(partieRepository, times(0)).delete(any(PartieEntity.class));
    }
}
