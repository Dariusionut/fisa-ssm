package ro.fisa.ssm.persistence.employer.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;

/**
 * Created at 4/11/2024 by Darius
 **/
@AllArgsConstructor
@Getter
public class EmployerDetailsProjection {
    private EmployerEntity employer;
    private long contractCount;
}
