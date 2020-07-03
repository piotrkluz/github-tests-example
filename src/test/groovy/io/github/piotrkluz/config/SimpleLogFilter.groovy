package io.github.piotrkluz.config

import io.restassured.filter.Filter
import io.restassured.filter.FilterContext
import io.restassured.filter.log.UrlDecoder
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification

import java.nio.charset.Charset

class SimpleLogFilter implements Filter {
    private final static LIMIT_LENGTH = 2000

    @Override
    Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        // Log Request
        String uri = prepareUri(requestSpec)
        String requestBody = prepareBody(requestSpec.getBody())
        String method = requestSpec.getMethod()
        System.out.println("\n$method $uri $requestBody")

        // Log response
        Response response = ctx.next(requestSpec, responseSpec)
        String responseBody = prepareBody(response.getBody().asString())
        System.out.println("RESPONSE ${response.statusCode} [${response.getTime()}ms] $responseBody")

        return response
    }

    private String prepareUri(FilterableRequestSpecification requestSpec) {
        return UrlDecoder.urlDecode(
                requestSpec.getURI(),
                Charset.forName(requestSpec.getConfig().getEncoderConfig().defaultQueryParameterCharset()),
                true
        )
    }

    private String prepareBody(String body) {
        body == null ? "" : body.length() > LIMIT_LENGTH
                ? body.substring(0, LIMIT_LENGTH)
                : body
    }
}