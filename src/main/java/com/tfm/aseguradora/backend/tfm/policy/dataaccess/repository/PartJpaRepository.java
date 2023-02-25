package com.tfm.aseguradora.backend.tfm.policy.dataaccess.repository;

import com.tfm.aseguradora.backend.tfm.policy.dataaccess.entity.*;
import org.springframework.data.jpa.repository.*;

public interface PartJpaRepository extends JpaRepository<PartEntity,Integer> {
}
