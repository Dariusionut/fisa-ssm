package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.controller.params.ContractApiParams;
import ro.fisa.ssm.controller.params.mapper.ContractApiParamsMapper;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.port.primary.ContractService;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/24/2024 by Darius
 **/
@RestController
@RequestMapping(path = "api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DomainPage<Contract>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0") int number,
                                                       @RequestParam(value = "pageSize", defaultValue = "50") int size,
                                                       @RequestParam(value = "employerName", defaultValue = "") String employerName,
                                                       ContractApiParams contractApiParams
    ) {
        final ContractContext context = this.createContractContext(contractApiParams);
        final DomainPage<Contract> contractPage = this.contractService.getContractPage(number, size, employerName, context);

        return ResponseEntity.ok(contractPage);
    }

    @GetMapping(path = "cnp")
    public ResponseEntity<Collection<Contract>> getByEmployeeCnp(@RequestParam(name = "cnp") final String cnp,
                                                                 ContractApiParams contractApiParams

    ) {
        final ContractContext context = this.createContractContext(contractApiParams);
        final Collection<Contract> contracts = this.contractService.getByEmployeeCnp(cnp, context);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping(path = "number")
    public ResponseEntity<Contract> getByNumber(@RequestParam(name = "contractNumber") final String number,
                                                ContractApiParams contractApiParams) {
        final ContractContext context = this.createContractContext(contractApiParams);
        final Contract contract = this.contractService.getByNumber(number, context);
        return ResponseEntity.ok(contract);
    }

    private ContractContext createContractContext(final ContractApiParams contractApiParams) {
        return ContractApiParamsMapper.INSTANCE.toContext(contractApiParams);
    }
}
