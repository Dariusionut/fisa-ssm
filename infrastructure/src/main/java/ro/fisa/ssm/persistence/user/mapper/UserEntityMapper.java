package ro.fisa.ssm.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

/**
 * Created at 3/16/2024 by Darius
 **/
@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity toEntity(Employee employee);
}
