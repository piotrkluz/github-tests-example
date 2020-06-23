package io.github.piotrkluz.client

import io.github.piotrkluz.config.Config
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification

abstract class GithubClient {
    protected String baseUrl = Config.githubApiUrl()
    private String token = Config.accessToken()

    RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", "token $token")
                .header("content-type", "application/json")
    }
}
