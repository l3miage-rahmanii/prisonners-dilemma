package fr.uga.l3miage.pc.mappers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface JoueurMapper {


    JoueurResponseDTO toResponse(JoueurEntity entity);

}