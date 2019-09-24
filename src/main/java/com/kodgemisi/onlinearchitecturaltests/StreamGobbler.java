package com.kodgemisi.onlinearchitecturaltests;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@Slf4j
class StreamGobbler implements Runnable {

	private InputStream inputStream;

	private Consumer<String> consumer;

	StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
		this.inputStream = inputStream;
		this.consumer = consumer;
	}

	@Override
	public void run() {

		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			reader.lines().forEach(consumer);
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}
}