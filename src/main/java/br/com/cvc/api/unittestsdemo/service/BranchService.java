package br.com.cvc.api.unittestsdemo.service;

import br.com.cvc.api.unittestsdemo.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {

    Optional<List<Branch>> findByState(String state);

}