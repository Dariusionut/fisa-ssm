package ro.fisa.ssm.model;

import lombok.*;

/**
 * Created at 3/16/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Job extends PrimaryModel<Long> {

    private String name;
}
