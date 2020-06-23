package io.github.piotrkluz.dto

import groovy.transform.Canonical

@Canonical
class AddBranchRequest {
    String ref // example "refs/heads/featureA"
    String sha
}
