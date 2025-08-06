import java.time.Duration
import java.time.LocalDate



class AttendanceList : ArrayList<Attendance>() {


    fun addCheckIn(attendance: Attendance): Boolean {
        if (hasAlreadyCheckedIn(attendance.employeeId, attendance.checkIn.toLocalDate())) {
            return false
        }
        this.add(attendance)
        return true
    }


    fun hasAlreadyCheckedIn(employeeId: String, date: LocalDate): Boolean {
        return this.any {
            it.employeeId == employeeId && it.checkIn.toLocalDate() == date
        }
    }

    fun hasAlreadyCheckedOut(employeeId: String): Boolean {
        return this.none {
            it.employeeId == employeeId && it.checkOut == null
        }
    }


    fun delete(employeeId: String): Boolean {
        return this.removeIf { it.employeeId == employeeId }
    }


    fun getAll(): List<Attendance> = this.toList()

    fun findAttendanceById(employeeId: String): Attendance? {
        return this.find {
            it.employeeId == employeeId && it.checkOut == null
        }
    }

    fun summaryOfWorkingHours(employeeId: String): String {
        val totalMinutes = this
            .filter { it.employeeId == employeeId && it.checkOut != null }
            .sumOf { Duration.between(it.checkIn, it.checkOut).toMinutes() }

        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return "$employeeId has worked $hours hour(s) and $minutes minute(s) in total."
    }


    override fun toString(): String {
        return if (this.isEmpty()) {
            "No attendance records found."
        } else {
            this.joinToString("\n") { it.toString() }
        }
    }
}
