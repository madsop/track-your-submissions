package org.acme.rest


data class SubmissionRequest(
        val conference: String,
        val year: Int,
        val talk: String
)