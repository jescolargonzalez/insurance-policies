package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import com.tfm.aseguradora.backend.tfm.policy.service.domain.*;
import com.tfm.aseguradora.backend.tfm.policy.service.exception.*;
import com.tfm.aseguradora.backend.tfm.policy.service.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PeritageService{

    @Autowired
    private DecissionJpaRepository decissionJpaRepository;
    @Autowired
    private PeritageMapper peritageMapper;
    @Autowired
    private PartJpaRepository partJpaRepository;

    @Autowired
    private PeritageJpaRepository peritageJpaRepository;

    public PeritageDomain savePeritage(PeritageDomain peritageDomain){
        var partOpt = partJpaRepository.findById(peritageDomain.getPartId());
        if(partOpt.isPresent()){
            var peritage = peritageMapper.fromDomainToEntity(peritageDomain);
            peritage.setDeleted(false);
            var auxDecission = peritage.getDecission().getId();
            if(auxDecission == null){
                var decission = decissionJpaRepository.findById(2).get();
                peritage.setDecission(decission);
            }else{
                var decission = decissionJpaRepository.findById(auxDecission).get();
                peritage.setDecission(decission);
            }

            peritageJpaRepository.save(peritage);
            return peritageMapper.fromEntityToDomain(peritage);
        }else {
            throw new BadRequestException("The peritage with partid " + peritageDomain.getPartId() + " does not exist");
        }

    }


}
