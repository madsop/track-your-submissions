package org.acme.rest.talks

data class TalkRequest(
    var title: String
)
{
    constructor(): this("")
}
