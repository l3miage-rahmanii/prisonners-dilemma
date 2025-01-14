package fr.uga.l3miage.pc.mappers;

import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServeurMapper {

    ServeurEntity toEntity(ServeurRequestDTO request);

    ServeurResponseDTO toResponse(ServeurEntity entity);

    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ServeurRequestDTO request, @MappingTarget ServeurEntity entity);
}