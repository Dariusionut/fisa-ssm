package ro.fisa.ssm.persistence.nationality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.nationality.entity.NationalityEntity;

import java.util.Optional;

/**
 * Created at 3/16/2024 by Darius
 **/
@Repository
public interface JpaNationalityRepository extends JpaRepository<NationalityEntity, Integer> {

    @Query("""
                     SELECT n FROM NationalityEntity n
                     WHERE n.name = :name
            """)
    Optional<NationalityEntity> fetchByName(@Param("name") String name);
}
