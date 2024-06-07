import kotlinx.datetime.Clock

fun gregorian(): Long {
    val now = Clock.System.now()
    val nano = now.nanosecondsOfSecond
    val second = now.epochSeconds + GREGORIAN_OFFSET
    return (second * 10_000_000L) + (nano / 100L)
}

/**
 * 1582-10-15T00:00:00Z
 */
private const val GREGORIAN_OFFSET = 12219292800L