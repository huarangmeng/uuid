import kotlinx.serialization.Serializable

/**
 * Represents a universally unique identifier (UUID), a 128-bit value.
 *
 * @property mostSigBits The most significant 64 bits of the UUID.
 * @property leastSigBits The least significant 64 bits of the UUID.
 */
@Serializable
data class UUID(val mostSigBits: Long, val leastSigBits: Long) {

    /**
     * Constructs a new UUID using the specified byte array.
     *
     * @param data A 16-byte array from which to construct the UUID.
     * @throws IllegalArgumentException If the data array is not of the correct length.
     */
    constructor(data: ByteArray) : this(
        data.copyOfRange(0, 8).toLong(),
        data.copyOfRange(8, 16).toLong()
    )

    /**
     * Returns the version number of the UUID. The version number describes
     * how the UUID was generated, according to the following meanings:
     * 1: Time-based UUID
     * 2: DCE security UUID
     * 3: Name-based UUID using MD5 hashing
     * 4: Randomly generated UUID
     * 5: Name-based UUID using SHA-1 hashing
     *
     * @return The version number of this UUID.
     */
    fun version(): Int {
        // The version is represented by bits 12-15 of the most significant bits.
        return ((mostSigBits shr 12) and 0x0f).toInt()
    }

    /**
     * Returns a string representation of the UUID.
     *
     * @return A string representation of the UUID in the format of 8-4-4-4-12 hexadecimal digits.
     */
    override fun toString(): String {
        return buildString {
            append(digits(mostSigBits shr 32, 8)).append('-')
            append(digits(mostSigBits shr 16, 4)).append('-')
            append(digits(mostSigBits, 4)).append('-')
            append(digits(leastSigBits ushr 48, 4)).append('-')
            append(digits(leastSigBits, 12))
        }
    }
}

private fun ByteArray.toLong(): Long {
    var result = 0L
    for (byte in this) {
        result = (result shl 8) or (byte.toLong() and 0xff)
    }
    return result
}

/**
 * Helper function to convert a portion of the UUID to a string with the specified number of hexadecimal digits.
 *
 * @param value The value to convert to a string.
 * @param digits The number of hexadecimal digits to include in the string.
 * @return A string representation of the value with the specified number of digits.
 */
private inline fun digits(value: Long, digits: Int): String {
    val hi = 1L shl (digits * 4)
    return (hi or (value and (hi - 1))).toString(16).substring(1)
}

/**
 * Name space to be used when the name string is a fully-qualified domain name.
 */
val NAMESPACE_SDS = UUID(0x6ba7b8109dad11d1L, 0x80b400c04fd430c8U.toLong())