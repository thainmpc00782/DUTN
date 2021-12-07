app.controller('statistics-ctrl', function($scope, $http) {
	$scope.tickets = [];
	$scope.ticketsfood = [];
	$scope.ticketstemp = [];
	$scope.ticketsfoodtemp = [];
	$scope.auths = [];
	$scope.authstemp = [];
	$scope.movie = [];
	$scope.fill = [];
	$scope.cinema = [];
	$scope.SumRevenues = [];
	$scope.Sumrevenuesbycinema = [];
	$scope.top5ticketmovie = [];
	$scope.NumberTicketTimenow = [];
	$scope.timeNow = [];
	$scope.Synthetic = [];
	$scope.NumberFoodTimenow = [];
	$scope.sumOrder = '';
	$scope.init = function() {
		//load vé xem phim
		$http.get("/rest/statistics").then(resp => {
			$scope.tickets = resp.data

		}).catch(error => {
			console.log("Error", error)
		})
		//lay list phim
		$http.get("/rest/statistics/getlistmovie").then(resp => {
			$scope.movie = resp.data
		}).catch(error => {
			console.log("Errormovie", error)
		})
		//load vÃ© Ä‘á»“ Äƒn
		$http.get("/rest/statistics/food").then(resp => {
			$scope.ticketsfood = resp.data
			//	console.log($scope.ticketsfood)
			$scope.sumCheckouts();
		}).catch(error => {
			console.log("Error", error)
		})
		//load account
		$http.get("/rest/statistics/accounts").then(resp => {
			$scope.auths = resp.data
			$scope.sumActive();
		}).catch(error => {
			console.log("Error", error)
		})
		
		// load tá»•ng thá»‘ng kÃª
		$http.get("/rest/statistics/getstatisticticketandfood").then(resp => {
			$scope.SumRevenues = resp.data;
			//console.log($scope.SumRevenues)
		}).catch(error => {
			console.log("ERROR",error)
		})

		// load tá»•ng doanh thu tá»«ng ráº¡p
		$http.get("/rest/statistics/getumrevenuesbycinema").then(resp => {
			$scope.Sumrevenuesbycinema = resp.data;
			//console.log($scope.SumRevenues)
		}).catch(error => {
			console.log("ERROR",error)
		})
		
		// load tá»•ng doanh thu tá»«ng ráº¡p
		$http.get("/rest/cinemas").then(resp => {
			$scope.cinema = resp.data;
			//console.log($scope.SumRevenues)
		}).catch(error => {
			console.log("ERROR",error)
		})
	
		//load so luong ve mua top 5 phim
		$http.get("/rest/statistics/top5sumticketmovie").then(resp => {
			$scope.top5ticketmovie = resp.data;
			//console.log($scope.SumRevenues)
		}).catch(error => {
			console.log("ERROR",error)
		})
		//load doanh thu ngay, thang, nam
		
		$http.get("/rest/statistics/getSynthetic").then(resp => {
			$scope.Synthetic = resp.data;
			//console.log($scope.SumRevenues)
		}).catch(error => {
			console.log("ERROR",error)
		})
		
		//load
	}
	
	$scope.getDatafromTimeNow = function(){
		$http.get("/rest/statistics/getSumTicketByTime").then(resp => {
			$scope.NumberTicketTimenow = resp.data;
	//		console.log(resp.data)
		}).catch(error => {
			console.log("ERROR",error)
		})
		
		$http.get("/rest/statistics/getSumFoodByTime").then(resp => {
			$scope.NumberFoodTimenow = resp.data;
	//		console.log(resp.data)
		}).catch(error => {
			console.log("ERROR",error)
		})
		
		$http.get("/rest/statistics/gettimenow").then(resp => {
			$scope.timeNow = resp.data;
			//console.log($scope.timeNow[0])
			time = new Date("1974-03-12T"+ $scope.timeNow[0])
		}).catch(error => {
			console.log("ERROR",error)
		})
		
	}
	$scope.changedate = function(datec) {
		var d = datec.getDate().toString()
		var m = (datec.getMonth() + 1).toString();
		//console.log(d,m,datec.getDate())
		if (d.length == 1) {
			d = "0" + d
		}
		if (m.length == 1) {
			m = "0" + m
		}
		var d = datec.getFullYear() + "-" + m + "-" + d;
		//console.log(d)
		return d
	}
	$scope.sumOrders = function() {
		$scope.sumOrder = $scope.ticketsfood.length;
	}
	$scope.sumAccount = function() {
		$scope.sumaccount = $scope.auths.length;
	}
	$scope.allSave = function() {
		if ($scope.ticketstemp.length == 0) {
			$scope.ticketstemp = $scope.tickets;
			//console.log($scope.ticketstemp);
		}
		if ($scope.ticketsfoodtemp.length == 0) {
			$scope.ticketsfoodtemp = $scope.ticketsfood;
		}
		if ($scope.authstemp.length == 0) {
			$scope.authstemp = $scope.auths;
		}
	}
	$scope.All = function() {
		if ($scope.ticketstemp.length != 0) {
			$scope.tickets = angular.copy($scope.ticketstemp);
			//	alert("all"+$scope.tickets.length)
		}
		if ($scope.ticketsfoodtemp.length != 0) {
			$scope.tickets = angular.copy($scope.ticketsfoodtemp);
		}
		if ($scope.authstemp.length != 0) {
			$scope.auths = angular.copy($scope.authstemp);
		}
	}
	$scope.fills = function() {
		$scope.All();
		$scope.allSave();
		let startdate = undefined;
		let enddate = undefined;
		let movie = undefined;
		startdate = document.getElementById('startdate').value;
		enddate = document.getElementById('enddate').value;
		movie = document.getElementById('movie').value;
		//console.log("Movie: "+movie)
		//console.log(startdate+"|"+enddate+"|"+movie)
		if (movie.length == 0) {
			movie = undefined;
		}


		if (startdate == undefined || startdate == "" && enddate == undefined || enddate == "") {
			return Swal.fire({
				position: 'top-mid',
				icon: 'error',
				title: 'Vui lòng chọn ngày kết thúc!',
				showConfirmButton: false,
				timer: 1500
			})

		}
		if (startdate != undefined || startdate != "" && enddate != undefined || enddate != "") {
			//console.log(startdate != undefined + startdate != "" + enddate != undefined +"||"+ enddate != "")

			if (movie != undefined) {
				//	console.log(movie != undefined)
				$scope.fill = [];
				for (let i = 0; i < $scope.tickets.length; i++) {
					var sd = new Date(startdate);
					var ed = new Date(enddate);
					var createDate = new Date($scope.tickets[i].createDate);
					/*console.log(sd<createDate)
					console.log(sd >= createDate);
					console.log(ed <= createDate)*/
					//console.log(movie == $scope.tickets[i].show.movie.id)
					if (sd <= createDate && ed >= createDate && movie == $scope.tickets[i].show.movie.id) {
						$scope.fill.push($scope.tickets[i]);
					}
				}
				$scope.tickets = [];
				//alert($scope.fill.length)
				startdate = undefined;
				enddate = undefined;
				movie = undefined;
				return $scope.tickets = $scope.fill;
			}

		}
		if (startdate != undefined || startdate != "" && enddate != undefined || enddate != "") {
			$scope.fill = [];
			//alert($scope.tickets.length);
			for (let i = 0; i < $scope.tickets.length; i++) {
				var sd = new Date(startdate);
				var ed = new Date(enddate);
				var createDate = new Date($scope.tickets[i].createDate);
				//	console.log(sd <= createDate)
				//	console.log(ed >= createDate)
				if (sd <= createDate && ed >= createDate) {
					//	console.log(sd <= createDate)
					//console.log(ed >= createDate)
					$scope.fill.push($scope.tickets[i]);
				}
			}
			$scope.tickets = [];
			startdate = undefined;
			enddate = undefined;
			movie = undefined;
			//console.log($scope.fill.length)
			return $scope.tickets = $scope.fill;
		}
	}




	// Table 2
	$scope.changestatus = function() {
		
	}
	$scope.sumCheckouts = function() {
		$scope.allSave();
		$scope.ticketsfoodcheckout = [];
		$scope.ticketsfoodnotcheckout = [];
		//doanh thu
		$scope.revenue = 0;
		$scope.ticketsfood.filter(function(element) {
			if (element.order.active == true) {
				$scope.ticketsfoodcheckout.push(element);
			}
			if (element.order.active == false) {
				$scope.ticketsfoodnotcheckout.push(element);
			}
			$scope.revenue += element.order.totalmoney;
		})
		$scope.sumOrders();
	}
	$scope.fills = function() {
		let startdate = document.getElementById('startdatefood').value;
		let enddate = document.getElementById('enddatefood').value;
		$scope.allSave();
		//alert(startdate);
		//alert(enddate);
		$scope.ticketsfood = [];
		if (startdate == undefined || startdate == "") {
			return;
		}
		if (enddate == undefined || enddate == "") {
			enddate = new Date(startdate);
		}
		if (enddate != "") {
			enddate = new Date(enddate);
		}
		startdate = new Date(startdate);
		$scope.ticketsfoodtemp.filter(function(element) {
			//console.log(startdate <= new Date(element.order.createdate) && enddate >= new Date(element.order.createdate))
			if (startdate <= new Date(element.order.createdate) && enddate >= new Date(element.order.createdate)) {
				$scope.ticketsfood.push(element);
			}
		})
		$scope.sumCheckouts();
	}

	//Account

	$scope.sumActive = function() {
		$scope.allSave();
		$scope.accountActive = [];
		$scope.accountNotActive = [];
		$scope.auths.filter(function(element) {
			if (element.user.activity == true) {
				$scope.accountActive.push(element);
			}
			if (element.user.activity == false) {
				$scope.accountNotActive.push(element);
			}
		})
		//	alert($scope.accountActive.length)
		//alert($scope.accountNotActive.length)
		$scope.sumAccount();
		$scope.fillsaccount();
	}
	$scope.fillss = function(){
		let startdate = document.getElementById('startdate').value;
		let enddate = document.getElementById('enddate').value;
		let movie = document.getElementById('moviere').value;
			// alert(movie)
		movie = $scope.replaceNumber(movie);
		$scope.allSave();
		$scope.tickets = [];
		if(startdate == undefined || startdate == ""){
		startdate = undefined;
		}
		if(enddate == undefined || enddate == ""){
		enddate = undefined;
		}
		//alert(movie)
		//alert(startdate+" | "+enddate)
		if(movie == undefined && startdate != undefined && enddate != undefined){
		$scope.ticketstemp.filter(function(element){
		
			if(startdate <= new Date(element.order.createdate) && enddate >= new Date(element.order.createdate)){
				$scope.tickets.push(element);
				console.log(element)
			}
				})}
		
		if(movie != undefined && startdate == undefined && enddate == undefined){
			
			$scope.ticketstemp.filter(function(element){
				if(movie == element.show.movie.id){
					$scope.tickets.push(element);
				}
			});
		}
		if(movie == undefined || movie == "" && startdate == undefined && enddate == undefined){
			$scope.tickets = $scope.ticketstemp;
		}
		if(movie != undefined || movie != "" && startdate == undefined && enddate == undefined){
			$scope.ticketstemp.filter(function(element){
			//console.log(movie == element.show.movie.id && new Date(startdate) <= new Date(element.order.createdate) && new Date(enddate) >= new Date(element.order.createdate))
				console.log(movie == element.show.movie.id)
				if(movie == element.show.movie.id){
					if(new Date(startdate) <= new Date(element.order.createdate) && new Date(enddate) >= new Date(element.order.createdate)){	
					$scope.tickets.push(element);
					}
				}
				console.log(element)
			});
		}
	$scope.sumAccount();
	}
	
	$scope.fillsticketfood = function(){
		$scope.allSave();
		$scope.All();
		let startdate = document.getElementById('startdatefood').value;
		let enddate = document.getElementById('enddatefood').value;
		let status = document.getElementById('status').value;
		if(startdate == ""){
			startdate = undefined;
		}
		if(enddate == ""){
			enddate = undefined;
		}
		if(status == ""){
			status = undefined;
		}
		if(startdate != undefined && enddate != undefined){
		$scope.ticketsfood = [];
			$scope.ticketsfoodtemp.filter(function(e){
				if(e.order.createdate >= startdate && e.order.createdate <= enddate){
					$scope.ticketsfood.push(e);
				}
			});
		}
		//alert(startdate +" | "+enddate +" | "+ status)
		if(startdate == undefined && enddate == undefined && status != undefined){
			if (status == "") {
			$scope.ticketsfood = $scope.ticketsfoodtemp;
		}
		if(status == 1) {
			$scope.ticketsfood = [];
			$scope.ticketsfoodtemp.filter(function(element) {
				if (element.order.active == false) {
					$scope.ticketsfood.push(element);
				}
			})
		}
		if (status == 2) {
			$scope.ticketsfood = [];
			$scope.ticketsfoodtemp.filter(function(element) {
				if (element.order.active == true ) {
					$scope.ticketsfood.push(element);
				}
			})
		}
		}
		if(startdate != undefined && enddate != undefined && status != undefined){
			$scope.ticketsfood = [];
			if (status == 2) {
			$scope.ticketsfood = [];
			$scope.ticketsfoodtemp.filter(function(element) {
				if (element.order.active == true && element.order.createdate >= startdate && element.order.createdate <= enddate) {
					$scope.ticketsfood.push(element);
				}
			})
		}
		if (status == 1) {
			$scope.ticketsfood = [];
			$scope.ticketsfoodtemp.filter(function(element) {
				if (element.order.active == false && element.order.createdate >= startdate && element.order.createdate <= enddate) {
					$scope.ticketsfood.push(element);
				}
			})
		}
		}
		if(status == undefined && startdate == undefined && enddate == undefined){
			$scope.ticketsfood = $scope.ticketsfoodtemp;
		}
		$scope.sumOrders();
	}
	
	$scope.fillsaccount = function() {
	
		$scope.staf = []; 
		$scope.user = [];
		$scope.auths.filter(function(element) {
			//	console.log(element.role.id)
			if (element.role.id == "STA") {
				$scope.staf.push(element);
			}
			if (element.role.id == "US") {
				$scope.user.push(element);
			}
		})
	}
	
	$scope.Fullnamesearch = function(){
		let input = document.getElementById('fullnamesearch').value;
		$scope.allSave();
		$scope.All();
		alert(input);
		if(input == undefined || input == ""){
			$scope.auths = $scope.authstemp;
		}
		$scope.authstemps = [];
		if(input != undefined && input != ""){
			let i = 0;
			$scope.auths.filter(function(e){
			
				if(e.user.fullname[i] == input){
					$scope.authstemps.push(e)
				}
				i += 1;
			})
			$scope.auths = [];
			$scope.auths = $scope.authstemps;
		}
	}
	
	$scope.changestatusaccount = function() {
		$scope.allSave();
		$scope.All();
		let input = document.getElementById('statusaccount').value;
		//alert(input);
		if(input == undefined || input == ""){
			$scope.auths = $scope.authstemp;
		}
		$scope.temp = [];
		if(input != undefined && input != "" && input == 2){
			$scope.auths.filter(function(e){
				if(e.user.activity == true){
						$scope.temp.push(e)
				}
			})
			$scope.auths = [];
			$scope.auths = $scope.temp;
		}
		if(input != undefined && input != "" && input == 1){
			$scope.auths.filter(function(e){
				if(e.user.activity == false){
						$scope.temp.push(e)
				}
			})
			$scope.auths = [];
			$scope.auths = $scope.temp;
		}
		
	}
	//Biểu đồ account
	$(document).ready(function() {
		$.ajax({
			type: 'GET',
			dataType: "json",
			contentType: "application/json",
			//url: '${pageContext.request.contextPath}/rest/statistics/filall',
			url: '/rest/statistics/filall',
			success: function(result) {
				google.charts.load('current', {
					'packages': ['corechart']
				});
				google.charts.setOnLoadCallback(function() {
					drawChart(result);
				})
			}
		});

		function drawChart(result) {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'TÃ i khoáº£n');
			data.addColumn('number', 'Sá»‘ lÆ°á»£ng');
			var dataArray = [];
			$.each(result, function(i, obj) {
				dataArray.push([obj.position, obj.sum]);
			});
			//	console.log(dataArray)
			data.addRows(dataArray);
			//	console.log(data)
			var piechart_options = {
				title: 'Thống kê số lượng tài khoản ',
				width: 400,
				height: 300,
				is3D: true,
			};
			var piechart = new google.visualization.PieChart(document.getElementById('piechart_div'));
			piechart.draw(data, piechart_options);

			var columnchart_options = {
				title: 'Thống kê chi tiết số lượng tài khoản',
				width: 700,
				height: 300,
				legend: 'none',

				animation: {
					duration: 1000,
					easing: 'linear',
					startup: true,
				}
			};
			var barchart = new google.visualization.ColumnChart(document.getElementById('columnchart_div'));
			barchart.draw(data, columnchart_options);
		}
	});
//Replace string
	$scope.replaceString = function(value){
		value = value.replace(":","");
		value = value.replace("string","");
		value = value.replace("? ","");
		value = value.replace(" ?","");
		//console.log(value)
		return value
	}
	$scope.replaceNumber = function(value){
		value = value.replace(":","");
		value = value.replace("number","");
		value = value.replace("? ","");
		value = value.replace(" ?","");
		//console.log(value)
		return value
	}
	//Report 1
	 $scope.SumRevenuesOf10Years = function() {
		var xValues = [];
		var yValues = [];
	$scope.SumRevenues.filter(function(elements,index){
		//	console.log(index)
			//console.log(elements[index])
			if(index == 0){
				xValues.push(elements[index]);
			}
		})
		
		//let mang = [{ "nam": 2019,"sum": 10},{ "nam": 2020,"sum": 70},{ "nam": 2021,"sum": 957040}]
		$scope.SumRevenues.filter(function(e){
			xValues.push(e[0]);
			yValues.push(e[1]);
		})
		// console.log(xValues)
	//	console.log(yValues)
		
		var c = document.querySelectorAll("#myChart");
		//alert(c)
		new Chart(c, {
			type: "line",
			data: {
				labels: xValues,
				datasets: [{
					fill: false,
					lineTension: 0,
					backgroundColor: "rgba(0,0,255,1.0)",
					borderColor: "rgba(0,0,255,0.1)",
					data: yValues
				}]
			},
			options: {
				legend: { display: false },
				scales: {
					yAxes: [{ ticks: { min: 0, max: yValues[-1] } }],
				}
			}
		});
	}
	//thong ke doanh thu từng rạp
	$scope.SumRevenuesByCinema = function () {
	let data = [];
	//console.log($scope.cinema)
	$scope.Sumrevenuesbycinema.filter(function(e){
		let temp = {
		y: e.summoney,
		idmovie: e.movieid,
		label: e.moviename
		}
		data.push(temp)
	})
	var chart = new CanvasJS.Chart("SumRevenuesByCinemas", {
	animationEnabled: true,
	
	title:{
		text:"Thống kê tổng doanh thu rạp"
	},
	axisX:{
		interval: 1
	},
	axisY2:{
		interlacedColor: "rgba(1,77,101,.2)",
		gridColor: "rgba(1,77,101,.1)",
		title: "KhÃ´ng hiá»‡n lÃ  doanh thu báº±ng 0"
	},
	data: [{
		type: "bar",
		name: "companies",
		axisYType: "secondary",
		color: "#014D65",
		dataPoints: data
	}]
});
chart.render();
}
//Tổng số lượng vé theo phim
$scope.SumTicketMovie = function () {
let data = [];

$scope.top5ticketmovie.filter(function(e){
	var temp = { label: e.moviename, y: e.sum }
	data.push(temp)	
})

var chart = new CanvasJS.Chart("chartSumTicketMovie", {
	title: {
		text: "Top 5 phim số lượng mua cao nhất trong tháng"
	},
	axisY: {
		title: "Số vé",
		includeZero: true,
		suffix: " Vé"
	},
	data: [{
		type: "column",	
		yValueFormatString: "#,### Vé",
		indexLabel: "{y}",
		dataPoints: data
	}]
});

function updateChart() {
	var boilerColor, deltaY, yVal;
	var dps = chart.options.data[0].dataPoints;
//	console.log(dps)
	chart.options.data[0].dataPoints = dps; 
	//console.log(dps)
	chart.render();
};
updateChart();

setInterval(function() {updateChart()}, 500);

}

$scope.reportTicket = function () {

var dataPoints1 = [];
//var dataPoints2 = [];

var chart = new CanvasJS.Chart("reportticket", {
	zoomEnabled: true,
	title: {
		text: "Doanh thu vé mua theo thời gian thực"
	},
	axisX: {
	//	title: "chart updates every 3 secs"
	},
	axisY:{
		prefix: "$"
	}, 
	toolTip: {
		shared: true
	},
	legend: {
		cursor:"pointer",
		verticalAlign: "top",
		fontSize: 22,
		fontColor: "dimGrey",
		itemclick : toggleDataSeries
	},
	data: [{ 
		type: "line",
		xValueType: "dateTime",
		yValueFormatString: "$####.00",
		xValueFormatString: "hh:mm:ss TT",
		showInLegend: true,
		name: "Doanh thu vé",
		dataPoints: dataPoints1
		}/*,
		{				
			type: "line",
			xValueType: "dateTime",
			yValueFormatString: "$####.00",
			showInLegend: true,
			name: "Company B" ,
			dataPoints: dataPoints2
	}*/]
});

function toggleDataSeries(e) {
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	}
	else {
		e.dataSeries.visible = true;
	}
	chart.render();
}

var updateInterval = 1000;
// initial value
var yValue1 = 600; 
var yValue2 = 605;
//var a = "1974-03-12T"+ $scope.timeNow[0];
var time = new Date();
// starting at 9.30 am
//time.setMinutes(time.getMinutes()-5);
//time.setSeconds(00);
//time.setMilliseconds(00);

function updateChart(count) {
	
	count = count || 1;
	var deltaY1, deltaY2;
	for (var i = 0; i < count; i++) {
		if(i == 0) {
			$scope.getDatafromTimeNow();
		}
		time.setTime(time.getTime()+ updateInterval);
		
		deltaY1 = 0//.5 + Math.random() *(-.5-.5);
		deltaY2 = 0//.5 + Math.random() *(-.5-.5);
	// adding random value and rounding it to two digits. 
		
		
		$scope.NumberTicketTimenow.filter(function(e){
			if(time.getHours() == new Date("1974-03-12T"+e.order.time).getHours() && time.getMinutes() == new Date("1974-03-12T"+e.order.time).getMinutes()) {
				//alert(time.getTime()+" | "+new Date("1974-03-12T"+$scope.timeNow[0]).getTime())
				console.log(new Date("1974-03-12T"+$scope.timeNow[0]).getMinutes())
				deltaY1 = e.order.totalmoney;
				deltaY2 = 20;
			}
		})
		
	yValue1 = deltaY1 //Math.round((yValue1 + deltaY1)*100)/100;
	//yValue2 = deltaY2 //Math.round((yValue2 + deltaY2)*100)/100;

   //console.log(yValue1+"|"+i)
	//console.log(yValue2+"|"+i)

	// pushing the new values
	dataPoints1.push({
		x: time.getTime(),
		y: yValue1
	});
	/*dataPoints2.push({
		x: time.getTime(),
		y: yValue2
	});*/
	}
	
	// updating legend text with  updated with y Value 
	chart.options.data[0].legendText = " Doanh thu vé  VNĐ " + yValue1;
	//chart.options.data[1].legendText = " Company B  $" + yValue2; 
	chart.render();
}
// generates first set of dataPoints 
updateChart(1);	
setInterval(function(){updateChart()}, updateInterval);
}


//food
$scope.reportFood = function () {

var dataPoints1 = [];
var dataPoints2 = [];

var chart = new CanvasJS.Chart("reportfood", {
	zoomEnabled: true,
	title: {
		text: "Doanh thu đồ ăn theo thời gian thực trong ngày"
	},
	axisX: {
	//	title: "chart updates every 3 secs"
	},
	axisY:{
		prefix: "$"
	}, 
	toolTip: {
		shared: true
	},
	legend: {
		cursor:"pointer",
		verticalAlign: "top",
		fontSize: 22,
		fontColor: "dimGrey",
		itemclick : toggleDataSeriess
	},
	data: [{ 
		type: "line",
		xValueType: "dateTime",
		yValueFormatString: "$####.00",
		xValueFormatString: "hh:mm:ss TT",
		showInLegend: true,
		name: "Doanh thu vé",
		dataPoints: dataPoints1
		}/*,
		{				
			type: "line",
			xValueType: "dateTime",
			yValueFormatString: "$####.00",
			showInLegend: true,
			name: "Company B" ,
			dataPoints: dataPoints2
	}*/]
});

function toggleDataSeriess(e) {
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	}
	else {
		e.dataSeries.visible = true;
	}
	chart.render();
}

var updateInterval = 1000;
// initial value
var yValue1 = 600; 
var yValue2 = 605;
//var a = "1974-03-12T"+ $scope.timeNow[0];
var time = new Date();
// starting at 9.30 am
//time.setMinutes(time.getMinutes()-5);
//time.setSeconds(00);
//time.setMilliseconds(00);

function updateCharts(count) {
	
	count = count || 1;
	var deltaY1, deltaY2;
	for (var i = 0; i < count; i++) {
		if(i == 0) {
			$scope.getDatafromTimeNow();
		}
		time.setTime(time.getTime()+ updateInterval);
		
		deltaY1 = 0//.5 + Math.random() *(-.5-.5);
		deltaY2 = 0//.5 + Math.random() *(-.5-.5);
	// adding random value and rounding it to two digits. 
		
		
		$scope.NumberFoodTimenow.filter(function(e){
			if(time.getHours() == new Date("1974-03-12T"+e.order.time).getHours() && time.getMinutes() == new Date("1974-03-12T"+e.order.time).getMinutes()) {
				//alert(time.getTime()+" | "+new Date("1974-03-12T"+$scope.timeNow[0]).getTime())
				console.log(new Date("1974-03-12T"+$scope.timeNow[0]).getMinutes())
				deltaY1 = e.order.totalmoney;
				deltaY2 = 20;
			}
		})
		
	yValue1 = deltaY1 //Math.round((yValue1 + deltaY1)*100)/100;
	//yValue2 = deltaY2 //Math.round((yValue2 + deltaY2)*100)/100;

   //console.log(yValue1+"|"+i)
	//console.log(yValue2+"|"+i)

	// pushing the new values
	dataPoints1.push({
		x: time.getTime(),
		y: yValue1
	});
	/*dataPoints2.push({
		x: time.getTime(),
		y: yValue2
	});*/
	}
	
	// updating legend text with  updated with y Value 
	chart.options.data[0].legendText = " Doanh thu vé  VNĐ " + yValue1;
	//chart.options.data[1].legendText = " Company B  $" + yValue2; 
	chart.render();
}
// generates first set of dataPoints 
updateCharts(1);	
setInterval(function(){updateCharts()}, updateInterval);
}

	$scope.init();
});