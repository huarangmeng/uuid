import java.security.MessageDigest

private class JvmHasher(algorithmName: String, override val version: Int) : UUIDHasher {
    private val digest = MessageDigest.getInstance(algorithmName)

    override fun update(input: ByteArray) {
        digest.update(input)
    }

    override fun digest(): ByteArray {
        return digest.digest()
    }
}

actual fun getHasher(version: Int): UUIDHasher {
    return if (version == 3) {
        JvmHasher("MD5", version)
    } else {
        JvmHasher("SHA-1", version)
    }
}