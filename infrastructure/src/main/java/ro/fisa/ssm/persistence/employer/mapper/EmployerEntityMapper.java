package ro.fisa.ssm.persistence.employer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;

/**
 * Created at 3/25/2024 by Darius
 **/
@Mapper
public interface EmployerEntityMapper {

    EmployerEntityMapper INSTANCE = Mappers.getMapper(EmployerEntityMapper.class);

    Employer toModel(EmployerEntity employer);
}
