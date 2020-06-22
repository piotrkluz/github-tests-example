package io.github.piotrkluz.client

import io.github.piotrkluz.config.Config
import io.github.piotrkluz.dto.CreateRepoRequest
import io.github.piotrkluz.dto.model.Repo
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

abstract class GithubClient {
    protected String baseUrl = "https://api.github.com"
    private String token = Config.accessToken()

    RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", "token $token")
                .header("content-type", "application/json")
    }
}
