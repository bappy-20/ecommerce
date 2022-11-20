var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#userAddForm').validate({
	rules:{
	fullname:"required",
	username:"required",
	userType:"required",
	nid:"required",
	mobile:"required",
	},messages:{
		fullname:"Please enter full name",
		username:"Please enter user name",
		userType:"Please enter user type",
		nid:"Please enter nid",
		mobile:"Please enter mobile number",	
	},
	submitHandler:function(form){
		submitUserFrom();		
	}	
});

jQuery('#userEditForm').validate({
	rules:{
	fullname:"required",
	username:"required",
	userType:"required",
	nid:"required",
	mobile:"required",
	},messages:{
		fullname:"Please enter full name",
		username:"Please enter user name",
		userType:"Please enter user type",
		nid:"Please enter nid",
		mobile:"Please enter mobile number",
	},
	submitHandler:function(form){
		submitUserEditFrom();
	}	
});

function submitUserFrom() {
	this.event.preventDefault();
    let user = {};
    user["fullName"] = $("#fullName").val();
    user["username"] = $("#username").val();
    user["email"] = $("#email").val();
    user["password"] = $("#password").val();
    user["userType"] = $("#userType").val();
    user["mobile"] = $("#mobile").val();
    user["presentAddress"] = $("#presentAddress").val();
    user["nidNumber"] = $("#nidNumber").val();
    user["bloodGroup"] = $("#bloodGroup").val();
    user["profileImage"] = $("#profileImage").val();
    user["nidCopy"] = $("#nidImage").val();
    submitUserFromToServer(user);
}


function submitUserFromToServer(data) {

   let url = baseUrl + "/api/user/registration"
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
    		swal(`${result.message}`);
    		swal("New User Created");
 			window.location.href = baseUrl + "/admin/user";
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
             //alert("Problem");
        }
    });
}

function getUser(id){

	let url = baseUrl + "/api/user/"+id;
	
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheUserValues(result);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheUserValues(result){

	 $("#id").val(result.id);
	 $("#fullName").val(result.fullName);
     $("#username").val(result.username);
   	 $("#password").val(result.password);
   	 $("#email").val(result.email);
     $("#userType").val(result.userType).change();
     $("#mobile").val(result.mobile);
     $("#presentAddress").val(result.presentAddress);
   	 $("#nidNumber").val(result.nidNumber);
  	 $("#bloodGroup").val(result.bloodGroup).change();
 	 $("#profileImage").val(result.profileImage);
    $("#nidImage").val(result.nidCopy);
}


function submitUserEditFrom() {

	this.event.preventDefault();
    let user = {};
    let id = $("#id").val();
    user ["id"] = id;
    user["fullName"] = $("#fullName").val();
    user["username"] = $("#username").val();
    user["password"] = $("#password").val();
    user["email"] = $("#email").val();
    user["userType"] = $("#userType").val();
    user["mobile"] = $("#mobile").val();
    user["presentAddress"] = $("#presentAddress").val();
    user["nidNumber"] = $("#nidNumber").val();
    user["bloodGroup"] = $("#bloodGroup").val();
    user["profileImage"] = $("#profileImage").val();
    user["nidCopy"] = $("#nidImage").val();
    submitUserEditFromToServer(user, id);
}

function submitUserEditFromToServer(data, id) {
    let url = baseUrl + "/api/user/update/"+id;
    
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
            //console.log(result);
    		swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/user";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function deleteEntry(id) {		
	let url = baseurl+"/api/delete-user/"+id;
	
    	  swal({
    	      title: "Are you sure you want to delete?",
    	      text: "You will not be able to recover this data!",
    	      icon: "warning",
    	      buttons: [
    	        'No, cancel it!',
    	        'Yes, I am sure!'
    	      ],
    	      dangerMode: true,
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	        swal({
    	          title: 'Yes!',
    	          text: 'Deletion Successful!!',
    	          icon: 'success'
    	        }).then(function() {
    	        	 $.ajax({
    	                 type: "DELETE",
    	                 url: url,
    	                 contentType: 'application/json; charset=utf-8',
    	                 dataType: "JSON",
    	                 success: function (response) {
    	               	  window.location.reload();
    	                 },
    	                 error: function () {
    	                     Swal.fire('error')
    	                 }
    	             }); 
    	          
    	        });
    	      } else {
    	        swal("Cancelled", "Your data is safe :)", "error");
    	      }
    	    });
	}

function submitPasswordEditFrom() {

	this.event.preventDefault();
    let user = {};
    user["password"] = $("#password").val();
    user["corfirmPassword"] = $("#corfirmPassword").val();  
   submitPasswordEditFromToServer(user) ;
} 

function submitPasswordEditFromToServer(data) {
    let url = baseUrl + "/api/user/update/password";
    
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
            //console.log(result);
    		swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/user/changepassword";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}   
   
   
function getUserProfile(id){
	let url = baseUrl + "/api/user-profile/"+id;
	
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
				console.log(result);
	            setTheUserProfileValues(result.data);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}   
function setTheUserProfileValues(result){
	
	 $("#id").text(result.id);
	 $("#fullName").text(result.fullName);
     $("#nickName").text(result.nickName);
     $("#username").text(result.username);
   	 $("#password").text(result.password);
   	 $("#email").text(result.email);
     $("#userType").text(result.userType).change();
     $("#mobile").text(result.mobile);
     $("#presentAddress").text(result.presentAddress);
     $("#permanentAddress").text(result.permanentAddress);
     $("#age").text(result.age);
     $("#marritalStatus").text(result.marritalStatus).change();
   	 $("#nidNumber").text(result.nidNumber);
  	 $("#bloodGroup").text(result.bloodGroup).change();
 	 $("#driverLiecense").text(result.driverLiecense);
 	 $("#profileImage").text(result.profileImage);
 	 $("#expiredDate").text(result.expiredDate);
	
}

$("#roleName").change(function () {
	
    var role = $(this).val();
    $.ajax({
       type: "GET",
        dataType: "json",
         url: baseUrl+'/api/role-list',
        success:function(result) {
    				//let length =  result.length;
    				///alert(length)
    				for (let i = 0; i < result.length; i++) {
    					//alert(result[i].roleName)
    					if(result[i].roleName == role) {
    						document.getElementById('saveButton').style.visibility='hidden';
    						swal(`role is already assigned,you only can modify`);    						

    					}
    					}
        		}
        	});
    
});

   
// function setTheUserProfileValues(result){
//
//	 $("#id").text(result.id);
//	 $("#fullName").text(result.fullName);
//     $("#nickName").text(result.nickName);
//     $("#username").text(result.username);
//   	 $("#password").text(result.password);
//   	 $("#email").text(result.email);
//     $("#userType").text(result.userType).change();
//     $("#mobile").text(result.mobile);
//     $("#presentAddress").text(result.presentAddress);
//     $("#permanentAddress").text(result.permanentAddress);
//     $("#age").text(result.age);
//     $("#marritalStatus").text(result.marritalStatus).change();
//   	 $("#nidNumber").text(result.nidNumber);
//  	 $("#bloodGroup").text(result.bloodGroup).change();
// 	 $("#driverLiecense").text(result.driverLiecense);
// 	 $("#profileImage").text(result.profileImage);
// 	 $("#expiredDate").text(result.expiredDate);
//}  
 
 $('.allcheck').click(function(event) {
	    "use strict";
	    var acname=$(this).attr('title');
	    var mid=$(this).attr('usemap');
	    var myclass=acname+'_'+mid;
	    $("."+myclass).prop('checked', $(this).prop("checked"));
	    
	  });
 
 function getTableValue(){	 
	 this.event.preventDefault();
	    var menu=[];
	    var menuObj={};
	    
	    menuObj["roleName"]=$("#roleName").val();	    
	    $("#administrative").find('> tbody > tr').each(function () {
	    let subMenu={};
	    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
	    
	    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
	    	subMenu["canCreate"]=true;
        }else{
        	subMenu["canCreate"]=false;
        }
	    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
	    	subMenu["canRead"]=true;
        }else{
        	subMenu["canRead"]=false;
        }
	    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
	    	subMenu["canEdit"]=true;
        }else{
        	subMenu["canEdit"]=false;
        }
	    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
	    	subMenu["canDelete"]=true;
        }else{
        	subMenu["canDelete"]=false;
        }
	    menu.push(subMenu);
	   });
	   
	    $("#employee").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   });
	    $("#productMenu").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#purchaseProduct").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#productCampaign").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#DistributorRequisition").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#geoMapping").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#retailManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#routeManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#orderManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#expenseManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#attendanceManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#leaveManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#trackUserTable").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#visitHistory").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#noticeManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#reports").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    menuObj["subMenu"]=menu;
	    submitMenuToServer(menuObj);
	    
 }
 
 function submitMenuToServer(data) {
	 //alert(JSON.stringify(data));
	    let url = baseUrl + "/api/menu";
	    
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "JSON",
	        success: function (result) {
	        	///alert("Create Successful!");
	        	swal(`${result.message}`);
	        	window.location.href=baseUrl+"/admin/manage-role";
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	} 
 
 function getRole(id){

		let url = baseUrl + "/api/role/"+id;
		
		$.ajax({
		        type: "GET",
		        url: url,
		        dataType : "JSON",
		        success: function (result) {
		            setTheRoleValues(result.data);
		           
		        },
		        error: function (e) {
		            console.log("ERROR: ", e);
		        }
		    });

	}
 
 function setTheRoleValues(data){
	$("#id").val(data.id);
	$("#roleName").val(data.roleName);	
	 $.each(data.subMenu,function(i,obj){
		
		 if(obj.subMenuName=="Org"){
			if(obj.canCreate==true){
			  $("#create_0_0").prop( "checked", true );
			}else{
				$("#create_0_0").prop( "checked", false );
			}
			if(obj.canRead==true){
				  $("#read_0_0").prop( "checked", true );
				}else{
					$("#read_0_0").prop( "checked", false );
				}
			if(obj.canEdit==true){
				  $("#edit_0_0").prop( "checked", true );
				}else{
					$("#edit_0_0").prop( "checked", false );
				}
			if(obj.canDelete==true){
				  $("#delete_0_0").prop( "checked", true );
				}else{
					$("#delete_0_0").prop( "checked", false );
				}
			 
		 }
		 
		 if(obj.subMenuName=="Dist"){
				if(obj.canCreate==true){
				  $("#create_0_1").prop( "checked", true );
				}else{
					$("#create_0_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_0_1").prop( "checked", true );
					}else{
						$("#read_0_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_0_1").prop( "checked", true );
					}else{
						$("#edit_0_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_0_1").prop( "checked", true );
					}else{
						$("#delete_0_1").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="supplier"){
				if(obj.canCreate==true){
				  $("#create_0_2").prop( "checked", true );
				}else{
					$("#create_0_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_0_2").prop( "checked", true );
					}else{
						$("#read_0_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_0_2").prop( "checked", true );
					}else{
						$("#edit_0_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_0_2").prop( "checked", true );
					}else{
						$("#delete_0_2").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="webuser"){
				if(obj.canCreate==true){
				  $("#create_0_3").prop( "checked", true );
				}else{
					$("#create_0_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_0_3").prop( "checked", true );
					}else{
						$("#read_0_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_0_3").prop( "checked", true );
					}else{
						$("#edit_0_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_0_3").prop( "checked", true );
					}else{
						$("#delete_0_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="createrole"){
				if(obj.canCreate==true){
				  $("#create_0_4").prop( "checked", true );
				}else{
					$("#create_0_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_0_4").prop( "checked", true );
					}else{
						$("#read_0_4").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_0_4").prop( "checked", true );
					}else{
						$("#edit_0_4").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_0_4").prop( "checked", true );
					}else{
						$("#delete_0_4").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="rolemanagement"){
				if(obj.canCreate==true){
				  $("#create_0_5").prop( "checked", true );
				}else{
					$("#create_0_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_0_5").prop( "checked", true );
					}else{
						$("#read_0_5").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_0_5").prop( "checked", true );
					}else{
						$("#edit_0_5").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_0_5").prop( "checked", true );
					}else{
						$("#delete_0_5").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="empType"){
				if(obj.canCreate==true){
				  $("#create_1_0").prop( "checked", true );
				}else{
					$("#create_1_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_1_0").prop( "checked", true );
					}else{
						$("#read_1_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_1_0").prop( "checked", true );
					}else{
						$("#edit_1_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_1_0").prop( "checked", true );
					}else{
						$("#delete_1_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="appUser"){
				if(obj.canCreate==true){
				  $("#create_1_1").prop( "checked", true );
				}else{
					$("#create_1_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_1_1").prop( "checked", true );
					}else{
						$("#read_1_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_1_1").prop( "checked", true );
					}else{
						$("#edit_1_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_1_1").prop( "checked", true );
					}else{
						$("#delete_1_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="targetSR"){
				if(obj.canCreate==true){
				  $("#create_1_2").prop( "checked", true );
				}else{
					$("#create_1_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_1_2").prop( "checked", true );
					}else{
						$("#read_1_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_1_2").prop( "checked", true );
					}else{
						$("#edit_1_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_1_2").prop( "checked", true );
					}else{
						$("#delete_1_2").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="targetDE"){
				if(obj.canCreate==true){
				  $("#create_1_3").prop( "checked", true );
				}else{
					$("#create_1_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_1_3").prop( "checked", true );
					}else{
						$("#read_1_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_1_3").prop( "checked", true );
					}else{
						$("#edit_1_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_1_3").prop( "checked", true );
					}else{
						$("#delete_1_3").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="productCategory"){
				if(obj.canCreate==true){
				  $("#create_3_0").prop( "checked", true );
				}else{
					$("#create_3_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_0").prop( "checked", true );
					}else{
						$("#read_3_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_0").prop( "checked", true );
					}else{
						$("#edit_3_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_0").prop( "checked", true );
					}else{
						$("#delete_3_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="measurementUnit"){
				if(obj.canCreate==true){
				  $("#create_3_1").prop( "checked", true );
				}else{
					$("#create_3_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_1").prop( "checked", true );
					}else{
						$("#read_3_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_1").prop( "checked", true );
					}else{
						$("#edit_3_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_1").prop( "checked", true );
					}else{
						$("#delete_3_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="company"){
				if(obj.canCreate==true){
				  $("#create_3_2").prop( "checked", true );
				}else{
					$("#create_3_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_2").prop( "checked", true );
					}else{
						$("#read_3_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_2").prop( "checked", true );
					}else{
						$("#edit_3_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_2").prop( "checked", true );
					}else{
						$("#delete_3_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="brand"){
				if(obj.canCreate==true){
				  $("#create_3_3").prop( "checked", true );
				}else{
					$("#create_3_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_3").prop( "checked", true );
					}else{
						$("#read_3_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_3").prop( "checked", true );
					}else{
						$("#edit_3_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_3").prop( "checked", true );
					}else{
						$("#delete_3_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="product"){
				if(obj.canCreate==true){
				  $("#create_3_4").prop( "checked", true );
				}else{
					$("#create_3_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_4").prop( "checked", true );
					}else{
						$("#read_3_4").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_4").prop( "checked", true );
					}else{
						$("#edit_3_4").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_4").prop( "checked", true );
					}else{
						$("#delete_3_4").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="productBatch"){
				if(obj.canCreate==true){
				  $("#create_3_5").prop( "checked", true );
				}else{
					$("#create_3_5").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_5").prop( "checked", true );
					}else{
						$("#read_3_5").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_5").prop( "checked", true );
					}else{
						$("#edit_3_5").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_5").prop( "checked", true );
					}else{
						$("#delete_3_5").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="mappingProduct"){
				if(obj.canCreate==true){
				  $("#create_3_6").prop( "checked", true );
				}else{
					$("#create_3_6").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_6").prop( "checked", true );
					}else{
						$("#read_3_6").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_6").prop( "checked", true );
					}else{
						$("#edit_3_6").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_6").prop( "checked", true );
					}else{
						$("#delete_3_6").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="productPrice"){
				if(obj.canCreate==true){
				  $("#create_3_7").prop( "checked", true );
				}else{
					$("#create_3_7").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_7").prop( "checked", true );
					}else{
						$("#read_3_7").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_7").prop( "checked", true );
					}else{
						$("#edit_3_7").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_7").prop( "checked", true );
					}else{
						$("#delete_3_7").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="priceHistory"){
				if(obj.canCreate==true){
				  $("#create_3_8").prop( "checked", true );
				}else{
					$("#create_3_8").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_3_8").prop( "checked", true );
					}else{
						$("#read_3_8").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_3_8").prop( "checked", true );
					}else{
						$("#edit_3_8").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_3_8").prop( "checked", true );
					}else{
						$("#delete_3_8").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="productPurchase"){
				if(obj.canCreate==true){
				  $("#create_4_0").prop( "checked", true );
				}else{
					$("#create_4_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_4_0").prop( "checked", true );
					}else{
						$("#read_4_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_4_0").prop( "checked", true );
					}else{
						$("#edit_4_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_4_0").prop( "checked", true );
					}else{
						$("#delete_4_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="returnPurchase"){
				if(obj.canCreate==true){
				  $("#create_4_1").prop( "checked", true );
				}else{
					$("#create_4_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_4_1").prop( "checked", true );
					}else{
						$("#read_4_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_4_1").prop( "checked", true );
					}else{
						$("#edit_4_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_4_1").prop( "checked", true );
					}else{
						$("#delete_4_1").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="campaignType"){
				if(obj.canCreate==true){
				  $("#create_5_0").prop( "checked", true );
				}else{
					$("#create_5_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_5_0").prop( "checked", true );
					}else{
						$("#read_5_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_5_0").prop( "checked", true );
					}else{
						$("#edit_5_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_5_0").prop( "checked", true );
					}else{
						$("#delete_5_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="campaign"){
				if(obj.canCreate==true){
				  $("#create_5_1").prop( "checked", true );
				}else{
					$("#create_5_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_5_1").prop( "checked", true );
					}else{
						$("#read_5_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_5_1").prop( "checked", true );
					}else{
						$("#edit_5_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_5_1").prop( "checked", true );
					}else{
						$("#delete_5_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="cashBackCampaign"){
				if(obj.canCreate==true){
				  $("#create_5_2").prop( "checked", true );
				}else{
					$("#create_5_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_5_2").prop( "checked", true );
					}else{
						$("#read_5_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_5_2").prop( "checked", true );
					}else{
						$("#edit_5_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_5_2").prop( "checked", true );
					}else{
						$("#delete_5_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="requisition"){
				if(obj.canCreate==true){
				  $("#create_6_0").prop( "checked", true );
				}else{
					$("#create_6_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_0").prop( "checked", true );
					}else{
						$("#read_6_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_0").prop( "checked", true );
					}else{
						$("#edit_6_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_0").prop( "checked", true );
					}else{
						$("#delete_6_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="supplyChainRequisition"){
				if(obj.canCreate==true){
				  $("#create_6_1").prop( "checked", true );
				}else{
					$("#create_6_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_1").prop( "checked", true );
					}else{
						$("#read_6_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_1").prop( "checked", true );
					}else{
						$("#edit_6_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_1").prop( "checked", true );
					}else{
						$("#delete_6_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="operationRequisition"){
				if(obj.canCreate==true){
				  $("#create_6_2").prop( "checked", true );
				}else{
					$("#create_6_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_2").prop( "checked", true );
					}else{
						$("#read_6_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_2").prop( "checked", true );
					}else{
						$("#edit_6_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_2").prop( "checked", true );
					}else{
						$("#delete_6_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="approvedRequisition"){
				if(obj.canCreate==true){
				  $("#create_6_3").prop( "checked", true );
				}else{
					$("#create_6_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_3").prop( "checked", true );
					}else{
						$("#read_6_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_3").prop( "checked", true );
					}else{
						$("#edit_6_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_3").prop( "checked", true );
					}else{
						$("#delete_6_3").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="verifiedRequisition"){
				if(obj.canCreate==true){
				  $("#create_6_4").prop( "checked", true );
				}else{
					$("#create_6_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_4").prop( "checked", true );
					}else{
						$("#read_6_4").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_4").prop( "checked", true );
					}else{
						$("#edit_6_4").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_4").prop( "checked", true );
					}else{
						$("#delete_6_4").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="deliveredRequisition"){
				if(obj.canCreate==true){
				  $("#create_6_5").prop( "checked", true );
				}else{
					$("#create_6_5").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_6_5").prop( "checked", true );
					}else{
						$("#read_6_5").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_6_5").prop( "checked", true );
					}else{
						$("#edit_6_5").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_6_5").prop( "checked", true );
					}else{
						$("#delete_6_5").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="region"){
				if(obj.canCreate==true){
				  $("#create_7_0").prop( "checked", true );
				}else{
					$("#create_7_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_7_0").prop( "checked", true );
					}else{
						$("#read_7_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_7_0").prop( "checked", true );
					}else{
						$("#edit_7_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_7_0").prop( "checked", true );
					}else{
						$("#delete_7_0").prop( "checked", false );
					}
				 
			 }		 
		 if(obj.subMenuName=="area"){
				if(obj.canCreate==true){
				  $("#create_7_1").prop( "checked", true );
				}else{
					$("#create_7_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_7_1").prop( "checked", true );
					}else{
						$("#read_7_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_7_1").prop( "checked", true );
					}else{
						$("#edit_7_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_7_1").prop( "checked", true );
					}else{
						$("#delete_7_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="territory"){
				if(obj.canCreate==true){
				  $("#create_7_2").prop( "checked", true );
				}else{
					$("#create_7_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_7_2").prop( "checked", true );
					}else{
						$("#read_7_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_7_2").prop( "checked", true );
					}else{
						$("#edit_7_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_7_2").prop( "checked", true );
					}else{
						$("#delete_7_2").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="market"){
				if(obj.canCreate==true){
				  $("#create_7_3").prop( "checked", true );
				}else{
					$("#create_7_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_7_3").prop( "checked", true );
					}else{
						$("#read_7_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_7_3").prop( "checked", true );
					}else{
						$("#edit_7_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_7_3").prop( "checked", true );
					}else{
						$("#delete_7_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="retailType"){
				if(obj.canCreate==true){
				  $("#create_8_0").prop( "checked", true );
				}else{
					$("#create_8_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_8_0").prop( "checked", true );
					}else{
						$("#read_8_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_8_0").prop( "checked", true );
					}else{
						$("#edit_8_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_8_0").prop( "checked", true );
					}else{
						$("#delete_8_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="retail"){
				if(obj.canCreate==true){
				  $("#create_8_1").prop( "checked", true );
				}else{
					$("#create_8_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_8_1").prop( "checked", true );
					}else{
						$("#read_8_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_8_1").prop( "checked", true );
					}else{
						$("#edit_8_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_8_1").prop( "checked", true );
					}else{
						$("#delete_8_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="pendingRetail"){
				if(obj.canCreate==true){
				  $("#create_8_2").prop( "checked", true );
				}else{
					$("#create_8_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_8_2").prop( "checked", true );
					}else{
						$("#read_8_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_8_2").prop( "checked", true );
					}else{
						$("#edit_8_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_8_2").prop( "checked", true );
					}else{
						$("#delete_8_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="retailComplain"){
				if(obj.canCreate==true){
				  $("#create_8_3").prop( "checked", true );
				}else{
					$("#create_8_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_8_3").prop( "checked", true );
					}else{
						$("#read_8_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_8_3").prop( "checked", true );
					}else{
						$("#edit_8_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_8_3").prop( "checked", true );
					}else{
						$("#delete_8_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="route"){
				if(obj.canCreate==true){
				  $("#create_9_0").prop( "checked", true );
				}else{
					$("#create_9_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_9_0").prop( "checked", true );
					}else{
						$("#read_9_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_9_0").prop( "checked", true );
					}else{
						$("#edit_9_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_9_0").prop( "checked", true );
					}else{
						$("#delete_9_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="assignRoute"){
				if(obj.canCreate==true){
				  $("#create_9_1").prop( "checked", true );
				}else{
					$("#create_9_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_9_1").prop( "checked", true );
					}else{
						$("#read_9_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_9_1").prop( "checked", true );
					}else{
						$("#edit_9_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_9_1").prop( "checked", true );
					}else{
						$("#delete_9_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="orderDetails"){
				if(obj.canCreate==true){
				  $("#create_10_0").prop( "checked", true );
				}else{
					$("#create_10_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_10_0").prop( "checked", true );
					}else{
						$("#read_10_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_10_0").prop( "checked", true );
					}else{
						$("#edit_10_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_10_0").prop( "checked", true );
					}else{
						$("#delete_10_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="returnOrder"){
				if(obj.canCreate==true){
				  $("#create_10_1").prop( "checked", true );
				}else{
					$("#create_10_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_10_1").prop( "checked", true );
					}else{
						$("#read_10_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_10_1").prop( "checked", true );
					}else{
						$("#edit_10_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_10_1").prop( "checked", true );
					}else{
						$("#delete_10_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="expenseType"){
				if(obj.canCreate==true){
				  $("#create_11_0").prop( "checked", true );
				}else{
					$("#create_11_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_0").prop( "checked", true );
					}else{
						$("#read_11_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_0").prop( "checked", true );
					}else{
						$("#edit_11_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_0").prop( "checked", true );
					}else{
						$("#delete_11_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="expense"){
				if(obj.canCreate==true){
				  $("#create_11_1").prop( "checked", true );
				}else{
					$("#create_11_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_1").prop( "checked", true );
					}else{
						$("#read_11_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_1").prop( "checked", true );
					}else{
						$("#edit_11_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_1").prop( "checked", true );
					}else{
						$("#delete_11_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="approvedExpense"){
				if(obj.canCreate==true){
				  $("#create_11_2").prop( "checked", true );
				}else{
					$("#create_11_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_2").prop( "checked", true );
					}else{
						$("#read_11_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_2").prop( "checked", true );
					}else{
						$("#edit_11_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_2").prop( "checked", true );
					}else{
						$("#delete_11_2").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="dateWiseExpense"){
				if(obj.canCreate==true){
				  $("#create_11_3").prop( "checked", true );
				}else{
					$("#create_11_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_3").prop( "checked", true );
					}else{
						$("#read_11_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_3").prop( "checked", true );
					}else{
						$("#edit_11_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_3").prop( "checked", true );
					}else{
						$("#delete_11_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="dateWisePendingExpense"){
				if(obj.canCreate==true){
				  $("#create_11_4").prop( "checked", true );
				}else{
					$("#create_11_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_4").prop( "checked", true );
					}else{
						$("#read_11_4").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_4").prop( "checked", true );
					}else{
						$("#edit_11_4").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_4").prop( "checked", true );
					}else{
						$("#delete_11_4").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="expenseBy"){
				if(obj.canCreate==true){
				  $("#create_11_5").prop( "checked", true );
				}else{
					$("#create_11_5").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_11_5").prop( "checked", true );
					}else{
						$("#read_11_5").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_11_5").prop( "checked", true );
					}else{
						$("#edit_11_5").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_11_5").prop( "checked", true );
					}else{
						$("#delete_11_5").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="dateWiseAttendance"){
				if(obj.canCreate==true){
				  $("#create_12_0").prop( "checked", true );
				}else{
					$("#create_12_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_12_0").prop( "checked", true );
					}else{
						$("#read_12_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_12_0").prop( "checked", true );
					}else{
						$("#edit_12_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_12_0").prop( "checked", true );
					}else{
						$("#delete_12_0").prop( "checked", false );
					}
				 
			 }
		 
		 if(obj.subMenuName=="employeWiseAttedance"){
				if(obj.canCreate==true){
				  $("#create_12_1").prop( "checked", true );
				}else{
					$("#create_12_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_12_1").prop( "checked", true );
					}else{
						$("#read_12_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_12_1").prop( "checked", true );
					}else{
						$("#edit_12_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_12_1").prop( "checked", true );
					}else{
						$("#delete_12_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="dailyAttendance"){
				if(obj.canCreate==true){
				  $("#create_12_2").prop( "checked", true );
				}else{
					$("#create_12_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_12_2").prop( "checked", true );
					}else{
						$("#read_12_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_12_2").prop( "checked", true );
					}else{
						$("#edit_12_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_12_2").prop( "checked", true );
					}else{
						$("#delete_12_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="leave"){
				if(obj.canCreate==true){
				  $("#create_13_0").prop( "checked", true );
				}else{
					$("#create_13_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_13_0").prop( "checked", true );
					}else{
						$("#read_13_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_13_0").prop( "checked", true );
					}else{
						$("#edit_13_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_13_0").prop( "checked", true );
					}else{
						$("#delete_13_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="pendingLeave"){
				if(obj.canCreate==true){
				  $("#create_13_1").prop( "checked", true );
				}else{
					$("#create_13_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_13_1").prop( "checked", true );
					}else{
						$("#read_13_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_13_1").prop( "checked", true );
					}else{
						$("#edit_13_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_13_1").prop( "checked", true );
					}else{
						$("#delete_13_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="approvedLeave"){
				if(obj.canCreate==true){
				  $("#create_13_2").prop( "checked", true );
				}else{
					$("#create_13_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_13_2").prop( "checked", true );
					}else{
						$("#read_13_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_13_2").prop( "checked", true );
					}else{
						$("#edit_13_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_13_2").prop( "checked", true );
					}else{
						$("#delete_13_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="trackUser"){
				if(obj.canCreate==true){
				  $("#create_14_0").prop( "checked", true );
				}else{
					$("#create_14_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_14_0").prop( "checked", true );
					}else{
						$("#read_14_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_14_0").prop( "checked", true );
					}else{
						$("#edit_14_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_14_0").prop( "checked", true );
					}else{
						$("#delete_14_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="trackingAllUsers"){
				if(obj.canCreate==true){
				  $("#create_14_1").prop( "checked", true );
				}else{
					$("#create_14_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_14_1").prop( "checked", true );
					}else{
						$("#read_14_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_14_1").prop( "checked", true );
					}else{
						$("#edit_14_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_14_1").prop( "checked", true );
					}else{
						$("#delete_14_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="employeeVisitReport"){
				if(obj.canCreate==true){
				  $("#create_15_0").prop( "checked", true );
				}else{
					$("#create_15_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_15_0").prop( "checked", true );
					}else{
						$("#read_15_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_15_0").prop( "checked", true );
					}else{
						$("#edit_15_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_15_0").prop( "checked", true );
					}else{
						$("#delete_15_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="notification"){
				if(obj.canCreate==true){
				  $("#create_16_0").prop( "checked", true );
				}else{
					$("#create_16_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_16_0").prop( "checked", true );
					}else{
						$("#read_16_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_16_0").prop( "checked", true );
					}else{
						$("#edit_16_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_16_0").prop( "checked", true );
					}else{
						$("#delete_16_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="productInventory"){
				if(obj.canCreate==true){
				  $("#create_17_0").prop( "checked", true );
				}else{
					$("#create_17_0").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_17_0").prop( "checked", true );
					}else{
						$("#read_17_0").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_17_0").prop( "checked", true );
					}else{
						$("#edit_17_0").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_17_0").prop( "checked", true );
					}else{
						$("#delete_17_0").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="pendingOrder"){
				if(obj.canCreate==true){
				  $("#create_17_1").prop( "checked", true );
				}else{
					$("#create_17_1").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_17_1").prop( "checked", true );
					}else{
						$("#read_17_1").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_17_1").prop( "checked", true );
					}else{
						$("#edit_17_1").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_17_1").prop( "checked", true );
					}else{
						$("#delete_17_1").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="deliveredOrder"){
				if(obj.canCreate==true){
				  $("#create_17_2").prop( "checked", true );
				}else{
					$("#create_17_2").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_17_2").prop( "checked", true );
					}else{
						$("#read_17_2").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_17_2").prop( "checked", true );
					}else{
						$("#edit_17_2").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_17_2").prop( "checked", true );
					}else{
						$("#delete_17_2").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="retailWiseOrder"){
				if(obj.canCreate==true){
				  $("#create_17_3").prop( "checked", true );
				}else{
					$("#create_17_3").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_17_3").prop( "checked", true );
					}else{
						$("#read_17_3").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_17_3").prop( "checked", true );
					}else{
						$("#edit_17_3").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_17_3").prop( "checked", true );
					}else{
						$("#delete_17_3").prop( "checked", false );
					}
				 
			 }
		 if(obj.subMenuName=="dateWiseProductDelivery"){
				if(obj.canCreate==true){
				  $("#create_17_4").prop( "checked", true );
				}else{
					$("#create_17_4").prop( "checked", false );
				}
				if(obj.canRead==true){
					  $("#read_17_4").prop( "checked", true );
					}else{
						$("#read_17_4").prop( "checked", false );
					}
				if(obj.canEdit==true){
					  $("#edit_17_4").prop( "checked", true );
					}else{
						$("#edit_17_4").prop( "checked", false );
					}
				if(obj.canDelete==true){
					  $("#delete_17_4").prop( "checked", true );
					}else{
						$("#delete_17_4").prop( "checked", false );
					}
				 
			 }
		 
	 });
 }
 function prepareDataForSubmit(){	 
	 this.event.preventDefault();
	    var menu=[];
	    var menuObj={};
	    
	    menuObj["roleName"]=$("#roleName").val();
	    
	    $("#administrative").find('> tbody > tr').each(function () {
	    let subMenu={};
	    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
	    
	    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
	    	subMenu["canCreate"]=true;
        }else{
        	subMenu["canCreate"]=false;
        }
	    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
	    	subMenu["canRead"]=true;
        }else{
        	subMenu["canRead"]=false;
        }
	    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
	    	subMenu["canEdit"]=true;
        }else{
        	subMenu["canEdit"]=false;
        }
	    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
	    	subMenu["canDelete"]=true;
        }else{
        	subMenu["canDelete"]=false;
        }
	    menu.push(subMenu);
	   });
	   
	    $("#employee").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   });
	    $("#productMenu").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#purchaseProduct").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#productCampaign").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#DistributorRequisition").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#geoMapping").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#retailManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#routeManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#orderManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#expenseManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#attendanceManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    
	    $("#leaveManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#trackUserTable").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#visitHistory").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#noticeManagement").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    $("#reports").find('> tbody > tr').each(function () {
		    let subMenu={};
		    subMenu["subMenuName"]=$(this).find("td:eq(1)").attr("id");
		    
		    if($(this).find("td:eq(2)").find("input").prop("checked") == true){
		    	subMenu["canCreate"]=true;
	        }else{
	        	subMenu["canCreate"]=false;
	        }
		    if($(this).find("td:eq(3)").find("input").prop("checked") == true){
		    	subMenu["canRead"]=true;
	        }else{
	        	subMenu["canRead"]=false;
	        }
		    if($(this).find("td:eq(4)").find("input").prop("checked") == true){
		    	subMenu["canEdit"]=true;
	        }else{
	        	subMenu["canEdit"]=false;
	        }
		    if($(this).find("td:eq(5)").find("input").prop("checked") == true){
		    	subMenu["canDelete"]=true;
	        }else{
	        	subMenu["canDelete"]=false;
	        }
		    menu.push(subMenu);
		   }); 
	    menuObj["subMenu"]=menu;
	    submitEditMenuToServer(menuObj,id);
	    
 }
 function submitEditMenuToServer(data,id) {	
	    let url = baseUrl + "/api/menu/"+id;	    
	    $.ajax({
	        type: "PUT",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "JSON",
	        success: function (result) {
	        	//alert("Update Successful!");
	         swal(`${result.message}`);	        
	           window.location.href=baseUrl+"/admin/manage-role";
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}