const app = angular.module("token-app", []);
app.controller("token-ctrl", function($scope, $http){
	
	$scope.token={};
	$scope.code="";
	
	$scope.initialize = function(){
		$http.get("/rest/tokens/change").then(resp => {
			$scope.token = resp.data;
			console.log($scope.token);
		})
	}
	
	$scope.submit = function(){
		if($scope.code == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập mã xác nhận!'
			})
			$scope.initialize();
		} else {
			if($scope.code != $scope.token.code){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Mã xác nhận không chính xác!'
				})
				$scope.initialize();
			} else {
				$http.put("/rest/users/change", $scope.token).then(resp => {
					Swal.fire({
						  position: 'top-end',
						  icon: 'success',
						  title: 'Đổi mật khẩu thành công!',
						  showConfirmButton: false,
						  timer: 1500
					})
					location.href="/security/login/form"			
				}).catch(error => {
					alert("Lỗi");
					console.log("Error", error)
				})
			}
		}
	}
	
	$scope.initialize();
	
})