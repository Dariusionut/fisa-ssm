package ro.fisa.ssm.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;
import ro.fisa.ssm.model.AppDocument;

import java.io.IOException;

/**
 * Created at 3/14/2024 by Darius
 **/
@Mapper
public interface MultipartFileMapper {
    MultipartFileMapper INSTANCE = Mappers.getMapper(MultipartFileMapper.class);

    @Mapping(target = "originalFileName", source = "originalFilename")
    AppDocument toAppDocument(MultipartFile multipartFile) throws IOException;
}
