package ro.fisa.ssm.persistence.job.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created at 3/16/2024 by Darius
 **/
@Repository
public interface JpaJobRepository extends JpaRepository<JobEntity, Integer> {

    @Query("""
                     SELECT j FROM JobEntity j
                     WHERE TRIM(LOWER(j.name)) = TRIM(LOWER(:name))
            """)
    Optional<JobEntity> fetchByName(@Param("name") String name);

    @Query("""
                     SELECT j.id FROM JobEntity j
                     WHERE TRIM(LOWER(j.name)) = TRIM(LOWER(:name))
            """)
    Optional<Long> fetchIdByName(@Param("name") String name);

}
