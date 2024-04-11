package ro.fisa.ssm.persistence.employer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.projection.EmployerDetailsProjection;

import java.util.Optional;

/**
 * Created at 3/19/2024 by Darius
 **/
@Repository
public interface JpaEmployerRepository extends JpaRepository<EmployerEntity, Long> {

    @Query("""
             SELECT new ro.fisa.ssm.persistence.employer.projection.EmployerDetailsProjection(
             e,
             COUNT(c)
             )
             FROM ContractEntity c
             LEFT JOIN c.employer e
             WHERE TRIM(e.name) = TRIM(:name)
             GROUP BY e
            """)
    Optional<EmployerDetailsProjection> fetchByName(@Param("name") String name);

    @Query("""
             SELECT new ro.fisa.ssm.persistence.employer.projection.EmployerDetailsProjection(
             e,
             COUNT(c)
             )
             FROM ContractEntity c
             LEFT JOIN c.employer e
             GROUP BY e
            """)
    Page<EmployerDetailsProjection> fetchPage(Pageable pageable);
}
