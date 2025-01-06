package fr.uga.l3miage.pc.mappers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface JoueurMapper {
    JoueurMapper INSTANCE = Mappers.getMapper(JoueurMapper.class);
    JoueurResponseDTO toResponse(JoueurEntity partie);
}
