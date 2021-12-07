app.controller("room-ctrl", function($scope, $http, $location) {
	
	$scope.cinemas = [];
	$scope.rooms = [];
	$scope.seats = [];
	$scope.citites = [];
	$scope.types = [];
	$scope.positions = [];
	
	$scope.room = {};
	$scope.seat = {};
	$scope.updateRoom ={};
	$scope.updateSeat ={};
	$scope.position = null;
	
	$scope.show = false;
	$scope.index = 0;
	$scope.indexButton = 0;
	
	$scope.initialize = function() {
		$http.get("/rest/rooms").then(resp => {
			$scope.rooms = resp.data
		}).catch(error => {
			console.log(error)
		})

		$http.get('/rest/city').then(resp => {
			$scope.cities = resp.data
		}).catch(error => {
			console.log(error)
		})
		
		$http.get('/rest/types').then(resp => {
			$scope.types = resp.data
		}).catch(error => {
			console.log(error)
		})
	}
	
	$scope.createPosition = function(){
		for(var i = 1; i <= 210; i++){
			var position = {
				id: i,
				seat:{}
			}
			$scope.positions.push(position);
		}
	}
	
	$scope.findRapFromCity = function(){
		var cityid = $scope.room.cinema.city.id;
		if(cityid == ""){
			$scope.cinemas = [];
		} else {
			$http.post(`/rest/cinemas/findByCity/` + cityid).then(resps => {
				$scope.cinemas = resps.data
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	$scope.findByCinema = function(){
		var cinemaid = $scope.room.cinema.id;
		$http.get(`/rest/room/findByCinema/`+cinemaid).then(resp => {
			$scope.rooms = resp.data
		}).catch(error => {
			console.log("Error!", error)
		})
	}
	
	$scope.create = function(){
		if($scope.room.name==null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập tên phòng!'
			})
		} else if($scope.room.cinema == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn rạp chiếu!'
			})
		} else if($scope.room.cinema.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn rạp chiếu!'
			})
		} else {
			var room = $scope.room;
			$http.post("/rest/rooms", room).then(resp => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Tạo phòng chiếu thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.initialize();
				$scope.edit(resp.data);
				$scope.indexButton = 1;
			}).catch(error => {
				console.log("Error!", error)
			})
		}
		
	}
	
	$scope.update = function(){
		if($scope.room.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phòng chiếu!'
			})
		} else if($scope.room.name == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập tên phòng chiếu!'
			})
		} else if($scope.room.cinema == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn rạp chiếu!'
			})
		} else if($scope.room.cinema.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn rạp chiếu!'
			})
		} else {
			var room = $scope.room;
			$http.put("/rest/rooms", room).then(resp => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Cập nhật thông tin phòng chiếu thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.initialize();
				$scope.edit(resp.data);
			}).catch(error => {
				alert("lỗi!")
				console.log(error)
			})
		}
	}
	
	$scope.delete = function(){
		if($scope.updateRoom.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phòng chiếu!'
			})
		} else {
			if($scope.updateRoom.activity == true){
				$scope.updateRoom.activity = false;
				$http.put("/rest/rooms", $scope.updateRoom).then(resp => {
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Phòng chiếu đang trong trạng thái bảo trì!',
						showConfirmButton: false,
						timer: 1500
					})
					$scope.initialize();
					$scope.edit(resp.data);
				}).catch(error => {
					alert("lỗi!")
					console.log(error)
				})
			} else {
				$scope.updateRoom.activity = true;
				$http.put("/rest/rooms", $scope.updateRoom).then(resp => {
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Phòng chiếu đã hoạt động trở lại!',
						showConfirmButton: false,
						timer: 1500
					})
					$scope.initialize();
					$scope.edit(resp.data);
				}).catch(error => {
					alert("lỗi!")
					console.log(error)
				})
			}
		}
	}
	
	$scope.edit = function(item){
		$scope.positions = [];
		$scope.createPosition();
		$scope.room = item;
		$scope.updateRoom = item;
		$http.get(`/rest/seats/${item.id}`).then(resp => {
			$scope.seats = resp.data;
			for(var i = 0; i < $scope.seats.length;i++){
				for(var j = 0;j < $scope.positions.length;j++){
					if($scope.seats[i].position == $scope.positions[j].id){
						$scope.positions[j].seat = $scope.seats[i];
					} 
				}
			}
		}).catch(error => {
			console.log(error)
		})
		$scope.findRapFromCity();
		$(".nav-tabs a:eq(0)").tab('show');
		$scope.show = true;
		$scope.index = 1;
	}
	
	$scope.reset = function(){
		$scope.room = {};
		$scope.updateRoom ={};
		$scope.cinemas = [];
		$scope.positions = [];
		$scope.show = false;
		$scope.index = 0;
		$scope.resetSeat();
	}
	
	$scope.add = function(){
		if($scope.room.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phòng chiếu!'
			})
		} else if($scope.position == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn vị trí ghế!'
			})
		} else if($scope.seat.name == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập số ghế!'
			})
		} else if($scope.seat.type == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn loại ghế!'
			})
		} else if($scope.seat.type.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn loại ghế!'
			})
		} else {
			$scope.seat.room = $scope.room;
			$scope.seat.position = $scope.position;
			$scope.seat.activity = true;
			$http.post("/rest/seats", $scope.seat).then(resps => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Thêm ghế vào phòng chiếu thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.edit($scope.room);
				$scope.resetSeat();
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	$scope.updateSeats = function(){
		if($scope.room.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phòng chiếu!'
			})
		} else if($scope.seat.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn ghế!'
			})
		} else if($scope.seat.name == null || $scope.seat.name==""){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng nhập số ghế!'
			})
		} else if($scope.seat.type == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn loại ghế!'
			})
		} else if($scope.seat.type.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn loại ghế!'
			})
		} else {
			$scope.seat.room = $scope.room;
			$scope.seat.activity = true;
			$http.put("/rest/seats", $scope.seat).then(resps => {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Cập nhật thông tin ghế thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.edit($scope.room);
				$scope.resetSeat();
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	$scope.deleteSeat = function(){
		if($scope.room.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn phòng chiếu!'
			})
		} else if($scope.updateSeat.id == null){
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Vui lòng chọn ghế!'
			})
			$scope.resetSeat();
		} else {
			$scope.updateSeat.activity = false;
			$http.put("/rest/seats", $scope.updateSeat).then(resp => {	
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Ghế đã được xóa thành công!',
					showConfirmButton: false,
					timer: 1500
				})
				$scope.edit($scope.room);
				$scope.resetSeat();
			}).catch(error => {
				console.log("Error!", error)
			})
		}
	}
	
	$scope.editSeat = function(item){
		$scope.seat = item.seat;
		$scope.updateSeat = item.seat;
		$scope.position = item.id;
		if($scope.seat.id == null){
			$scope.indexButton = 0;
		} else {
			$scope.indexButton = 1;
		}
	}
	
	$scope.resetSeat = function(){
		document.querySelector('input[name="options"]:checked').checked = false;
		$scope.seat = {};
		$scope.updateSeat = {};
		$scope.position = null;
		$scope.indexButton = 0;
	}
	
	$scope.pager = {
		page: 0,
		size: 10,
		get items(){
			var start = this.page * this.size;
			return $scope.rooms.slice(start, start + this.size);
		},
		get count(){
			return Math.ceil(1.0 * ($scope.rooms.length/this.size));
		},
		first(){
			this.page = 0
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){	
				this.first();
			}
		},
		last(){
			this.page = this.count - 1;
		}
	}
	
	$scope.initialize();
	
})
