$(document).ready(init());

// Hàm để khởi tạo func
function init() {
  start();
  onClick();
  onHover();
}

// Hàm để fixed UI ngay lúc vừa vào page và khởi tạo biến trong này
function start() {

}

// Hàm để viết sự kiện click
function onClick() {
  $('.iconSlideUp').click(function () {
    $(this).toggleClass('icon-chevron-down');
    $(this).parent().next().slideToggle();
  });
}

function onHover() {
  $('.tooltip-tether').hover(function () {
    $(this).find('.tooltip').toggleClass('active');
  })
}







