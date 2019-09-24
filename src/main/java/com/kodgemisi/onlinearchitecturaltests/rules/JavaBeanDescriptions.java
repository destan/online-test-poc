package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanDescriptions.ARE_SPRING_BEANS;
import static com.kodgemisi.onlinearchitecturaltests.rules.SpringBeanDescriptions.ARE_SPRING_VALIDATORS;

public interface JavaBeanDescriptions {

	DescribedPredicate<JavaClass> ARE_EXCEPTION = new DescribedPredicate<>("exceptions") {
		@Override
		public boolean apply(JavaClass input) {
			return input.isAssignableTo(Exception.class);
		}
	};

	DescribedPredicate<JavaClass> ARE_ENTITY = new DescribedPredicate<>("entities") {
		@Override
		public boolean apply(JavaClass input) {
			return input.isAnnotatedWith("javax.persistence.Entity") || input.isMetaAnnotatedWith("javax.persistence.Entity");
		}
	};

	DescribedPredicate<? super JavaClass> ARE_POJOS = new DescribedPredicate<>("POJOs") {
		@Override
		public boolean apply(JavaClass input) {
			return  !ARE_EXCEPTION.apply(input) &&
					!ARE_ENTITY.apply(input) &&
					!ARE_SPRING_BEANS.apply(input) &&
					!ARE_SPRING_VALIDATORS.apply(input);
		}
	};
}