package ro.fisa.ssm.persistence.contract.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 3/16/2024 by Darius
 **/
@Repository
public interface JpaContractRepository extends JpaRepository<ContractEntity, Long> {


}
