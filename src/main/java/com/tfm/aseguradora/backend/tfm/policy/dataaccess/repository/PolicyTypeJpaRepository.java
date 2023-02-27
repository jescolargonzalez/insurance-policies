package com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import org.jetbrains.annotations.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PolicyTypeJpaRepository extends JpaRepository<PolicyTypeEntity, Integer> {

    Optional<PolicyTypeEntity> findById(Integer id);

}
