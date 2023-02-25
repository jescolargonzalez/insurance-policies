package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;

import com.tfm.aseguradora.backend.middle.users.client.UserApi;
import org.jetbrains.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class PolicyService {

    @Autowired
    private PolicyJpaRepository policyJpaRepository;

    @Autowired
    private PolicyTypeJpaRepository policyTypeJpaRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private UserApi usersApi;

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

    @Transactional
    public PolicyDomain save(@NotNull PolicyDomain policyDomain){
        var typeId = policyDomain.getTypeId();
        var typeOpt = policyTypeJpaRepository.findById(typeId);

        if (typeOpt.isPresent()) {
            var policyEntity = policyMapper.fromDomainToEntity(policyDomain);
            policyEntity.setType(typeOpt.get());
            policyEntity = policyJpaRepository.save(policyEntity);
            return policyMapper.fromEntityToDomain(policyEntity);
        }
        else {
            throw new BadRequestException("The policy type " + policyDomain.getTypeId() + " does not exist");
        }
    }



}