package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created at 3/17/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuditableModel<T extends Number> extends VersionedModel<T> {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
