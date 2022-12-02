const header = document.querySelector('.header-top')

fetch('/QLHTT/src/main/resources/templates/headerCustomer.html')
    .then(response => response.text())
    .then(data => {
        header.innerHTML = data
    })