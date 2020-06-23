package io.github.piotrkluz.dto.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(ignoreUnknown=true)
class Repo {
    long id
    String node_id
    String name
    String full_name
    @JsonProperty("private")
    boolean private_
    GithubUser owner
    String description
    boolean fork
    String url
    String html_url
    Date created_at
}
