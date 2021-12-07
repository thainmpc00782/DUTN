/**
 * 
 */

app.controller("movie-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.isEdit = undefined;
	$scope.isLoad = undefined;

	/*$scope.index = 0 ;*/


	$scope.initialize = function() {
		// load movie
		$http.get("/rest/movie").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.releaseDate = new Date(item.releaseDate)
				item.runningTime= new Date("1974-03-12T"+item.runningTime)				
			});
		});

		// load genre
		$http.get("/rest/genre").then(resp => {
			$scope.genre = resp.data;
		});
		// load rated
		$http.get("/rest/rated").then(resp => {
			$scope.rated = resp.data;
		});
		$scope.isLoad = true;
	}

	// Khởi đầu
	$scope.initialize();


	// Xóa form
	$scope.reset = function() {
		$scope.form = {
			createdate: new Date(),
			poster: 'film.png',
			banner: 'film.png',
			activity: true
		}
		$scope.isEdit = false;
		$scope.isLoad = true;

		/*$scope.index = 0;*/
	}

	$scope.reset();


	// Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.form.runningtime = new Date("1974-03-12T" + item.runningtime)
		$(".nav-tabs a:eq(0)").tab('show');
		$scope.isEdit = true;
		$scope.isLoad = false;

		/*$scope.index = 1;*/
	}



	// Thêm movie mới
	$scope.create = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của food
		var fname = document.getElementById("name").value;
		var fgenre = document.getElementById('genre').value;
		var frated = document.getElementById("rated").value;
		var fdirector = document.getElementById("director").value;
		var fcast = document.getElementById("cast").value;
		var flanguage = document.getElementById("language").value;
		var ftrailer = document.getElementById("trailer").value;
		var fsummary = document.getElementById("summary").value;


		console.log("Form: " + $scope.form);
		if (fname == "" || fname == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên Phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fgenre == "" || fgenre == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn thể loại phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (frated == "" || frated == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn phân loại phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fdirector == "" || fdirector == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên đạo diễn !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcast == "" || fcast == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên diễn viên!',
				width: '500px',
				heightAuto: true
			})
		}
		else if (flanguage == "" || flanguage == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập ngôn ngữ',
				width: '500px',
				heightAuto: true
			})
		} else if (ftrailer == "" || ftrailer == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập trailer!',
				width: '500px',
				heightAuto: true
			})

		} else if (fsummary == "" || fsummary == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tóm tắt phim !',
				width: '500px',
				heightAuto: true
			})
		} else {
			$http.post(`/rest/movie`, item).then(resp => {

				resp.data.releasedate = new Date(resp.data.releasedate); // sau khi server trả dữ liệu về -> chuyển ngày sang javascript
				$scope.items.push(resp.data);
				console.log("Foood demo : " + resp.data);
				$scope.item = resp.data;
				$scope.reset();
				Swal.fire({
					icon: 'success',
					title: 'Thành công !',
					text: 'Thêm Phim thành công !',
					width: '500px',
					heightAuto: true
				})


			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Thất bại !',
					text: 'Thêm phim thất bại !',
					width: '500px',
					heightAuto: true
				})
				console.log("Error", error);
			});


		}
	}

	// Cập nhật sản phẩm mới
	$scope.update = function() {
		var item = angular.copy($scope.form); // Lấy thông tin của sp
		var fname = document.getElementById("name").value;
		var fgenre = document.getElementById('genre').value;
		var frated = document.getElementById("rated").value;
		var fdirector = document.getElementById("director").value;
		var fcast = document.getElementById("cast").value;
		var flanguage = document.getElementById("language").value;
		var ftrailer = document.getElementById("trailer").value;
		var fsummary = document.getElementById("summary").value;
		var freleasedate = document.getElementById("releaseDate").value;
		var frunningtime = document.getElementById("runningTime").value;

		if (fname == "" || fname == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên Phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fgenre == "" || fgenre == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn thể loại phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (frated == "" || frated == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng chọn phân loại phim !',
				width: '500px',
				heightAuto: true
			})
		} else if (fdirector == "" || fdirector == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên đạo diễn !',
				width: '500px',
				heightAuto: true
			})
		} else if (fcast == "" || fcast == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tên diễn viên!',
				width: '500px',
				heightAuto: true
			})
		}
		else if (flanguage == "" || flanguage == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập ngôn ngữ',
				width: '500px',
				heightAuto: true
			})
		} else if (ftrailer == "" || ftrailer == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập trailer!',
				width: '500px',
				heightAuto: true
			})

		} else if (frunningtime == "" || frunningtime == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập thời lượng!',
				width: '500px',
				heightAuto: true
			})

		} else if (freleasedate == "" || freleasedate == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập thời lượng!',
				width: '500px',
				heightAuto: true
			})

		}
		else if (fsummary == "" || fsummary == null) {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại !',
				text: 'Vui lòng nhập tóm tắt phim !',
				width: '500px',
				heightAuto: true
			})
		} else {
			Swal.fire({
				title: 'Cập nhật Sản phẩm !',
				text: "Bạn có muốn cập nhật sản phẩm này không ? ",
				icon: 'question',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Có'
			}).then((result) => {
				if (result.isConfirmed) {
					$http.put(`/rest/movie`, item).then(resp => {
						var index = $scope.items.findIndex(p => p.id == item.id); // tìm sp trong csdl và thay đổi thông tin
						resp.data.releasedate = new Date(resp.data.releasedate)
						$scope.items[index] = item;
						Swal.fire({
							icon: 'success',
							title: 'Thành công !',
							text: 'Cập nhật phim thành công  !',
							width: '500px',
							heightAuto: true
						})
					}).catch(error => {
						Swal.fire({
							icon: 'error',
							title: 'Thất bại !',
							text: 'Cập nhật phim thất bại !',
							width: '500px',
							heightAuto: true
						})
						console.log("Error", error);
					});
				}
			})
		}
	}

	// Upload Posters
	$scope.imageChangedPosters = function(files) {
		var data = new FormData(); // tạo form data để chứa ảnh đã chọn
		data.append('file', files[0]);
		var path="movies/posters" // bỏ ảnh vào form data
		$http.post(`/rest/upload/movies/${path}`, data, {
			transformRequest: angular.identity, headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.poster = resp.data.name;
			console.log("HHLL: " + 	$scope.form.poster);
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Lỗi upload Poster !',
				width: '500px',
				heightAuto: true
			})
			console.log("Error", error);
		});
	}
	// Upload Banners
	$scope.imageChangedBanners = function(files) {
		var data = new FormData(); // tạo form data để chứa ảnh đã chọn
		data.append('file', files[0]);
		var path="movies/banners" // bỏ ảnh vào form data
		$http.post(`/rest/upload/movies/${path}`, data, {
			transformRequest: angular.identity, headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.banner = resp.data.name;
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Lỗi upload Banner !',
				width: '500px',
				heightAuto: true
			})
			console.log("Error", error);
		});
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size; // vị trí sản phẩm bắt đầu lấy
			return $scope.items.slice(start, start + this.size); // Tách những sản phẩm đang xem
		},

		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},

		first() {
			this.page = 0;
		},

		prev() {
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
});