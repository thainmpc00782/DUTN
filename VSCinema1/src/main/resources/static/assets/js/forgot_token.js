const app = angular.module("tokenForgot-app", []);
app.controller("tokenForgot-ctrl", function($scope, $http){
	
	$scope.token={};
	$scope.code="";
	
	$scope.initialize = function(){
		$http.get("/rest/tokens/forgot").then(resp => {
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
				$http.put("/rest/users/forgot", $scope.token).then(resp => {
					Swal.fire({
						  position: 'top-end',
						  icon: 'success',
						  title: 'Mật khẩu đã được gửi vào email của bạn!',
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