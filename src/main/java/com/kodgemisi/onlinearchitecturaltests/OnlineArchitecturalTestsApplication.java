package com.kodgemisi.onlinearchitecturaltests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GitHub;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class OnlineArchitecturalTestsApplication implements WebMvcConfigurer {

	private final ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(OnlineArchitecturalTestsApplication.class, args);
	}

	@Bean
	GitHub github() throws IOException {
		return GitHub.connect();
	}

	@Bean
	TaskExecutor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@PostConstruct
	void init() {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.getSerializerProvider().setNullKeySerializer(new MyDtoNullKeySerializer());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	static class MyDtoNullKeySerializer extends StdSerializer<Object> {

		MyDtoNullKeySerializer() {
			this(null);
		}

		MyDtoNullKeySerializer(Class<Object> t) {
			super(t);
		}

		@Override
		public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused) throws IOException {
			jsonGenerator.writeFieldName("");
		}
	}
}
