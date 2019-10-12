package org.acme.rest

import java.util.*


data class Submission(
        val id: UUID,
        val conference: String,
        val year: Int,
        var status: Status
)