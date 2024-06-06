import java.security.MessageDigest

private class AndroidHasher(algorithmName: String, override val version: Int) : UUIDHasher {
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
        AndroidHasher("MD5", version)
    } else {
        AndroidHasher("SHA-1", version)
    }
}