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

    return version(msb, lsb, 1)
}

fun uuidV2(localDomain: Byte, localIdentifier: Int): UUID {
    val uuidV1 = uuidV1()
    val msb = (uuidV1.mostSigBits and MASK_32) or ((localIdentifier.toLong() and MASK_32) shl 32)
    val lsb = (uuidV1.leastSigBits and 0x3f00_ffff_ffff_ffffL) or ((localDomain.toLong() and MASK_08) shl 48)
    return version(msb, lsb, 2)
}

private fun version(hi: Long, lo: Long, version: Int): UUID {
    // set the 4 most significant bits of the 7th byte
    val msb = (hi and ((1L shl 48) - 1)) or ((version.toLong() and MASK_04) shl 12)
    // set the 2 most significant bits of the 9th byte to 1 and 0
    val lsb = (lo and 0x3fff_ffff_ffff_ffffL) or (1L shl 63)
    return UUID(msb, lsb)
}

/**
 * 1582-10-15T00:00:00Z
 */
private const val GREGORIAN_OFFSET = 122192928000000000L
private const val MULTICAST = 0x0000_0100_0000_0000L
private const val MASK_04 = 0x0000_0000_0000_000fL

private const val MASK_08 = 0x0000_0000_0000_00ffL
private const val MASK_12 = 0x0000_0000_0000_0fffL
private const val MASK_16 = 0x0000_0000_0000_ffffL
private const val MASK_32 = 0x0000_0000_ffff_ffffL