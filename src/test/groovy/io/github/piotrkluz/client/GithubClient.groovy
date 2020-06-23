package io.github.piotrkluz.client

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification

abstract class GithubClient {
    private Config conf = loadConfig()
    private String baseUrl = conf.getString("prod.githubApiUrl")
    private String token = conf.getString("prod.githubToken")

    RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", "token $token")
                .header("content-type", "application/json")
    }

    private static loadConfig() {
        Config conf = ConfigFactory.load()
        if(conf.getString("prod.githubToken").isEmpty()) {
            throw new RuntimeException("Please set Github Access token in resources/application.conf")
        }

        return conf
    }
}
