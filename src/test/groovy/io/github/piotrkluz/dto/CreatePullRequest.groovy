package io.github.piotrkluz.dto

import groovy.transform.Canonical

@Canonical
class CreatePullRequest {
        String title

        String head
        String base
        String draft
        String body
//        String maintainer_can_modify
}
