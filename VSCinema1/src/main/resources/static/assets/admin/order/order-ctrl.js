app.controller("order-ctrl", function($scope, $http, $location) {
	$scope.items = []
	$scope.orderDetail = []
	$scope.order = {}
	$scope.edited = false
	$scope.initialize = function() {
		$http.get("/rest/orders").then(resp => {
			$scope.items = resp.data
			console.log($scope.items)
		}).catch(error => {
			console.log(error)
		})
	}
	$scope.initialize()
	$scope.findByDate = function() {
		var date = new Date($('#createdate').val());
		if (date == "Invalid Date") {
			$scope.initialize()
		} else {
			$http.get(`/rest/orders/findbydate/${date}`).then(resp => {
				$scope.items = resp.data
				console.log(resp.data)
			}).catch(error => {
				console.log(error)
			})
		}
	}
	$scope.edit = function(item) {
		$scope.edited = true
		$scope.order = angular.copy(item)
		$http.get(`/rest/orders/${item.id}`).then(resp => {
			$scope.orderDetail = resp.data
			console.log($scope.orderDetail)
		}).catch(error => {
			console.log(error)
		})
		$(".nav-tabs a:eq(0)").tab('show');
	}
	$scope.reset = function() {
		$scope.order = {}
		$scope.edited = false
	}
	$scope.SeachList = $scope.listshowdate;
	$scope.replace = function(userData) {
		userData = userData.replace("+", "")
		userData = userData.replace(".", "")
		userData = userData.replace(":", "")
		userData = userData.replace(";", "")
		userData = userData.replace(",", "")
		userData = userData.replace("?", "")
		userData = userData.replace("|", "")
		userData = userData.replace("/", "")
		userData = userData.replace("'", "")
		userData = userData.replace("+", "")
		userData = userData.replace(".", "")
		userData = userData.replace(":", "")
		userData = userData.replace(";", "")
		userData = userData.replace(",", "")
		userData = userData.replace("?", "")
		userData = userData.replace("|", "")
		userData = userData.replace("/", "")
		userData = userData.replace("'", "")
		userData = userData.replace("!", "")
		userData = userData.replace("@", "")
		userData = userData.replace("#", "")
		userData = userData.replace("$", "")
		userData = userData.replace("%", "")
		userData = userData.replace("^", "")
		userData = userData.replace("&", "")
		userData = userData.replace("*", "")
		userData = userData.replace("(", "")
		userData = userData.replace(")", "")
		userData = userData.replace("_", "")
		userData = userData.replace("{", "")
		userData = userData.replace("}", "")
		userData = userData.replace("}", "")
		userData = userData.replace("[", "")
		userData = userData.replace("]", "")
		userData = userData.replace(">", "")
		userData = userData.replace("<", "")
		userData = userData.replace(" ", "")
		userData = userData.replace("-", "")
		userData = userData.replace("=", "")
		userData = userData.replace("`", "")
		userData = userData.replace("~", "")
		userData = userData.replace(/[a-zA-Z]{1,100}/, "")
		return userData;
	}
	$scope.Search = function() {
		const searchWrapper = document.querySelector(".excame");
		alert(searchWrapper)
		const inputBox = searchWrapper.querySelector("input");
		const suggBox = searchWrapper.querySelector(".autocom-box");
		let userData = document.getElementById('inputsearch').value; //cho userData bằng giá trị nhập vào input
		//let userData = $scope.inputsearch;
		let emptyArray = [];
		//console.log(searchWrapper)
		var tr = '';
		for (let i = 0; i < userData.length; i++) {
			tr += $scope.replace(userData[i]);
		}
		userData = tr;
		//		console.log(userData)
		var str = '';
		if (userData) {
			for (let i = 0; i < userData.length; i++) {
				str += userData[i];
				if (i == 7) {
					str = str.slice(0, 8) + str.slice(8, 11)
					//console.log(userData[i])
				}
				if (i == 6) {
					if (userData[i] != 0) {
						str = str.slice(0, 7) + "-0" + str.slice(7, 10)
					}
					if (userData[i] == 0) {
						str = str.slice(0, 7) + "-" + str.slice(7, 10)
					}
				}
				if (i == 5) {
					//  str.splice(5, 1)
					str = str.slice(0, 5) + str.slice(6, 10);
				}
				if (i == 4) {
					str = str.slice(0, 4) + "-0" + str.slice(4, 5);
				}
			}
			if (userData.length == 8) {
				$scope.fills();
				return searchWrapper.classList.remove("active");
			}
			emptyArray = []
			userData = str
			if (userData.length < 10) {
				emptyArray.push("Có phải ý bạn là: " + str)
			}

			$scope.listshowdate.filter(function(value) {
				var exites = '';
				for (var i = 0; i < value.length; i++) {
					//			console.log(value)
					exites += value[i];
					//	console.log(exites)
					if (userData == exites) {
						//				console.log(userData+"|"+exites)
						emptyArray.push(value)
					}
				}

			})
			//	console.log("emptyArray.length: "+emptyArray.length,emptyArray)
			$scope.SeachList = emptyArray;
			emptyArray = emptyArray.map((data) => {
				data = '<li>' + data + '</li>'
				//console.log(data)
				return data;
			});

			if (emptyArray.length == 0) {
				//alert("Đúng")
				document.querySelector(".excame").classList.remove("active")
				return;
			}
			//console.log(emptyArray);
			searchWrapper.classList.add("active"); //show autocom box

			//showSuggestions(emptyArray,inputBox);
			searchWrapper.classList.add("active");
		} else {
			console.log(userData)
			searchWrapper.classList.remove("active") //hide autocombox
		}

	}

	$scope.select = function(element) {
		let selectUserData = element.lsd;
		selectUserData = selectUserData.replace("Có phải ý bạn là: ", "")
		let searchWrapper = document.querySelector(".excame");
		searchWrapper.querySelector("input").value = selectUserData;
		document.querySelector(".excame").classList.remove("active") //hide autocombox
	}

	function showSuggestions(list, inputBox) {
		let listData;
		if (!list.length) {
			let userValue = inputBox.value;
			listData = '<li>' + userValue + '</li>'
		} else {
			listData = list.join('')
		}
	}
	$scope.pager = {
		page: 0,
		size: 6,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
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
		}
	}
})