$(document).ready(function () {
    $('.owl-one').owlCarousel({
        stagePadding:280,
        loop:true,
        margin: 20,
        nav: true,
        responsiveClass: true,
        autoplay: true,
        autoplayTimeout: 5000,
        autoplaySpeed: 1000,
        autoplayHoverPause: false,
        responsive: {
            0: {
                items: 1,
                nav: false
            },
            480: {
                items: 1,
                nav: true
            },
            667: {
                items: 1,
                nav: true
            },
            1000: {
                items: 1,
                nav: true
            }
        }
    })
})
$(document).ready(function () {
    $('.owl-three').owlCarousel({
        loop: true,
        margin: 20,
        nav: false,
        responsiveClass: true,
        autoplay: true,
        autoplayTimeout: 5000,
        autoplaySpeed: 1000,
        autoplayHoverPause: false,
        responsive: {
            0: {
                items: 2,
                nav: false
            },
            480: {
                items: 2,
                nav: true
            },
            667: {
                items: 3,
                nav: true
            },
            1000: {
                items: 5,
                nav: true
            }
        }
    })
})