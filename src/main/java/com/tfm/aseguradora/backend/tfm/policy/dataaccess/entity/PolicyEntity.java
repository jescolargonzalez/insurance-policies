package com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "policies")
public class PolicyEntity {

    private static final long serialVersionUID = -912412354249214L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer tomadorId;

    @Column(name = "benefit_dni")
    private String benefitDni;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PolicyTypeEntity type;

    @OneToMany(mappedBy = "policy")
    private List<PartEntity> parts;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

}
