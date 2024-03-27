package ro.fisa.ssm.persistence.contract.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.enums.ContractStatusEnum;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.contract_status.entity.ContractStatusEntity;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.mapper.EmployerEntityMapper;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.job.mapper.JobEntityMapper;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/17/2024 by Darius
 **/
@Mapper(uses = {UserEntityMapper.class})
public interface ContractEntityMapper {
    ContractEntityMapper INSTANCE = Mappers.getMapper(ContractEntityMapper.class);

    ContractEntity toEntity(Contract employeeContract);

    @Mapping(target = "employee", qualifiedByName = "toEmployeeIgnoreContracts")
    @Mapping(target = "status", ignore = true)
    Contract toModel(ContractEntity contractEntity);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "employer", ignore = true)
    @Mapping(target = "job", ignore = true)
    @Mapping(target = "status", ignore = true)
    Contract toModel(ContractEntity contractEntity, @Context ContractContext context);

    @Mapping(target = "offset", source = "pageable.offset")
    @Mapping(target = "content", source = "content", qualifiedByName = "contractEntitiesToModels")
    DomainPage<Contract> toDomainPage(final Page<ContractEntity> page, @Context ContractContext context);

    @Named("toModelIgnoreEmployee")
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "status", ignore = true)
    Contract toModelIgnoreEmployee(ContractEntity contractEntity);


    @Named("contractEntitiesToModels")
    default Collection<Contract> contractEntitiesToModels(Collection<ContractEntity> contractEntities, @Context ContractContext context) {
        return contractEntities.stream()
                .map(c -> this.toModel(c, context))
                .toList();
    }

    @AfterMapping
    default void afterMappingToEntity(@MappingTarget ContractEntity contractEntity,
                                      Contract contract
    ) {
        final ContractStatusEnum status = contract.getStatus();
        final ContractStatusEntity contractStatusEntity = new ContractStatusEntity();
        contractStatusEntity.setId(status.id());
        contractStatusEntity.setName(status.name());
        contractEntity.setStatus(contractStatusEntity);
    }

    @AfterMapping
    default void afterMappingToAllModels(@MappingTarget Contract contract,
                                         ContractEntity contractEntity) {
        final ContractStatusEntity statusEntity = contractEntity.getStatus();
        final String statusStr = statusEntity.getName();
        final ContractStatusEnum statusEnum = ContractStatusEnum.valueOf(statusStr);
        contract.setStatus(statusEnum);
    }

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
