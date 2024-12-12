package fr.uga.l3miage.pc.Mappers;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.responses.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientResponseDTO toResponse(ClientEntity client);
}
