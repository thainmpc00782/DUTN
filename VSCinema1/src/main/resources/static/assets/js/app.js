// import axios from 'axios';

// window.addEventListener('load', () => {

//   const api = 'http://www.colr.org/json/color/random';
//   const body = document.querySelector('body');

//   function randomColor() {
//     axios.get(api).then(res => {
//       let color = res.data.colors[0].hex;

//       if (!color) {
//         console.error('Random color could not be fetched.');
//       }

//       color = '#' + color;

//       body.style.backgroundColor = color;
//     }).catch(() => console.error('Random color could not be fetched.'));
//   }

//   randomColor();

//   setInterval(randomColor, 8000);

// });

console.log("Live");
$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').trigger('focus')
})
window.onscroll = function () {
  scrollFunction()
};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    document.getElementById("movetop").style.display = "block";
  } else {
    document.getElementById("movetop").style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}
$(document).ready(function () {
  $('.popup-with-zoom-anim').magnificPopup({
    type: 'inline',

    fixedContentPos: false,
    fixedBgPos: true,

    overflowY: 'auto',

    closeBtnInside: true,
    preloader: false,

    midClick: true,
    removalDelay: 300,
    mainClass: 'my-mfp-zoom-in'
  });

  $('.popup-with-move-anim').magnificPopup({
    type: 'inline',

    fixedContentPos: false,
    fixedBgPos: true,

    overflowY: 'auto',

    closeBtnInside: true,
    preloader: false,

    midClick: true,
    removalDelay: 300,
    mainClass: 'my-mfp-slide-bottom'
  });
});
$(window).on("scroll", function () {
  var scroll = $(window).scrollTop();

  if (scroll >= 80) {
    $("#site-header").addClass("nav-fixed");
  } else {
    $("#site-header").removeClass("nav-fixed");
  }
});

//Main navigation Active Class Add Remove
$(".navbar-toggler").on("click", function () {
  $("header").toggleClass("active");
});
$(document).on("ready", function () {
  if ($(window).width() > 991) {
    $("header").removeClass("active");
  }
  $(window).on("resize", function () {
    if ($(window).width() > 991) {
      $("header").removeClass("active");
    }
  });
});
$(function () {
  $('.navbar-toggler').click(function () {
    $('body').toggleClass('noscroll');
  })
});