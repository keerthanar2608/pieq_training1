import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

class EmployeeManager {
    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()

    fun addEmployee(
        firstName: String,
        lastName: String,
        roleInput: String,
        departmentInput: String,
        reportingTo: String?
    ): Employee? {
        val employee = Employee(firstName, lastName, Role.valueOf(roleInput), Department.valueOf(departmentInput), reportingTo)
        return if (employeeList.addEmployee(employee)) employee else null
    }

    fun deleteEmployeeById(id: String): Boolean {
        return employeeList.deleteEmployeeById(id)
    }

    fun getAllEmployees(): List<Employee> = employeeList.getAllEmployees()



    fun checkIn(id: String, checkInTime: LocalDateTime): Boolean {
        val attendance = Attendance(id, checkInTime)
        return attendanceList.addCheckIn(attendance)
    }

    fun checkOut(id: String, checkOutTime: LocalDateTime): Pair<Boolean, Pair<Int, Int>?> {
        val attendance = attendanceList.findAttendanceById(id) ?: return Pair(false, null)
        val success = attendance.checkOut(checkOutTime)
        return Pair(success, attendance.workingHours)
    }

    fun deleteAttendanceById(id: String): Boolean {
        return attendanceList.delete(id)
    }

    fun getAllAttendance(): List<Attendance> = attendanceList.getAll()

    fun getCheckedInEmployees(): List<Employee> {
        return attendanceList
            .filter { it.checkOut == null }
            .mapNotNull { attendance -> employeeList.getEmployeeById(attendance.employeeId) }
    }

    fun getWorkingHoursBetweenDates(fromDate: LocalDate, toDate: LocalDate): Map<Employee, Pair<Long, Long>> {
        val result = mutableMapOf<Employee, Pair<Long, Long>>()

        for (employee in employeeList.getAllEmployees()) {
            val records = attendanceList.filter {
                it.employeeId == employee.id &&
                        !it.checkIn.toLocalDate().isBefore(fromDate) &&
                        !it.checkIn.toLocalDate().isAfter(toDate) &&
                        it.checkOut != null
            }

            if (records.isNotEmpty()) {
                val totalMinutes = records.sumOf {
                    Duration.between(it.checkIn, it.checkOut).toMinutes()
                }
                result[employee] = Pair(totalMinutes / 60, totalMinutes % 60)
            }
        }

        return result
    }
}


//fun employeeExists(id: String): Boolean = employeeList.employeeExists(id)
