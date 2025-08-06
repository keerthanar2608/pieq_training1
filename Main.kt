import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun main() {
    val employeeManager = EmployeeManager()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    while (true) {
        println(
            """
            1. Add New Employee
            2. Delete Employee
            3. List All Employees
            4. Check-In
            5. Check-Out
            6. Delete Attendance Record
            7. List All Attendance Records
            8. View Working Hours Between Dates
            9. List Checked-In Employees
            0. Exit
            Enter your choice:
            """.trimIndent()
        )

        when (readln().trim()) {
            "1" -> {
                print("Enter First Name: ")
                val firstName = readln().trim()

                print("Enter Last Name: ")
                val lastName = readln().trim()

                print("Enter Role (MANAGER, DEVELOPER, HR): ")
                val roleInput = readln().trim().uppercase()
                val role = try {
                    Role.valueOf(roleInput)
                } catch (e: Exception) {
                    println("Invalid Role.")
                    continue
                }

                print("Enter Department (IT, SALES, MARKETING, FINANCE): ")
                val deptInput = readln().trim().uppercase()
                val department = try {
                    Department.valueOf(deptInput)
                } catch (e: Exception) {
                    println("Invalid Department.")
                    continue
                }

                print("Enter Reporting To (or leave empty): ")
                val reportingTo = readln().trim().takeIf { it.isNotEmpty() }

                val addedEmployee = employeeManager.addEmployee(
                    firstName,
                    lastName,
                    role.name,
                    department.name,
                    reportingTo
                )

                if (addedEmployee != null) {
                    println("Employee added successfully with ID: ${addedEmployee.id}")
                } else {
                    println("Failed to add employee. Duplicate data.")
                }
            }

            "2" -> {
                print("Enter Employee ID to delete: ")
                val id = readln().trim()
                val deleted = employeeManager.deleteEmployeeById(id)
                println(if (deleted) "Employee deleted successfully." else "Employee not found.")
            }

            "3" -> {
                val employees = employeeManager.getAllEmployees()
                if (employees.isEmpty()) {
                    println("No employees found.")
                } else {
                    println("All Employees:")
                    employees.forEach { println(it) }
                }
            }

            "4" -> {
                print("Enter Employee ID to check-in: ")
                val id = readln().trim()

                print("Enter check-in time (yyyy-MM-dd HH:mm) : ")
                val input = readln().trim()
                val checkInTime = try {
                    if (input.isEmpty()) LocalDateTime.now()
                    else LocalDateTime.parse(input, formatter)
                } catch (e: Exception) {
                    println("Invalid format. Please use yyyy-MM-dd HH:mm")
                    continue
                }

                val success = employeeManager.checkIn(id, checkInTime)
                if (success) {
                    println("Check-in successful for employee $id at ${checkInTime.format(formatter)}")
                } else {
                    println("Check-in failed. Employee already checked in today.")
                }
            }

            "5" -> {
                print("Enter Employee ID to check-out: ")
                val id = readln().trim()

                print("Enter check-out time (yyyy-MM-dd HH:mm) or press Enter to use current time: ")
                val input = readln().trim()
                val checkOutTime = try {
                    if (input.isEmpty()) LocalDateTime.now()
                    else LocalDateTime.parse(input, formatter)
                } catch (e: Exception) {
                    println("Invalid format. Please use yyyy-MM-dd HH:mm")
                    continue
                }

                val (success, workingHours) = employeeManager.checkOut(id, checkOutTime)
                if (success) {
                    val (hrs, mins) = workingHours ?: Pair(0, 0)
                    println("Check-out successful for employee $id at ${checkOutTime.format(formatter)}")
                    println("Worked: $hrs hours and $mins minutes")
                } else {
                    println("Check-out failed. Check-in not found or time invalid.")
                }
            }

            "6" -> {
                print("Enter Employee ID to delete attendance: ")
                val id = readln().trim()
                val deleted = employeeManager.deleteAttendanceById(id)
                println(if (deleted) "Attendance deleted." else "No attendance found for employee.")
            }

            "7" -> {
                val records = employeeManager.getAllAttendance()
                if (records.isEmpty()) {
                    println("No attendance records found.")
                } else {
                    println("Attendance Records:")
                    records.forEach { println(it) }
                }
            }

            "8" -> {
                print("Enter From Date (yyyy-MM-dd): ")
                val fromInput = readln().trim()
                print("Enter To Date (yyyy-MM-dd): ")
                val toInput = readln().trim()

                val fromDate = try {
                    LocalDate.parse(fromInput)
                } catch (e: Exception) {
                    println("Invalid from-date format.")
                    continue
                }

                val toDate = try {
                    LocalDate.parse(toInput)
                } catch (e: Exception) {
                    println("Invalid to-date format.")
                    continue
                }

                if (fromDate.isAfter(toDate)) {
                    println("From date cannot be after To date.")
                    continue
                }

                val results = employeeManager.getWorkingHoursBetweenDates(fromDate, toDate)
                if (results.isEmpty()) {
                    println("No records found in given date range.")
                } else {
                    results.forEach { (employee, time) ->
                        println("${employee.firstName} ${employee.lastName} (${employee.id}) worked ${time.first}h ${time.second}m")
                    }
                }
            }

            "9" -> {
                val checkedIn = employeeManager.getCheckedInEmployees()
                if (checkedIn.isEmpty()) {
                    println("No employees are currently checked in.")
                } else {
                    println("Currently Checked-In Employees:")
                    checkedIn.forEach { println(it) }
                }
            }

            "0" -> {
                println("Exiting application.")
                return
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}
