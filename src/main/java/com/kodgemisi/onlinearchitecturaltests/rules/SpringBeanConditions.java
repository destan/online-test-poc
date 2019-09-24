package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanDescriptions.HAVE_AOP_ANNOTATIONS;

/**
 * Created on August, 2019
 *
 * @author destan
 */
public interface SpringBeanConditions {

	@ArchTest
	ArchCondition<JavaMethod> CALL_OWN_AOP_ANNOTATED_METHODS =
			new ArchCondition<JavaMethod>("call own AOP annotated methods") {
				@Override
				public void check(JavaMethod item, ConditionEvents events) {

					for (JavaMethodCall call : item.getMethodCallsFromSelf()) {
						for (JavaMethod javaMethod : call.getTarget().resolve()) {
							if (call.getTargetOwner().equals(item.getOwner()) && HAVE_AOP_ANNOTATIONS.apply(javaMethod)) {
								final String message = String.format("Method %s is calling %s which is in the same class and AOP annotated", item.getFullName(), javaMethod.getFullName());
								events.add(SimpleConditionEvent.satisfied(item, message));
							}
						}
					}
				}
			};

}
