package com.tfm.aseguradora.backend.tfm.policy.service.mapper;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    PolicyDomain fromEntityToDomain(PolicyEntity policyEntity);

    PolicyEntity fromDomainToEntity(PolicyDomain policyDomain);
}
