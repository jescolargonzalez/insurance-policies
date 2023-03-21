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
    private PeritageService peritageService;

    @Autowired
    private PeritageDtoMapper peritageDtoMapper;
    @Autowired
    private PolicyDtoMapper policyDtoMapper;
    @Autowired
    private PolicyTypeDtoMapper policyTypeDtoMapper;
    @Autowired
    private PartDtoMapper partDtoMapper;

    @Override
    public ResponseEntity<PolicyDto> createPolicy(PolicyDto policyDto) {
        var policyDomain = policyDtoMapper.DtoToDomain(policyDto);
        policyDomain = policyService.save(policyDomain);
        var policyDtoResponse = policyDtoMapper.domainToDTO(policyDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(policyDtoResponse);
    }

    @Override
    public ResponseEntity<PolicyesWrapperDto> getPolicyByDni(String tomadorDni, String benefitDni) {
        if (tomadorDni != null) {
            var responseDto = new PolicyesWrapperDto();
            var aux = policyService.findByUserDni(tomadorDni);
            var auxDto = aux.stream()
                    .map(policyDtoMapper::domainToDTO)
                    .collect(Collectors.toList());
            responseDto.setTypes(auxDto);
            return ResponseEntity.ok(responseDto);
            } else {
                var responseDto = new PolicyesWrapperDto();
                var aux = policyService.findByBenefitDni(benefitDni);
                var auxDto = aux.stream().map(policyDtoMapper::domainToDTO).collect(Collectors.toList());
                responseDto.setTypes(auxDto);
                return ResponseEntity.ok(responseDto);
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

    @Override
    public ResponseEntity<Void> savePart(Integer idPolicy, PartDto partDto) {
        partDto.setPolicyId(idPolicy.longValue());
        var opt = policyService.findPolicyById(idPolicy);
        if (opt.getId() == idPolicy) {
            var part = partDtoMapper.DtoToDomain(partDto);
            partService.savePart(part);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PeritageDto> doPeritageAction(Integer idPolicy, Integer idPart, PeritageDto peritageDto) {
        policyService.findPolicyById(idPolicy);
        var opt = partService.findPartById(idPart);
        if(opt.getId() == idPart){
            var peritage = peritageDtoMapper.fromDtoToDomain(peritageDto);
            peritageService.savePeritage(peritage);
        }
        return ResponseEntity.ok().build();
    }
}

