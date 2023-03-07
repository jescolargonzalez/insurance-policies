package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
            return partMapper.fromEntityToDomain(part);
        }else {
            throw new BadRequestException("The policy with id " + partDomain.getPolicyId() + " does not exist");
        }

    }



}
