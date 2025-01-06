package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ServeurMapper {
    ServeurMapper INSTANCE = Mappers.getMapper(ServeurMapper.class);
    ServeurResponseDTO toResponse(ServeurEntity serveur);
}