package fr.uga.l3miage.pc.prisonersdilemma.Components;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ServeurComponentTest {

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
        Long id = 1L;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus("En ligne");
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        ServeurEntity result = serveurComponent.getServeurById(id);

        assertEquals(serveur, result);
        assertEquals("En ligne", result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
    }

    @Test
    void testGetServeurByIdNotFound() {
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundServeurEntityException.class, () -> serveurComponent.getServeurById(id));
        verify(serveurRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStatusSuccess() throws NotFoundServeurEntityException, BadRequestException {
        Long id = 1L;
        String nouveauStatus = "Maintenance";
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus("En ligne");
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));
        when(serveurRepository.save(serveur)).thenReturn(serveur);

        ServeurEntity result = serveurComponent.updateStatus(id, nouveauStatus);

        assertNotNull(result);
        assertEquals(nouveauStatus, result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).save(serveur);
    }

    @Test
    void testUpdateStatusInvalid() {
        Long id = 1L;
        String nouveauStatus = "";
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        assertThrows(BadRequestException.class, () -> serveurComponent.updateStatus(id, nouveauStatus));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).save(serveur);
    }

    @Test
    void testUpdateStatusWithNullStatus() {
        Long id = 1L;
        String nouveauStatus = null;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        assertThrows(BadRequestException.class, () -> serveurComponent.updateStatus(id, nouveauStatus));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).save(serveur);
    }

    @Test
    void testDeleteServeurSuccess() throws NotFoundServeurEntityException {
        Long id = 1L;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        serveurComponent.deleteServeur(id);

        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).delete(serveur);
    }

    @Test
    void testDeleteServeurNotFound() {
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundServeurEntityException.class, () -> serveurComponent.deleteServeur(id));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).delete(any(ServeurEntity.class));
    }

    @Test
    void testDeleteServeurExceptionDuringDelete() {
        Long id = 1L;
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));
        doThrow(new RuntimeException("Suppression échouée")).when(serveurRepository).delete(serveur);

        assertThrows(RuntimeException.class, () -> serveurComponent.deleteServeur(id));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).delete(serveur);
    }
    @Test
    void testUpdateStatusWithSameStatus() throws NotFoundServeurEntityException, BadRequestException {
        Long id = 1L;
        String currentStatus = "En ligne";
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus(currentStatus);
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));
        when(serveurRepository.save(serveur)).thenReturn(serveur);

        ServeurEntity result = serveurComponent.updateStatus(id, currentStatus);

        assertNotNull(result);
        assertEquals(currentStatus, result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).save(serveur);
    }

    @Test
    void testDeleteAlreadyDeletedServeur() {
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundServeurEntityException.class, () -> serveurComponent.deleteServeur(id));
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(0)).delete(any(ServeurEntity.class));
    }

    @Test
    void testUpdateStatusWithVeryLongStatus() throws NotFoundServeurEntityException, BadRequestException {
        Long id = 1L;
        String longStatus = "A".repeat(1000);
        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(id);
        serveur.setStatus("En ligne");
        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));
        when(serveurRepository.save(serveur)).thenReturn(serveur);

        ServeurEntity result = serveurComponent.updateStatus(id, longStatus);

        assertNotNull(result);
        assertEquals(longStatus, result.getStatus());
        verify(serveurRepository, times(1)).findById(id);
        verify(serveurRepository, times(1)).save(serveur);
    }

}
