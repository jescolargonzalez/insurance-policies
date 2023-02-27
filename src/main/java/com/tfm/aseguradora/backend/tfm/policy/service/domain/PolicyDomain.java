package com.tfm.aseguradora.backend.tfm.policy.service.domain;

import lombok.*;

@Data
public class PolicyDomain {

    private Integer id;

    private Integer tomadorId;

    private Integer vehicleId;

    private Integer typeId;

    private String benefitDni;

}
