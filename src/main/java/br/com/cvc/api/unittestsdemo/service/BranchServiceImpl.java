package br.com.cvc.api.unittestsdemo.service;

import br.com.cvc.api.unittestsdemo.exception.CouldNotFindBranchesException;
import br.com.cvc.api.unittestsdemo.model.Branch;
import br.com.cvc.api.unittestsdemo.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService{

    private static final String STATE_MUST_NOT_BE_NULL = "The given state must not be null!";

    private final BranchRepository branchRepository;

    @Override
    public Optional<List<Branch>> findByState(final String state) {
        if(state == null || state.isEmpty()) {
            throw new IllegalArgumentException(STATE_MUST_NOT_BE_NULL);
        }

        log.info("Finding Branches by State: {}", state);

        try{
            return this.branchRepository.findAllByState(state);
        } catch (final DataAccessException dae) {
            log.error("Data Exception finding branches by state", dae);
            throw new CouldNotFindBranchesException(dae);
        } catch (final Exception e) {
            log.error("Unexpected Exception finding branches by state", e);
            throw new CouldNotFindBranchesException(e);
        }
    }

}