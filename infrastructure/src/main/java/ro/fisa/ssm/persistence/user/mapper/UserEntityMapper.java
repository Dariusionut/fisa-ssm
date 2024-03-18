package ro.fisa.ssm.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.contract.mapper.ContractEntityMapper;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

/**
 * Created at 3/16/2024 by Darius
 **/
@Mapper(uses = {ContractEntityMapper.class})
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity toEntity(Employee employee);


    @Mapping(target = "contracts", source = "contracts", qualifiedByName = "toModelIgnoreEmployee")
    Employee toEmployeeModel(UserEntity userEntity);


}
