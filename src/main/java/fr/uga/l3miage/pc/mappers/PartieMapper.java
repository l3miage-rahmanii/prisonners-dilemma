package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PartieMapper {
    PartieResponseDTO toResponse(PartieEntity entity);
}