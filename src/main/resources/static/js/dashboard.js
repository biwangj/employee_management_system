import URLS from './config.js';

const pageSize = 5;

document.addEventListener("DOMContentLoaded", function() {
    fetchEmployees();
    fetchAverages();
    fetchDepartments();

    document.getElementById("departmentFilter").addEventListener("change", filterByDepartment);
    document.getElementById("ageFilter").addEventListener("input", filterByAge);
    document.getElementById("clearFilters").addEventListener("click", clearFilters);
    document.getElementById("searchInput").addEventListener("input", function () {
            const query = this.value.trim();
            if (query.length > 0) {
                fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/search?search=${query}`)
                    .then(response => response.json())
                    .then(data => updateTable(data))
                    .catch(error => console.error("Error searching employees: ", error));
            } else {
                fetchEmployees(); // Reload all employees if search is empty
            }
        });

    console.log("Page loaded, fetching average age...");
});

function fetchEmployees(page = 0) {
    console.log("Fetching employees for page:", page); // Debugging log

    fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/list?page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            console.log("Fetched Employees Data:", data); // Debugging log
            updateTable(data.content);
            updatePagination(data);
        })
        .catch(error => console.error("Error fetching employees:", error));
}

function updatePagination(data) {
    const paginationContainer = document.getElementById("paginationControls");
    paginationContainer.innerHTML = "";

    if (data.totalPages > 1) {
        if (data.pageable.pageNumber > 0) {
            paginationContainer.innerHTML += `<button onclick="fetchEmployees(${data.pageable.pageNumber - 1})">Previous</button>`;
        }

        for (let i = 0; i < data.totalPages; i++) {
            paginationContainer.innerHTML += `<button onclick="fetchEmployees(${i})">${i + 1}</button>`;
        }

        if (data.pageable.pageNumber < data.totalPages - 1) {
            paginationContainer.innerHTML += `<button onclick="fetchEmployees(${data.pageable.pageNumber + 1})">Next</button>`;
        }
    }
}

function fetchAverages() {
        fetch(`${URLS.EMPLOYEE}/age`)
            .then(response => response.json())
            .then(avgAge => {
                console.log("Ave age is:", avgAge);
                document.getElementById("averageAge").textContent = avgAge.toFixed(2);
            })
            .catch(error => console.error("Fetch error:", error));

    fetch(`${URLS.EMPLOYEE}/salary`)
        .then(response => response.text())
        .then(avgSalary => {
            console.log("Ave salary is:", avgSalary); // Debug log
            const salaryValue = parseFloat(avgSalary);
            if (!isNaN(salaryValue)) {
                document.getElementById("averageSalary").textContent = salaryValue.toFixed(2);
            } else {
                document.getElementById("averageSalary").textContent = "N/A";
            }
        })
        .catch(error => console.error("Error fetching average salary:", error));
}

function deleteEmployee(id) {
    if (confirm("Are you sure you want to delete this employee?")) {
        fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/delete/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) fetchEmployees();
        })
        .catch(error => console.error("Error deleting employee:", error));
    }
}

function updateEmployee(id) {
    window.location.href = `${URLS.EMPLOYEE}/update-employee/${id}`;
}

function fetchDepartments() {
    fetch("/departments/list")
        .then(response => response.json())
        .then(departments => {
            console.log("Fetched Departments:", departments); // Debugging log

            const departmentFilter = document.getElementById("departmentFilter");
            departmentFilter.innerHTML = '<option value="">All Departments</option>'; // Reset options

            departments.forEach(department => {
                const option = document.createElement("option");
                option.value = department.name; // Ensure this matches the correct field
                option.textContent = department.name;
                departmentFilter.appendChild(option);
            });
        })
        .catch(error => console.error("Error fetching departments:", error));
}

function filterByDepartment() {
    const department = document.getElementById("departmentFilter").value;

    if (department) {
        fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/department/${department}`) //Make sure this endpoint exists
            .then(response => response.json())
            .then(data => {
                console.log("Filtered Employees:", data); // Debugging log
                updateTable(data); // ✅ Update table with filtered employees
            })
            .catch(error => console.error("Error filtering employees by department:", error));
    } else {
        fetchEmployees(); // Load all employees if no department is selected
    }
}

function filterByAge() {
    const age = document.getElementById("ageFilter").value.trim();
    if (age) {
        fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/age/${age}`) // ✅ Correctly formats URL with age
            .then(response => response.json())
            .then(data => updateTable(data))
            .catch(error => console.error("Error filtering employees by age: ", error));
    } else {
        fetchEmployees(); // Reload all employees if no age is entered
    }
}

function searchEmployees() {
    const query = document.getElementById("searchInput").value.trim();
    if (query) {
        fetch(`${URLS.BASE_URL}${URLS.EMPLOYEE}/search?search=${query}`)
            .then(response => response.json())
            .then(data => updateTable(data))
            .catch(error => console.error("Error searching employees: ", error));
    } else {
        fetchEmployees();
    }
}

function clearFilters() {
    document.getElementById("departmentFilter").value = "";
    document.getElementById("ageFilter").value = "";
    document.getElementById("searchInput").value = "";
    fetchEmployees();
    fetchAverages();
}

function updateTable(employees) {
    console.log("Updating table with employees:", employees); // Debugging log

    const tableBody = document.querySelector("#employeeTable tbody");
    tableBody.innerHTML = "";

    if (!employees || employees.length === 0) {
        console.log("No employees found!"); // Debugging log
        tableBody.innerHTML = "<tr><td colspan='6'>No employees found</td></tr>";
        return;
    }

    employees.forEach(employee => {
        console.log("Adding Employee:", employee); // Debugging log

        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${employee.employeeId}</td>
            <td>${employee.name}</td>
            <td>${employee.dateOfBirth}</td>
            <td>${employee.department}</td>
            <td>${employee.salary}</td>
            <td>
                <button onclick="updateEmployee(${employee.id})">Update</button>
                <button onclick="deleteEmployee(${employee.id})" class="delete-btn">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

window.fetchEmployees = fetchEmployees;
window.updateEmployee = updateEmployee;
window.deleteEmployee = deleteEmployee;