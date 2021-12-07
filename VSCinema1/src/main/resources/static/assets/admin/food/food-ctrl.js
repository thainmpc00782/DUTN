/**
 * 
 */

app.controller("food-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.isEdit = undefined;
	$scope.isLoad = undefined;

    /*$scope.index = 0 ;*/

	$scope.initialize = function() {
		// load food
		$http.get("/rest/food").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createdate = new Date(item.createdate)
			});
		});

		// load city
		$http.get("/rest/city").then(resp => {
			$scope.city = resp.data;
		});

		// load cinema
		$http.get("/rest/cinemas").then(resp => {
			$scope.cinema = resp.data;
		});
		$scope.isLoad = true;
	}

	// Khởi đầu
	$scope.initialize();


	// Xóa form
	$scope.reset = function() {
		$scope.form = {
			createdate: new Date(),
			image: 'upload.png',
			active: true
		}
		$scope.isEdit = false;
		$scope.isLoad = true;
		
		/*$scope.index = 0;*/
	}

	$scope.reset();

	// Load Cinema theo City
	$scope.findCinemaFromCity = function() {
		var cityid = document.getElementById('city').value;
		$http.post(`/rest/cinemas/findCinemaByCity/` + cityid).then(resps => {
			$scope.cinema = resps.data;
			console.log(resps.data);
		}).catch(error => {
			console.log("Error!" + error);
		})
	}

	// Load City theo Cinema
	/*$scope.findCityFromCinema = function() {
		var cinemaid = document.getElementById('cinema').value;
		$http.post(`/rest/cinemas/findCityByCinema/` + cinemaid).then(resps => {
			$scope.city = resps.data;
			console.log(resps.data);
		}).catch(error => {
			console.log("Error!" + error);
		})
	}*/
	
	// Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');
		$scope.isEdit = true;
		$scope.isLoad = false;
		$http.post(`/rest/cinemas/findCinemaByCity/` + $scope.form.cinema.city.id).then(resps => {
			$scope.cinema = resps.data;
			console.log(resps.data);
		}).catch(error => {
			console.log("Error!" + error); 
		})
		
		/*$scope.index = 1;*/
	}



	// Thêm food mới
	$scope.create = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của food
		var fname = document.getElementById("name").value;
		var fcity = document.getElementById('city').value;
		var fcinema = document.getElementById("cinema").value;
		var fprice = document.getElementById("price").value;
		var fdescription = document.getElementById("description").value;

		var fpriceInt = parseInt(fprice, 10); // Convert Price from String to Integer
		//var fpriceRegex = /^\d+$/;

		console.log("Fỏmm: " + $scope.form);
		if (fname == "" || fname == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên sản phẩm !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcity == "" || fcity == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn Tỉnh/Thành phố !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcinema == "" || fcinema == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn Rạp phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fprice == "" || fprice == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập Giá sản phẩm !',
				width: '500px',
				heightAuto: true
			})

		} else if (fpriceInt <= 0) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Giá sản phẩm phải lớn hơn 0 !',
				width: '500px',
				heightAuto: true
			})
		} else if (isNaN(fprice)) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Giá sản phẩm phải là số nguyên dương lớn hơn 0 !',
				width: '500px',
				heightAuto: true
			})
		} else if (fdescription == "" || fdescription == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập Mô tả sản phẩm !',
				width: '500px',
				heightAuto: true
			})
		} else {
			$http.post(`/rest/food`, item).then(resp => {

				resp.data.createdate = new Date(resp.data.createdate); // sau khi server trả dữ liệu về -> chuyển ngày sang javascript
				$scope.items.push(resp.data);
				console.log("Foood demo : " + resp.data);
				$scope.item = resp.data;
				$scope.reset();
				Swal.fire({
					icon: 'success',
					title: 'Thành công !',
					text: 'Thêm sản phẩm thành công !',
					width: '500px',
					heightAuto: true
				})
				// Khởi tạo priceHistory, lấy thông tin food từ form, khởi tạo time
				var priceHistoryRequest = {
					food: resp.data,
					price: item.price,
					date: new Date()
				};

				console.log(item.id);
				// gọi về server // tạo priceHistory
				$http.post(`/rest/priceHistory`, priceHistoryRequest).then(resp => {
				}).catch(error => {

				});

			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại !',
					text: 'Thêm sản phẩm thất bại !',
					width: '500px',
					heightAuto: true
				})
				console.log("Error", error);
			});


		}
	}

	// Cập nhật sản phẩm mới
	$scope.update = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của sp
		var fname = document.getElementById("name").value;
		var fcity = document.getElementById('city').value;
		var fcinema = document.getElementById("cinema").value;
		var fprice = document.getElementById("price").value;
		var fdescription = document.getElementById("description").value;

		var fpriceInt = parseInt(fprice, 10); // Convert Price from String to Integer

		if (fname == "" || fname == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên sản phẩm !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcity == "" || fcity == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn Tỉnh/Thành phố !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcinema == "" || fcinema == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn Rạp phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fprice == "" || fprice == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập Giá sản phẩm !',
				width: '500px',
				heightAuto: true
			})

		} else if (fpriceInt <= 0) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Giá sản phẩm phải lớn hơn 0 !',
				width: '500px',
				heightAuto: true
			})
		} else if (isNaN(fprice)) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Giá sản phẩm phải là số nguyên dương lớn hơn 0 !',
				width: '500px',
				heightAuto: true
			})
		} else if (fdescription == "" || fdescription == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập Mô tả sản phẩm !',
				width: '500px',
				heightAuto: true
			})
		} else {
			Swal.fire({
				title: 'Cập nhật Sản phẩm !',
				text: "Bạn có muốn cập nhật sản phẩm này không ? ",
				icon: 'question',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Có'
			}).then((result) => {
				if (result.isConfirmed) {
					$http.put(`/rest/food`, item).then(resp => {
						var index = $scope.items.findIndex(p => p.id == item.id); // tìm sp trong csdl và thay đổi thông tin
						resp.data.createdate = new Date(resp.data.createdate)
						$scope.items[index] = item;
						Swal.fire({
							icon: 'success',
							title: 'Thành công !',
							text: 'Vui lòng nhập tên sản phẩm !',
							width: '500px',
							heightAuto: true
						})
					}).catch(error => {
						Swal.fire({
							icon: 'error',
							title: 'Thất bại !',
							text: 'Cập nhật sản phẩm thất bại !',
							width: '500px',
							heightAuto: true
						})
						console.log("Error", error);
					});

					// Khởi tạo priceHistory, lấy thông tin food từ form, khởi tạo time
					var priceHistoryRequest = {
						food: item,
						price: item.price,
						date: new Date()
					};
					// gọi về server // tạo priceHistory
					$http.post(`/rest/priceHistory`, priceHistoryRequest).then(resp => {
					}).catch(error => {

					});
				}
			})
		}
	}


	// Xóa sản phẩm
	/* $scope.delete = function(item) {
		Swal.fire({
			title: 'Xóa Sản phẩm !',
			text: "Bạn có muốn Xóa sản phẩm này không ? ",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Có'
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete(`/rest/food/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id); // tìm sp trong csdl và thay đổi thông tin
					$scope.items.splice(index, 1); // xóa phần tử tại index - xóa 1 phần tử
					$scope.reset();
					Swal.fire({
						icon: 'success',
						title: 'Thành công !',
						text: 'Xóa sản phẩm thành công !',
						width: '500px',
						heightAuto: true
					})
				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Thất bại',
						text: 'Xóa sản phẩm thất bại !',
						width: '500px',
						heightAuto: true
					})
					console.log("Error", error);
				});
			}
		})
	}
	*/

	// Upload hình
	$scope.imageChanged = function(files) {
		var data = new FormData(); // tạo form data để chứa ảnh đã chọn
		data.append('file', files[0]); // bỏ ảnh vào form data
		$http.post('/rest/upload/foods/foods', data, {
			transformRequest: angular.identity, headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Lỗi upload hình ảnh !',
				width: '500px',
				heightAuto: true
			})
			console.log("Error", error);
		});
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size; // vị trí sản phẩm bắt đầu lấy
			return $scope.items.slice(start, start + this.size); // Tách những sản phẩm đang xem
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