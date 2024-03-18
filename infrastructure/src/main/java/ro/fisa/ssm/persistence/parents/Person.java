package ro.fisa.ssm.persistence.parents;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Created at 3/15/2024 by Darius
 **/
@Getter
@Setter
@MappedSuperclass
public class Person extends AuditableEntity {

    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column(name = "cnp")
    protected String cnp;
    @Column(name = "email")
    protected String email;
    @Column(name = "password")
    protected String password;
    @Column(name = "address")
    protected String address;

}
