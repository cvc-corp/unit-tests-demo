package br.com.cvc.api.unittestsdemo.templates;

import br.com.cvc.api.unittestsdemo.model.Branch;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.six2six.fixturefactory.Fixture.of;

public class BranchTemplate implements TemplateLoader {

    @Override
    public void load() {
        of(Branch.class).addTemplate("branch10", new Rule() {{
            add("id", 10L);
            add("code", 10L);
            add("enabled", 'S');
            add("state", "SP");
        }});

        of(Branch.class).addTemplate("branch20", new Rule() {{
            add("id", 20L);
            add("code", 20L);
            add("enabled", 'S');
            add("state", "SP");
        }});
    }

}