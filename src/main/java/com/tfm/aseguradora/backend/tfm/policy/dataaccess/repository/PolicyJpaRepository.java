package com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PolicyJpaRepository extends JpaRepository<PolicyEntity, Integer>{


    Optional<PolicyEntity> findByTomadorId(Integer tomadorId);
}
