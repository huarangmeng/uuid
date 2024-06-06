import kotlinx.datetime.Clock
import kotlin.random.Random

/**
 * Time-based UUID
 */
fun uuidV1(): UUID {
    val timestamp = gregorian()

    // 构造最高有效位（MSB）
    val timeLow = (timestamp shl 32)
    val timeMid = (timestamp ushr 16) and (MASK_16 shl 16)
    val timeHiAndVersion = (timestamp ushr 48) and MASK_12

    val msb = timeLow or timeMid or timeHiAndVersion

    // 构造最低有效位（LSB）
    val lsb = Random.nextLong() or MULTICAST

    return version(msb, lsb, 1)
}

/**
 * Returns a DCE Security unique identifier (UUIDv2).
 *
 * @param localDomain     a custom local domain byte
 * @param localIdentifier a local identifier
 * @return a uuid v2
 */
fun uuidV2(localDomain: Byte, localIdentifier: Int): UUID {
    val uuidV1 = uuidV1()
    val msb = (uuidV1.mostSigBits and MASK_32) or ((localIdentifier.toLong() and MASK_32) shl 32)
    val lsb =
        (uuidV1.leastSigBits and 0x3f00_ffff_ffff_ffffL) or ((localDomain.toLong() and MASK_08) shl 48)
    return version(msb, lsb, 2)
}

/**
 * Returns a name-based unique identifier that uses MD5 hashing (UUIDv3).
 *
 * Usage:
 *  val uuid: UUID = uuidV3([NAMESPACE_SDS], "www.example.com")
 *
 * @param namespace a UUID
 * @param name      a string
 * @return a UUID
 */
fun uuidV3(namespace: UUID, name: String): UUID {
    return hash(3, null, namespace, name);
}

/**
 * Returns a random-based unique identifier (UUIDv4).
 * <p>
 * It is an extremely fast and non-blocking alternative to
 * {@link UUID#randomUUID()}.
 * <p>
 * It employs [Random.Default] which works very well, although not
 * cryptographically strong.
 * <p>
 *
 * @return a UUID
 */
fun uuidV4(): UUID {
    val random = Random.Default
    val msb = random.nextLong()
    val lsb = random.nextLong()
    return version(msb, lsb, 4)
}

/**
 * Returns a name-based unique identifier that uses SHA-1 hashing (UUIDv5).
 * <p>
 * Usage:
 *  val uuid: UUID = uuidV3([NAMESPACE_SDS], "www.example.com")
 *
 * @param namespace a UUID
 * @param name      a string
 * @return a UUID
 */
fun uuidV5(namespace: UUID, name: String): UUID {
    return hash(5, null, namespace, name)
}

/**
 * Returns a reordered gregorian time-based unique identifier (UUIDv6).
 * <p>
 * The clock sequence and node bits are reset to a pseudo-random value for each
 * new UUIDv6 generated.
 * <p>
 *
 * @return a GUID
 */
fun uuidV6(): UUID {
    val time = gregorian()
    val msb = ((time and MASK_12.inv()) shl 4) or (time and MASK_12)
    val lsb = Random.Default.nextLong() or MULTICAST
    return version(msb, lsb, 6)
}

/**
 * Returns a Unix epoch time-based unique identifier (UUIDv7).
 *
 * @return a GUID
 */
fun uuidV7(): UUID {
    val time = Clock.System.now().toEpochMilliseconds()
    val random = Random.Default
    val msb = (time shl 16) or (random.nextLong() and MASK_16)
    val lsb = random.nextLong()
    return version(msb, lsb, 7)
}

private fun hash(
    version: Int,
    hashSpace: UUID?,
    namespace: UUID,
    name: String
): UUID {
    val hasher = getHasher(version)
    if (hashSpace != null) {
        val ns = ByteArray(16)
        putLongToByteArray(ns, 0, hashSpace.mostSigBits)
        putLongToByteArray(ns, 8, hashSpace.leastSigBits)
        hasher.update(ns)
    }

    val ns = ByteArray(16)
    putLongToByteArray(ns, 0, namespace.mostSigBits)
    putLongToByteArray(ns, 8, namespace.leastSigBits)
    hasher.update(ns)

    hasher.update(stringToUtf8ByteArray(name))
    val hash = hasher.digest()

    val msb = getLongFromByteArray(hash, 0)
    val lsb = getLongFromByteArray(hash, 8)
    return version(msb, lsb, version)
}

private fun version(hi: Long, lo: Long, version: Int): UUID {
    // set the 4 most significant bits of the 7th byte
    val msb =
        (hi.toULong() and 0xffff_ffff_ffff_0ffFU) or ((version.toULong() and MASK_04.toULong()) shl 12)
    // set the 2 most significant bits of the 9th byte to 1 and 0
    val lsb = (lo.toULong() and 0x3fff_ffff_ffff_ffffU) or (0x8000_0000_0000_0000U)
    return UUID(msb.toLong(), lsb.toLong())
}

private const val MULTICAST = 0x0000_0100_0000_0000L
private const val MASK_04 = 0x0000_0000_0000_000fL

private const val MASK_08 = 0x0000_0000_0000_00ffL
private const val MASK_12 = 0x0000_0000_0000_0fffL
private const val MASK_16 = 0x0000_0000_0000_ffffL
private const val MASK_32 = 0x0000_0000_ffff_ffffL