package fr.uga.l3miage.pc.prisonersdilemma.Components;

import fr.uga.l3miage.pc.Components.ServeurComponent;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.Repositories.ServeurRepository;
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

public class ServeurComponentTest {

    @Mock
    private ServeurRepository serveurRepository;

    @InjectMocks
    private ServeurComponent serveurComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetServeurByIdSuccess() throws NotFoundServeurEntityException {
        // Given
        Long id = 1L;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus("En ligne");
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        // When
        ServeurEntity result = serveurComponent.getServeurById(id);

        // Then
        assertEquals(serveur, result);
        assertEquals("En ligne", result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
    }

    @Test
    void testGetServeurByIdNotFound() {
        // Given
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundServeurEntityException.class, () -> serveurComponent.getServeurById(id));
        verify(serveurRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStatusSuccess() throws NotFoundServeurEntityException, BadRequestException {
        // Given
        Long id = 1L;
        String nouveauStatus = "Maintenance";
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus("En ligne");
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));
        when(serveurRepository.save(serveur)).thenReturn(serveur);

        // When
        ServeurEntity result = serveurComponent.updateStatus(id, nouveauStatus);

        // Then
        assertNotNull(result);
        assertEquals(nouveauStatus, result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).save(serveur);
    }

    @Test
    void testUpdateStatusInvalid() {
        // Given
        Long id = 1L;
        String nouveauStatus = "";
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        // When/Then
        assertThrows(BadRequestException.class, () -> serveurComponent.updateStatus(id, nouveauStatus));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).save(serveur);
    }

    @Test
    void testDeleteServeurSuccess() throws NotFoundServeurEntityException {
        // Given
        Long id = 1L;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        // When
        serveurComponent.deleteServeur(id);

        // Then
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).delete(serveur);
    }

    @Test
    void testDeleteServeurNotFound() {
        // Given
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundServeurEntityException.class, () -> serveurComponent.deleteServeur(id));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).delete(any(ServeurEntity.class));
    }
}