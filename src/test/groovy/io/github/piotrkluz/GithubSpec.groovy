package io.github.piotrkluz

import io.github.piotrkluz.client.GithubClient
import io.github.piotrkluz.config.SimpleLogFilter
import io.restassured.RestAssured
import io.restassured.filter.log.LogDetail
import io.restassured.response.Response
import spock.lang.Specification

class GithubSpec extends Specification {
    protected static GithubClient githubClient

    def setupSpec() {
        githubClient = new GithubClient()


        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
    }

    def "authenticate"() {
        given:
        Response res = githubClient.request().get("/")
        expect:
            res.statusCode == 400
    }
}