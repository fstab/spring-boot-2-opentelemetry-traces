# Spring Boot 2 Metrics OpenTelemetry Exemplars Demo

Simple Demo project to show how to get Prometheus Exemplars in a Spring Boot 2 application with traces from the OpenTelemetry instrumentation.

## Build

```
git clone https://github.com/fstab/spring-boot-2-opentelemetry-traces.git
cd spring-boot-2-opentelemetry-traces/
./mvnw package
```

This should create the file `./target/demo-0.0.1-SNAPSHOT.jar`

## Run

```
OTEL_TRACES_EXPORTER=none java -javaagent:opentelemetry-javaagent.jar -jar target/demo-0.0.1-SNAPSHOT.jar
```

The environment variable `OTEL_TRACES_EXPORTER=none` means that the OpenTelemetry Java agent should not send
traces to a monitoring backend. That way we can quickly test if Exemplars work without configuring a monitoring backend.

## Test Call

```
curl http://localhost:8080
```

This should return "Hello, world!".

## View Exemplars

```
curl -H 'Accept: application/openmetrics-text; version=1.0.0; charset=utf-8' http://localhost:8080/actuator/prometheus
```


The metrics should include a histogram `http_server_requests_second` with histogram buckets and Exemplars like this

```
...
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",le="0.001747626"} 1.0 # {span_id="1088a82a040cb136",trace_id="3316c5af7c639cbc84910438d0614794"} 0.001646636 1671141016.218
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",le="0.002446676"} 2.0 # {span_id="0e5c3c4aaf76526e",trace_id="ab9c0b44c1844ec0a6bf884f6317165c"} 0.002272089 1671141016.515
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",le="0.005592405"} 5.0 # {span_id="14cd6cf4a9c0a70c",trace_id="ed470c179a2a6d8d3c51f7e1c95401d3"} 0.004491088 1671141016.760
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",le="0.006990506"} 7.0 # {span_id="22dda8461edf00ce",trace_id="d24ed258a9ea4cf4f38040be9d7d9029"} 0.006004178 1671141017.183
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",le="0.061516456"} 8.0 # {span_id="53f4bbd9afb2a34e",trace_id="0053a978472ee9183bd056d8972c2ebb"} 0.058643061 1671141015.645
...
```
