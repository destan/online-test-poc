package com.kodgemisi.onlinearchitecturaltests;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

@Slf4j
class MarkAsDeleteOnExit extends SimpleFileVisitor<Path> {

	/**
	 * We this method instead of {@code postVisitDirectory} because
	 * <em>Files (or directories) are deleted in the reverse order that they are registered.</em>
	 *
	 * @see java.io.File#deleteOnExit()
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		dir.toFile().deleteOnExit();
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		file.toFile().deleteOnExit();
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		log.error("Error visiting path " + file.toAbsolutePath(), exc);
		return CONTINUE;
	}

}