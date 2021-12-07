var chair = [];
var cate = 'Thuong';
var SLCombo = [{
    "id": 0,
    "aquantity": 0,
    "price": 0
}];
var chairid = "";
var tongtien = 0;
function getslsp(){
    var priceproduct = 0;
    var combotitle = "";
    for(let sl of slsp){
    
        priceproduct = priceproduct + (Number(sl.price) * Number(sl.sl));
        if(sl.id != 0){
            var name = "";
            if(sl.id == 'i1'){
                name = "CGV SNACK COMBO";
            }else if(sl.id == 'i2'){
                name = "MY COMBO";
            }else if(sl.id == 'i3'){
                name = "CGV COMBO";
            }else if(sl.id == 'i4'){
                name = "SHIN-CHAN COMBO";
            }else{
                name = "MILO COMBO 2021";   
            }
            combotitle = combotitle + (name + ": "+sl.sl+"\n");
        }
    }



    console.log(combotitle);
    console.log(slsp);
    $('#giacombo').val(priceproduct);
    combotitle = combotitle.replace(",","\n")
       $('#combotitle').val(combotitle);

}
function next(){
    window.location.href = "/ticket/checkout";
}
 function load(){
    getslsp();
    //-------------------------------------
    chairid = "";
    chair =  JSON.parse(localStorage.getItem('chair'));
    $('#nameDisplay').val( localStorage.getItem('username'));
     $('#NumberDisplay').val( localStorage.getItem('number'));
     $('#seatsDisplay').val("");
     $('#seatsDisplay').val(chair);
   //  console.log(localStorage.getItem('username')  + " | " + localStorage.getItem('number') + " | "+ localStorage.getItem('chair'));
     document.getElementById('i1').setAttribute('value', 0);
     var chairstring = ""; 
     for(let c of chair){
            console.log(c.slice(0,1));
              if(c.slice(0,1) != 'A' && c.slice(0,1) != 'B' && c.slice(0,1) != 'C'){
                cate = 'VIP';
            }   
            chairid =  chairid + c + ",  ";  
        }
        //Lấy giá phim và nhân với số ghế sau đó đưa lên giá phim 
        var pricephim = localStorage.getItem('price');

        $('#giaphim').val(chair.length*pricephim);
        chairid = chairid.slice(0, chairid.length-3);
        document.getElementById('cate_chair').innerHTML = cate;
        document.getElementById('chair_id').innerHTML = chairid;
        
        tinhtongtien();
        document.getElementById('tongtien').innerHTML = tongtien;
}

function tinhtongtien(){
   var giaphim = $("#giaphim").val();
   var giacombo = $("#giacombo").val();
    giaphim = Number(giaphim);
    giacombo  = Number(giacombo);
   // console.log(giaphim + giacombo);
    tongtien = giaphim+giacombo;
}
var slsp = [
    {
        "id": 0,    
        "sl": 0,
        "price": 0
    }
];
var sl = 0;
function tangsl(id,gia){
    var tim = slsp.find(a => a.id == id);
    if(tim == undefined){
    sl = 1;
        var temp = {
        "id": id,
        "sl": sl,
        "price": gia
    }
    slsp.push(temp);
    $('#'+id).val(sl);

}else{
    if(tim.sl >= 99){
        alert("Bạn không thể đặt số lượng lớn hơn 99")
    }else{
    //  console.log("TSoluong: "+tim.sl);
    sl = tim.sl;
    sl = sl + 1;
   console.log("Soluong: "+sl);
   var index = slsp.findIndex(s => s.id == id);
    if(index >= 0){
        slsp.splice(index, 1);
        console.log(index);
    }
    var temp = {
        "id": id,
        "sl": sl,
        "price": gia
    }
    console.log(tim);
    slsp.push(temp);
    document.getElementById(id).setAttribute('value', sl);
    $('#'+id).val(sl);
   
}

}
// var priceproduct = 0;
// var combotitle = "";
// for(let sl of slsp){

//     priceproduct = priceproduct + (Number(sl.price) * Number(sl.sl));
//     if(sl.id != 0){
//         combotitle = combotitle + (sl.id + "<b>"+sl.sl+"</b> <br>");
//     }
// }

// document.getElementById('combotitle').innerHTML = combotitle;
// console.log(combotitle);
// console.log(slsp);
// $('#giacombo').val(priceproduct);
load();
}



function prev(){
    window.location.href='/datve.html';
}
function giamsl(id,gia){
   var tim = slsp.find(a => a.id == id);
    if(tim == undefined){
        sl = 0;
        var temp = {
            "id": id,
            "sl": sl
        }
        slsp.push(temp);
        alert("Số lượng không thể nhỏ hơn 0")
    }else{
         sl = tim.sl;
        if(tim.sl <= 0 ){
            alert("Số lượng không thể nhỏ hơn 0");
        }else{
            sl--;
        var temp = {
            "id": id,
            "sl": sl
        }
        slsp.splice(tim);
        slsp.push(temp);
        document.getElementById(id).setAttribute('value', sl);
    }
    }
}
load();