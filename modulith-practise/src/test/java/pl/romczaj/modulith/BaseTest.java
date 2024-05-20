package pl.romczaj.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class BaseTest {

    ApplicationModules modules = ApplicationModules.of(Main.class);

    @Test
    void shouldVerifyModules() {
        modules.verify();
    }


    @Test
    void writeDocumentation() {
        new Documenter(modules)
                .writeModuleCanvases()
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeDocumentation();
    }
}
