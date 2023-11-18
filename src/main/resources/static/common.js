// common.js
document.addEventListener('DOMContentLoaded', function () {
    // Specify the ID of the element where you want to inject the sidebar
    var sidebarContainerId = 'sidebar-container';

    // Fetch the sidebar HTML
    fetch('/templates/sidebar.html')
        .then(response => response.text())
        .then(html => {
            // Inject the sidebar HTML into the specified container
            var sidebarContainer = document.getElementById(sidebarContainerId);
            if (sidebarContainer) {
                sidebarContainer.innerHTML = html;
            }
        })
        .catch(error => console.error('Error fetching sidebar:', error));
});
