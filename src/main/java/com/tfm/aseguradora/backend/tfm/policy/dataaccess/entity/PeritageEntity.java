package com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "peritages")
public class PeritageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private PartEntity part;

    @ManyToOne
    @JoinColumn(name = "decission_id")
    private DecissionTypeEntity decission;

    @Column(name = "information")
    private String information;

    @Column(name = "create_time")
    private LocalDate create_time;

    @Column(name = "update_time")
    private LocalDate update_time;

    @Column(name = "deleted")
    private Boolean deleted;




}
