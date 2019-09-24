package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.properties.HasName;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.kodgemisi.onlinearchitecturaltests.rules.JavaBeanDescriptions.ARE_POJOS;

/**
 * Created on August, 2019
 *
 * @author destan
 */
public interface JavaBeanConditions {

	ArchCondition<JavaClass> BE_POJOS = new ArchCondition<>("be POJOs") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			if( ARE_POJOS.apply(item)) {
				final String message = String.format("Class %s is a POJO.", item.getFullName());
				events.add(SimpleConditionEvent.satisfied(item, message));
			}
		}
	};


	ArchCondition<HasName.AndFullName> CONSIST_OF_ASCII_CHARS = new ArchCondition<>("consist of ASCII characters") {

		/**
		 * inclusive. 0x20 is the char blank space (space)
		 */
		private static final int PRINTABLE_ASCII_LOWER_BOUND = 0x20;

		/**
		 * inclusive. 0x7E is the char tilda {@code ~}
		 */
		private static final int PRINTABLE_ASCII_UPPER_BOUND = 0x7E;

		@Override
		public void check(HasName.AndFullName item, ConditionEvents events) {

			// thanks: https://stackoverflow.com/questions/3585053/is-it-possible-to-check-if-a-string-only-contains-ascii#comment47157534_3585791
			if (item.getName().chars().allMatch(c -> c >= PRINTABLE_ASCII_LOWER_BOUND && c <= PRINTABLE_ASCII_UPPER_BOUND)) {
				final String message = String.format("%s consists only of ASCII character(s).", item.getFullName());
				events.add(SimpleConditionEvent.satisfied(item, message));
			}
			else {
				final String message = String.format("%s consists non-ASCII character(s).", item.getFullName());
				events.add(SimpleConditionEvent.violated(item, message));
			}
		}
	};

	ArchCondition<HasName.AndFullName> START_WITH_UPPER_CASE = new ArchCondition<>("start with upper case") {

		@Override
		public void check(HasName.AndFullName item, ConditionEvents events) {

			// getName() returns FQN of classes but simple name of methods and fields.
			final String simpleName = item instanceof JavaClass ? ((JavaClass) item).getSimpleName() : item.getName();

			// Some generated classes' simple names are resolved as empty
			// For example: getName() gives com.kodgemisi.guzzlo.archtests.SpringBeanDescriptions$6 but getSimpleName() gives empty string.
			if (simpleName.isEmpty()) {
				return;
			}

			if (Character.isUpperCase(simpleName.charAt(0))) {
				final String message = String.format("%s starts with an upper case char. [%s]", simpleName, item.getFullName());
				events.add(SimpleConditionEvent.satisfied(item, message));
			}
			else {
				final String message = String.format("%s does NOT start with an upper case char. [%s]", simpleName, item.getFullName());
				events.add(SimpleConditionEvent.violated(item, message));
			}
		}
	};

}
