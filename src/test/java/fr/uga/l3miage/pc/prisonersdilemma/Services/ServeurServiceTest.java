package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.Technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.Mappers.ServeurMapper;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.Services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ServeurServiceTest {

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
    }

    @Test
    void testGetServeurByIdSuccess() throws Exception {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .adresse("192.168.0.1")
                .build();

        ServeurResponseDTO responseDTO = ServeurResponseDTO.builder()
                .id(serveurId)
                .status("disponible")
                .adresse("192.168.0.1")
                .build();

        when(serveurComponent.getServeurById(serveurId)).thenReturn(serveurEntity);
        when(serveurMapper.toResponse(serveurEntity)).thenReturn(responseDTO);

        // When
        ServeurResponseDTO result = serveurService.getServeurById(serveurId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("disponible");
        assertThat(result.getAdresse()).isEqualTo("192.168.0.1");
        verify(serveurComponent, times(1)).getServeurById(serveurId);
        verify(serveurMapper, times(1)).toResponse(serveurEntity);
    }

    @Test
    void testGetServeurByIdNotFound() throws Exception {
        // Given
        Long serveurId = 1L;
        when(serveurComponent.getServeurById(serveurId))
                .thenThrow(new NotFoundServeurEntityException("Serveur non trouvé"));

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> serveurService.getServeurById(serveurId));
        verify(serveurComponent, times(1)).getServeurById(serveurId);
    }

    @Test
    void testUpdateServeurStatusSuccess() throws Exception {
        // Given
        Long serveurId = 1L;
        String nouveauStatus = "occupé";

        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status(nouveauStatus)
                .build();

        ServeurResponseDTO responseDTO = ServeurResponseDTO.builder()
                .id(serveurId)
                .status(nouveauStatus)
                .build();

        when(serveurComponent.updateStatus(serveurId, nouveauStatus)).thenReturn(serveurEntity);
        when(serveurMapper.toResponse(serveurEntity)).thenReturn(responseDTO);

        // When
        ServeurResponseDTO result = serveurService.updateServeurStatus(serveurId, nouveauStatus);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(nouveauStatus);
        verify(serveurComponent, times(1)).updateStatus(serveurId, nouveauStatus);
        verify(serveurMapper, times(1)).toResponse(serveurEntity);
    }

    @Test
    void testUpdateServeurStatusBadRequest() throws Exception {
        // Given
        Long serveurId = 1L;
        String nouveauStatus = "";

        when(serveurComponent.updateStatus(serveurId, nouveauStatus))
                .thenThrow(new BadRequestException("Statut invalide"));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.updateServeurStatus(serveurId, nouveauStatus));
        verify(serveurComponent, times(1)).updateStatus(serveurId, nouveauStatus);
    }

    @Test
    void testCreerServeur() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");
        request.setAdresse("192.168.0.1");

        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(1L)
                .status("disponible")
                .adresse("192.168.0.1")
                .build();

        when(serveurRepository.save(any(ServeurEntity.class))).thenReturn(serveurEntity);

        // When
        ServeurResponseDTO result = serveurService.creerServeur(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("disponible");
        assertThat(result.getAdresse()).isEqualTo("192.168.0.1");
        verify(serveurRepository, times(1)).save(any(ServeurEntity.class));
    }

    @Test
    void testReserverServeurSuccess() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .build();

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));
        when(serveurRepository.save(any(ServeurEntity.class))).thenReturn(serveurEntity);

        // When
        ServeurEntity result = serveurService.reserverServeur(serveurId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("occupe");
        verify(serveurRepository, times(1)).findById(serveurId);
        verify(serveurRepository, times(1)).save(serveurEntity);
    }

    @Test
    void testReserverServeurNotAvailable() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("occupé")
                .build();

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.reserverServeur(serveurId));
        verify(serveurRepository, times(1)).findById(serveurId);
    }
}