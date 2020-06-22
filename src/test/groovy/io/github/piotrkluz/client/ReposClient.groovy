package io.github.piotrkluz.client

import io.github.piotrkluz.dto.CreateRepoRequest
import io.github.piotrkluz.dto.model.Repo
import io.restassured.response.Response

class ReposClient extends GithubClient {

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
