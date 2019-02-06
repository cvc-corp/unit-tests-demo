package br.com.cvc.api.unittestsdemo.service;

import br.com.cvc.api.unittestsdemo.exception.CouldNotFindBranchesException;
import br.com.cvc.api.unittestsdemo.model.Branch;
import br.com.cvc.api.unittestsdemo.repository.BranchRepository;
import org.assertj.core.api.Condition;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.CannotAcquireLockException;

import java.util.List;
import java.util.Optional;

import static br.com.cvc.api.unittestsdemo.templates.Templates.TEMPLATES_PACKAGE;
import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl target;

    @BeforeClass
    public static void setUpClass() {
        loadTemplates(TEMPLATES_PACKAGE);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findByStateWithNullParameterMustThrowException() {
        //given
        final String state = null;

        //when
        final Optional<List<Branch>> byState = target.findByState(state);

        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByStateWithEmptyParameterMustThrowException() {
        //given
        final String state = "";

        //when
        final Optional<List<Branch>> byState = target.findByState(state);
    }

    @Test
    public void findByStateWithValidStringMustReturnEmptyList() {
        //given
        final String state = "JJ";

        Mockito.when(branchRepository.findAllByState(state))
                .thenReturn(Optional.empty());

        //when
        final Optional<List<Branch>> byState = target.findByState(state);

        //then
        assertThat(byState).isEmpty();
    }

    @Test
    public void findByStateWithExistingStringMustReturnList() {
        //given
        final String state = "SP";
        final List<Branch> branches = from(Branch.class).gimme(1, "branch10");

        Mockito.when(branchRepository.findAllByState(state))
                .thenReturn(Optional.of(branches));

        //when
        final Optional<List<Branch>> byState = target.findByState(state);

        //then
        assertThat(byState).isNotEmpty().hasValue(branches);
    }

    @Test
    public void findByStateWithExistingStringMustReturnListValidatingOptionalWithValueSatisfying() {
        //given
        final String state = "SP";
        final List<Branch> branches = from(Branch.class).gimme(1, "branch10");

        Mockito.when(branchRepository.findAllByState(state))
                .thenReturn(Optional.of(branches));

        //when
        final Optional<List<Branch>> byState = target.findByState(state);

        //then
        assertThat(byState).isNotEmpty().hasValueSatisfying(value -> {
            assertThat(value).hasSize(1);
            assertThat(value).containsOnly(branches.get(0));
        });

        final Condition<List<Branch>> isBranchTen =
                new Condition<>(b -> b.get(0).getCode().equals(10L), "branch 10");
        assertThat(byState).hasValueSatisfying(isBranchTen);
    }

    @Test(expected = CouldNotFindBranchesException.class)
    public void findByStateWithValidParameterMustThrowExceptionWhenRepositoryThrowsException() {
        //given
        final String state = "SP";

        final Throwable exception = new CannotAcquireLockException("Deu ruim");

        Mockito.when(this.branchRepository.findAllByState(state))
                .thenThrow(exception);

        //when
        target.findByState(state);
    }

    @Test(expected = CouldNotFindBranchesException.class)
    public void findByStateWithValidParameterMustThrowExceptionWhenRepositoryThrowsUnexpectedException() {
        //given
        final String state = "SP";

        final Throwable exception = new NullPointerException("Deu ruim");

        Mockito.when(this.branchRepository.findAllByState(state))
                .thenThrow(exception);

        //when
        target.findByState(state);
    }

}