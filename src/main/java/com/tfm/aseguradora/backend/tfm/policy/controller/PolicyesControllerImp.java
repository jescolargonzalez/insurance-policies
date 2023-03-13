package com.tfm.aseguradora.backend.tfm.policy.controller;

import com.tfm.aseguradora.backend.tfm.policy.controller.mapper.*;
import com.tfm.aseguradora.backend.tfm.policy.service.*;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.util.*;
import java.util.stream.*;

@RestController
public class PolicyesControllerImp implements PolizyesApi {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private PartService partService;
    @Autowired
    private PolicyDtoMapper policyDtoMapper;
    @Autowired
    private PolicyTypeDtoMapper policyTypeDtoMapper;
    @Autowired
    private PartDtoMapper partDtoMapper;
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return PolizyesApi.super.getRequest();
    }

    @Override
    public ResponseEntity<PolicyDto> createPolicy(PolicyDto policyDto) {
        var policyDomain = policyDtoMapper.DtoToDomain(policyDto);
        policyDomain = policyService.save(policyDomain);
        var policyDtoResponse = policyDtoMapper.domainToDTO(policyDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(policyDtoResponse);
    }
    //TODO error al haber varios benefitDNI ==    ||     no funciona la parte del tomadorDni
    @Override
    public ResponseEntity<PolicyDto> getPolicyByDni(String tomadorDni, String benefitDni) {
        if (tomadorDni != null) {
            var aux = policyService.findByUserDni(tomadorDni);
            return ResponseEntity.ok(policyDtoMapper.domainToDTO(aux));
        } else {
            var aux = policyService.findByBenefitDni(benefitDni);
            return ResponseEntity.ok(policyDtoMapper.domainToDTO(aux));
        }
    }

    @Override
    public ResponseEntity<PolicyesTypesWrapperDto> findTypesOfPolicy() {
        var policyTypesDomain = policyService.findAllPolicyType();
        var policyTypesDto = policyTypesDomain.stream()
                .map(policyTypeDtoMapper::domainToDto).collect(Collectors.toList());
        var responseDto = new PolicyesTypesWrapperDto();
        responseDto.setTypes(policyTypesDto);
        return ResponseEntity.ok(responseDto);
    }
//TODO dice err501 methods no implementado :S
    @Override
    public ResponseEntity<Void> savePart(Integer idPolicy, PartDto partDto) {
        partDto.setPolicyId(idPolicy.longValue());
        var part = partDtoMapper.DtoToDomain(partDto);
        partService.savePart(part);
        return ResponseEntity.ok().build();
    }

}

