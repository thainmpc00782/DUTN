app.controller("account-ctrl" , function($scope ,$http){

    $scope.items =[]; 
	$scope.form = {
		image : 'user.png'
	}
	$scope.isLoad = undefined;
   	$scope.error={}
    $scope.initialize =function(){

        //load account
        $http.get("/rest/users").then(resp =>{
             $scope.items = resp.data;
         });

    }

	  //hien thi len form 
    	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');
		$scope.isLoad = true;
		
    }

	$scope.isLoad = false;

    // khoi dau
    $scope.initialize();

    //xoa form 
    $scope.reset = function(){
        $scope.form = {           
            image : 'user.png',
            activity : true   ,
			gender : true,       
        };
		$scope.isLoad = false;
		 $scope.initialize();
		$scope.error={}
    }

  


    //them account
   $scope.create = function() {
		var item = angular.copy($scope.form);
		item.role=["user"]
		var acc = $scope.items.find(ac => ac.username==item.username);
		if(acc){
				Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Tài khoản đã tồn tại!'
				})
			} else{
		$http.post(`/rest/users/signup`, item).then(resp => {
			$scope.items.push(resp.data)
			$scope.reset()
			$scope.initialize()
			Swal.fire({
				icon: 'success',
				title: 'Thành công',
				text: 'Thêm mới thành công',
				width: '500px',
				heightAuto: true
			})
		}).catch(error => {
			$scope.error = error.data;
			console.log(error)
			Swal.fire({
				icon: 'error',
				title: 'Thất bại',
				text: 'Lỗi thêm mới sản phẩm',
				width: '500px',
				heightAuto: true
			})
		})
	}
	}
		
		//update
		 //cap nhat sp 
   $scope.update=function(){
		var item=angular.copy($scope.form);
		$http.put(`/rest/users/${item.username}`,item).then(resp=>{
			var index=$scope.items.findIndex(r=> r.username==item.username);
			$scope.items[index]=item
			$scope.reset()
			$scope.error={}
			Swal.fire({
				  icon: 'success',
				  title: 'Thành công',
				  text: 'Cập nhật thành công',			  
				  width:'500px',
				  heightAuto:true
				})
				
		}).catch(error=>{
			$scope.error = error.data;
			Swal.fire({
				  icon: 'error',
				  title: 'Thất bại',
				  text: 'Cập nhật thất bại',			  
				  width:'500px',
				  heightAuto:true
				})
				console.log("Error",error);
		})
		
	}



    //xoa 
   $scope.delete = function(username) {
		Swal.fire({
			title: 'Are you sure?',
			text: "You won't be able to revert this!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				var item = angular.copy($scope.form);
				$http.delete(`/rest/users/${username}`).then(resp => {
					var index = $scope.items.findIndex(r => r.username == username);
					$scope.items.splice(index, 1);
					$scope.reset();
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Xóa thành công',
						width: '500px',
						heightAuto: true
					})
				}).catch(error => {
					console.log(error)
				})
			}
		})

	}

    //upload hinh
    $scope.imageChange = function(files){
        var data = new FormData();
        data.append('file' ,files[0]);
        $http.post('/rest/upload/images/' , data ,{
            transformRequest : angular.identity,
            headers: {'Content-Type' : undefined}
        }).then(resp =>{
            $scope.form.image = resp.data.name;

        }).catch(error => {
            alert("Error upload image");
            console.log("Error",error);
        })
    }

    // phan trang
    $scope.pager ={
        page : 0,
        size : 6 ,
        get items(){

            var start = this.page * this.size;
         return   $scope.items.slice(start , start + this.size)
        },
        get count(){
            return Math.ceil(1.0 * $scope.items.length / this.size)
        },
        first(){
           this.page = 0;
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
            this.page = this.count -1
            
        }


    }
    });