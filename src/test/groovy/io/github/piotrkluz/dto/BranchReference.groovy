package io.github.piotrkluz.dto

class BranchReference {
    String ref // example refs/heads/master
    String node_id
    String url
    ReferenceObject object

    class ReferenceObject {
        String sha
        String type
        String url
    }
}