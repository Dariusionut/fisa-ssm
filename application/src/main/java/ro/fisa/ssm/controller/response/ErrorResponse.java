package ro.fisa.ssm.controller.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created at 3/25/2024 by Darius
 **/
@Data
public class ErrorResponse {
    protected String message;
    protected HttpStatus status;

    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public int getStatusCode() {
        return this.status.value();
    }
}
