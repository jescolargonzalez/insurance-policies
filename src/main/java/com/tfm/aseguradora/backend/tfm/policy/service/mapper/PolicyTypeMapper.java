package com.tfm.aseguradora.backend.tfm.policy.service.mapper;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PolicyTypeMapper {
      PolicyTypeDomain fromEntityToDomain(PolicyTypeEntity policyTypeEntity);

      PolicyTypeEntity fromDomainToEntity(PolicyTypeDomain policyTypeDomain);
}
