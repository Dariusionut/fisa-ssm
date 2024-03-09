package ro.fisa.ssm.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;

/**
 * Created at 3/9/2024 by Darius
 **/
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Short> {
}
