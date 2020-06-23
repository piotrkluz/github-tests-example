package io.github.piotrkluz

import io.github.piotrkluz.client.BranchesClient
import io.github.piotrkluz.client.CommitsClient
import io.github.piotrkluz.client.PullRequestClient
import io.github.piotrkluz.client.ReposClient
import io.github.piotrkluz.config.SimpleLogFilter
import io.github.piotrkluz.dto.CommitFileResponse
import io.github.piotrkluz.dto.model.GithubUser
import io.github.piotrkluz.dto.model.Repo
import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class GithubSpec extends Specification {
    private final static String TEST_REPO_NAME = "api-test-repo4"
    protected static ReposClient repoClient
    protected static CommitsClient commitClient
    protected static PullRequestClient pullRequestClient
    protected static BranchesClient branchesClient

    def setupSpec() {
        repoClient = new ReposClient()
        commitClient = new CommitsClient()
        pullRequestClient = new PullRequestClient()
        branchesClient = new BranchesClient()

        RestAssured.replaceFiltersWith(
                new SimpleLogFilter(),
//                new RequestLoggingFilter(), new ResponseLoggingFilter()
        )
    }

    def "Test authorization"() {
        expect:
            repoClient.request().get("/user/repos")
                    .then().statusCode(200)
    }

    def "Create repository"() {
        when:
            String repositoryName = "apitest-" + randomAlphabetic(5)
            Response res = repoClient.sendCreateRepo(repositoryName)

        then:
            res.statusCode == 201
            Repo createdRepo = res.as(Repo.class)
            createdRepo.name == repositoryName

            // clean after test
            repoClient.deleteRepo(createdRepo)
    }

    def "Delete repository"() {
        given:
            String repositoryName = "apitest-" + randomAlphabetic(5)
            Repo repo = repoClient.createRepo(repositoryName)

        when:
            Response res = repoClient.sendDeleteRepo(repo.owner.login, repo.name)

        then:
            res.statusCode == 204
            res.body.asString() == ""
    }

    def "Commit a file"() {
        given:
            Repo repo = getTestRepo()

            String fileName = "test" + randomAlphabetic(4) + ".txt"
            String commitMessage = "Add example file"
        when:
            Response res = commitClient.sendCommitFile(
                    repo,
                    "develop",
                    commitMessage,
                    fileName,
                    "Test file content" + randomAlphabetic(4)
            )

        then:
            res.statusCode == 201
            CommitFileResponse response = res.as(CommitFileResponse.class)
            response.content.path == fileName
            response.commit.message == commitMessage
    }

    def "Create pull request"() {
        given:
            Repo repo = getTestRepo()
            String newBranch = "feature" + randomAlphabetic(3)
            String fileName = "test" + randomAlphabetic(5)
            branchesClient.addBranch(repo, "master", newBranch)
            commitClient.commitFile(repo, newBranch, "Some commit", fileName, "File content")

        when:
            Response res = pullRequestClient.sendCreatePullRequest(repo, newBranch, "master", "Pull request 1")

        then:
            res.statusCode == 201


    }

    private Repo getTestRepo() {
        GithubUser user = repoClient.getUserInfo()
        Repo repo = repoClient.tryGetRepo(user.login, TEST_REPO_NAME)

        if (repo == null) {
            repo = repoClient.createRepo(TEST_REPO_NAME)
            commitClient.commitFile(repo, "master", "Initial commit", "readme.md", "Hello world")
        }

        return repo

    }
}