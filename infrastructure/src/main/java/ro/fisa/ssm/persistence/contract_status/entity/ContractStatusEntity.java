package ro.fisa.ssm.persistence.contract_status.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/27/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.CONTRACT_STATUS, schema = DbConstants.Schemas.PUBLIC)
public class ContractStatusEntity {

    @Id
    private Short id;

    @Column(name = DbConstants.Column.NAME, length = DbConstants.Length.LENGTH_15, unique = true)
    private String name;

}
