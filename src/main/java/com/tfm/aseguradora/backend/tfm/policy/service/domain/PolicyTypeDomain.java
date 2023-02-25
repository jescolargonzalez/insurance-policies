package com.tfm.aseguradora.backend.tfm.policy.service.domain;

import lombok.*;

import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyTypeDomain {

    private Integer id;

    private String nombre;

    private String description;
}
