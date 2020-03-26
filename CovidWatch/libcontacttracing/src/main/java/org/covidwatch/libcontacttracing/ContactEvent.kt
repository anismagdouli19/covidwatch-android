package org.covidwatch.libcontacttracing

/**
 * A ContactEvent stores the data required generated by a contact event
 * Exposes functions to compare the events based on time and equality
 *
 * @param data ByteArray of size 16 (required to be exactly 16 bytes)
 * @param timestamp The timestamp when the ContactEvent was generated
 * @param RSSI The Received Signal Strength Indicator to indicate distance
 */
data class ContactEvent(
    var data: ByteArray,
    var timestamp: Int,
    var RSSI: Int
) {

    init {
        require(data.size == 16)
    }

    /**
     * Equality check two ContactEvents, compares down to the
     * time and RSSI
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactEvent

        if (!data.contentEquals(other.data)) return false
        if (timestamp != other.timestamp) return false
        if (RSSI != other.RSSI) return false

        return true
    }

    /**
     * Compare Operator overriding to implement RSSI based comparison
     * The lower the RSSI value, the closer the ContactEvent is to the current
     * user.
     *
     * Ex. CE1 > CE2 returns true if CE1 has a stronger signal strength
     */
    operator fun compareTo(other: ContactEvent): Int = when {
        RSSI != other.RSSI -> (RSSI - other.RSSI)
        else -> (RSSI - other.RSSI)
    }

    /**
     * Hash
     */
    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + timestamp
        result = 31 * result + RSSI
        return result
    }
}