package com.tfm.aseguradora.backend.tfm.policy.service;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PeritageService{

    @Autowired
    private DecissionJpaRepository decissionJpaRepository;

    @Autowired
    private PartJpaRepository partJpaRepository;

    @Autowired
    private PolicyJpaRepository policyJpaRepository;



}
