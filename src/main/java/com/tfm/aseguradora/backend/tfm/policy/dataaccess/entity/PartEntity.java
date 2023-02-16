package com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "parts")
public class PartEntity {

    private static final long serialVersionUID = -912412431249214L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "affected_dni")
    private String affectedDni;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private PolicyEntity policy;

    @Column(name = "add_info")
    private String addInfo;

    @Column(name = "pay")
    private Boolean pay;

    @Column(name = "create_time")
    private LocalDate create_time;

    @Column(name = "update_time")
    private LocalDate update_time;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "part")
    private List<PeritageEntity> peritages;


}
