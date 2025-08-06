fun main() {
    val employeeManager = EmployeeManager()

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
            "1" -> employeeManager.addEmployee()

            "2" -> employeeManager.deleteEmployee()

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
                val result = employeeManager.checkIn(id)
                println(result)
            }

            "5" -> {
                print("Enter Employee ID to check-out: ")
                val id = readln().trim()
                val result = employeeManager.checkOut(id)
                println(result)
            }

            "6" -> employeeManager.deleteAttendance()

            "7" -> {
                val attendanceRecords = employeeManager.getAllAttendance()
                if (attendanceRecords.isEmpty()) {
                    println("No attendance records found.")
                } else {
                    println("Attendance Records:")
                    attendanceRecords.forEach { println(it) }
                }
            }

            "8" -> employeeManager.listWorkingHoursBetweenDates()

            "9" -> {
                val checkedInEmployees = employeeManager.getCheckedInEmployees()
                if (checkedInEmployees.isEmpty()) {
                    println("No employees are currently checked in.")
                } else {
                    println("Currently Checked-In Employees:")
                    checkedInEmployees.forEach { println(it) }
                }
            }

            "0" -> {
                println("Exiting application.")
                return
            }

            else -> println("Invalid choice. Please try again.")
        }

    }
}
