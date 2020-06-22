package dto.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(ignoreUnknown=true)
class RepoOwner {
    long id
    String login
    String type
    String site_admin
}
