package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.controller.mapper.*;
import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;

import com.tfm.aseguradora.backend.middle.users.client.UserApi;
import com.tfm.aseguradora.backend.middle.users.client.VehicleApi;
import com.tfm.aseguradora.generated.backend.tfm.policies.controller.*;
import org.jetbrains.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.*;

@Service
public class PolicyService {

    @Autowired
    private PolicyJpaRepository policyJpaRepository;
    @Autowired
    private PolicyTypeJpaRepository policyTypeJpaRepository;
    @Autowired
    private PolicyTypeMapper policyTypeMapper;
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private UserApi usersApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Transactional(readOnly = true)
    public PolicyDomain findByUserDni(String userDni){

        var userListWrapper = usersApi.getUsers(userDni,null);

        var userDto = userListWrapper.getUsers().get(0);

        var idDto = userDto.getId().intValue();

        var policyOpt = policyJpaRepository.findByTomadorId(idDto);

        if(policyOpt.isPresent()){
            return policyMapper.fromEntityToDomain(policyOpt.get());
        }
        else{
            throw new ResourceNotFoundException(PolicyDomain.class, userDni);
        }
    }

    @Transactional(readOnly = true)
    public PolicyDomain findByBenefitDni(String benefitDni) {
        var opt = policyJpaRepository.findByBenefitDni(benefitDni);
        if(opt.isPresent()){
            return policyMapper.fromEntityToDomain(opt.get());
        }else{
            throw new BadRequestException("not exist that dni");
        }
    }

    @Transactional
    public PolicyDomain save(PolicyDomain policyDomain){
        var typeId = policyDomain.getTypeId();
        var typeOpt = policyTypeJpaRepository.findById(typeId);

        if (typeOpt.isPresent()) {
            try {
                vehicleApi.getVehiclesById(policyDomain.getVehicleId());
            } catch (HttpClientErrorException ex) {
                if (ex.getRawStatusCode() == 404) {
                    throw new BadRequestException("The vehicle id " + policyDomain.getTypeId() + " does not exist");
                }
                throw ex;
            }

            var policyEntity = policyMapper.fromDomainToEntity(policyDomain);
            policyEntity.setType(typeOpt.get());
            policyEntity.setDeleted(Boolean.FALSE);
            policyEntity = policyJpaRepository.save(policyEntity);
            return policyMapper.fromEntityToDomain(policyEntity);
        }
        else {
            throw new BadRequestException("The policy type " + policyDomain.getTypeId() + " does not exist");
        }
    }
    @Transactional(readOnly = true)
    public PolicyTypeDomain findPolicyTypeById(int type){
        var aux = policyTypeJpaRepository.findById(type);
        if(aux.isPresent()){
            return policyTypeMapper.fromEntityToDomain(aux.get());
        }else{
            throw new ResourceNotFoundException(PolicyTypeDomain.class, type);
        }
    }

    @Transactional(readOnly = true)
    public List<PolicyTypeDomain> findAllPolicyType(){
        var auxEnt = policyTypeJpaRepository.findAll();
        var auxDom = auxEnt.stream()
                .map(policyTypeMapper::fromEntityToDomain)
                .collect(Collectors.toList());

        if(!auxDom.isEmpty()){
            return auxDom;
        }else{
            throw new BadRequestException("Algo no ha ido bien");
        }
    }

}