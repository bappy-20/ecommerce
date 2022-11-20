/*var baseUrl= "http://43.224.110.67:8080/vms";
*/
$(document).ready(()=> {

$('#route_table').DataTable();


if(window.location.pathname=="/vms/admin/rent-assign/new")
{
	loadCheckPoint();
}


let actionButton = $('.btn.btn-light.move-all');
let actionButton1 = $('.btn.btn-light.remove-all');
actionButton[1].innerText = ">>>>";
actionButton1[1].innerText = "<<<<";
actionButton[1].style.marginTop = "10px";
actionButton[0].style.marginTop = "10px";
actionButton1[1].style.marginTop = "10px";
actionButton1[0].style.marginTop = "10px";

$("#test_1").addClass("test_1");
$("select")[0].style.minHeight = "208px";
$("select")[0].style.minWidth = "220px";
$("select")[1].style.minHeight = "208px";
$("select")[1].style.minWidth = "220px";
$("#route_name").style.maxWidth = "300px";
;


});


let rentAssingSuccess = (data)=> {
	
	let p =data.data.map(dt=>{return  '<option value="'+dt.id+'">'+dt.name+'</option>'}).join('');
	$("#test").html(p);
	duallistbox(document.getElementById('test'));
	
	
let actionButton = $('.btn.btn-light.move-all');
let actionButton1 = $('.btn.btn-light.remove-all');
actionButton[1].innerText = ">>>>";
actionButton1[1].innerText = "<<<<";
actionButton[1].style.marginTop = "40px";
actionButton[0].style.marginTop = "10px";
actionButton1[1].style.marginTop = "10px";
actionButton1[0].style.marginTop = "10px";

$("#test_1").addClass("test_1");
$("select")[0].style.minHeight = "208px";
$("select")[0].style.minWidth = "244px";
$("select")[1].style.minHeight = "208px";
$("select")[1].style.minWidth = "244px";

	
}

let rentAssingError = (data) => {

}

let loadCheckPoint = ()=> {
	let url ='/vms/api/check-points';
	let method = 'GET';
	
	
	backendRequest(url, method, rentAssingSuccess, rentAssingError);
}


let successRentAssign = (data) =>{
$('#message-container').text('New Route Create Successful');
$('#message-container').style.color ='black';
$('#message-container').show();
for(let i = 0;i<$('input').length;i++){
	let id = $('input')[i].id;
	if(id.length)
	{
	$('#'+id).val('');
	}
	
	
}
setTimeout(()=>{document.location.href='/vms/admin/rent-assign/new'},1000);

	
}

let errorRentAssign = (error) => {
	$('#message-container').text('New Route Create Unsuccessful');
	$('#message-container').show();
}

let saveRoutePointCost = (data) => {
	let url ='/vms/api/rent-assign';
	let method = 'POST';
	backendRequest(url, method, successRentAssign, errorRentAssign, data);
}



