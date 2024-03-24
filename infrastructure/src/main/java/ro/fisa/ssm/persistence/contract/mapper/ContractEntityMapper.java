package ro.fisa.ssm.persistence.contract.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.mapper.EmployerEntityMapper;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.job.mapper.JobEntityMapper;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;

/**
 * Created at 3/17/2024 by Darius
 **/
@Mapper
public interface ContractEntityMapper {
    ContractEntityMapper INSTANCE = Mappers.getMapper(ContractEntityMapper.class);

    ContractEntity toEntity(Contract employeeContract);

    @Mapping(target = "employee.contracts", ignore = true)
    Contract toModel(ContractEntity contractEntity);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "employer", ignore = true)
    @Mapping(target = "job", ignore = true)
    Contract toModel(ContractEntity contractEntity, @Context ContractContext context);


    @Named("toModelIgnoreEmployee")
    @Mapping(target = "employee", ignore = true)
    Contract toModelIgnoreEmployee(ContractEntity contractEntity);

    @AfterMapping
    default void afterMapping(@MappingTarget Contract contract,
                              ContractEntity contractEntity,
                              @Context ContractContext context) {
        if (!context.ignoreEmployee()) {
            final UserEntity userEntity = contractEntity.getEmployee();
            contract.setEmployee(UserEntityMapper.INSTANCE.toEmployeeIgnoreContracts(userEntity));
        }
        if (!context.ignoreEmployer()) {
            final EmployerEntity employer = contractEntity.getEmployer();
            contract.setEmployer(EmployerEntityMapper.INSTANCE.toModel(employer));
        }
        if (!context.ignoreJob()) {
            final JobEntity jobEntity = contractEntity.getJob();
            contract.setJob(JobEntityMapper.INSTANCE.toModel(jobEntity));
        }
    }


}
