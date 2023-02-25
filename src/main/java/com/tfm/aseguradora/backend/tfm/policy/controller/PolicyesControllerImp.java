package com.tfm.aseguradora.backend.tfm.policy.controller;

import com.tfm.aseguradora.backend.tfm.policy.controller.mapper.*;
import com.tfm.aseguradora.backend.tfm.policy.service.*;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.util.*;

@RestController
public class PolicyesControllerImp implements PolizyesApi {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private PolicyDtoMapper policyDtoMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() { return PolizyesApi.super.getRequest(); }

    @Override
    public ResponseEntity<PolicyDto> createPolicy(PolicyDto policyDto){
        var policyDomain = policyDtoMapper.DtoToDomain(policyDto);
        policyDomain = policyService.save(policyDomain);
        var policyDtoResponse = policyDtoMapper.domainToDTO(policyDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(policyDtoResponse);
    }

    @Override
    public ResponseEntity<PolicyDto> getPolicyByDni(String tomadorDni, String benefitDni) {
        if (tomadorDni != null) {
            var aux = policyService.findByUserDni(tomadorDni);
            return ResponseEntity.ok(policyDtoMapper.domainToDTO(aux));
        }
        else {
            var aux = policyService.findByUserDni(benefitDni);
            return ResponseEntity.ok(policyDtoMapper.domainToDTO(aux));
        }
    }



}