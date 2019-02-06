package br.com.cvc.api.unittestsdemo.controller;

import br.com.cvc.api.unittestsdemo.model.Branch;
import br.com.cvc.api.unittestsdemo.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static br.com.cvc.api.unittestsdemo.templates.Templates.TEMPLATES_PACKAGE;
import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BranchController.class)
@TestPropertySource("classpath:application.properties")
public class BranchControllerTest {

    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BranchService branchService;

    @BeforeClass
    public static void setUpClass() {
        loadTemplates(TEMPLATES_PACKAGE);
    }

    @Before
    public void setUp() {
        final BranchController target = new BranchController(branchService);
        this.mockMVC = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    @Test
    public void findBranchesMustReturnExceptionWhenPostIsSent() throws Exception {
        this.mockMVC.perform(post("/branches/SP"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findBranchesMustReturnNotFoundWithInvalidState() throws Exception {
        //given
        final String state = "JJ";
        when(this.branchService.findByState(state))
                .thenReturn(Optional.empty());

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findBranchesMustReturnOKWithValidState() throws Exception {
        //given
        final String state = "SP";

        final List<Branch> branches = from(Branch.class).gimme(1, "branch10");
        when(this.branchService.findByState(state))
                .thenReturn(Optional.of(branches));

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(10)));
    }

    @Test
    public void findBranchesMustReturnOKWithListWithValidState() throws Exception {
        //given
        final String state = "SP";

        final List<Branch> branches = from(Branch.class).gimme(2, "branch10", "branch20");

        when(this.branchService.findByState(state))
                .thenReturn(Optional.of(branches));

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(10)))
                .andExpect(jsonPath("$[1].id",is(20)));
    }

    @Test
    public void findBranchesMustReturnInternalServerErrorWhenServiceThrowsException() throws Exception {
        //given
        final String state = "JJ";
        final Throwable exception = new NullPointerException("Deu ruim na Controller");
        when(this.branchService.findByState(state))
                .thenThrow(exception);

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findBranchesMustReturnOKWithListWithValidStateValidatingBodyJson() throws Exception {
        //given
        final String state = "SP";

        final List<Branch> branches = from(Branch.class).gimme(2, "branch10", "branch20");

        when(this.branchService.findByState(state))
                .thenReturn(Optional.of(branches));

        //when
        final byte[] responseBody = this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andReturn().getResponse().getContentAsByteArray();

        final List<Branch> responseBranches = this.objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Branch.class));
        assertThat(responseBranches).hasSize(2).containsOnly(branches.get(0), branches.get(1));
    }

    @Test
    public void findBranchesMustReturnOKWithListWithValidStateValidatingConvertingAssertionToJson() throws Exception {
        //given
        final String state = "SP";

        final List<Branch> branches = from(Branch.class).gimme(2, "branch10", "branch20");

        when(this.branchService.findByState(state))
                .thenReturn(Optional.of(branches));

        final String branchesJson = this.objectMapper.writeValueAsString(branches);

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andExpect(content().json(branchesJson));

    }

    @Test
    public void findBranchesMustReturnOKWithListWithValidStateValidatingConvertingAssertionToJsonUsingFixture() throws Exception {
        //given
        final String state = "SP";

        final List<Branch> branches = from(Branch.class).gimme(2, "branch10", "branch20");

        when(this.branchService.findByState(state))
                .thenReturn(Optional.of(branches));

        final String branchesJson = this.objectMapper.writeValueAsString(branches);

        //when
        this.mockMVC.perform(get("/branches/" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andExpect(content().json(branchesJson));

    }

}