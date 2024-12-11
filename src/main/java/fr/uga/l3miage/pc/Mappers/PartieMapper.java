package fr.uga.l3miage.pc.Mappers;

import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Responses.PartieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartieMapper {
    PartieMapper INSTANCE = Mappers.getMapper(PartieMapper.class);
    PartieResponseDTO toResponse(PartieEntity partie);
}