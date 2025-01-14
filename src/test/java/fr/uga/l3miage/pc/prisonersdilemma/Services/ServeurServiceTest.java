package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.mappers.ServeurMapper;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ServeurServiceTest {

    private ServeurRequestDTO serveurRequest;
    private ServeurEntity serveurEntity;
    private ServeurResponseDTO serveurResponse;
    private PartieEntity partie;

    @Mock
    private ServeurMapper serveurMapper;

    @Mock
    private ServeurComponent serveurComponent;

    @Mock
    private ServeurRepository serveurRepository;

    @InjectMocks
    private ServeurService serveurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        serveurRequest = new ServeurRequestDTO();
        partie = PartieEntity.builder()
                .id(1L)
                .status("en_cours")
                .build();

        serveurEntity = ServeurEntity.builder()
                .id(1L)
                .status("Active")
                .partie(partie)
                .build();

        serveurResponse = new ServeurResponseDTO();
    }

    @Test
    void testCreateServeurWithActivePartie() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");

        when(serveurRepository.existsByPartieNotNull()).thenReturn(true);

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.createServeur(request));
        verify(serveurRepository, times(1)).existsByPartieNotNull();
    }

    @Test
    void deleteServeurServeurNotFound() {
        // Arrange
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundEntityRestException.class,
                () -> serveurService.deleteServeur(id),
                "Serveur non trouvé");

        verify(serveurRepository).findById(id);
        verify(serveurRepository, never()).deleteById(any());
    }

    @Test
    void deleteServeur_ActivePartieExists() {
        // Arrange
        Long id = 1L;
        partie.setStatus("en_cours");
        serveurEntity.setPartie(partie);

        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveurEntity));

        // Act & Assert
        assertThrows(BadRequestRestException.class,
                () -> serveurService.deleteServeur(id),
                "Impossible de supprimer un serveur avec une partie en cours");

        verify(serveurRepository).findById(id);
        verify(serveurRepository, never()).deleteById(any());
    }

    @Test
    void deleteServeur_WaitingPartieExists() {
        // Arrange
        Long id = 1L;
        partie.setStatus("en_attente");
        serveurEntity.setPartie(partie);

        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveurEntity));

        // Act & Assert
        assertThrows(BadRequestRestException.class,
                () -> serveurService.deleteServeur(id),
                "Impossible de supprimer un serveur avec une partie en cours");

        verify(serveurRepository).findById(id);
        verify(serveurRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateServeurNotFound() {
        // Given
        Long serveurId = 1L;
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> serveurService.updateServeur(serveurId, request));
        verify(serveurRepository, times(1)).findById(serveurId);
    }

    @Test
    void createServeurServerWithActiveGameExists() {
        // Arrange
        when(serveurRepository.existsByPartieNotNull()).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestRestException.class,
                () -> serveurService.createServeur(serveurRequest),
                "Un serveur avec une partie active existe déjà");

        verify(serveurRepository).existsByPartieNotNull();
        verify(serveurMapper, never()).toEntity(any());
        verify(serveurRepository, never()).save(any());
        verify(serveurMapper, never()).toResponse(any());
    }

    @Test
    void createServeurSuccess() {
        // Arrange
        when(serveurRepository.existsByPartieNotNull()).thenReturn(false);
        when(serveurMapper.toEntity(serveurRequest)).thenReturn(serveurEntity);
        when(serveurRepository.save(serveurEntity)).thenReturn(serveurEntity);
        when(serveurMapper.toResponse(serveurEntity)).thenReturn(serveurResponse);

        // Act
        ServeurResponseDTO result = serveurService.createServeur(serveurRequest);

        // Assert
        assertNotNull(result);
        verify(serveurRepository).existsByPartieNotNull();
        verify(serveurMapper).toEntity(serveurRequest);
        verify(serveurRepository).save(serveurEntity);
        verify(serveurMapper).toResponse(serveurEntity);
    }

    @Test
    void createServeurMappingError() {
        // Arrange
        when(serveurRepository.existsByPartieNotNull()).thenReturn(false);
        when(serveurMapper.toEntity(serveurRequest)).thenThrow(new RuntimeException("Mapping error"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> serveurService.createServeur(serveurRequest));

        verify(serveurRepository).existsByPartieNotNull();
        verify(serveurMapper).toEntity(serveurRequest);
        verify(serveurRepository, never()).save(any());
        verify(serveurMapper, never()).toResponse(any());
    }

    @Test
    void createServeur_SaveError() {
        // Arrange
        when(serveurRepository.existsByPartieNotNull()).thenReturn(false);
        when(serveurMapper.toEntity(serveurRequest)).thenReturn(serveurEntity);
        when(serveurRepository.save(any())).thenThrow(new RuntimeException("Save error"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> serveurService.createServeur(serveurRequest));

        verify(serveurRepository).existsByPartieNotNull();
        verify(serveurMapper).toEntity(serveurRequest);
        verify(serveurRepository).save(serveurEntity);
        verify(serveurMapper, never()).toResponse(any());
    }

    @Test
    void testDeleteServeurSuccess() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(null) // Pas de partie en cours
                .build();

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When
        serveurService.deleteServeur(serveurId);

        // Then
        verify(serveurRepository, times(1)).deleteById(serveurId);
    }

    @Test
    void testGetServeurById_NotFound() {
        // Given
        Long id = 1L;
        when(serveurRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> serveurService.getServeurById(id));
        verify(serveurRepository).findById(id);
    }

    @Test
    void testUpdateServeur_FailWhenNotFound() {
        // Given
        Long serveurId = 1L;
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");
        when(serveurRepository.findById(serveurId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> serveurService.updateServeur(serveurId, request));
        verify(serveurRepository).findById(serveurId);
    }

    @Test
    void testDeleteServeur_FailWhenServeurHasActivePartie() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(PartieEntity.builder().status("en_cours").build()) // Partie active
                .build();
        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.deleteServeur(serveurId),
                "Impossible de supprimer un serveur avec une partie en cours");
        verify(serveurRepository).findById(serveurId);
        verify(serveurRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteServeur_FailWhenPartieIsNotTerminated() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(PartieEntity.builder().status("en_attente").build()) // Partie en attente
                .build();
        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.deleteServeur(serveurId),
                "Impossible de supprimer un serveur avec une partie en cours");
        verify(serveurRepository).findById(serveurId);
        verify(serveurRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteServeur_Success() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(null) // Partie terminée ou inexistante
                .build();
        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When
        serveurService.deleteServeur(serveurId);

        // Then
        verify(serveurRepository).deleteById(serveurId);
    }


}