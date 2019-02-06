package br.com.cvc.api.unittestsdemo.controller;

import br.com.cvc.api.unittestsdemo.model.Branch;
import br.com.cvc.api.unittestsdemo.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Api(
        value = "/branches",
        description = "Branches Operations",
        protocols = "http",
        tags = "branches"
)
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/{stateCode}")
    @ApiOperation(value = "Find all branches by state code",
            notes = "Possible values: SP, MG and RJ")
    public List<Branch> getBranchesByState(@ApiParam(name="stateCode", allowableValues = "SP, MG, RJ", defaultValue = "SP")
                                               @PathVariable("stateCode") final String stateCode) {
        try {
            return this.branchService.findByState(stateCode)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch(final ResponseStatusException rse) {
            throw rse;
        } catch(final Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", e);
        }
    }

}