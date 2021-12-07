const app = angular.module("change-app", []);
app.controller("change-ctrl", function($scope, $http){
	
	$scope.account={};
	$scope.oldPassword="";
	$scope.newPassword="";
	$scope.confirmPassword="";	
		
	$scope.initialize = function(){
		$http.get("/rest/users/change").then(resp => {
			$scope.account = resp.data;
		}).catch(error => {
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng đăng nhập!'
			})
		})
	}
	
	$scope.change = function(){
		if($scope.oldPassword == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập mật khẩu hiện tại!'
			})
		} else if($scope.newPassword == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập mật khẩu mới!'
			})
		} else if($scope.confirmPassword == ""){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập xác nhận mật khẩu!'
			})
		} else if($scope.oldPassword != $scope.account.password){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Mật khẩu hiện tại không chính xác!'
			})
		} else if($scope.newPassword != $scope.confirmPassword){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Xác nhận mật khẩu không đúng!'
			})
		} else {
			var tokenRequest = {
				user: $scope.account,
				password: $scope.newPassword,
				expiry: new Date(),
				category: 0,
				activity: true,
			}

			$http.post("/rest/tokens/change", tokenRequest).then(resp => {
				location.href="/security/token/change"; 
			}).catch(error => {
				alert("Lỗi!")
				console.log(error)
			})
			
		}
	}
	
	$scope.initialize();
	
})