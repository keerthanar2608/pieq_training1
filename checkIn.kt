
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Employee(
    val EmployeeId: Int,
    val FirstName: String,
    val LastName: String,
    val Role: String,
    val ReportingTo: Int? = null
)

val EmployeeList = mutableListOf<Employee>()

data class CheckIn(
    val EmployeeId: Int,
    val CheckInDate: LocalDate,
    val CheckInTime: LocalTime
)

val CheckInList = mutableListOf<CheckIn>()

fun CheckedIn(employeeId: Int, checkInDate: LocalDate) {
    val employee = validate(employeeId)
    if (employee == null) {
        println("$employeeId is not a valid EmployeeId")
        return
    }

    val alreadyCheckedIn = CheckInList.any { it.EmployeeId == employeeId && it.CheckInDate == checkInDate }
    if (alreadyCheckedIn) {
        println("Employee ID ${employee.EmployeeId} : ${employee.FirstName} ${employee.LastName} has already checked in on $checkInDate.")
    } else {
        val checkInEntry = CheckIn(employeeId, checkInDate, LocalTime.now())
        val formattedTime = checkInEntry.CheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        CheckInList.add(checkInEntry)

        val reportingMessage = employee.ReportingTo?.let { managerId ->
            " (Reports to: $managerId)"
        } ?: "null"

        println("Employee ID ${employee.EmployeeId}: ${employee.FirstName} ${employee.LastName} checked in on ${checkInEntry.CheckInDate} at $formattedTime.$reportingMessage")
    }
}

fun validate(employeeId: Int): Employee? {
    return EmployeeList.find { it.EmployeeId == employeeId }
}

fun main() {

    EmployeeList.add(Employee(1, "Anu", "M", "Manager"))
    EmployeeList.add(Employee(2, "Sowmi", "S", "HR", 1))
    EmployeeList.add(Employee(3, "Keerthana", "Ravikumar", "TeamLeader", 4))
    EmployeeList.add(Employee(4, "Hashini", "Ravikumar", "Manager"))
    EmployeeList.add(Employee(5, "Maha", "E", "Developer", 4))
    EmployeeList.add(Employee(6, "Keerthu", "R", "Developer", 1))

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()

    while (true) {
        print("\nEnter Employee ID and Date (yyyy-MM-dd), separated by space  ")
        val input = readln()

        if (input == "-1") {
            println("Exiting.")
            break
        }

        val parts = input.trim().split(" ")

        val employeeId = parts.getOrNull(0)?.toIntOrNull()
        if (employeeId == null) {
            println("Invalid Employee ID.")
            continue
        }

        val inputDate = parts.getOrNull(1)
        val checkInDate = if (inputDate.isNullOrBlank()) {
            today
        } else {
            try {
                val parsedDate = LocalDate.parse(inputDate, dateFormatter)
                if (parsedDate != today) {
                    println("Only today's date (${today}) is allowed.")
                    continue
                }
                parsedDate
            } catch (e: Exception) {
                println("Invalid date format. Use yyyy-MM-dd.")
                continue
            }
        }

        CheckedIn(employeeId, checkInDate)
    }
}

