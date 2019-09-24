package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

/**
 * Created on August, 2019
 *
 * @author destan
 */
@AnalyzeClasses(packages = {"com.kodgemisi.guzzlo"}, importOptions = ImportOption.DoNotIncludeTests.class)
class JavaBestPracticeRules {

	@ArchTest
	static final ArchRule NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

	@ArchTest
	static final ArchRule NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

	@ArchTest
	static final ArchRule NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

}
