package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

class SpringSlices {

	static final SliceAssignment CONTROLLER_AND_REPOSITORY_SLICES = new SliceAssignment() {
		// this will specify which classes belong together in the same slice
		@Override
		public SliceIdentifier getIdentifierOf(JavaClass javaClass) {

			if (javaClass.isAnnotatedWith(Controller.class) || javaClass.isAnnotatedWith(RestController.class)) {
				return SliceIdentifier.of("Controller");
			}

			if (javaClass.isAssignableTo(Repository.class)) {
				return SliceIdentifier.of("Repository");
			}

			// if the class does not match anything, we ignore it
			return SliceIdentifier.ignore();
		}

		// this will be part of the rule description if the test fails
		@Override
		public String getDescription() {
			return "controller layer";
		}
	};

}