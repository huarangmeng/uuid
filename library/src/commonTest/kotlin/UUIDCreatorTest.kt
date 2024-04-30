import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class UUIDCreatorTest {

    @Test
    fun `UUIDV1 format is valid`() {
        val uuid = uuidV1()
        val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-1[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}\$")
        assertTrue(uuidRegex.matches(uuid.toString()), "Generated UUID does not match the expected format")
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
}