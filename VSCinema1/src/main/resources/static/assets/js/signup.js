const app = angular.module("signup-app", []);
app.controller("signup-ctrl", function($scope, $http){
	
	$scope.items=[];
	$scope.confirmPassword="";
	$scope.initialize = function(){
		$http.get("/rest/users").then(resp => {
			$scope.items = resp.data;
			console.log($scope.items)
		});
	}
	$scope.reset=function(){
		$scope.account={
			activity: true,
			role:[
			"user"
		]
		}
		$scope.confirmPassword=""
	}
	
	$scope.account={
		username:"",
		password:"",
		fullname:"",
		email:"",
		phone:"",
		gender: true,
		cmnd:"",
		address:"",
		image:"",
		activity: true,
		role:[
			"user"
		],
		create(){
			var account = angular.copy(this);
			var item = $scope.items.find(ac => ac.username==account.username);
			if(account.username == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập tài khoản!'
				})
			} else if(item){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Tài khoản đã tồn tại!'
				})
			} else if(account.password == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập mật khẩu!'
				})
			}else if($scope.confirmPassword == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập xác nhận mật khẩu!'
				})
			} else if(account.password!= $scope.confirmPassword){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Xác nhận mật khẩu không đúng!'
				})
			} else if(account.fullname == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập họ và tên!'
				})
			} else if(account.email == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập email!'
				})
			} else if(account.phone == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập số điện thoại!'
				})
			} else if(account.cmnd == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập số CMND!'
				})
			} else if(account.address == ""){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Vui lòng nhập địa chỉ!'
				})
			} else{
				if(account.gender == false){
					account.image = "avatar7.png";
				} else {
					account.image = "avatar3.png";
				}
				$http.post("/rest/users/signup", account).then(resp => {
					Swal.fire({
					  position: 'center',
					  icon: 'success',
					  title: 'Đăng ký thành công!',
					  showConfirmButton: false,
					  timer: 1500
					})
					$scope.initialize();
					$scope.reset()
				}).catch(error => {
					alert("Đăng ký lỗi!")
					console.log(error)
				})
			}
		}
	}
	
	$scope.initialize();
	
})