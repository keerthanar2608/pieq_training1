

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

class EmployeeManager {
    private val employeeList = EmployeeList()
    private val attendanceList = AttendanceList()

    fun addEmployee() {
        println("Enter First Name:")
        val firstName = readln().trim()

        println("Enter Last Name:")
        val lastName = readln().trim()

        println("Enter Role (MANAGER, DEVELOPER, HR):")
        val role = try {
            Role.valueOf(readln().trim().uppercase())
        } catch (e: Exception) {
            println("Invalid Role.")
            return
        }

        println("Enter Department (IT, SALES, MARKETING, FINANCE):")
        val department = try {
            Department.valueOf(readln().trim().uppercase())
        } catch (e: Exception) {
            println("Invalid Department.")
            return
        }

        println("Enter Reporting To (or leave empty):")
        val reportingTo = readln().trim().takeIf { it.isNotEmpty() }

        val employee = Employee(firstName, lastName, role, department, reportingTo)

        if (employeeList.addEmployee(employee)) {
            println("Employee added successfully: ${employee.id}")
        } else {
            println("Failed to add employee. Duplicate or invalid data.")
        }
    }

    fun deleteEmployee() {
        println("Enter Employee ID to delete:")
        val id = readln().trim()

        if (employeeList.deleteEmployeeById(id)) {
            println("Employee deleted successfully.")
        } else {
            println("Employee not found.")
        }
    }

    fun listEmployees() {
        println("Employee List:")
        println(employeeList)
    }

    fun checkIn(id: String): String {
        if (!employeeList.employeeExists(id)) {
            return "Employee with ID $id not found."
        }

        if (attendanceList.hasAlreadyCheckedIn(id, LocalDate.now())) {
            return "Employee $id has already checked in today."
        }

        print("Enter check-in date and time (yyyy-MM-dd HH:mm) : ")
        val input = readln().trim()

        val checkInTime = if (input.isEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                val parsed = LocalDateTime.parse(input, formatter)
                if (parsed.isAfter(LocalDateTime.now())) {
                    return "Check-in time cannot be in the future."
                }
                parsed
            } catch (e: Exception) {
                return "Invalid date/time format. Use yyyy-MM-dd HH:mm"
            }
        }

        val attendance = Attendance(id, checkInTime)
        return if (attendanceList.addCheckIn(attendance)) {
            "Check-in recorded for employee $id at ${checkInTime.format(formatter)}"
        } else {
            "Allows Only One CheckIn and CheckOut For Employee Per Day"
        }
    }


    fun checkOut(id: String): String {
        val attendance = attendanceList.findAttendanceById(id)
        if (attendance == null) {
            return "No active check-in found for employee $id."
        }

        print("Enter check-out date and time (yyyy-MM-dd HH:mm) or press Enter to use current time: ")
        val input = readln().trim()

        val checkOutTime = if (input.isEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                val parsed = LocalDateTime.parse(input, formatter)
                if (parsed.isAfter(LocalDateTime.now())) {
                    return "Check-out time cannot be in the future."
                }
                parsed
            } catch (e: Exception) {
                return "Invalid date/time format. Use yyyy-MM-dd HH:mm"
            }
        }

        if (checkOutTime.isBefore(attendance.checkIn)) {
            return "Check-out time cannot be before check-in time."
        }

        val success = attendance.checkOut(checkOutTime)
        return if (success) {
            "Check-out recorded for employee $id at ${checkOutTime.format(formatter)}\n" +
                    "Working hours: ${attendance.workingHours?.first} hrs ${attendance.workingHours?.second} mins"
        } else {
            "Failed to check out employee $id."
        }
    }


    fun deleteAttendance() {
        println("Enter Employee ID to delete attendance:")
        val id = readln().trim()

        if (attendanceList.delete(id)) {
            println("Attendance deleted successfully.")
        } else {
            println("No attendance found for this employee.")
        }
    }

    fun listAttendance() {
        println("Attendance Records:")
        println(attendanceList)
    }

    fun listEmployeesCheckedIn() {
        val checkedIn = attendanceList.filter { it.checkOut == null }

        if (checkedIn.isEmpty()) {
            println("No employees currently checked in.")
            return
        }

        println("Employees currently checked in:")
        checkedIn.forEach {
            val emp = employeeList.getEmployeeById(it.employeeId)
            val checkInTime = it.checkIn.format(formatter)
            println("${emp?.firstName} ${emp?.lastName} (ID: ${emp?.id}) - Checked in at $checkInTime")
        }
    }

    fun listWorkingHoursBetweenDates() {
        print("Enter From Date (yyyy-MM-dd): ")
        val fromInput = readln().trim()
        print("Enter To Date (yyyy-MM-dd): ")
        val toInput = readln().trim()

        val fromDate: LocalDate
        val toDate: LocalDate

        try {
            fromDate = LocalDate.parse(fromInput)
            toDate = LocalDate.parse(toInput)
        } catch (e: Exception) {
            println("Invalid date format.")
            return
        }

        if (fromDate.isAfter(toDate)) {
            println("From date cannot be after To date.")
            return
        }

        employeeList.getAllEmployees().forEach { employee ->
            val records = attendanceList.filter {
                it.employeeId == employee.id &&
                        !it.checkIn.toLocalDate().isBefore(fromDate) &&
                        !it.checkIn.toLocalDate().isAfter(toDate) &&
                        it.checkOut != null
            }

            if (records.isEmpty()) {
                println("No records for ${employee.firstName} ${employee.lastName} (${employee.id})")
            } else {
                val totalMinutes = records.sumOf {
                    Duration.between(it.checkIn, it.checkOut).toMinutes()
                }
                val hours = totalMinutes / 60
                val minutes = totalMinutes % 60
                println("${employee.firstName} ${employee.lastName} (${employee.id}) worked: ${hours}h ${minutes}m")
            }
        }
    }

    fun getCheckedInEmployees(): List<Employee> {
        return attendanceList
            .filter { it.checkOut == null }
            .mapNotNull { attendance -> employeeList.getEmployeeById(attendance.employeeId) }
    }

    fun getAllEmployees(): List<Employee> = employeeList.getAllEmployees()

    fun getAllAttendance(): List<Attendance> = attendanceList.getAll()
}
