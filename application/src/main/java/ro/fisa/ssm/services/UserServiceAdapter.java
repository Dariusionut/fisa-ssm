package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.model.Induction;
import ro.fisa.ssm.model.InductionDetail;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.port.secondary.UserRepository;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

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
    public DomainPage<InductionDetail> fetchUnacceptedInductions(final long employeeId, int number, int size) {
        final Page<InductionDetail> detailPage = this.contractRepository
                .fetchByEmployeeId(employeeId, PageRequest.of(number, size))
                .map(contract -> {
                    final InductionDetail inductionDetail = new InductionDetail();
                    final Employer employer = contract.getEmployer();
                    final Induction induction = employer.getInduction();
                    inductionDetail.setLastUpdate(induction.getUpdatedAt());
                    inductionDetail.setEmployerName(employer.getName());
                    inductionDetail.setContractNo(contract.getNumber());
                    inductionDetail.setContractId(contract.getId());
                    inductionDetail.setAccepted(false);
                    return inductionDetail;
                });
        return DomainPage.fromPage(detailPage);
    }

    @Override
    public void acceptInduction(Long contractId) {
        this.contractRepository.acceptInduction(contractId);
    }
}
