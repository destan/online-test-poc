package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.conditions.ArchConditions;

import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanConditions.CALL_OWN_AOP_ANNOTATED_METHODS;
import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanDescriptions.*;
import static com.kodgemisi.onlinearchitecturaltests.rules.SpringSlices.CONTROLLER_AND_REPOSITORY_SLICES;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Created on Mayıs, 2019
 *
 * @author destans
 */
@AnalyzeClasses(packages = {"com.kodgemisi.guzzlo"}, importOptions = ImportOption.DoNotIncludeTests.class)
public interface SpringBeansRules {

	@ArchTest
	ArchRule REPOSITORIES_AND_CONTROLLERS_DO_NOT_DEPEND_ON_EACH_OTHER = slices()
			.assignedFrom(CONTROLLER_AND_REPOSITORY_SLICES)
			.should().notDependOnEachOther()
			.as("Controller and Repository classes should not depend on each other");

	@ArchTest
	ArchRule NO_CLASSES_DEPENDS_ON_CONTROLLERS = noClasses().should().dependOnClassesThat(ARE_SPRING_CONTROLLERS);

	@ArchTest
	ArchRule NOT_CALL_OWN_AOP_METHODS = methods()
			.that().areDeclaredInClassesThat(are(ARE_SPRING_BEANS))
			.and(DescribedPredicate.not(HAVE_AOP_ANNOTATIONS))
			.should(ArchConditions.not(CALL_OWN_AOP_ANNOTATED_METHODS));

}