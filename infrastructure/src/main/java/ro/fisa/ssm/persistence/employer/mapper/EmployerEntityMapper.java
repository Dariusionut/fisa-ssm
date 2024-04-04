package ro.fisa.ssm.persistence.employer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/25/2024 by Darius
 **/
@Mapper
public interface EmployerEntityMapper {

    EmployerEntityMapper INSTANCE = Mappers.getMapper(EmployerEntityMapper.class);

    Employer toModel(EmployerEntity employer);

    EmployerEntity toEntity(Employer employer);

    @Mapping(target = "offset", source = "pageable.offset")
    @Mapping(target = "content", source = "content", qualifiedByName = "employerEntitiesToModels")
    DomainPage<Employer> toDomainPage(final Page<EmployerEntity> page);

    @Named("employerEntitiesToModels")
    default Collection<Employer> employerEntitiesToModels(Collection<EmployerEntity> entities) {
        return entities.stream()
                .map(this::toModel)
                .toList();
    }
}
