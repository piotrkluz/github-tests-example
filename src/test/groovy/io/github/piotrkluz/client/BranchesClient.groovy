package io.github.piotrkluz.client

import io.github.piotrkluz.dto.AddBranchRequest
import io.github.piotrkluz.dto.BranchReference
import io.github.piotrkluz.dto.model.Repo

class BranchesClient extends GithubClient {

    BranchReference getBranchReference(Repo repo, String branchName) {
        request().get("/repos/${repo.owner.login}/${repo.name}/git/ref/heads/$branchName")
                .then().statusCode(200)
                .extract().as(BranchReference.class)
    }

    BranchReference addBranch(Repo repo, String branchFrom, String newBranchName) {
        BranchReference fromRef = getBranchReference(repo, branchFrom)
        def body = new AddBranchRequest(
                ref: "refs/heads/$newBranchName",
                sha: fromRef.object.sha
        )

        request().body(body)
                .post("/repos/${repo.owner.login}/${repo.name}/git/refs")
                .then().statusCode(201)
                .extract().as(BranchReference.class)
    }

}
