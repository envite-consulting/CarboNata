package de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test

class ArchitectureRuleTests {

    private val domain: String = "..domain.."
    private val useCase: String = "..usecase.."
    private val adapter: String = "..adapter.."
    private val basePackage: String = "de.envite.greenbpm.camunda_process_carbon_pricing.carbonataplate"

    @Test
    fun domain_use_case_should_not_import_adapters() {
        val classes = ClassFileImporter().importPackages(basePackage)

        val rule: ArchRule = ArchRuleDefinition.noClasses()
            .that().resideInAPackage(domain)
            .should().accessClassesThat().resideInAPackage(adapter)

        rule.check(classes)
    }

    @Test
    fun use_case_should_not_import_adapters() {
        val classes = ClassFileImporter().importPackages(basePackage)

        val rule: ArchRule = ArchRuleDefinition.noClasses()
            .that().resideInAPackage(useCase)
            .should().accessClassesThat().resideInAPackage(adapter)

        rule.check(classes)
    }
}