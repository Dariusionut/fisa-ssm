package ro.fisa.ssm.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created at 4/11/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Induction extends PrimaryModel<Long> {

    private String value;
    private LocalDateTime updatedAt;
    private int version;

    public Induction(String value) {
        this.value = value;
    }
}
