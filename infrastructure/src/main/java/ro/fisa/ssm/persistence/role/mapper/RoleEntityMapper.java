package ro.fisa.ssm.persistence.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Role;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;

/**
 * Created at 3/9/2024 by Darius
 **/
@Mapper
public interface RoleEntityMapper {
    RoleEntityMapper INSTANCE = Mappers.getMapper(RoleEntityMapper.class);

    RoleEntity toEntity(Role role);
}
