package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PartieMapper {

    PartieEntity toEntity(PartieRequestDTO request);

    PartieResponseDTO toResponse(PartieEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(PartieRequestDTO request, @MappingTarget PartieEntity entity);
}