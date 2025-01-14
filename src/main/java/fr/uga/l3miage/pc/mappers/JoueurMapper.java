package fr.uga.l3miage.pc.mappers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.requests.JoueurRequestDTO;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface JoueurMapper {

    JoueurEntity toEntity(JoueurRequestDTO request);


    JoueurResponseDTO toResponse(JoueurEntity entity);


    JoueurRequestDTO toRequest(JoueurEntity joueurEntity);


    void updateEntityFromRequest(JoueurRequestDTO request, @MappingTarget JoueurEntity entity);
}