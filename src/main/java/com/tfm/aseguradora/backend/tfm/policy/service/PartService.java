package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
public class PartService {

    @Autowired
    private PartJpaRepository partJpaRepository;
    @Autowired
    private PartMapper partMapper;
    @Autowired
    private PolicyJpaRepository policyJpaRepository;

    @Transactional
    public PartDomain savePart(PartDomain partDomain){
        var policyOpt = policyJpaRepository.findById(partDomain.getPolicyId());
        if(policyOpt.isPresent()){
            var part = partMapper.fromDomainToEntity(partDomain);
            part.setDeleted(Boolean.FALSE);
            partJpaRepository.save(part);
            var rs = partMapper.fromEntityToDomain(part);
            return rs ;
        }else {
            throw new BadRequestException("The policy with id " + partDomain.getPolicyId() + " does not exist");
        }
    }


    public PartDomain findPartById(Integer id){
        var aux = partJpaRepository.findById(id);
        if(aux.isPresent()){
            return partMapper.fromEntityToDomain(aux.get());
        }else{
            throw new ResourceNotFoundException(PartDomain.class,id);
        }
    }

    public List<PartDomain> findAllByPolicyIds(List<Integer> list) {
        var aux = partJpaRepository.findAllByPolicyIdIn(list);
        if(!aux.isEmpty()){
            var auxDom = aux.stream().map(partMapper::fromEntityToDomain).collect(Collectors.toList());
            return auxDom;
        }else{
            throw new ResourceNotFoundException();
        }
    }
}
