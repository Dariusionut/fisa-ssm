package ro.fisa.ssm.persistence.induction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.induction.entity.InductionEntity;

/**
 * Created at 4/10/2024 by Darius
 **/
@Repository
public interface JpaInductionRepository extends JpaRepository<InductionEntity, Long> {
}
