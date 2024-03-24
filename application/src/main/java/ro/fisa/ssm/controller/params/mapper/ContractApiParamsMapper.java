package ro.fisa.ssm.controller.params.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.controller.params.ContractApiParams;

/**
 * Created at 3/25/2024 by Darius
 **/
@Mapper
public interface ContractApiParamsMapper {

    ContractApiParamsMapper INSTANCE = Mappers.getMapper(ContractApiParamsMapper.class);

    ContractContext toContext(ContractApiParams contractApiParams);
}
