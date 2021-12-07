app.controller("authority-ctrl", function($scope, $http, $location){
	
	$scope.roles = [];
	$scope.users = [];
	$scope.authorities = [];
	$scope.authority =[];
	
	$scope.initialize = function(){
		$http.get("/rest/roles").then(resp => {
			$scope.roles = resp.data;
		})
		
		$http.get("/rest/users").then(resp => {
			$scope.users = resp.data;
		})
		
		$http.get("/rest/authorities").then(resp => {
			$scope.authorities=resp.data;
		}).catch(error => {
			$location.path("/unauthorized");
		})
	}
	
	$scope.search = function(username){
		var user = $scope.users.find(ur => ur.username == username);
		if(user == null){
			alert("Tài khoản không tồn tại.");
			$scope.initialize();
		} else {
			$scope.users = [];
			$scope.users.push(user);
		}
	}
	
	$scope.authority_changed = function(acc, role){
		var authority = $scope.authority_of(acc, role);
		if(authority){
			$scope.revoke_authority(authority);
		} else {
			authority = {user:acc, role:role};
			$scope.grant_authority(authority);
		}
	}
	
	$scope.authority_of = function(acc,role){
		if($scope.authorities){
			return $scope.authorities
				.find(ur => ur.user.username==acc.username && ur.role.id==role.id);
		}
	}
	
	$scope.grant_authority = function(authority){
		$http.post('/rest/authorities/', authority).then(resp => {
			$scope.authorities.push(resp.data)
			alert("Cấp quyền sử dụng thành công")
		}).catch(error => {
			alert("Cấp quyền sử dụng thất bại");
			console.log("Error", error)
		});
	}
	
	$scope.revoke_authority = function(authority){
		$http.delete(`/rest/authorities/${authority.id}`).then(resp => {
			var index = $scope.authorities.findIndex(a => a.id == authority.id);
			$scope.authorities.splice(index, 1)
			alert("Thu hồi quyền sử dụng thành công")
		}).catch(error => {
			alert("Thu hồi quyền sử dụng thất bại");
			console.log("Error", error)
		});
	}
	
	$scope.initialize();
	
})