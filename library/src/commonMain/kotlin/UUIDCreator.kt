import kotlinx.datetime.Clock
import kotlin.random.Random

/**
 * Time-based UUID
 */
fun uuidV1(): UUID {
    val now = Clock.System.now()
    val timestamp = (now.epochSeconds * 1_000_000_000L + now.nanosecondsOfSecond) / 100 + GREGORIAN_OFFSET

    // 构造最高有效位（MSB）
    val timeLow = (timestamp shl 32)
    val timeMid = (timestamp and (MASK_16 shl 32)) ushr 16
    val timeHiAndVersion = ((timestamp and (MASK_12 shl 48)) ushr 48) or (1L shl 12) // 设置版本为1

    val msb = timeLow or timeMid or timeHiAndVersion

    // 构造最低有效位（LSB）
    val clockSeq = Random.nextInt() and MASK_16.toInt()
    val clockSeqHiAndReserved = (clockSeq and 0x3FFF) or 0x8000 // 设置变体
    val node = Random.nextLong() or MULTICAST // 设置多播位

    val lsb = (clockSeqHiAndReserved.toLong() shl 48) or node

    return UUID(msb, lsb)
}

/**
 * 1582-10-15T00:00:00Z
 */
private const val GREGORIAN_OFFSET = 122192928000000000L
private const val MULTICAST = 0x0000010000000000L
private const val MASK_12 = 0x0000000000000fffL
private const val MASK_16 = 0x000000000000ffffL