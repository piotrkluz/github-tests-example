package io.github.piotrkluz.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import io.github.piotrkluz.dto.model.GitUser

@Canonical
@JsonIgnoreProperties(ignoreUnknown=true)
class CommitFileResponse {
    Content content
    Commit commit

    @Canonical
    @JsonIgnoreProperties(ignoreUnknown=true)
    class Content {
        String name
        String path
        String sha
        int size
        String url
        String html_url
        String type
    }

    @Canonical
    @JsonIgnoreProperties(ignoreUnknown=true)
    class Commit {
        String sha
        String url
        String html_url
        GitUser author
        GitUser committer
        String message
    }
}