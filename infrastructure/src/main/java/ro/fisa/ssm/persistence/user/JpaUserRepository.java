package ro.fisa.ssm.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created at 3/9/2024 by Darius
 **/
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {


    @Query("""
                SELECT u FROM UserEntity u
                WHERE u.cnp = :cnp
            """)
    Optional<UserEntity> findByCnp(@Param("cnp") String cnp);

    @Query("""
                SELECT u.id FROM UserEntity u
                WHERE u.cnp = :cnp
            """)
    Optional<Long> findIdByCnp(@Param("cnp") String cnp);

    @Query("""
            SELECT e FROM UserEntity e
            LEFT JOIN e.role r
            WHERE r.id = 1
            """)
    Collection<UserEntity> fetchEmployees();

    @Query("""
                SELECT u
                FROM UserEntity u
                WHERE u.cnp IN :cnpList
            """)
    Stream<UserEntity> fetchAllByCnp(@Param("cnpList") Collection<String> cnpList);
}
