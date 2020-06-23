package io.github.piotrkluz.config

import groovy.transform.Canonical

@Canonical
class ConfigModel {
    String githubApiUrl
    String githubToken
}
