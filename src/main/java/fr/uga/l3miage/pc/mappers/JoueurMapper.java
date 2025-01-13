package fr.uga.l3miage.pc.mappers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.requests.JoueurRequestDTO;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface JoueurMapper {
   // @Mapping(target = "score", constant = "0")
    //@Mapping(target = "parties", ignore = true)
    //@Mapping(target = "serveurs", ignore = true)
    JoueurEntity toEntity(JoueurRequestDTO request);

    //@Mapping(target = "parties", ignore = true)
    JoueurResponseDTO toResponse(JoueurEntity entity);

   // @Mapping(target = "joueur")
    JoueurRequestDTO toRequest(JoueurEntity joueurEntity);

   // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(JoueurRequestDTO request, @MappingTarget JoueurEntity entity);
}