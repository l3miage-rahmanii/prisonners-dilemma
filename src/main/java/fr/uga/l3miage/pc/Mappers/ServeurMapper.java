package fr.uga.l3miage.pc.Mappers;

import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ServeurMapper {
    ServeurMapper INSTANCE = Mappers.getMapper(ServeurMapper.class);
    ServeurResponseDTO toResponse(ServeurEntity serveur);
}