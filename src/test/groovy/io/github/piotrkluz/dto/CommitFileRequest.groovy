package io.github.piotrkluz.dto

import groovy.transform.Canonical
import io.github.piotrkluz.dto.model.GitUser

@Canonical
class CommitFileRequest {
    String message
    GitUser committer
    String content
    String branch
}
