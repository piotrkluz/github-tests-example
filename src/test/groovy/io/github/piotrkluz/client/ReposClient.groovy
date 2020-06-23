package io.github.piotrkluz.client

import io.github.piotrkluz.dto.CreateRepoRequest
import io.github.piotrkluz.dto.model.GithubUser
import io.github.piotrkluz.dto.model.Repo
import io.restassured.response.Response

class ReposClient extends GithubClient {

    GithubUser getUserInfo() {
        request().get("/user")
                .then().statusCode(200)
                .extract().as(GithubUser.class)
    }

    Repo tryGetRepo(String user, String repoName) {
        Response res = request().get("/repos/$user/$repoName")
        return res.statusCode == 200
                ? res.as(Repo.class)
                : null
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
