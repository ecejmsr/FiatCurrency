import com.bps.fiatscape.common.dataclasses.Coin
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "currencies") val currencies: List<Coin>,
    @Json(name = "exchanges") val exchanges: List<Exchange>,
    @Json(name = "icos") val icos: List<Ico>,
    @Json(name = "people") val people: List<Person>,
    @Json(name = "tags") val tags: List<Tag>,
)

@JsonClass(generateAdapter = true)
data class Currency(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int
)

@JsonClass(generateAdapter = true)
data class Exchange(
    val id: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Ico(
    val id: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Person(
    val id: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Tag(
    val id: String,
    val name: String
)
