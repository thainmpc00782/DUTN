app.controller("cinema-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.cities={};
	$scope.form = {};
	$scope.isEdit = undefined;
	$scope.isLoad = undefined;
	
	$scope.findCinemaByCity = function() {
		var cityid=$scope.form.city.id;
		url=`/rest/cinemas/findByCity/`+cityid
		if(cityid=""){
			$scope.cinemas = [];
		}else{
			$http.get(url).then(resp => {
				$scope.items = resp.data;
			}).catch(error=>{
				console.log(error)
			});
		}
	}
		
	$scope.initialize = function() {
		// load cinema
		$http.get("/rest/cinemas").then(resp => {
			$scope.items = resp.data;
		});
		
		$scope.isLoad = true;
	}
	
	// start
	$scope.initialize();

	// load city
	$http.get("/rest/city").then(resp => {
		$scope.city = resp.data;
	});

	// refresh form
	$scope.reset = function() {
		$scope.form = {
		}
		$scope.isEdit = false;
		$scope.isLoad = true;
	}

	// fill to form cinema

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');
		$scope.isEdit = true;
		$scope.isLoad = false;
	}


	// create cinema
	$scope.create = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của cinema
		var cname = document.getElementById("name").value;
		var caddress = document.getElementById("address").value;
		var ccity = document.getElementById("city").value;
		if (cname == "" || cname == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên rạp phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (caddress == "" || caddress == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng nhập địa chỉ !',
				width: '500px',
				heightAuto: true
			})
		} else if (ccity == "" || ccity == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng chọn thành phố !',
				width: '500px',
				heightAuto: true
			})
		} else {
			$http.post(`/rest/cinemas`, item).then(resp => {
				$scope.items.push(resp.data);
				$scope.reset();
				Swal.fire({
					icon: 'success',
					title: 'Thành công !',
					text: 'Thêm rạp phim thành công !',
					width: '500px',
					heightAuto: true
				})

				$scope.initialize();
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại !',
					text: 'Thêm rạp phim thất bại !',
					width: '500px',
					heightAuto: true
				})
			});
		}
	}

	// update cinema
	$scope.update = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của cinema
		var cname = document.getElementById("name").value;
		var caddress = document.getElementById("address").value;
		var ccity = document.getElementById("city").value;
		if (cname == "" || cname == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên rạp phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (caddress == "" || caddress == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng nhập địa chỉ !',
				width: '500px',
				heightAuto: true
			})
		} else if (ccity == "" || ccity == null) {
			Swal.fire({
				icon: 'warning',
				title: 'Thất bại !',
				text: 'Vui lòng chọn thành phố !',
				width: '500px',
				heightAuto: true
			})
		} else {
			Swal.fire({
				title: 'Cập nhật rạp phim',
				icon: 'question',
				text: "Bạn có muốn cập nhật rạp phim không ? ",
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Có'
			}).then((result) => {
				if (result.isConfirmed) {
					$http.put(`/rest/cinemas`, item).then(resp => {
						var index = $scope.items.findIndex(p => p.id == item.id); // tìm cinema trong csdl và thay đổi thông tin
						$scope.items[index] = item;
						Swal.fire({
							icon: 'success',
							title: 'Thành công !',
							text: 'Cập nhật rạp phim thành công !',
							width: '500px',
							heightAuto: true
						})
					}).catch(error => {
						Swal.fire({
							icon: 'error',
							title: 'Thất bại !',
							text: 'Cập nhật rạp phim thất bại !',
							width: '500px',
							heightAuto: true
						})
						console.log("Error", error);
					});
				}
			})
		}
	}

	// remove cinema
	/* $scope.delete = function(item) {
		Swal.fire({
			title: 'Xóa rạp phim',
			text: "Bạn có muốn xóa rạp phim không ? ",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Có'
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete(`/rest/cinema/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id); // tìm cinema trong csdl và thay đổi thông tin
					$scope.items.splice(index, 1); // xóa phần tử tại index - xóa 1 phần tử
					$scope.reset();
					Swal.fire({
						icon: 'success',
						title: 'Thành công !',
						text: 'Xóa rạp phim thành công !',
						width: '500px',
						heightAuto: true
					})
				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Thất bại !',
						text: 'Xóa rạp phim thất bại !',
						width: '500px',
						heightAuto: true
					})
					console.log("Error", error);
				});
			}
		})
	}
	*/

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size; // vị trí cinema bắt đầu lấy
			return $scope.items.slice(start, start + this.size); // Tách những cinema đang xem
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
});