package io.github.piotrkluz.config

class Config {
    static String accessToken() {
        String token = System.getenv("token")

        if(token == null || token.length() == 0) {
            throw new RuntimeException("Token system property is required. Actual value is '$token'")
        }

        token
    }
}
