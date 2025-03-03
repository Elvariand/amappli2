// Populate the form with data when a row is clicked
document.addEventListener('DOMContentLoaded', function () {
    let rows = document.querySelectorAll('#rolesTable tbody tr:not(.table-active)');

    rows.forEach(row => {
        row.addEventListener('click', function () {
            let roleId = this.getAttribute('data-role-id');
            let roleName = this.querySelector('th:first-child').textContent.trim();

            // Set the form values
            document.getElementById('roleId').value = roleId;
            document.getElementById('roleName').value = roleName;

            // Clear all checkboxes
            document.querySelectorAll('#permissions input[type=checkbox]').forEach(checkbox => {
                checkbox.checked = false;
            });

            // Find checked permissions from table cells
            let cells = this.querySelectorAll('td[data-permission-id]');
            cells.forEach(cell => {
                console.log(cell.textContent.trim());
                if (cell.textContent.trim() === '✓') { // Check if the cell contains a checkmark
                    let permissionId = cell.getAttribute('data-permission-id');
                    let checkboxId = "perm-"+permissionId;
                    let checkbox = document.getElementById(checkboxId);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                }
            });
        });
    });
});