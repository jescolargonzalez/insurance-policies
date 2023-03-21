package com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PartJpaRepository extends JpaRepository<PartEntity,Integer> {

    @Query(name = "select p from PartEntity p where p.")
    List<PartEntity> findAllByPolicyIdIn(List<Integer> id);

}