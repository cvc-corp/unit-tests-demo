package br.com.cvc.api.unittestsdemo.repository;

import br.com.cvc.api.unittestsdemo.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<List<Branch>> findAllByState(String state);

}