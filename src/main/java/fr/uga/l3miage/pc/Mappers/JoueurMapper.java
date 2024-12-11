package fr.uga.l3miage.pc.Mappers;


import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Responses.JoueurResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface JoueurMapper {
    JoueurMapper INSTANCE = Mappers.getMapper(JoueurMapper.class);
    JoueurResponseDTO toResponse(JoueurEntity partie);
}
