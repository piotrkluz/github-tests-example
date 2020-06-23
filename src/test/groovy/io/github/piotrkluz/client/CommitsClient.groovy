package io.github.piotrkluz.client

import io.github.piotrkluz.dto.CommitFileRequest
import io.github.piotrkluz.dto.CommitFileResponse
import io.github.piotrkluz.dto.model.GitUser
import io.github.piotrkluz.dto.model.Repo
import io.restassured.response.Response
import org.apache.commons.codec.binary.Base64

class CommitsClient extends GithubClient {

    CommitFileResponse commitFile(Repo repo, String branch, String commitMessage, String filePath, String content) {
        sendCommitFile(repo, branch, commitMessage, filePath, content)
                .then().statusCode(201)
                .extract().as(CommitFileResponse.class)
    }

    Response sendCommitFile(Repo repo, String branch, String commitMessage, String filePath, String content) {
        def body = new CommitFileRequest(
                committer: new GitUser(name: "Unknown Committer", email: "unknown@email.com"),
                message: commitMessage,
                content: encodeBase64(content),
                branch: branch
        )

        //remove beginning slash if needed
        filePath = filePath[0] == "/" ? filePath.substring(1) : filePath
        request().body(body)
                .put("/repos/${repo.owner.login}/${repo.name}/contents/$filePath")
    }

    private static String encodeBase64(String input) {
        new String(
                Base64.encodeBase64(input.getBytes())
        )
    }
}
