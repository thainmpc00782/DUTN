app.controller('sale-ctrl', function ($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.foods = [];
	$scope.check = {};
	
	$scope.initialize = function () {
		// load products
		$http.get('/rest/sale').then(  response => {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.endDate = new Date(item.endDate)
			})
			console.log($scope.items);
		}).catch(error=>{
			console.log(error);
		});
		$http.get('/rest/food').then(function (response) {
			$scope.foods = response.data;
			$scope.reset();
		});
	}
	

	$scope.reset = function () {
		var today = new Date();
		today.setDate(today.getDate() + 5)
		$scope.form = {
			startDate: new Date(),
			 food: { image: 'baprangbo.jpg'},
			image: 'baprangbo.jpg',
			endDate : today, 
		};
		
	}
	// Hiển thị sản phẩm lên form
	$scope.editSale = function (item) {
		$scope.reset();
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');
		console.log($scope.form);
	}
	$scope.editFood = function (item) {
		$scope.reset();
		$scope.form ={
			food : {
				id : item.id,
				name : item.name,
				price : item.price,
				image : item.image,
			},
			startDate : new Date()
		}
		$(".nav-tabs a:eq(0)").tab('show');
		console.log($scope.form);
	}
	// Cập nhật sản phẩm 

	$scope.update = function () {
		var item = angular.copy($scope.form);
		if($scope.form.endDate == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Không thể để trống ngày kết thúc',
				width: '500px',
				heightAuto: true
			})
		}else if($scope.form.discount == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Không thể để trống khuyến mãi',
				width: '500px',
				heightAuto: true
			})
		}else{
			$http.put(`/rest/sale/${item.id}`, item).then(function (response) {
				var index = $scope.items.findIndex(p => p.id == item.id);
				$scope.items[index] = item;
				Swal.fire({
					icon: 'success',
					title: 'Thành công',
					text: 'Cập nhật thành công',
					width: '500px',
					heightAuto: true
				})
				$scope.reset();
			}).catch(function (err) {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: 'Lỗi cập nhật khuyến mãi',
					width: '500px',
					heightAuto: true
				})
				console.log("Erorr", err);
			})
		}


	}
	// Thêm sản phẩm 
	$scope.create = function () {
		var item = $scope.form;
		console.log(item)
		var endDate = $scope.form.endDate;
		var startDate = $scope.form.startDate;
		if(endDate.getTime() < startDate.getTime()){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Ngày kết thúc không thể nhỏ hơn ngày bắt đầu',
				width: '500px',
				heightAuto: true
			})
		}else if($scope.form.endDate == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Không thể để trống ngày kết thúc',
				width: '500px',
				heightAuto: true
			})
		}else if($scope.form.discount == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Không thể để trống khuyến mãi',
				width: '500px',
				heightAuto: true
			})
		}else{
			$http.post('/rest/sale', item).then(function (response) {
				Swal.fire({
					icon: 'success',
					title: 'Thành công',
					text: 'Thêm mới thành công',
					width: '500px',
					heightAuto: true
				})
				$scope.initialize();
				$scope.reset();
					
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại',
					text: 'Lỗi thêm mới khuyến mãi',
					width: '500px',
					heightAuto: true
				})
				console.log("Erorr", error);
			})
		}

	}

	// Xóa sản phẩm 
	$scope.delete = function (item) {
		$http.delete(`/rest/sale/${item.id}`).then(function (response) {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert('Xóa thành công');
			location.reload()
		}).catch(function (err) {
			alert('Lỗi xóa sản phẩm');
			console.log("Erorr", err);
		})
	}

	$scope.imageChange = function (files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.entity,
			headers: {
				'Content-Type': undefined
			}
		}).then(response => {
			$scope.form.image = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
			console.log("Erorr", err);
		})

	}

	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length
				/ this.size);
		},
		first() {
			this.page = 0;

		},
		pre() {
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
		size: 5,
		get foods() {
			var start = this.page * this.size;
			return $scope.foods.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.foods.length
				/ this.size);
		},
		first() {
			this.page = 0;

		},
		pre() {
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
	$scope.reset();
});