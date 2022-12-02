const footer = document.querySelector('.footer')

fetch('/QLHTT/src/main/resources/templates/footerCustomer.html')
    .then(response => response.text())
    .then(data => {
        footer.innerHTML = data
    })