app.controller("price-ctrl", function($scope, $http, $location) {
	
	$scope.priceShows=[];
	$scope.price = {
		id: "",
		price: 0,
		createDate: new Date(),
		expiry: new Date(),
		activity: true
	};
	
	$scope.initialize = function(){
		$http.get("/rest/price_show").then(resp => {
			$scope.priceShows = resp.data;
		})
	}
	
	$scope.edit = function(item){
		$scope.price = item;
		$(".nav-tabs a:eq(0)").tab('show');
	}
	
	$scope.create = function(){
		$http.post("/rest/price_show", $scope.price).then(resp => {
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Tạo giá bán thành công!',
				showConfirmButton: false,
				timer: 1500
			})
			$scope.initialize();
		}).catch(error => {
			alert("lỗi!")
			console.log(error)
		})
	}
	
	$scope.update = function(){
		$http.put("/rest/price_show", $scope.price).then(resp => {
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Cập nhật thông tin giá bán thành công!',
				showConfirmButton: false,
				timer: 1500
			})
			$scope.initialize();
		}).catch(error => {
			alert("lỗi!")
			console.log(error)
		})
	}
	
	$scope.initialize();
})
