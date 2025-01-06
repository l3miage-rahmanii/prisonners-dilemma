package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartieMapper {
    PartieMapper INSTANCE = Mappers.getMapper(PartieMapper.class);
    PartieResponseDTO toResponse(PartieEntity partie);
}