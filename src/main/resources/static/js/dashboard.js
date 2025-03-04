<script>
    function openModal() {
        document.getElementById("addEmployeeModal").classList.add("show");
        document.getElementById("modalOverlay").classList.add("show");
    }

    function closeModal() {
        document.getElementById("addEmployeeModal").classList.remove("show");
        document.getElementById("modalOverlay").classList.remove("show");
    }

    // Close when clicking outside the modal
    window.onclick = function(event) {
        let modal = document.getElementById("addEmployeeModal");
        let overlay = document.getElementById("modalOverlay");
        if (event.target === overlay) {
            closeModal();
        }
    }
</script>