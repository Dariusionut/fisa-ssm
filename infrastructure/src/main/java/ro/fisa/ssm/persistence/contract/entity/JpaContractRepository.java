package ro.fisa.ssm.persistence.contract.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created at 3/16/2024 by Darius
 **/
@Repository
public interface JpaContractRepository extends JpaRepository<ContractEntity, Long> {

    @Query("""
                SELECT c FROM ContractEntity c
                LEFT JOIN c.employee e
                WHERE TRIM(e.cnp) = TRIM(:cnp)
            """)
    Optional<ContractEntity> findByCnp(@Param("cnp") String cnp);
}
