package ro.fisa.ssm.controller.advices;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import ro.fisa.ssm.controller.response.ErrorResponse;
import ro.fisa.ssm.exceptions.NotFoundException;

/**
 * Created at 3/25/2024 by Darius
 **/

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorNotFoundResponse extends ErrorResponse {

    public ErrorNotFoundResponse(NotFoundException e) {
        super(e);
        super.status = HttpStatus.NOT_FOUND;
    }
}
