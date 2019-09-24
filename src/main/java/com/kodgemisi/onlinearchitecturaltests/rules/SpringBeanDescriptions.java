package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Validator;

public interface SpringBeanDescriptions {

	DescribedPredicate<JavaClass> ARE_SPRING_BEANS =
			new DescribedPredicate<>("are spring beans"){
				@Override
				public boolean apply(JavaClass input) {
					return input.isAnnotatedWith(Component.class) || input.isMetaAnnotatedWith(Component.class);
				}
			};

	DescribedPredicate<JavaClass> ARE_SPRING_CONTROLLERS =
			new DescribedPredicate<>("are spring controllers"){
				@Override
				public boolean apply(JavaClass input) {
					return input.isAnnotatedWith(Controller.class) || input.isMetaAnnotatedWith(Controller.class);
				}
			};

	DescribedPredicate<JavaClass> ARE_SPRING_REPOSITORIES =
			new DescribedPredicate<>("are spring data repositories"){
				@Override
				public boolean apply(JavaClass input) {
					return input.isAssignableTo(Repository.class);
				}
			};

	DescribedPredicate<JavaClass> ARE_SPRING_CONFIGURATIONS =
			new DescribedPredicate<>("are spring configurations"){
				@Override
				public boolean apply(JavaClass input) {
					return input.isAssignableTo(Repository.class);
				}
			};

	DescribedPredicate<JavaClass> ARE_SPRING_VALIDATORS =
			new DescribedPredicate<>("are spring validators"){
				@Override
				public boolean apply(JavaClass input) {
					return input.isAssignableTo(Validator.class);
				}
			};

	DescribedPredicate<JavaMethod> HAVE_AOP_ANNOTATIONS = new DescribedPredicate<JavaMethod>("have AOP annotations"){
		@Override
		public boolean apply(JavaMethod input) {
			return input.isAnnotatedWith("javax.transaction.Transactional") || input.isAnnotatedWith("javax.transaction.Transactional")
					|| input.isAnnotatedWith("javax.persistence.Cacheable") || input.isAnnotatedWith("javax.persistence.Cacheable");
		}
	};
}