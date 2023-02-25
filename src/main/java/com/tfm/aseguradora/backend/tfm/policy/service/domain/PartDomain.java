package com.tfm.aseguradora.backend.tfm.policy.service.domain;

import lombok.*;

@Data
public class PartDomain {

    private Integer id;

    private Integer policyId;

    private String affectedDni;

    private String addInfo;

    private Boolean pay;


}
