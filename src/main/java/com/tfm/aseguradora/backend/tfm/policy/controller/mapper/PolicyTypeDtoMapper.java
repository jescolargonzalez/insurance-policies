package com.tfm.aseguradora.backend.tfm.policy.controller.mapper;

import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PolicyTypeDtoMapper {
     PolicyTypeDto domainToDto (PolicyTypeDomain policyTypeDomain);
}
