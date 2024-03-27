package ro.fisa.ssm.persistence.contract.listener;

import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;

/**
 * Created at 3/27/2024 by Darius
 **/

@Slf4j
public class ContractEntityListener {


    @PrePersist
    public void prePersist(final ContractEntity contract) {

    }
}
