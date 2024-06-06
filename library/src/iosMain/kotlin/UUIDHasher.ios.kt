import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_MD5
import platform.CoreCrypto.CC_MD5_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA1
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH

private class AppleHasher(private val digestFunc: (ByteArray) -> ByteArray, override val version: Int) :
    UUIDHasher {
    private var data = ByteArray(0)
    override fun update(input: ByteArray) {
        val prevLength = data.size
        data = data.copyOf(data.size + input.size)
        input.copyInto(data, prevLength)
    }

    override fun digest(): ByteArray {
        return digestFunc(data)
    }

    companion object {
        @OptIn(ExperimentalForeignApi::class)
        fun sha1Digest(data: ByteArray): ByteArray {
            return ByteArray(CC_SHA1_DIGEST_LENGTH).also { bytes: ByteArray ->
                bytes.usePinned { digestPin ->
                    data.usePinned { dataPin ->
                        CC_SHA1(
                            dataPin.addressOf(0),
                            data.size.toUInt(),
                            digestPin.addressOf(0).reinterpret()
                        )
                    }
                }
            }
        }

        @OptIn(ExperimentalForeignApi::class)
        fun md5Digest(data: ByteArray): ByteArray {
            return ByteArray(CC_MD5_DIGEST_LENGTH).also { bytes: ByteArray ->
                bytes.usePinned { digestPin ->
                    data.usePinned { dataPin ->
                        CC_MD5(
                            dataPin.addressOf(0),
                            data.size.toUInt(),
                            digestPin.addressOf(0).reinterpret()
                        )
                    }
                }
            }
        }
    }
}

actual fun getHasher(version: Int): UUIDHasher {
    return if (version == 3) {
        AppleHasher(AppleHasher.Companion::md5Digest, version)
    } else {
        AppleHasher(AppleHasher.Companion::sha1Digest, version)
    }
}