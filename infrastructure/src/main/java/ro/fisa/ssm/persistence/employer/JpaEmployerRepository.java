package ro.fisa.ssm.persistence.employer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;

import java.util.Optional;

/**
 * Created at 3/19/2024 by Darius
 **/
@Repository
public interface JpaEmployerRepository extends JpaRepository<EmployerEntity, Long> {

    @Query("""
             SELECT e
             FROM EmployerEntity e
             WHERE TRIM(e.name) = TRIM(:name)
            """)
    Optional<EmployerEntity> fetchByName(@Param("name") String name);
}
