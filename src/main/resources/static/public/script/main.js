$(document).ready(function () {
    //slider
    $(".owl-carousel#slider-banner").owlCarousel({
      items: 1,
      autoplay: true,
      loop: true,
      mouseDrag: false,
      autoplayTimeout: 8000,
    });
    $(".owl-carousel#slider-product").owlCarousel({
      items: 5,
      autoplay: true,
      loop: true,
      dots: false,
      nav: true,
      navText: ['<i class="fa-solid fa-chevron-left"></i>', '<i class="fa-solid fa-chevron-right"></i>'],
      mouseDrag: false,
      autoplayTimeout: 8000,
    });
})
