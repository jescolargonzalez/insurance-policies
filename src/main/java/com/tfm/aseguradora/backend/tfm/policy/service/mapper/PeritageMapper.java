package com.tfm.aseguradora.backend.tfm.policy.service.mapper;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PeritageMapper {
    @Mappings({
            @Mapping(source = "decission.id", target = "decissionId"),
            @Mapping(source = "part.id", target = "partId")
    })
    PeritageDomain fromEntityToDomain(PeritageEntity peritageEntity);

    @Mappings({
            @Mapping(source = "decissionId", target = "decission.id"),
            @Mapping(source = "partId", target = "part.id")
    })
    PeritageEntity fromDomainToEntity(PeritageDomain peritageDomain);
}
