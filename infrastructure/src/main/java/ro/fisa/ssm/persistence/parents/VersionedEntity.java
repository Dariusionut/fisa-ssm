package ro.fisa.ssm.persistence.parents;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Created at 3/16/2024 by Darius
 **/
@Getter
@Setter
@MappedSuperclass
public class VersionedEntity {

//    @Version
//    protected int version;
}
