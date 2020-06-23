package io.github.piotrkluz.client

import io.github.piotrkluz.dto.CreatePullRequest
import io.github.piotrkluz.dto.model.Repo
import io.restassured.response.Response

class PullRequestClient extends GithubClient {

    Response sendCreatePullRequest(Repo repo, String branchFrom, String branchTo, String title) {
        def body = new CreatePullRequest(
                title: title,
                head: branchFrom,
                base: branchTo
        )
        request().body(body)
                .post("/repos/${repo.owner.login}/${repo.name}/pulls")
    }
}
