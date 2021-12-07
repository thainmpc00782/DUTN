const app = angular.module("shopping-cart-app",[]);
app.controller("shoppingCrl" , function($scope ,$http){
    $scope.users =[];
    
    $scope.form = {
      
            photo : 'user.png'
           
    };

    $scope.initialize =function(){

       

        //  //load account
        // $http.get("/rest/accounts").then(resp =>{
        //     $scope.items = resp.data;
        //     // $scope.items.forEach(item => {
        //     //     item.createDate =new Date(item.createDate)
        //     // })

        // });

        //load custommer
        $http.get("/rest/accounts?user=true").then(resp =>{
            $scope.users = resp.data ;
        })


        // $http.get("/rest/roles").then(resp => {
        //     $scope.roles = resp.data;
        // })

          //load cust
          $http.get("/rest/cust?user=true").then(resp => {
            $scope.authorities = resp.data ;
        })
       
        
    }

 



    // khoi dau
    $scope.initialize();

    //xoa form 
    $scope.reset = function(){

        $scope.form = {
            
            photo : 'user.png'
            
        };
    }

    

    //them account
    $scope.create= function(){ 
        var user = angular.copy($scope.form);
        
        $http.post(`/rest/accounts` , user ).then(resp =>{
         
            $scope.reset();
            
            alert("Đăng Ký Thành Công !!")
            

        }).catch(error => {
            alert("Error Đăng ký Thất Bại ");
            console.log("Error",error);
        }); 
    }


    //upload hinh
    $scope.imageChange = function(files){
        var data = new FormData();
        data.append('file' ,files[0]);
        $http.post('/rest/upload/images' , data ,{
            transformRequest : angular.identity,
            headers: {'Content-Type' : undefined}

        }).then(resp =>{
            $scope.form.photo = resp.data.name;

        }).catch(error => {
            alert("Error upload image");
            console.log("Error",error);
        })
    }





    $scope.items =[];
    // quan ly gio hang
    $scope.cart={
        items:[],
        
        //them vao gio hang
        add(id){
          var item = this.items.find(item => item.id ==id );
          if (item) {
              item.qty++;
              this.saveToLocalStorage();
          } else {
             $http.get(`/rest/products/${id}`) .then(resp => {
                 resp.data.qty =1;
                 this.items.push(resp.data);
                 this.saveToLocalStorage();
             })
          }
        }, 

        //xoa san pham khoi gio hang
        remove(id){
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index ,1);
            this.saveToLocalStorage();
        },

        //xoa sach gio hang
        clear(){
        this.items =[];
        this.saveToLocalStorage();
        
        },

        //tinh thanh tien cua 1 sp
        amt_of(item){},

        //tinh tong so luong cac mat hang trong gio
        get count(){
            return this.items
            .map(item => item.qty )
            .reduce((total , qty) => total +=qty ,0);
        },
            
        //tinh tong thanh tien cac mat hang trong gio
        get amount(){
            return this.items
            .map(item => item.qty * item.price)
            .reduce((total , qty) => total +=qty ,0);
        },

        //luu gio hang vao cac local storage
        saveToLocalStorage(){
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart" ,json);
        },

        //doc gio hang vao local storage
        loadFormLocalstorage(){
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }
    }
    $scope.cart.loadFormLocalstorage();
    $scope.oder={
        createDate : new Date(),
        address : "",
        account : {username : $("#username").text()},
        get orderDetails(){  
            return $scope.cart.items.map(item => {
                return{
                product : {id: item.id},
                price : item.price,
                quantity : item.qty
                }
            });
           
        },
        purchase(){
            var oder = angular.copy(this);
            //thu hien dat hang
            $http.post("/rest/oders ",oder).then(resp =>{
                alert("DAT HANG THANH CONG !!");
                $scope.cart.clear();
                location.href ="/oder/detail/" + resp.data.id;

            }).catch(error => {
                alert("dat hang loi!");
                console.log(error)
            })
        }


    }

   

     
})