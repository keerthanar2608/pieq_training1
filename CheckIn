
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

fun CheckedIn(employeeId: Int) {
    val employee = validate(employeeId)
    if (employee == null) {
        println("$employeeId is not a valid EmployeeId")
        return
    }

    val alreadyCheckedIn = CheckInList.any { it.EmployeeId == employeeId }
    if (alreadyCheckedIn) {
        println("Employee ID ${employee.EmployeeId} : ${employee.FirstName} ${employee.LastName} has already checked in.")
    } else {
        val checkInEntry = CheckIn(employeeId, LocalDate.now(), LocalTime.now())
        val formattedTime = checkInEntry.CheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        CheckInList.add(checkInEntry)

        val reportingMessage = employee.ReportingTo?.let { managerId ->
            val manager = validate(managerId)
            if (manager != null) {
                " (Reports to: ${manager.EmployeeId})"
            } else {
                " (Reports to: Unknown Manager ID $managerId)"
            }
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
    EmployeeList.add(Employee(3, "Keerthana", "Ravikumar", "TeamLeader" , 4))
    EmployeeList.add(Employee(4, "Hashini", "Ravikumar", "Manager"))
    EmployeeList.add(Employee(5, "Maha", "E", "Developer", 4))
    EmployeeList.add(Employee(6, "Keerthu", "R", "Developer", 1))
    EmployeeList.add(Employee(7, "Saru", "M", "Developer", 8))

    while (true) {
        print("\nEnter Employee ID to checkIn: ")
        val input = readln()

        if (input == "-1") {
            println("Invalid Id")
            break
        }

        val employeeId = input.toIntOrNull()
        if (employeeId == null) {
            println("Please enter a valid  Employee ID.")
            continue
        }

        CheckedIn(employeeId)
    }
}




