document.addEventListener("DOMContentLoaded", function () {
    fetchDepartments();

    document.getElementById("departmentForm").addEventListener("submit", function (event) {
        event.preventDefault();
        addDepartment();
    });
});

// Fetch all departments and populate the table
function fetchDepartments() {
    fetch("/departments/list")
        .then(response => response.json())
        .then(departments => {
            const tableBody = document.getElementById("departmentTableBody");
            tableBody.innerHTML = "";

            departments.forEach(department => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${department.id}</td>
                    <td>${department.name}</td>
                    <td>
                        <button onclick="updateDepartment(${department.id})">Edit</button>
                        <button onclick="deleteDepartment(${department.id})">Delete</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error fetching departments:", error));
}

// Add a new department
function addDepartment() {
    const departmentName = document.getElementById("departmentName").value;

    fetch("/departments/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: departmentName })
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("departmentForm").reset();
            fetchDepartments(); // Refresh list
        }
    })
    .catch(error => console.error("Error adding department:", error));
}

// Update a department
function updateDepartment(id) {
    const newName = prompt("Enter new department name:");

    if (newName) {
        fetch(`/departments/update/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name: newName })
        })
        .then(response => {
            if (response.ok) fetchDepartments(); // Refresh list
        })
        .catch(error => console.error("Error updating department:", error));
    }
}

// Delete a department
function deleteDepartment(id) {
    if (confirm("Are you sure you want to delete this department?")) {
        fetch(`/departments/delete/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) fetchDepartments(); // Refresh list
        })
        .catch(error => console.error("Error deleting department:", error));
    }
}

function updateTable(department) {
    console.log("Updating table with departments:", department); // Debugging log

    const tableBody = document.querySelector("#departmentTableBody tbody");
    tableBody.innerHTML = "";

    if (!department || department.length === 0) {
        console.log("No departments found!"); // Debugging log
        tableBody.innerHTML = "<tr><td colspan='3'>No employees found</td></tr>";
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