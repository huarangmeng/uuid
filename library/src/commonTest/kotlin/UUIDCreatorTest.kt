import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class UUIDCreatorTest {

    @Test
    fun `UUIDV1 format is valid`() {
        val uuid = uuidV1()
        val uuidRegex =
            Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-1[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")
        val uuidString = uuid.toString()
        println("v1 = $uuidString")
        assertTrue(
            uuidRegex.matches(uuidString),
            "Generated UUID does not match the expected format"
        )
    }

    @Test
    fun `UUIDV1 is v1`() {
        val uuid = uuidV1()
        assertEquals(1, uuid.version(), "Generated UUID does not version 1")
    }

    @Test
    fun `UUIDV1 is unique`() {
        val uuid1 = uuidV1()
        val uuid2 = uuidV1()
        val uuid3 = uuidV1()
        val uuid4 = uuidV1()
        assertNotEquals(uuid1, uuid2, "Generated UUIDs should be unique")
        assertNotEquals(uuid3, uuid4, "Generated UUIDs should be unique")
        assertNotEquals(uuid1, uuid3, "Generated UUIDs should be unique")
        assertNotEquals(uuid2, uuid4, "Generated UUIDs should be unique")
    }

    @Test
    fun `UUIDV2 is v2`() {
        val uuid = uuidV2(0.toByte(), 1)
        println("v2 = $uuid")
        assertEquals(2, uuid.version(), "Generated UUID does not version 2")
    }

    @Test
    fun `UUIDV3 is v3`() {
        val uuid = uuidV3(NAMESPACE_SDS, "www.example.com")
        println("v3 = $uuid")
        assertEquals(3, uuid.version(), "Generated UUID does not version 3")
    }

    @Test
    fun `UUIDV4 is v4`() {
        val uuid = uuidV4()
        println("v4 = $uuid")
        assertEquals(4, uuid.version(), "Generated UUID does not version 4")
    }

    @Test
    fun `UUIDV5 is v5`() {
        val uuid = uuidV5(NAMESPACE_SDS, "www.example.com")
        println("v5 = $uuid")
        assertEquals(5, uuid.version(), "Generated UUID does not version 5")
    }

    @Test
    fun `UUIDV6 is v6`() {
        val uuid = uuidV6()
        println("v6 = $uuid")
        assertEquals(6, uuid.version(), "Generated UUID does not version 6")
    }

    @Test
    fun `UUIDV7 is v7`() {
        val uuid = uuidV7()
        println("v7 = $uuid")
        assertEquals(7, uuid.version(), "Generated UUID does not version 7")
    }
}