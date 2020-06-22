package io.github.piotrkluz.client

import dto.CreateRepoRequest
import dto.model.Repo
import io.github.piotrkluz.config.Config
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class ReposClient {
    protected String baseUrl = "https://api.github.com"
    private String token = Config.accessToken()

    RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", "token $token")
                .header("content-type", "application/json")
    }

    Repo createRepo(String name) {
        sendCreateRepo(name)
                .then().statusCode(201)
                .extract().as(Repo.class)
    }

    void deleteRepo(Repo repo) {
        sendDeleteRepo(repo.owner.login, repo.name)
                .then().statusCode(204)
    }

    Response sendCreateRepo(String name) {
        def body = new CreateRepoRequest(name: name, private_: false)
        request()
                .body(body)
                .post("/user/repos")
    }

    Response sendDeleteRepo(String owner, String repoName) {
        request().delete("/repos/$owner/$repoName")
    }
}
