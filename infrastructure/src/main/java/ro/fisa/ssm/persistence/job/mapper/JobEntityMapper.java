package ro.fisa.ssm.persistence.job.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fisa.ssm.model.Job;
import ro.fisa.ssm.persistence.job.entity.JobEntity;

/**
 * Created at 3/17/2024 by Darius
 **/
@Mapper
public interface JobEntityMapper {
    JobEntityMapper INSTANCE = Mappers.getMapper(JobEntityMapper.class);
    Job toModel(JobEntity jobEntity);
}
