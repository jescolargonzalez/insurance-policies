package com.tfm.aseguradora.backend.tfm.policy.service.mapper;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PartMapper {
    @Mappings({
            @Mapping(source = "policy.id", target = "policyId")
    })
    PartDomain fromEntityToDomain(PartEntity partEntity);

    @Mappings({
            @Mapping(source = "policyId", target = "policy.id")
    })
    PartEntity fromDomainToEntity(PartDomain partDomain);


}
