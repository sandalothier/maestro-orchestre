package com.brain;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.brain");

        noClasses()
            .that()
            .resideInAnyPackage("com.brain.service..")
            .or()
            .resideInAnyPackage("com.brain.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.brain.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
