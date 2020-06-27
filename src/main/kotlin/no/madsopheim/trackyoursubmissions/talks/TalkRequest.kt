package no.madsopheim.trackyoursubmissions.talks

data class TalkRequest(
    var title: String,
    var cospeaker: String?
)
{
    constructor(): this(title = "")
    constructor(title: String): this(title = title, cospeaker = "")
}
