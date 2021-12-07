const app = angular.module("booking-ticket-app",[]);
app.controller("booking-ticket-ctrl" , function($scope , $http){
	
	$scope.dates = [];
	$scope.shows = [];
	$scope.cities = [];
	$scope.cinemas = [];
	$scope.citiesFoods = [];
	$scope.cinemasFoods = [];
	$scope.listFoodsCinema = [];
	$scope.orderFoods = [];
	$scope.error = 0;
	
	$scope.positions = [];
	$scope.seats = [];
	$scope.seats2 = [];
	$scope.tickets = [];
	$scope.listFood = [];
	$scope.foods = [];
	$scope.show ={};
	$scope.couponRequest = {
		coupon: {},
		message: null
	};
	$scope.coupon = {
		id: null,
		discount: 0,
		quantity: 0,
		activity: false,
		expiry: null,
		createdate: new Date()
	};
	$scope.cinemaInfo = {};
	$scope.amountFoods = null;
	$scope.payment = null;
	$scope.vnpay = "VNPAY";
	$scope.bankCode = null;
	$scope.bankNCB = "NCB";
	$scope.bankSACOMBANK = "SACOMBANK";
	$scope.bankTPBANK = "TPBANK";
	$scope.bankBIDV = "BIDV";
	$scope.code = null;
	$scope.totalSeat = null;
	$scope.amountSeat = null;
	$scope.amountFood = null;
	$scope.total = null;
	$scope.buttonCoupon = null;
	
	$scope.listOrder = [];
	$scope.showOrder = {};
	$scope.account = {};
	
	$scope.initialize = function(){
		$http.get("/rest/show/date").then(resp =>{
          	$scope.dates = resp.data;
		});
	}
	
	$scope.accountLogin = function(){
		$http.get("/rest/users/getAccountLogin").then(resp =>{
			if(resp.data.username == null){
				window.location.href = "/security/login/form";
			} else {
				$scope.account = resp.data;
			}
		});
	}
	
	$scope.saveID = function(item){
		var movieid = JSON.stringify(item);
		localStorage.setItem("movieID", movieid);
		location.href ="/cinema/cinema-list";
	}
	
	$scope.getShow = function(d){
		var movieid = localStorage.getItem("movieID");
		var item = movieid?JSON.parse(movieid):[];
		if(item == ""){
			$scope.error = 1;
			$scope.shows = [];
			
		}else{
			$scope.error = 0;
			$scope.shows = [];
			$scope.cities = [];
			$scope.cinemas = [];
			$http.post(`/rest/show/showtimes/${item}`, d).then(resp =>{
	          	$scope.shows = resp.data;
				$scope.cities=[];
				for(var i=0; i<$scope.shows.length;i++){
					var city = $scope.cities.find(c => c.id == $scope.shows[i].room.cinema.city.id);
					if(city == null){
						$scope.cities.push($scope.shows[i].room.cinema.city);
					}
				}
			});
		}
	}
	
	$scope.showtimes = function(item){
		$scope.cinemas = [];
		for(var i = 0; i < $scope.shows.length; i++){
			if($scope.shows[i].room.cinema.city.id == item.id){
				var cinema = null;
				cinema = $scope.cinemas.find(c => c.id == $scope.shows[i].room.cinema.id);
				if(cinema == null){
					 $scope.cinemas.push($scope.shows[i].room.cinema);
				}
			}
		}
	} 
	
	$scope.chooseShow = function(item){
		$http.get(`/rest/show/checkshow/${item.id}`).then(resp =>{
			var checkshow = resp.data;
			if(checkshow.id == null){
				Swal.fire({
					title: "Suất chiếu đã hết giờ đặt vé!",
					confirmButtonText: 'Xác nhận',
				}).then((result) => {
					if (result.isConfirmed) {
						Swal.fire('Loading!', '', 'success')
						window.location.href = "/cinema/cinema-list";
					} 
				})
			} else {
				var show = JSON.stringify(item);
				localStorage.setItem("show", show);
				location.href ="/ticket/book-tickets";
			}
		});
	}
	
	$scope.getSeats = function(){
		var show = localStorage.getItem("show");
		var item = show?JSON.parse(show):[];
		if(item == ""){
			$scope.show == [];
			Swal.fire({
				title: "Vui lòng chọn xuất chiếu!",
				confirmButtonText: 'Xác nhận',
			}).then((result) => {
				if (result.isConfirmed) {
					Swal.fire('Loading!', '', 'success')
					$scope.clearA();
					window.location.href = "/cinema/cinema-list";
				} 
			})
		} else {
			$scope.show = item;
			$scope.createPosition();
			$scope.getTicket(item);
			$http.get(`/rest/seats/${item.room.id}`).then(resp => {
				$scope.seats = resp.data;
				for(var i = 0; i < $scope.seats.length;i++){
					for(var j = 0;j < $scope.positions.length;j++){
						if($scope.seats[i].position == $scope.positions[j].id){
							$scope.positions[j].seat = $scope.seats[i];
							$scope.positions[j].value = 1;
						} 
					}
				}
				for(var i = 0; i < $scope.positions.length;i++){
					for(var j = 0;j < $scope.tickets.length;j++){
						if($scope.positions[i].seat.id == $scope.tickets[j].seat.id){
							$scope.positions[i].value = 3;
						} 
					}
				}
			}).catch(error => {
				console.log(error)
			})
		}
	}
	
	$scope.createPosition = function(){
		$scope.positions = [];
		for(var i = 1; i <= 210; i++){
			var position = {
				id: i,
				seat:{},
				value: 0
			}
			$scope.positions.push(position);
		}
	}
	
	$scope.getTicket = function(item){
		$scope.tickets = [];
		$http.get(`/rest/ticket/${item.id}`).then(resp => {
			$scope.tickets = resp.data;
		}).catch(error => {
			console.log(error)
		})
	}
	
	$scope.add = function(item){
		$http.get(`/rest/show/checkshow/${$scope.show.id}`).then(resp => {
			var checkshow = resp.data;
			if(checkshow.id == null){
				Swal.fire({
					title: "Suất chiếu đã hết giờ đặt vé!",
					confirmButtonText: 'Xác nhận',
				}).then((result) => {
					if (result.isConfirmed) {
						Swal.fire('Loading!', '', 'success')
						$scope.clearA();
						window.location.href = "/cinema/cinema-list";
					} 
				})
			} else {
				var seat = $scope.seats2.find(s => s.id == item.seat.id);
				if(seat){
					var index = $scope.seats2.findIndex(s => s.id == item.seat.id);
					$scope.seats2.splice(index, 1);
					$scope.amount();
					var index2 = $scope.positions.findIndex(s => s.id == item.id);
					$scope.positions[index2].value = 1;
				} else {
					if($scope.seats2.length == 5){
						Swal.fire({
							icon: 'error',
							title: 'Thất bại',
							text: 'Bạn chỉ có thể chọn tối đa 5 ghế!'
						})
					} else{
						var index2 = $scope.positions.findIndex(s => s.id == item.id);
						$scope.positions[index2].value = 2;
						$scope.seats2.push(item.seat);
						$scope.amount();
					}
				}
			}
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Suất chiếu đã hết hạn!'
			})
		})
	}
	
	$scope.amount = function(){
		$scope.totalSeat = (($scope.seats2.length*$scope.show.priceShow.price) + ($scope.seats2.map(t => t.type.surcharge).reduce((total, surcharge) => total+=surcharge,0)));
	}
	
	$scope.previous = function(){
		var movieid = [];
		localStorage.setItem("movieID", movieid);
		var show = [];
		localStorage.setItem("show", show);
		var seat2 = [];
		var listSeats = JSON.stringify(seat2);
		localStorage.setItem("listSeats", listSeats);
		var totalSeat = [];
		var amountSeat = JSON.stringify(totalSeat);
		localStorage.setItem("amountSeat", amountSeat);
		location.href ="/home";
	}
	
	$scope.next = function(){
		if($scope.seats2.length == 0){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn ghế!'
			})
		} else {
			$scope.tickets = [];
			$http.get(`/rest/ticket/${$scope.show.id}`).then(resp => {
				$scope.tickets = resp.data;
				var check = null;
				for(var i = 0;i < $scope.seats2.length;i++){
					check = $scope.tickets.find(t => t.seat.id == $scope.seats2[i].id);
					if(check != null){
						i = $scope.seats2.length;
					}
				}
				if(check != null){
					Swal.fire({
						title: "Ghế đã được đặt! Vui lòng chọn lại ghế.",
						confirmButtonText: 'Xác nhận',
					}).then((result) => {
						if (result.isConfirmed) {
							$scope.seats2 = [];
							var listSeats = JSON.stringify($scope.seats2);
							localStorage.setItem("listSeats", listSeats);
							Swal.fire('Loading!', '', 'success')
							window.location.href = "/ticket/book-tickets";
						} 
					})
				} else {
					$http.get(`/rest/show/checkshow/${$scope.show.id}`).then(resp => {
						var checkshow = resp.data;
						if(checkshow.id == null){
							Swal.fire({
								title: "Suất chiếu đã hết giờ đặt vé!",
								confirmButtonText: 'Xác nhận',
							}).then((result) => {
								if (result.isConfirmed) {
									Swal.fire('Loading!', '', 'success')
									$scope.clearA();
									window.location.href = "/cinema/cinema-list";
								} 
							})
						} else {
							var listSeats = JSON.stringify($scope.seats2);
							localStorage.setItem("listSeats", listSeats);
							var amountSeat = JSON.stringify($scope.totalSeat);
							localStorage.setItem("amountSeat", amountSeat);
							location.href ="/ticket/bookfood";
						}
					}).catch(error => {
						Swal.fire({
							icon: 'error',
							title: 'Thất bại',
							text: 'Suất chiếu đã hết hạn!'
						})
					})
				}
			});
		}
	}
	
	$scope.getFood = function(){
		var show = localStorage.getItem("show");
		var item = show?JSON.parse(show):[];
		if(item == ""){
			$scope.show == [];
			Swal.fire({
				title: "Vui lòng chọn xuất chiếu!",
				confirmButtonText: 'Xác nhận',
			}).then((result) => {
				if (result.isConfirmed) {
					Swal.fire('Loading!', '', 'success')
					$scope.clearA();
					window.location.href = "/cinema/cinema-list";
				} 
			})
		} else{
			$scope.show = item;
			$http.get(`/rest/show/checkshow/${$scope.show.id}`).then(resp => {
				var checkshow = resp.data;
				if(checkshow.id == null){
					Swal.fire({
						title: "Suất chiếu đã hết giờ đặt vé!",
						confirmButtonText: 'Xác nhận',
					}).then((result) => {
						if (result.isConfirmed) {
							Swal.fire('Loading!', '', 'success')
							$scope.clearA();
							window.location.href = "/cinema/cinema-list";
						} 
					})
				} else {
					$scope.show = item;
					var listSeats = localStorage.getItem("listSeats");
					$scope.seats2 = listSeats?JSON.parse(listSeats):[];
					var amountSeat = localStorage.getItem("amountSeat");
					$scope.totalSeat = amountSeat?JSON.parse(amountSeat):[];
					$http.get(`/rest/food/Cinema/${item.room.cinema.id}`).then(resp =>{
			          	$scope.listFood = resp.data;
						for(var i=0;i<$scope.listFood.length;i++){
							if($scope.listFood[i].sale == null){
								var sale = {
									id: null,
									discount: 0
								}
								$scope.listFood[i].sale = sale;
							}
						}
					});
				}
			})
		}
	}
	
	$scope.cart = {
		add(item){
			for(var i=0;i<$scope.listFood.length;i++){
				if(item.food.id == $scope.listFood[i].food.id){
					if($scope.listFood[i].quantity >= 4){
						$scope.listFood[i].quantity = 4;
					} else{
						$scope.listFood[i].quantity++;	
					}
				}
			}
		},
		remove(item){
			for(var i=0;i<$scope.listFood.length;i++){
				if(item.food.id == $scope.listFood[i].food.id){
					if($scope.listFood[i].quantity <= 0){
						$scope.listFood[i].quantity = 0;
					} else{
						$scope.listFood[i].quantity--;	
					}
				}
			}
		},
		get amount(){
			return $scope.listFood.map(item => (item.quantity*item.food.price) - ((item.quantity*item.food.price*item.sale.discount)/100)).reduce((total, qty) => total+=qty,0);
		},
		get total(){
			return $scope.totalSeat + $scope.cart.amount;
		},
		previous(){
			if($scope.show.id == null){
				$scope.previous();
			} else {
				var listseat2 = [];
				var listSeats = JSON.stringify(listseat2);
				localStorage.setItem("listSeats", listSeats);
				var totalSeat = [];
				var amountSeat = JSON.stringify(totalSeat);
				localStorage.setItem("amountSeat", amountSeat);
				location.href ="/ticket/book-tickets";
			}
		},
		next(){
			$http.get(`/rest/show/checkshow/${$scope.show.id}`).then(resp => {
				var checkshow = resp.data;
				if (checkshow.id == null) {
					Swal.fire({
						title: "Suất chiếu đã hết giờ đặt vé!",
						confirmButtonText: 'Xác nhận',
					}).then((result) => {
						if (result.isConfirmed) {
							Swal.fire('Loading!', '', 'success')
							$scope.clearA();
							window.location.href = "/cinema/cinema-list";
						}
					})
				} else {
					$scope.tickets = [];
					$http.get(`/rest/ticket/${$scope.show.id}`).then(resp => {
						$scope.tickets = resp.data;
						var check = null;
						for(var i = 0;i < $scope.seats2.length;i++){
							check = $scope.tickets.find(t => t.seat.id == $scope.seats2[i].id);
							if(check != null){
								i = $scope.seats2.length;
							}
						}
						if(check != null){
							Swal.fire({
								title: "Ghế đã được đặt! Vui lòng chọn lại ghế.",
								confirmButtonText: 'Xác nhận',
							}).then((result) => {
								if (result.isConfirmed) {
									$scope.seats2 = [];
									var listSeats = JSON.stringify($scope.seats2);
									localStorage.setItem("listSeats", listSeats);
									Swal.fire('Loading!', '', 'success')
									window.location.href = "/ticket/book-tickets";
								} 
							})
						} else {
							var food = JSON.stringify($scope.listFood);
							localStorage.setItem("listFood", food);
							var amount = JSON.stringify($scope.cart.amount);
							localStorage.setItem("amount", amount);
							var total = JSON.stringify($scope.cart.total);
							localStorage.setItem("total", total);
							location.href ="/ticket/checkout";
						}
					});
				}
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: 'Suất chiếu đã hết hạn!'
				})
			})
		}
	}
	
	$scope.createPayment = function() {
		var show = localStorage.getItem("show");
		$scope.show = show?JSON.parse(show):[];
		var listSeats = localStorage.getItem("listSeats");
		$scope.seats2 = listSeats?JSON.parse(listSeats):[];
		var amountSeat = localStorage.getItem("amountSeat");
		$scope.amountSeat = amountSeat?JSON.parse(amountSeat):[];
		var foods = localStorage.getItem("listFood");
		$scope.foods = foods?JSON.parse(foods):[];
		var amount = localStorage.getItem("amount");
		$scope.amountFood = amount?JSON.parse(amount):[];
		var total = localStorage.getItem("total");
		$scope.total = total?JSON.parse(total):[];
	}
	
	$scope.couponCode = function(item){
		if($scope.buttonCoupon != null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Bạn đã sử dụng mã giảm giá.'
			})
		} else if(item == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập mã giảm giá.'
			})
		} else {
			$http.get(`/rest/coupons/${item}`).then(resp => {
				$scope.couponRequest = resp.data;
				console.log($scope.couponRequest);
				if($scope.couponRequest.message != null){
					Swal.fire({
						icon: 'error',
						title: 'Thất bại',
						text: $scope.couponRequest.message
					})
				} else if($scope.couponRequest.message == null) {
					$scope.coupon = $scope.couponRequest.coupon;
					$scope.amountSeat = ($scope.amountSeat - (($scope.amountSeat*$scope.coupon.discount)/100));
					$scope.total = $scope.amountSeat+$scope.amountFood;
					$scope.buttonCoupon = 0;
				}
			})
		}
	}
	
	$scope.choosePay = function(item){
		if($scope.payment == item){
			$scope.payment = null;
			$scope.bankCode = null;
		} else {
			$scope.payment = item;
		}
	}
	
	$scope.chooseBank = function(item){
		$scope.bankCode = item;
	}
	
	$scope.createOrder = function(){
		if($scope.show.id == null || $scope.seats2.length == 0){
			$scope.clear();
		} else if($scope.payment == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phương thức thanh toán.'
			})
		} else if($scope.bankCode == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn ngân hàng.'
			})
		} else{
			$http.get(`/rest/show/checkshow/${$scope.show.id}`).then(resp => {
				var checkshow = resp.data;
				if (checkshow.id == null) {
					Swal.fire({
						title: "Suất chiếu đã hết giờ đặt vé!",
						confirmButtonText: 'Xác nhận',
					}).then((result) => {
						if (result.isConfirmed) {
							Swal.fire('Loading!', '', 'success')
							$scope.clearA();
							window.location.href = "/cinema/cinema-list";
						}
					})
				} else {
					$scope.tickets = [];
					$http.get(`/rest/ticket/${$scope.show.id}`).then(resp => {
						$scope.tickets = resp.data;
						var check = null;
						for(var i = 0;i < $scope.seats2.length;i++){
							check = $scope.tickets.find(t => t.seat.id == $scope.seats2[i].id);
							if(check != null){
								i = $scope.seats2.length;
							}
						}
						if(check != null){
							Swal.fire({
								title: "Ghế đã được đặt! Vui lòng chọn lại ghế.",
								confirmButtonText: 'Xác nhận',
							}).then((result) => {
								if (result.isConfirmed) {
									$scope.seats2 = [];
									var listSeats = JSON.stringify($scope.seats2);
									localStorage.setItem("listSeats", listSeats);
									var totalSeat = [];
									var amountSeat = JSON.stringify(totalSeat);
									localStorage.setItem("amountSeat", amountSeat);
									
									var listFood = [];
									var food = JSON.stringify(listFood);
									localStorage.setItem("listFood", food);
									var amountFood = [];
									var amount = JSON.stringify(amountFood);
									localStorage.setItem("amount", amount);
									var total = JSON.stringify([]);
									localStorage.setItem("total", total);
									Swal.fire('Loading!', '', 'success')
									window.location.href = "/ticket/book-tickets";
								} 
							})
						} else {
							$scope.show.startTime = new Date("1974-03-12T"+$scope.show.startTime);
							$scope.show.endTime = new Date("1974-03-12T"+$scope.show.endTime);
							$scope.show.movie.runningTime =new Date("1974-03-12T"+$scope.show.movie.runningTime);
							var CreateOrderRequest = {
								show: $scope.show,
								seat: $scope.seats2,
								food: $scope.foods,
								coupon: $scope.coupon,
								amount: $scope.total,
								bank: $scope.bankCode
							}
							$http.post("/rest/orders/create", CreateOrderRequest).then(resp => {
								var urlRequest = resp.data;
								$scope.clearA();
								window.location.href =urlRequest.url;
							}).catch(error => {
								console.log("Error!", error)
							})
						}
					})
				}
			});
		}
	}
	
	$scope.clearA = function(){
		var show = [];
		localStorage.setItem("show", show);
		var seat2 = [];
		var listSeats = JSON.stringify(seat2);
		localStorage.setItem("listSeats", listSeats);
		var totalSeat = [];
		var amountSeat = JSON.stringify(totalSeat);
		localStorage.setItem("amountSeat", amountSeat);
		
		var listFood = [];
		var food = JSON.stringify(listFood);
		localStorage.setItem("listFood", food);
		var amountFood = [];
		var amount = JSON.stringify(amountFood);
		localStorage.setItem("amount", amount);
		var total = JSON.stringify([]);
		localStorage.setItem("total", total);
	}
	
	$scope.clear = function(){
		var movieid = [];
		localStorage.setItem("movieID", movieid);
		var show = [];
		localStorage.setItem("show", show);
		var seat2 = [];
		var listSeats = JSON.stringify(seat2);
		localStorage.setItem("listSeats", listSeats);
		var totalSeat = [];
		var amountSeat = JSON.stringify(totalSeat);
		localStorage.setItem("amountSeat", amountSeat);
		
		var listFood = [];
		var food = JSON.stringify(listFood);
		localStorage.setItem("listFood", food);
		var amountFood = [];
		var amount = JSON.stringify(amountFood);
		localStorage.setItem("amount", amount);
		var total = JSON.stringify([]);
		localStorage.setItem("total", total);
        window.location.href = "/home";
	}
	
	$scope.getOrder = function(){
		$http.get("/rest/orders/checkOrder").then(resp => {
			$scope.listOrder = resp.data;
			console.log($scope.listOrder)
		});
	}
	
	$scope.pager = {
		page: 0,
		size: 5,
		get items(){
			var start = this.page * this.size;
			return $scope.listOrder.slice(start, start + this.size);
		},
		get count(){
			//console.log("ItemL: "+ $scope.items.length/this.size);
			return Math.ceil(1.0 * ($scope.listOrder.length/this.size));
		},
		first(){
			this.page = 0
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){	
				this.first();
			}
		},
		last(){
			this.page = this.count - 1;
		}
	}
	
	$scope.checkOrder = function(item){
		$http.get(`/rest/orders/checkOrder/${item}`).then(resp => {
			var query = resp.data;
			if(query.code == '00'){
				Swal.fire({
					title: query.message,
					showDenyButton: true,
					confirmButtonText: 'Xác nhận',
				}).then((result) => {
					if (result.isConfirmed) {
						Swal.fire('Saved!', '', 'success')
						window.location.href = "/home/order";
					} 
				})
			} else if(query.code == '94') {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: query.message
				})
			} else {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: query.message
				})
			}
		});
	}
	
	$scope.getCities = function(){
		$http.get("/rest/city").then(resp => {
			$scope.citiesFoods = resp.data;
		})
	}
	
	$scope.getCinemas = function(item){
		$http.get(`/rest/cinemas/findByCity/${item.id}`).then(resp => {
			$scope.cinemasFoods = resp.data;
		})
	}
	
	$scope.getFoodsCinema = function(item){
		$scope.listFoodsCinema = [];
		$scope.cinemaInfo = {};
		$scope.cinemaInfo = item;
		$http.get(`/rest/food/Cinema/${item.id}`).then(resp => {
			$scope.listFoodsCinema = resp.data;
			for (var i = 0; i < $scope.listFoodsCinema.length; i++) {
				if ($scope.listFoodsCinema[i].sale == null) {
					var sale = {
						id: null,
						discount: 0
					}
					$scope.listFoodsCinema[i].sale = sale;
				}
			}
		});
	}
	
	$scope.cartFoods = {
		add(item){
			for(var i=0;i<$scope.listFoodsCinema.length;i++){
				if(item.food.id == $scope.listFoodsCinema[i].food.id){
					if($scope.listFoodsCinema[i].quantity >= 4){
						$scope.listFoodsCinema[i].quantity = 4;
					} else{
						$scope.listFoodsCinema[i].quantity++;	
					}
				}
			}
		},
		remove(item){
			for(var i=0;i<$scope.listFoodsCinema.length;i++){
				if(item.food.id == $scope.listFoodsCinema[i].food.id){
					if($scope.listFoodsCinema[i].quantity <= 0){
						$scope.listFoodsCinema[i].quantity = 0;
					} else{
						$scope.listFoodsCinema[i].quantity--;	
					}
				}
			}
		},
		clear(item){
			for(var i=0;i<$scope.listFoodsCinema.length;i++){
				if(item.food.id == $scope.listFoodsCinema[i].food.id){
					$scope.listFoodsCinema[i].quantity = 0;
				}
			}
		},
		get amount(){
			return $scope.listFoodsCinema.map(item => (item.quantity*item.food.price) - ((item.quantity*item.food.price*item.sale.discount)/100)).reduce((total, qty) => total+=qty,0);
		},
		clearAll(){
			for(var i=0;i<$scope.listFoodsCinema.length;i++){
				$scope.listFoodsCinema[i].quantity = 0;
			}
		},
		next(){
			if($scope.cartFoods.amount == 0){
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: 'Vui lòng chọn sản phẩm!'
				})
			} else {
				var food = JSON.stringify($scope.listFoodsCinema);
				localStorage.setItem("listFoodsCinema", food);
				var amount = JSON.stringify($scope.cartFoods.amount);
				localStorage.setItem("amountFoods", amount);
				location.href ="/food/checkout";	
			}
		}
	}
	
	$scope.createPaymentFood = function(){
		var food = localStorage.getItem("listFoodsCinema");
		$scope.listFoodsCinema = food?JSON.parse(food):[];
		
		var amount = localStorage.getItem("amountFoods");
		$scope.amountFoods = amount?JSON.parse(amount):[];
		if($scope.listFoodsCinema.length <= 0 || $scope.amountFoods == null || $scope.amountFoods == 0){
			var food = JSON.stringify([]);
			localStorage.setItem("listFoodsCinema", food);
			var amount = JSON.stringify([]);
			localStorage.setItem("amountFoods", amount);
			window.location.href="/food/booking-food"
		} else {
			$scope.listFoodsCinema = [];
			var food = localStorage.getItem("listFoodsCinema");
			$scope.listFoodsCinema = food?JSON.parse(food):[];
			$scope.amountFoods = null;
			var amount = localStorage.getItem("amountFoods");
			$scope.amountFoods = amount?JSON.parse(amount):[];
		}
	}
	
	$scope.createOrderFood = function(){
		if($scope.payment == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phương thức thanh toán.'
			})
		} else if($scope.bankCode == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn ngân hàng.'
			})
		} else if($scope.listFoodsCinema.length <= 0 || $scope.amountFoods == null || $scope.amountFoods == 0){
			var food = JSON.stringify([]);
			localStorage.setItem("listFoodsCinema", food);
			var amount = JSON.stringify([]);
			localStorage.setItem("amountFoods", amount);
			window.location.href="/food/booking-food"
		} else {
			var CreateOrderFoodRequest = {
				food: $scope.listFoodsCinema,
				amount: $scope.amountFoods,
				bank: $scope.bankCode
			}
			$http.post("/rest/orders/createOrderFood", CreateOrderFoodRequest).then(resp => {
				var urlRequest = resp.data;
				window.location.href = urlRequest.url;
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	
	$scope.checkUrl = function(){
		var url = window.location.href;
		if(url == "http://localhost:8080/home"){
			var movieid = [];
			localStorage.setItem("movieID", movieid);
			$scope.clearA ();
		} else if(url == "http://localhost:8080/cinema/cinema-list"){
			$scope.accountLogin();
			$scope.initialize();
		} else if(url == "http://localhost:8080/ticket/book-tickets") {
			$scope.accountLogin();
			var seat2 = [];
			var listSeats = JSON.stringify(seat2);
			localStorage.setItem("listSeats", listSeats);
			var totalSeat = [];
			var amountSeat = JSON.stringify(totalSeat);
			var listFood = [];
			var food = JSON.stringify(listFood);
			localStorage.setItem("listFood", food);
			var amountFood = [];
			var amount = JSON.stringify(amountFood);
			localStorage.setItem("amount", amount);
			var total = JSON.stringify([]);
			localStorage.setItem("total", total);
			$scope.getSeats();
		} else if(url == "http://localhost:8080/ticket/bookfood"){
			$scope.accountLogin();
			var listFood = [];
			var food = JSON.stringify(listFood);
			localStorage.setItem("listFood", food);
			var amountFood = [];
			var amount = JSON.stringify(amountFood);
			localStorage.setItem("amount", amount);
			var total = JSON.stringify([]);
			localStorage.setItem("total", total);
			$scope.getFood();
		} else if(url == "http://localhost:8080/ticket/checkout"){
			$scope.accountLogin();
			$scope.createPayment();
		} else if(url == "http://localhost:8080/home/order"){
			$scope.accountLogin();
			$scope.clearA ();
			$scope.getOrder();
		} else if(url == "http://localhost:8080/food/booking-food"){
			var food = JSON.stringify([]);
			localStorage.setItem("listFoodsCinema", food);
			var amount = JSON.stringify([]);
			localStorage.setItem("amountFoods", amount);
			$scope.getCities();
		} else if(url == "http://localhost:8080/food/checkout"){
			$scope.accountLogin();
			$scope.createPaymentFood();
		} else {
			$scope.clearA ();
		}
	}
	
	$scope.checkUrl();
	
});