package ro.fisa.ssm.persistence.contract.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created at 3/16/2024 by Darius
 **/
@Repository
public interface JpaContractRepository extends JpaRepository<ContractEntity, Long> {


    @Query("""
            SELECT c FROM ContractEntity c
            WHERE TRIM(c.number) = TRIM(:number)
            """)
    Optional<ContractEntity> findByNumber(@Param("number") String number);

    @Query("""
            SELECT c FROM ContractEntity c
             """)
    Page<ContractEntity> fetchContractPage(Pageable pageable);

    @Query("""
                SELECT c
                FROM ContractEntity c
                LEFT JOIN c.employee e
                WHERE TRIM(e.cnp) = TRIM(:cnp)
            """)
    Stream<ContractEntity> fetchByEmployeeCnp(@Param("cnp") final String cnp);

    @Query("""
                     SELECT c
                     FROM ContractEntity c
                     WHERE c.number IN :numbers
            """)
    Stream<ContractEntity> fetchAllByNumbers(@Param("numbers") final Collection<String> numbers);

}
