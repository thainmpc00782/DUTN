app.controller("censor-ctrl", function($scope, $http) {
	
	$scope.items = [];
	$scope.censors = [];
	$scope.show ={};
	$scope.account = {};
	$scope.buttonUpdate = true;
	$scope.buttonDelete = true;
	$scope.cinemas = [];
	$scope.cities = [];
	$scope.city ={};
	$scope.search = {};
	
	
	$scope.initialize = function() {
	/*	$http.get("/rest/show").then(resp => {
			$scope.items = resp.data;
		}); */
		
		$http.get("/rest/users/account").then(resp => {
			$scope.account = resp.data;
			if($scope.account.username == null){
				location.href="/security/login/form"
			}
		});
		
		$http.get("/rest/show/censor").then(resp => {
			$scope.items = resp.data;
		});
		
		$http.get("/rest/censors").then(resp => {
			$scope.censors = resp.data;
		});
		
		$http.get('/rest/city').then(resp => {
			$scope.cities = resp.data
		})
	}
	
	$scope.findRapFromCity = function(){
		var cityid = $scope.city.id;
		if(cityid == null || cityid == ""){
			$scope.cinemas = [];
			$scope.search = {};
			$scope.initialize();
		} else {
			$http.post(`/rest/cinemas/findByCity/` + cityid).then(resps => {
				$scope.cinemas = resps.data
				console.log($scope.cinemas)
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	$scope.reset = function(){
		$scope.show ={};
		$scope.buttonUpdate = true;
		$scope.buttonDelete = true;
		$scope.initialize();
	}
	
	$scope.edit = function(item){
		$scope.show = item;
		if(item.activity == 1){
			$scope.buttonUpdate = true;
			$scope.buttonDelete = true;
		} else if(item.activity == 2){
			$scope.buttonUpdate = false;
			$scope.buttonDelete = false;
		} else {
			$scope.buttonUpdate = false;
			$scope.buttonDelete = false;
		}
		$(".nav-tabs a:eq(0)").tab('show');
	}
	
	$scope.update = function(){
		if($scope.show.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn suất chiếu!'
			})
		} else {
			Swal.fire({
				title: 'Bạn có chắc không?',
				text: "Bạn muốn duyệt suất chiếu này!",
				icon: 'warning',
				showCancelButton: true,
				cancelButtonText: 'Hủy',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Xác nhận!'
			}).then((result) => {
				if (result.isConfirmed) {
					$scope.show.activity = 3;
					$scope.show.startTime = new Date("1974-03-12T"+$scope.show.startTime);
					$scope.show.endTime = new Date("1974-03-12T"+$scope.show.endTime);
					$scope.show.movie.runningTime = new Date("1974-03-12T"+$scope.show.movie.runningTime);
					var show = $scope.show;
					console.log(show);
					$http.put(`/rest/show`, show).then(resp => {
						Swal.fire({
							icon: 'success',
							title: 'Suất chiếu đã được duyệt!',
							showConfirmButton: false,
							timer: 1500
						})
						var censor = {
							createDate: new Date(),
							status: 3,
							show: show,
							user: $scope.account
						}
						$http.post(`/rest/censors`, censor).then(resp => {
							
						}).catch(error => {
							console.log("error", error)
						}); 
						$scope.initialize();
						$scope.reset()
					}).catch(error => {
						console.log("error", error)
						Swal.fire({
							icon: 'error',
							title: 'Duyệt suất chiếu thất bại!',
							showConfirmButton: false,
							timer: 1500
						})
					}); 
				}
			})
		}
	}
	
	$scope.delete = function(){
		if($scope.show.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn suất chiếu!'
			})
		} else if($scope.show.activity == 3){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Không thể hủy suất chiếu đã duyệt!'
			})
		} else {
			Swal.fire({
				title: 'Bạn có chắc không?',
				text: "Bạn muốn hủy suất chiếu này!",
				icon: 'warning',
				showCancelButton: true,
				cancelButtonText: 'Hủy',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Xác nhận!'
			}).then((result) => {
				if (result.isConfirmed) {
					$scope.show.activity = 2;
					$scope.show.startTime = new Date("1974-03-12T"+$scope.show.startTime);
					$scope.show.endTime = new Date("1974-03-12T"+$scope.show.endTime);
					$scope.show.movie.runningTime = new Date("1974-03-12T"+$scope.show.movie.runningTime);
					var show = $scope.show;
					console.log(show);
					$http.put(`/rest/show`, show).then(resp => {
						Swal.fire({
							icon: 'success',
							title: 'Suất chiếu đã được hủy!',
							showConfirmButton: false,
							timer: 1500
						})
						
						var censor = {
							createDate: new Date(),
							status: 2,
							show: show,
							user: $scope.account	
						}
						$http.post(`/rest/censors`, censor).then(resp => {
							
						}).catch(error => {
							console.log("error", error)
						}); 
						$scope.initialize();
						$scope.reset()
					}).catch(error => {
						console.log("error", error)
						Swal.fire({
							icon: 'error',
							title: 'Hủy suất chiếu thất bại!',
							showConfirmButton: false,
							timer: 1500
						})
					}); 
				}
			})
		}
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size; 
			return $scope.items.slice(start, start + this.size);
		},

		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},

		first() {
			this.page = 0;
		},

		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},

		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},

		last() {
			this.page = this.count - 1;
		},
	}
	
	$scope.pager2 = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size; 
			return $scope.censors.slice(start, start + this.size);
		},

		get count() {
			return Math.ceil(1.0 * $scope.censors.length / this.size);
		},

		first() {
			this.page = 0;
		},

		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},

		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},

		last() {
			this.page = this.count - 1;
		},
	}
	
	$scope.initialize();
});