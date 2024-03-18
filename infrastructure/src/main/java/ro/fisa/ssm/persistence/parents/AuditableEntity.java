package ro.fisa.ssm.persistence.parents;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Created at 3/18/2024 by Darius
 **/
@Getter
@Setter
@MappedSuperclass
public class AuditableEntity extends VersionedEntity {

    @CreatedDate
    protected LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}
