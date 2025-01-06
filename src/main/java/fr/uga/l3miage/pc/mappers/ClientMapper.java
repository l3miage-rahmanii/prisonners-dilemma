package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.ClientEntity;
import fr.uga.l3miage.pc.responses.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientResponseDTO toResponse(ClientEntity client);
}
