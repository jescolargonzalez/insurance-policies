package com.tfm.aseguradora.backend.tfm.policy.controller.mapper;

import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PeritageDtoMapper {

    @Mappings({
            @Mapping(source = "idPart", target = "partId"),
            @Mapping(source = "idDecission", target = "decissionId")
    })
    PeritageDomain fromDtoToDomain (PeritageDto peritageDto);


    @Mappings({
            @Mapping(source = "partId", target = "idPart"),
            @Mapping(source = "decissionId", target = "idDecission")
    })
    PeritageDto fromDomainToDto (PeritageDomain peritageDomain);
}
