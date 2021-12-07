var h = null;
var m = 0;
var s = 0;
function start()
{
    
    /*BƯỚC 1: LẤY GIÁ TRỊ BAN ĐẦU*/
    if (h === null)
    {
        h = 0;
        m = 50;
        s = 0;
    }
 
    /*BƯỚC 1: CHUYỂN ĐỔI DỮ LIỆU*/
    // Nếu số giây = -1 tức là đã chạy ngược hết số giây, lúc này:
    //  - giảm số phút xuống 1 đơn vị
    //  - thiết lập số giây lại 59
    if (s === -1){
        m -= 1;
        s = 59;
    }
 
    // Nếu số phút = -1 tức là đã chạy ngược hết số phút, lúc này:
    //  - giảm số giờ xuống 1 đơn vị
    //  - thiết lập số phút lại 59
    if (m === -1){
        h -= 1;
        m = 59;
    }
 
    // Nếu số giờ = -1 tức là đã hết giờ, lúc này:
    //  - Dừng chương trình
    if (h == -1){
        clearTimeout(timeout);
		var movieid = [];
		localStorage.setItem("movieID", movieid);
		var show = [];
		localStorage.setItem("show", show);
		var seat2 = [];
		var listSeats = JSON.stringify(seat2);
		localStorage.setItem("listSeats", listSeats);
		var totalSeat = [];
		var amountSeat = JSON.stringify(totalSeat);
		localStorage.setItem("amountSeat", amountSeat);
		
		var listFood = [];
		var food = JSON.stringify(listFood);
		localStorage.setItem("listFood", food);
		var amountFood = [];
		var amount = JSON.stringify(amountFood);
		localStorage.setItem("amount", amount);
		var total = JSON.stringify([]);
		localStorage.setItem("total", total);
		alert('Hết thời gian đặt vé');
        window.location.href = "/home";
        return false;
    }
 
    /*BƯỚC 1: HIỂN THỊ ĐỒNG HỒ*/
    $('#phut').val(m+ " phút");
    $('#s').val(s + " giây");
 
    /*BƯỚC 1: GIẢM PHÚT XUỐNG 1 GIÂY VÀ GỌI LẠI SAU 1 GIÂY */
    timeout = setTimeout(function(){
        s--;
        start();
    }, 1000);
   
}