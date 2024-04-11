package ro.fisa.ssm.persistence.employer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.persistence.employer.projection.EmployerDetailsProjection;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 4/11/2024 by Darius
 **/
@Mapper
public interface EmployerDetailsProjectionMapper {
    EmployerDetailsProjectionMapper INSTANCE = Mappers.getMapper(EmployerDetailsProjectionMapper.class);

    @Mapping(target = "name", source = "employer.name")
    @Mapping(target = "caen", source = "employer.caen")
    @Mapping(target = "contractsCount", source = "contractCount")
    @Mapping(target = "induction.id", source = "employer.induction.id")
    @Mapping(target = "induction.value", source = "employer.induction.value")
    @Mapping(target = "induction.updatedAt", source = "employer.induction.updatedAt")
    @Mapping(target = "induction.version", source = "employer.induction.version")
    @Mapping(target = "cuiCif", source = "employer.cuiCif")
    Employer toModel(EmployerDetailsProjection projection);

    @Mapping(target = "offset", source = "pageable.offset")
    @Mapping(target = "content", source = "content", qualifiedByName = "employerEntitiesToModels")
    DomainPage<Employer> toDomainPage(final Page<EmployerDetailsProjection> page);

    @Named("employerEntitiesToModels")
    default Collection<Employer> employerEntitiesToModels(Collection<EmployerDetailsProjection> entities) {
        return entities.stream()
                .map(this::toModel)
                .toList();
    }
}
