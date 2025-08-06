
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Attendance(
    val employeeId: String,
    val checkIn: LocalDateTime,
    var checkOut: LocalDateTime? = null
) {
    var workingHours: Pair<Int, Int>? = null
        internal set

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun checkOut(providedCheckOut: LocalDateTime): Boolean {
        if (providedCheckOut.isBefore(checkIn)) {
            println("Check-out time cannot be before check-in time.")
            return false
        }

        checkOut = providedCheckOut

        val duration = Duration.between(checkIn, checkOut)
        val hours = duration.toHours().toInt()
        val minutes = (duration.toMinutes() % 60).toInt()

        workingHours = Pair(hours, minutes)

        return true
    }

    override fun toString(): String {
        val checkInStr = checkIn.format(formatter)
        val checkOutStr = checkOut?.format(formatter) ?: "N/A"
        val hoursStr = workingHours?.let { "${it.first} hrs ${it.second} mins" } ?: "N/A"

        return "Employee Id=$employeeId, CheckIn=$checkInStr, CheckOut=$checkOutStr, WorkingHours=$hoursStr"
    }
}

