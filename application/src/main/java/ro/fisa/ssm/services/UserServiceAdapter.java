package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.*;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.port.secondary.UserRepository;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created at 3/18/2024 by Darius
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> getEmployees() {
        return this.userRepository.fetchEmployees();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<InductionDetail> fetchUnacceptedInductions(final long employeeId) {
        final Collection<Contract> contracts = this.contractRepository.fetchByEmployeeId(employeeId);
        final Collection<InductionDetail> inductionDetails = new LinkedList<>();

        contracts.parallelStream().forEach(contract -> {
            final InductionDetail inductionDetail = new InductionDetail();
            final Employer employer = contract.getEmployer();
            final Induction induction = employer.getInduction();
            inductionDetail.setLastUpdate(induction.getUpdatedAt());
            inductionDetail.setEmployerName(employer.getName());
            inductionDetail.setContractNo(contract.getNumber());
            inductionDetail.setContractId(contract.getId());
            inductionDetail.setAccepted(false);
            inductionDetails.add(inductionDetail);
        });
        return inductionDetails;
    }

    @Override
    public void acceptInduction(Long contractId) {
        this.contractRepository.acceptInduction(contractId);
    }
}
