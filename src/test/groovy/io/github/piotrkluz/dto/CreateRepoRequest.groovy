package io.github.piotrkluz.dto

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Builder
@Canonical
class CreateRepoRequest {
    String name
    @JsonProperty("private")
    boolean private_
}
