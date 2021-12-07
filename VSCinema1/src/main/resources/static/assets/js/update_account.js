const app = angular.module("update-app", []);
app.controller("update-ctrl", function($scope, $http){
	
	$scope.account={};
	$scope.fullname="";
	
	$scope.initialize = function(){
		$http.get("/rest/users/update").then(resp => {
			$scope.account = resp.data;
			$scope.fullname= $scope.account.fullname;
		});
	}
	
	$scope.update = function(){
		if($scope.account.fullname == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập họ và tên!'
				})
		} else if($scope.account.email == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập Email!'
				})
		} else if($scope.account.phone == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập số điện thoại!'
				})
		} else if($scope.account.cmnd == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập số CMND!'
				})
		} else if($scope.account.address == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập địa chỉ!'
				})
		} else {
			$http.put("/rest/users/update", $scope.account).then(resp => {
				Swal.fire({
					  position: 'center',
					  icon: 'success',
					  title: 'Cập nhật thông tin tài khoản thành công!',
					  showConfirmButton: false,
					  timer: 1500
					})
			}).catch(error => {
				alert("Lỗi");
				console.log("Error", error)
			})
		}
	}
	
	$scope.initialize();
	
})