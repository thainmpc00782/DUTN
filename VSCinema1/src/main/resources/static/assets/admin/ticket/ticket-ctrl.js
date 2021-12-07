app.controller('ticket-ctrl', function ($scope, $http) {
	$scope.items = [];
	$scope.form = {
	
	};
	$scope.editd= false;
	
	$scope.initialize = function () {
		// load products
		$http.get('/rest/ticket').then(  response => {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.creatDate = new Date(item.creatDate)
			})
			console.log($scope.items);
		}).catch(error=>{
			console.log(error);
		});
	}
	

	$scope.reset = function () {
		$scope.form = {
			show: {movie: { poster: 'film.png'}},
			seat: {room: {name: 'Vị Trí - |'}},
			seat: {room: {cinema: {name: 'Tên Rạp Chiếu Phim'}}},
			
		};
		
	} 
	// Hiển thị sản phẩm lên form
	$scope.editTicket = function (item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');
		var endtime1 = new Date("1974-03-12T"+ $scope.form.show.startTime); 
		var endtime2 = new Date("1974-03-12T"+ $scope.form.show.movie.runningTime); 
		var h2 = endtime2.getHours()* 60;
		var phut2 = endtime2.getMinutes();	
		h2 = h2 + phut2;
		var gio2 = new Date("1974-03-12T"+ $scope.form.show.startTime);
		gio2.setTime(gio2.getTime()+(h2*60000));
		var endtime = new Date(gio2);
		$scope.form.endtime = endtime.getTime();
		console.log($scope.form);
		$scope.editd = true;
	}
	

	$scope.imageChange = function (files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.entity,
			headers: {
				'Content-Type': undefined
			}
		}).then(response => {
			$scope.form.image = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
			console.log("Erorr", err);
		})

	}
	$scope.changesize = function(size){
		$scope.pager.size = size
	}
	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length
				/ this.size);
		},
		first() {
			this.page = 0;

		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		},
	}
	
	
	$scope.initialize();
	$scope.reset();
});