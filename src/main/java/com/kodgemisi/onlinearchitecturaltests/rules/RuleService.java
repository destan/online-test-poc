package com.kodgemisi.onlinearchitecturaltests.rules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kodgemisi.onlinearchitecturaltests.rules.JavaBestPracticeRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.kodgemisi.onlinearchitecturaltests.rules.JavaBestPracticeRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Created on September, 2019
 *
 * @author destan
 */
@Slf4j
@Service
public class RuleService {

	private static final ArchRule DEMO_RULE = noClasses().should().haveSimpleName("ThymeleafEmailTemplateService");

	private static final List<ArchRule> RULES = List.of(DEMO_RULE, NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS, NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS);

	public Optional<List<String>> test(Path classesPath) {

		log.info("Testing classes under {}", classesPath.toAbsolutePath());

		final JavaClasses classes = new ClassFileImporter().importPath(classesPath.toAbsolutePath());

		final List<String> result = new ArrayList<>();

		for (ArchRule rule : RULES) {
			final Optional<String> checkResult = checkInternal(rule, classes);
			checkResult.ifPresent(result::add);
		}

		return result.isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private Optional<String> checkInternal(ArchRule rule, JavaClasses classes) {
		try {
			rule.check(classes);
			return Optional.empty();
		}
		catch (AssertionError error) {
			log.error(error.getMessage());
			return Optional.of(error.getMessage());
		}
	}

}
