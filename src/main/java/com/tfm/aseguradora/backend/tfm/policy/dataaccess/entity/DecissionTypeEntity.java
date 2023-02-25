package com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "decissions")
public class DecissionTypeEntity {

    private static final long serialVersionUID = -912412431249214L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private LocalDate create_time;

    @Column(name = "update_time")
    private LocalDate update_time;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "decission")
    private List<PeritageEntity> peritages;

}
