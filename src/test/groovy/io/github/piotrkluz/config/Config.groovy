package io.github.piotrkluz.config

import com.fasterxml.jackson.databind.ObjectMapper

class Config {
    private static final String FILE = "config.json"
    private static ConfigModel config = readConfig()

    static String accessToken() {
        config.githubToken
    }
    static String githubApiUrl() {
        config.githubApiUrl
    }

    private static ConfigModel readConfig() {
        ObjectMapper mapper = new ObjectMapper();

        ConfigModel model = mapper.readValue(new File(FILE), ConfigModel.class)
        if(model.githubToken == null || model.githubToken.size() == 0) {
            throw new RuntimeException("Please update Github token in $FILE")
        }

        return model
    }
}
