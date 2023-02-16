package com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity;

import lombok.*;
import javax.persistence.*;
import java.time.*;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "policies_types")
public class PolicyTypeEntity {

    private static final long serialVersionUID = -912412796449214L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "type")
    private List<PolicyEntity> policies;

    @Column(name = "create_time")
    private LocalDate create_time;

    @Column(name = "update_time")
    private LocalDate update_time;

    @Column(name = "deleted")
    private Boolean deleted;



}
