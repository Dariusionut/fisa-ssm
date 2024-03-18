package ro.fisa.ssm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Created at 3/17/2024 by Darius
 **/
@Configuration
public class JacksonConfig {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    @Primary
    public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false).build();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder(final DateFormat dateFormat,
                                                      final LocalDateSerializer localDateSerializer,
                                                      final LocalDateDeserializer localDateDeserializer,
                                                      final LocalDateTimeSerializer localDateTimeSerializer,
                                                      final LocalDateTimeDeserializer localDateTimeDeserializer
    ) {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        return new Jackson2ObjectMapperBuilder()
                .modules(javaTimeModule)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .dateFormat(dateFormat)
                .serializers(localDateSerializer, localDateTimeSerializer)
                .deserializers(localDateDeserializer, localDateTimeDeserializer);
    }

    @Bean
    public DateFormat dateFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }

    @Bean
    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Bean
    public LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }
}
