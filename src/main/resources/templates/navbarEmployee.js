const navbar = document.querySelector('.navbar');

fetch('/QLHTT/src/main/resources/templates/navbarEmployee.html')
    .then(response => response.text())
    .then(data => {
        navbar.innerHTML = data
    })