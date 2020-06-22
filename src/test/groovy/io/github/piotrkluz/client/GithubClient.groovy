package io.github.piotrkluz.client

import io.restassured.RestAssured

class GithubClient {
    protected String baseUrl = "https://github.com"

    def request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("content-type", "application/json")
    }
}
