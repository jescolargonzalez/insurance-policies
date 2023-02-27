package com.tfm.aseguradora.backend.tfm.policy.service.mapper;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    @Mappings({
            @Mapping(source = "type.id", target = "typeId")
    })
    PolicyDomain fromEntityToDomain(PolicyEntity policyEntity);

    PolicyEntity fromDomainToEntity(PolicyDomain policyDomain);


}
