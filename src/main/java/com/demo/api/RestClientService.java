package com.demo.api;

import io.micronaut.http.MutableHttpRequest;
import io.micronaut.reactor.http.client.ReactorHttpClient;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Singleton
public class RestClientService {

    final ReactorHttpClient httpClient;

    public RestClientService(ReactorHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <U, T> Mono<T> externalApiCall(MutableHttpRequest<U> request, String requestId, Class<T> bodyType) {
        request.getHeaders().add("x-request-id", requestId).add("Connection", "close");
        return Mono.from(httpClient.retrieve(request, bodyType))
                .publishOn(Schedulers.boundedElastic())
                .doFinally(end -> {
                    System.out.println("generally we close open telemetry spans here");
                });
    }
}
