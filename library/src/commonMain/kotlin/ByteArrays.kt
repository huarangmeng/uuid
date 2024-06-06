internal fun putLongToByteArray(array: ByteArray, offset: Int, value: Long) {
    for (i in 0..7) {
        array[offset + i] = (value shr (56 - i * 8)).toByte()
    }
}

internal fun getLongFromByteArray(array: ByteArray, offset: Int): Long {
    var value = 0L
    for (i in 0..7) {
        value = value or ((array[offset + i].toLong() and 0xFF) shl (56 - i * 8))
    }
    return value
}

internal fun stringToUtf8ByteArray(string: String): ByteArray {
    val byteArray = ByteArray(string.length * 4)
    var byteIndex = 0
    for (char in string) {
        val codePoint = char.code
        when {
            codePoint <= 0x7f -> {
                byteArray[byteIndex++] = codePoint.toByte()
            }

            codePoint <= 0x7FF -> {
                byteArray[byteIndex++] = (0xC0 or (codePoint shr 6)).toByte()
                byteArray[byteIndex++] = (0x80 or (codePoint and 0x3F)).toByte()
            }

            codePoint <= 0xFFFF -> {
                byteArray[byteIndex++] = (0xE0 or (codePoint shr 12)).toByte()
                byteArray[byteIndex++] = (0x80 or ((codePoint shr 6) and 0x3F)).toByte()
                byteArray[byteIndex++] = (0x80 or (codePoint and 0x3F)).toByte()
            }

            else -> {
                byteArray[byteIndex++] = (0xF0 or (codePoint shr 18)).toByte()
                byteArray[byteIndex++] = (0x80 or ((codePoint shr 12) and 0x3F)).toByte()
                byteArray[byteIndex++] = (0x80 or ((codePoint shr 6) and 0x3F)).toByte()
                byteArray[byteIndex++] = (0x80 or (codePoint and 0x3F)).toByte()
            }
        }
    }
    return byteArray.copyOf(byteIndex)
}