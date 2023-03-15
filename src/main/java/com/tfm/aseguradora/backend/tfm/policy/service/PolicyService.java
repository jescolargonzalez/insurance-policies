package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.controller.mapper.*;
import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;

import com.tfm.aseguradora.backend.middle.users.client.UserApi;
import com.tfm.aseguradora.backend.middle.users.client.VehicleApi;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.context.*;
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
    public List<PolicyDomain> findByUserDni(String userDni){

        var userListWrapper = usersApi.getUsers(SecurityContextHolder.getContext().getAuthentication().getName(),
                userDni, null, "false");

        var idUser = new ArrayList<Integer>();
        var auxDom = new ArrayList<PolicyDomain>();

        for(int i = 0 ; i < userListWrapper.getUsers().size() ; i++){
            var userDto = userListWrapper.getUsers().get(i);
            var idDto = userDto.getId().intValue();
            idUser.add(idDto);
        }

        for (Integer aux : idUser) {
            var policyOpt = policyJpaRepository.findByTomadorId(aux);
            for (PolicyEntity policyEntity : policyOpt) {
                auxDom.add(policyMapper.fromEntityToDomain(policyEntity));
            }
        }

        if(!auxDom.isEmpty()){
            return auxDom;
        }else{
            throw new ResourceNotFoundException(PolicyDomain.class, userDni);
        }
    }

    @Transactional(readOnly = true)
    public List<PolicyDomain> findByBenefitDni(String benefitDni) {
        var opt = policyJpaRepository.findByBenefitDni(benefitDni);
        var auxDom = opt.stream()
                .map(policyMapper::fromEntityToDomain)
                .collect(Collectors.toList());
        if(!opt.isEmpty()){
            return auxDom;
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
    public PolicyDomain findPolicyById(Integer id){
        var aux = policyJpaRepository.findById(id);
        if(aux.isPresent()){
            return policyMapper.fromEntityToDomain(aux.get());
        }else{
            throw new ResourceNotFoundException(PolicyDomain.class,id);
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