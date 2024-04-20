package ro.fisa.ssm.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.enums.ContractStatusEnum;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.projection.UserSecurityDetailProjection;

import java.util.Collection;
import java.util.List;
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
             SELECT new ro.fisa.ssm.persistence.user.projection.UserSecurityDetailProjection(
             u.id,
             u.firstName,
             u.lastName,
             u.cnp,
             r.name,
             n.name,
             u.password
             ) FROM UserEntity u
             LEFT JOIN u.role r
             LEFT JOIN u.nationality n
             LEFT JOIN u.contracts c
             WHERE u.cnp = :cnp
            """)
    Optional<UserSecurityDetailProjection> findDetailsByCnp(@Param("cnp") String cnp);

    @Query("""
                  SELECT DISTINCT c.status.name
                  FROM ContractEntity c
                  LEFT JOIN c.employee u
                  WHERE u.cnp = :cnp
            """)
    List<ContractStatusEnum> fetchContractStatuses(@Param("cnp") String cnp);

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
