package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartieMapper {
    //@Mapping(target = "joueurs", ignore = true)
    //@Mapping(target = "serveur", ignore = true)
    PartieEntity toEntity(PartieRequestDTO request);

    PartieResponseDTO toResponse(PartieEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(PartieRequestDTO request, @MappingTarget PartieEntity entity);
}