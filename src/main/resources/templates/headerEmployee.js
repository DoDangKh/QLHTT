const header = document.querySelector('.employee-header')

fetch('/QLHTT/src/main/resources/templates/headerEmployee.html')
    .then(response => response.text())
    .then(data => {
        header.innerHTML = data
    })