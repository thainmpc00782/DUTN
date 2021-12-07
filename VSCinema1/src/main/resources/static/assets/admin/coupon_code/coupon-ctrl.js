app.controller("coupon-ctrl", function($scope, $http, $location){
	
	$scope.coupons = [];
	$scope.id ="";
	$scope.date = new Date();
	$scope.coupon = {
		id: "",
		createdate: new Date(),
		expiry: new Date(),
		discount: 0.0,
		quantity: 0,
		activity: true
	};
	$scope.code = {};
	
	$scope.initialize = function(){
		$http.get("/rest/coupons").then(resp => {
			$scope.coupons = resp.data;
		})
	}
	
	$scope.autoCreate = function(){
		$http.get("/rest/coupons/autoCreate").then(resp => {
			$scope.code = resp.data;
			$scope.coupon.id = $scope.code.code;
		})
	}
	
	$scope.edit = function(item){
		$scope.coupon = item;
		$scope.id = item.id;
		$scope.coupon.expiry = new Date(item.expiry);
		$(".nav-tabs a:eq(0)").tab('show');
	}
	
	$scope.create = function(){
		var item = $scope.coupons.find(cp => cp.id==$scope.coupon.id);
		if(item){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Mã giảm giá đã tồn tại!'
			})
		} else if($scope.coupon.id == ""){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập mã giảm giá!'
			})
		} else if($scope.coupon.createdate > $scope.coupon.expiry){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Hạn sử dụng không được nhỏ hơn ngày tạo!'
			})
		} else if($scope.coupon.discount == 0){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập Discount!'
			})
		} else if($scope.coupon.quantity == 0){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập số lần nhập mã!'
			})
		} else {
			$http.post("/rest/coupons", $scope.coupon).then(resp => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Tạo mã giảm giá thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.initialize();
			}).catch(error => {
				alert("lỗi!")
				console.log(error)
			})
		}
	}
	
	$scope.update = function(){
		$scope.coupon.id = $scope.id;
		if($scope.coupon.id == ""){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn mã cần cập nhật!'
			})
			$scope.initialize();
		} else if($scope.coupon.expiry < $scope.date){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Hạn sử dụng không được nhỏ hơn ngày hiện tại!'
			})
			$scope.initialize();
		} else if($scope.coupon.createdate > $scope.coupon.expiry){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Hạn sử dụng không được nhỏ hơn ngày tạo!'
			})
			$scope.initialize();
		} else if($scope.coupon.discount == 0){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập Discount!'
			})
			$scope.initialize();
		} else if($scope.coupon.quantity == 0){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập số lần nhập mã!'
			})
			$scope.initialize();
		} else {
			$http.put("/rest/coupons", $scope.coupon).then(resp => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Cập nhật thông tin mã giảm giá thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.initialize();
			}).catch(error => {
				alert("lỗi!")
				console.log(error)
			})
		}
	}
	
	$scope.reset = function(){
		$scope.coupon = {
			id: "",
			createdate: new Date(),
			expiry: new Date(),
			discount: 0.0,
			quantity: 0,
			activity: true
		};
		$scope.id = "";
	}
	
	$scope.pager = {
		page: 0,
		size: 10,
		get items(){
			var start = this.page * this.size;
			return $scope.coupons.slice(start, start + this.size);
		},
		get count(){
			return Math.ceil(1.0 * ($scope.coupons.length/this.size));
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
	
	$scope.initialize();
	
})