app.controller("admin-ctrl", function($scope, $http) {
	$scope.user = {};
	$http.get("/rest/users/getUser").then(resp => {
		$scope.user = resp.data;
		let image = $scope.user.image;
		$http.get(`/rest/test/image`).then(resp => {
			$scope.user.image = "default-user.jpg";
		});
	}).catch(e => {
		console.log("e", e);
	});

	//load account
	$http.get("/rest/authorities").then(resp => {
		$scope.auths = resp.data
	}).catch(error => {
		console.log("Error", error)
	})

	//load  cinema
	$http.get("/rest/cinemas").then(resp => {
		$scope.cinemas = resp.data
	}).catch(error => {
		console.log("Error", error)
	})

	//load movie
	$http.get("/rest/movie").then(resp => {
		$scope.movies = resp.data
	}).catch(error => {
		console.log("Error", error)
	})

	$http.get("/rest/statistics/getSynthetic").then(resp => {
		$scope.Synthetic = resp.data;
		//console.log($scope.SumRevenues)
	}).catch(error => {
		console.log("ERROR", error)
	})
})