package ro.fisa.ssm.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

import java.util.Optional;

/**
 * Created at 3/9/2024 by Darius
 **/
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {


    @Query("""
                SELECT u FROM UserEntity u
                WHERE u.cnp = :cnpOrEmail
                OR u.email = :cnpOrEmail
            """)
    Optional<UserEntity> findByCnpOrEmail(@Param("cnpOrEmail") String cnpOrEmail);
}
