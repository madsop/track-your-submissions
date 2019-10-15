package org.acme.rest.talks

data class TalkRequest(
    var title: String,
    var cospeaker: String?
)
{
    constructor(): this( "")
    constructor(title: String): this(title, "")
}
