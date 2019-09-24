package com.kodgemisi.onlinearchitecturaltests;

import com.kodgemisi.onlinearchitecturaltests.rules.RuleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

/**
 * Created on September, 2019
 *
 * @author destan
 */
@Slf4j
@Controller
@RequiredArgsConstructor
class DemoController {

	private final static SimpleFileVisitor<Path> MARK_AS_DELETE_ON_EXIT = new MarkAsDeleteOnExit();

	private final TaskExecutor executorService;

	private final GitHub github;

	private final RuleService ruleService;

	@PostMapping("/check")
	ResponseEntity<Object> demo(@Validated RepoInput repoInput) {

		try {
			final GHRepository repository = github.getUser(repoInput.getUser()).getRepository(repoInput.getRepo());

			final String gitCloneUrl = repository.getHttpTransportUrl();

			log.info(repository.getHttpTransportUrl());

			final GHContent content = repository.getFileContent("pom.xml");
			final InputStream inputStream = content.read();

			try (inputStream) {

				// https://riptutorial.com/maven/example/30104/reading-a-pom-xml-at-runtime-using-maven-model-plugin
				final MavenXpp3Reader reader = new MavenXpp3Reader();
				final Model model = reader.read(inputStream);

				log.info(model.getId());
				log.info(model.getGroupId());
				log.info(model.getArtifactId());
				log.info(model.getVersion());
				log.info(model.getParent() != null ? model.getParent().getArtifactId() : "no parent");

				log.info("Dependencies");
				for (Dependency dependency : model.getDependencies()) {
					log.info(dependency.getArtifactId());
				}

				final File tempFolder = Files.createTempDirectory("kodgemisi_").toFile();
				tempFolder.deleteOnExit();

				log.info("Folder " + tempFolder.getAbsolutePath());

				{
					final ProcessBuilder builder = new ProcessBuilder();
					builder.command("git", "clone", gitCloneUrl, "app");
					builder.directory(tempFolder);

					final Process process = builder.start();
					final StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), s -> log.warn(s));
					executorService.execute(streamGobbler);
					int exitCode = process.waitFor();

					assert exitCode == 0 : "git error";
				}

				{
					final ProcessBuilder builder = new ProcessBuilder();
					builder.command("mvn", "compile", "-e");
					builder.directory(tempFolder.toPath().resolve("app").toFile());

					final Process process = builder.start();
					final StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), s -> log.warn(s));
					executorService.execute(streamGobbler);
					int exitCode = process.waitFor();

					Files.walkFileTree(tempFolder.toPath(), MARK_AS_DELETE_ON_EXIT);

					assert exitCode == 0 : "maven error";
				}

				Object result = ruleService.test(tempFolder.toPath().resolve("app").resolve("target").resolve("classes"));
				return ResponseEntity.ok(result);
			}

		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return ResponseEntity.notFound().build();
	}

	@AllArgsConstructor
	@Getter
	private static class RepoInput {

		@NotBlank
		private final String user;

		@NotBlank
		private final String repo;
	}

}
