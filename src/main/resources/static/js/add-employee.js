import URLS from './config.js';

document.addEventListener("DOMContentLoaded", function () {
    fetchDepartments(); // Load departments when the page loads

    document.getElementById("employeeForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const employeeData = {
            name: document.getElementById("name").value,
            employeeId: document.getElementById("employeeId").value,
            dateOfBirth: document.getElementById("dateOfBirth").value,
            department: document.getElementById("department").value,
            salary: parseFloat(document.getElementById("salary").value)
        };

        fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(employeeData)
        })
        .then(response => {
            if (response.ok) {
                alert("Employee added successfully!");
                window.location.href = "/dashboard";
                //document.getElementById("employeeForm").reset(); // Reset form fields
            } else {
                alert("Failed to add employee.");
            }
        })
        .catch(error => console.error("Error adding employee:", error));
    });
});

function fetchDepartments() {
    fetch(`${URLS.DEPARTMENT}/list`)
        .then(response => response.json())
        .then(departments => {
            const departmentDropdown = document.getElementById("department");
            departmentDropdown.innerHTML = ""; // Clear previous options

            departments.forEach(department => {
                const option = document.createElement("option");
                option.value = department.name;
                option.textContent = department.name;
                departmentDropdown.appendChild(option);
            });
        })
        .catch(error => console.error("Error fetching departments:", error));
}

window.fetchDepartments = fetchDepartments;