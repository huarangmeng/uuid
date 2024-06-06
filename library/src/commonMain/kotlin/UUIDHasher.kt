interface UUIDHasher {

    /**
     * The UUID version, for which this
     * hash algorithm is being used:
     * - 3 for MD5
     * - 5 for SHA-1
     */
    val version: Int

    /**
     * Updates the hash's digest with more bytes
     * @param input to update the hasher's digest
     */
    fun update(input: ByteArray)

    /**
     * Completes the hash computation and returns the result
     * @note The hasher should not be used after this call
     */
    fun digest(): ByteArray
}

expect fun getHasher(version: Int): UUIDHasher