package io.github.piotrkluz

import io.github.piotrkluz.client.ReposClient
import io.github.piotrkluz.config.SimpleLogFilter
import io.github.piotrkluz.dto.model.Repo
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class GithubSpec extends Specification {
    protected static ReposClient githubClient

    def setupSpec() {
        githubClient = new ReposClient()

        RestAssured.replaceFiltersWith(
                new SimpleLogFilter(),
//                new RequestLoggingFilter(), new ResponseLoggingFilter()
        )
    }

    def "Test authorization"() {
        expect:
        githubClient.request().get("/user/repos")
        .then().statusCode(200)
    }

    def "Create repository"() {
        when:
            String repositoryName = "apitest-" + randomAlphabetic(5)
            Response res = githubClient.sendCreateRepo(repositoryName)

        then:
            res.statusCode == 201
            Repo createdRepo = res.as(Repo.class)
            createdRepo.name == repositoryName

            // clean after test
            githubClient.deleteRepo(createdRepo)
    }

    def "Delete repository"() {
        given:
            String repositoryName = "apitest-" + randomAlphabetic(5)
            Repo repo = githubClient.createRepo(repositoryName)

        when:
            Response res = githubClient.sendDeleteRepo(repo.owner.login, repo.name)

        then:
            res.statusCode == 204
            res.body.asString() == ""
    }
}