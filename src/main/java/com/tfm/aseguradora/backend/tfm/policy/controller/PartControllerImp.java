package com.tfm.aseguradora.backend.tfm.policy.controller;

import com.tfm.aseguradora.backend.tfm.policy.controller.mapper.*;
import com.tfm.aseguradora.backend.tfm.policy.service.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.*;

@RestController
public class PartControllerImp implements PartsApi {
    @Autowired
    private PartService partService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private PartDtoMapper partDtoMapper;

    @Override
    public ResponseEntity<PartsListWrapperDto> getPartsByDni(String benefitDni) {
         var policies = policyService.findByBenefitDni(benefitDni);
         var idsPolicy = policies.stream().map(PolicyDomain::getId).collect(Collectors.toList());
         var auxDom = partService.findAllByPolicyIds(idsPolicy);
         var auxDto = auxDom.stream().map(partDtoMapper::DomainToDto).collect(Collectors.toList());
         var responseDTO = new PartsListWrapperDto();
         for (PartDto aux : auxDto ) {
            responseDTO.addPartsItem(aux);
        }
         return ResponseEntity.ok(responseDTO);
    }
}
