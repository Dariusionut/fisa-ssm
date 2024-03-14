package ro.fisa.ssm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;

/**
 * Created at 3/14/2024 by Darius
 **/

@Getter
@Setter
@ToString
public class AppDocument {
    private String name;
    private String originalFileName;
    private byte[] bytes;
    private InputStream inputStream;
    private String type;
    private String contentType;
}
