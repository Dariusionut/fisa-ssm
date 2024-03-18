package ro.fisa.ssm.persistence.contract.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;

/**
 * Created at 3/17/2024 by Darius
 **/
@Mapper
public interface ContractEntityMapper {
    ContractEntityMapper INSTANCE = Mappers.getMapper(ContractEntityMapper.class);

    ContractEntity toEntity(Contract employeeContract);

    @Mapping(target = "employee.contracts", ignore = true)
    Contract toModel(ContractEntity contractEntity);

    @Named("toModelIgnoreEmployee")
    @Mapping(target = "employee", ignore = true)
    Contract toModelIgnoreEmployee(ContractEntity contractEntity);
}
