function setActiveFilter(filterName) {
    let filterElement = document.getElementById(filterName);
    filterElement.className = "uk-active";
}

function executeSearch() {
    let searchKey = document.getElementById("searchKey").value;
    window.location.href = "http://localhost:8080/search?searchKey=" + searchKey;
}