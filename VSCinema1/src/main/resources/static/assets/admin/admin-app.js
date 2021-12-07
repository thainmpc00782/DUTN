app = angular.module("admin-app",["ngRoute"]);

app.config(function($routeProvider){
    $routeProvider
    .when("/cinema" ,{
        templateUrl :"/assets/admin/cinema/index.html",
        controller: "cinema-ctrl", 
    })
    .when("/room" ,{
        templateUrl :"/assets/admin/room/index.html",
		 controller: "room-ctrl"
        
    })
     .when("/movie" ,{
        templateUrl :"/assets/admin/movie/index.html",
        controller: "movie-ctrl"
     })
     .when("/show" ,{
        templateUrl :"/assets/admin/show/index.html",
		 controller: "show-ctrl"
         
     })
     .when("/account" ,{
        templateUrl :"/assets/admin/account/index.html",
		 controller: "account-ctrl"

     })
     .when("/authorize" ,{
        templateUrl :"/assets/admin/authority/index.html",
        controller: "authority-ctrl"
    })
      .when("/foods" ,{
        templateUrl :"/assets/admin/food/index.html",
        controller: "food-ctrl"
    })
    .when("/sales" ,{
        templateUrl :"/assets/admin/sale/index.html",
        controller:"sale-ctrl"
    })
    .when("/orders" ,{
        templateUrl :"/assets/admin/order/index.html",
		 controller: "order-ctrl"
    })
	.when("/tickets" ,{
        templateUrl :"/assets/admin/ticket/index.html",
        controller: "ticket-ctrl"
    })
    .when("/censors" ,{
        templateUrl :"/assets/admin/censor/index.html",
        controller: "censor-ctrl"
    })
    .when("/prices" ,{
        templateUrl :"/assets/admin/price/index.html",
        controller: "price-ctrl"
    })
	.when("/statistics" ,{
        templateUrl :"/assets/admin/statistics/index.html",
        controller: "statistics-ctrl"
    })
    .when("/coupons" ,{
        templateUrl :"/assets/admin/coupon_code/index.html",
        controller: "coupon-ctrl"
    })
    .otherwise({
        template :"<h1 class='text-center' ></h1>"
    });
})