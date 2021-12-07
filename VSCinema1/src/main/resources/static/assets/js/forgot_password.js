const app = angular.module("forgot-app", []);
app.controller("forgot-ctrl", function($scope, $http){
	
	$scope.accounts =[];
	$scope.username="";
	$scope.email="";
		
	$scope.initialize = function(){
		$http.get("/rest/users/forgot").then(resp => {
			$scope.accounts = resp.data;
		})
	}
	
	$scope.forgot = function(){
		var account = $scope.accounts.find(acc => acc.username == $scope.username);
		if(!account){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Tài khoản không tồn tại!'
			})
		} else if($scope.email != account.email){
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Email không chính xác!'
			})
		} else {
			var tokenRequest = {
				user: account,
				password: "",
				expiry: new Date(),
				category: 1,
				activity: true,
			}
	
			$http.post("/rest/tokens/forgot", tokenRequest).then(resp => {
					location.href="/security/token/forgot"; 
			}).catch(error => {
					alert("Lỗi!")
					console.log(error)
			})
		}
	}
	
	$scope.initialize();
	
})