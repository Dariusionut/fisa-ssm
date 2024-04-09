package ro.fisa.ssm.persistence.induction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ro.fisa.ssm.persistence.utils.DbConstants;

import java.time.LocalDateTime;

/**
 * Created at 4/10/2024 by Darius
 **/

@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.INDUCTION, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.INDUCTION,
        sequenceName = DbConstants.Sequences.INDUCTION,
        allocationSize = 1
)
public class InductionEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.INDUCTION
    )
    protected Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @LastModifiedDate
    @CreatedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private int version;
}
