package ro.fisa.ssm.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

import java.util.Collection;

/**
 * Created at 3/16/2024 by Darius
 **/
@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "cnp", source = "cnp")
    UserEntity toEntity(Employee employee);

    default Collection<UserEntity> toEntities(Collection<Employee> employees) {
        return employees.stream().map(this::toEntity).toList();
    }

    Employee toEmployeeModel(UserEntity userEntity);

    default Collection<Employee> toEmployeeModels(Collection<UserEntity> entities) {
        return entities.stream().map(this::toEmployeeModel).toList();
    }
}
