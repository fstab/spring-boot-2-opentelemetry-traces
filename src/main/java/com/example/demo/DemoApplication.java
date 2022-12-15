package com.example.demo;

import io.prometheus.client.exemplars.tracer.common.SpanContextSupplier;
import io.prometheus.client.exemplars.tracer.otel_agent.OpenTelemetryAgentSpanContextSupplier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * If the OpenTelemetry instrumentation Java agent is attached we use the OpenTelemetryAgentSpanContextSupplier.
	 */
	@Bean
	@ConditionalOnClass(name = "io.opentelemetry.javaagent.shaded.io.opentelemetry.api.trace.Span")
	SpanContextSupplier spanContextSupplier() {
		return new OpenTelemetryAgentSpanContextSupplier();
	}
}
