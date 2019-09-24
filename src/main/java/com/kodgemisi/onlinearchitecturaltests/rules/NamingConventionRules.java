package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.kodgemisi.onlinearchitecturaltests.rules.JavaBeanConditions.*;
import static com.kodgemisi.onlinearchitecturaltests.rules.JavaBeanDescriptions.ARE_EXCEPTION;
import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanDescriptions.*;
import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Created on August, 2019
 *
 * @author destan
 */
@AnalyzeClasses(packages = {"com.kodgemisi.guzzlo"}, importOptions = ImportOption.DoNotIncludeTests.class)
public interface NamingConventionRules {

	@ArchTest
	ArchRule EXCEPTION_CLASSES_HAVE_SUFFIX = classes().that(ARE_EXCEPTION)
			.should().haveSimpleNameEndingWith("Exception");

	@ArchTest
	ArchRule DTOS_ARE_POJOS = classes().that().haveSimpleNameEndingWith("Dto").should(BE_POJOS);

	@ArchTest
	ArchRule REPOSITORY_CLASSES_HAVE_SUFFIX = classes().that(ARE_SPRING_REPOSITORIES)
			.should().haveSimpleNameEndingWith("Repository");

	@ArchTest
	ArchRule CONTROLLER_CLASSES_HAVE_SUFFIX = classes().that(ARE_SPRING_CONTROLLERS)
			.should().haveSimpleNameEndingWith("Controller");

	@ArchTest
	ArchRule CONFIGURATION_CLASSES_HAVE_SUFFIX = classes().that(ARE_SPRING_CONFIGURATIONS)
			.should().haveSimpleNameEndingWith("Config")
			.orShould().haveSimpleNameEndingWith("Configuration");

	@ArchTest
	ArchRule FIELD_NAMES_CONSISTS_OF_ASCII_CHARS = fields().should(CONSIST_OF_ASCII_CHARS);

	@ArchTest
	ArchRule METHOD_NAMES_CONSISTS_OF_ASCII_CHARS = methods().should(CONSIST_OF_ASCII_CHARS);

	@ArchTest
	ArchRule CLASS_NAMES_CONSISTS_OF_ASCII_CHARS = classes().should(CONSIST_OF_ASCII_CHARS);

	@ArchTest
	ArchRule CLASS_NAMES_STARTS_WITH_UPPER_CASE = classes().should(START_WITH_UPPER_CASE);

	@ArchTest
	ArchRule METHOD_NAMES_DO_NOT_START_WITH_UPPER_CASE = methods().should(not(START_WITH_UPPER_CASE));

	@ArchTest
	ArchRule NON_CONSTANT_FIELD_NAMES_DO_NOT_START_WITH_UPPER_CASE = fields()
			.that().areNotFinal().and().areNotStatic()
			.should(not(START_WITH_UPPER_CASE));
}
