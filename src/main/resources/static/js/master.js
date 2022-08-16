function executeSearch() {
    let searchTerm = document.getElementById("searchField").value;
    window.location.href = "http://localhost:8080/search?search=" + searchTerm;
}