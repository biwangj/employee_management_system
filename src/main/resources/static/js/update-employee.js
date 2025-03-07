import URLS from './config.js';  // If using a config file for base URLs

document.addEventListener("DOMContentLoaded", function () {
    const employeeId = new URLSearchParams(window.location.search).get("id"); // Get ID from URL

    if (employeeId) {
        fetchEmployeeDetails(employeeId); // Fetch and populate form
    }
});

function fetchEmployeeDetails(employeeId) {
    fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/${employeeId}`) // API Endpoint
        .then(response => response.json())
        .then(employee => {
            if (employee) {
                document.getElementById("id").value = employee.id;  // Primary Key
                document.getElementById("name").value = employee.name;
                document.getElementById("employeeId").value = employee.employeeId;
                document.getElementById("dateOfBirth").value = employee.dateOfBirth;
                document.getElementById("department").value = department.department;
                document.getElementById("salary").value = employee.salary;
            } else {
                alert("Employee not found!");
            }
        })
        .catch(error => console.error("Error fetching employee details:", error));
}

window.fetchEmployeeDetails = fetchEmployeeDetails;