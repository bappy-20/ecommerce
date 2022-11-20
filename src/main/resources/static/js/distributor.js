var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#distributorAddForm').validate({
	rules:{
	distname:"required",
	distaddress:"required",
	nid:"required",
	distphone:"required",
	//disttype:"required",
	//distcreadit:"required",
	},messages:{
		distname:"Please enter distributor name",
		distaddress:"Please enter distributor addresss",
		nid:"Please enter nid",
		distphone:"Please enter phone number",
		//disttype:"Please enter distributor type",
		//distcreadit:"Please enter distributor credit amount",		
	},
	submitHandler:function(form){
		submitDistributorFrom();		
	}	
});

jQuery('#distributorEditForm').validate({
	rules:{
	distname:"required",
	distaddress:"required",
	nid:"required",
	distphone:"required",
	//disttype:"required",
	//distcreadit:"required",		
	},messages:{
		distname:"Please enter distributor name",
		distaddress:"Please enter distributor addresss",
		nid:"Please enter distributor nid",
		distphone:"Please enter distributor phone number",
		//disttype:"Please enter distributor type",
		//distcreadit:"Please enter distributor credit amount",
	},
	submitHandler:function(form){
		submitDistributorEditForm();
	}	
});

function submitDistributorFrom() {
	this.event.preventDefault();
    let distributor = {};
    distributor["distributorName"] = $("#distributor_name").val();
    distributor["distributorAddress"] = $("#distributor_address").val();
    distributor["nid"] = $("#nid").val();
    //distributor["distributorOwner"] = $("#distributor_owner").val();
    distributor["distributorPhone"] = $("#distributor_phone").val(); 
    //distributor["distributorType"] = $("#distributort_type :selected").text(); 
    //distributor["distributorCredit"] = $("#distributor_credit").val();
    distributor["tradeImage"] = $("#trade_image").val()
     
    submitDistributorFromToServer(distributor);
   
}
function submitDistributorFromToServer(data) {

    let url = baseUrl +'/api/distributor'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
    	   swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/distributor';
            
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}


function getDistributor(id){

	let url = baseUrl + "/api/distributor/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheDistributorValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	            swal(`${e}`);
	        }
	    });

}


function setTheDistributorValues(result){	
	$("#id").val(result.id);
	$("#distributor_name").val(result.distributorName);
	$("#distributor_address").val(result.distributorAddress);
	$("#nid").val(result.nid);
   // $("#distributor_owner").val(result.distributorOwner);
    $("#distributor_phone").val(result.distributorPhone);
    //$("#distributort_type").val(result.distributorType);
    //$("#distributor_credit").val(result.distributorCredit);
    $("#trade_image").val(result.tradeImage); 
}

	function submitDistributorEditForm() {

	this.event.preventDefault();
	let distributor = {};
    let id = $("#id").val();
    distributor["id"] = id;    
    distributor["distributorName"] = $("#distributor_name").val();
    distributor["distributorAddress"] = $("#distributor_address").val();
    distributor["nid"] = $("#nid").val();
//    distributor["distributorOwner"] = $("#distributor_owner").val();
      distributor["distributorPhone"] = $("#distributor_phone").val();  
//    distributor["distributorType"] = $("#distributort_type").val();  
//    distributor["distributorCredit"] = $("#distributor_credit").val();  
    distributor["tradeImage"] = $("#trade_image").val();     
      
    submitDistributorEditFromToServer(distributor, id);
    
     } 
     
    
	function submitDistributorEditFromToServer(data, id) {

    let url = baseUrl + "/api/distributor/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	   swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/distributor";
        },
        error: function (e) {
        	swal(`${e}`);
            console.log("ERROR: ", e);
        }
    });
}	
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-distributor/"+id;
		
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

	function getdistributorProfile(id){
		 
		let url = baseUrl + "/api/distributor/"+id;
		
		$.ajax({
		        type: "GET",
		        url: url,
		        dataType : "JSON",
		        success: function (result) {
		            setTheUserProfileValues(result.data);
		            console.log(result);
		        },
		        error: function (e) {
		            console.log("ERROR: ", e);
		            swal(`${e}`);
		        }
		    });

	}   
	   
	 function setTheUserProfileValues(result){

		 $("#distributorName").text(result.distributorName);
		 $("#distributorAddress").text(result.distributorAddress);
//	     $("#distributorOwner").text(result.distributorOwner);
	     $("#distributorPhone").text(result.distributorPhone);
	     $("#distProfileImage").text(result.tradeImage);
	     $("#distProfileImage").src = result.tradeImage;//text(result.tradeImage);
	    // document.getElementById("myImg").src = myApi["Image"];
	     

	}  

		function createCheckboxList(){
	        
	        $.ajax({
	        url: baseUrl+'/api/dsr-user-list',
	        type: "GET",
	        dataType: "json",
	        success:function(data) {
	        $.each(data, function(idx, obj){ 
	             $('#chk')
	             .append('<div class="checkbox"><label><input name="user" type="checkbox" value="'+obj.id +'">'+ obj.username + '</label></div>');
	        });
	            
	        }
	        });
	             
	}
		
		function submitMappingForm(){
			this.event.preventDefault();
			var arealList=[];
			$('input[name="area"]:checkbox').each(function () {
			       if(this.checked){
			    	   arealList.push($(this).val());  
			       }
			       
			  });		
				var userId=$("#dist_id").val();			
				submitMappingFormToServer(arealList, userId) 
				
			
		}

		function submitMappingFormToServer(data, id) {

		    let url = baseUrl + "/api/distributor-mapping/"+id;
		    $.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: url, 
		        data: JSON.stringify(data),
		        dataType : "JSON",
		        success: function (result) {
		          if(result.statusCode==500){
		        	  //alert(result.message)
		        	  swal(`${result.message}`);
		          }else{
		        	  //alert("Success!");
		        	  swal(`${result.message}`);
		        	  window.location.href = baseUrl + "/admin/distributor";
		          }
		          
		        },
		        error: function (e) {
		            console.log("ERROR: ", e);
		            swal(`${e}`);
		        }
		    });
		}	
		
		function getDistributor1(id){

			let url = baseUrl + "/api/distributor/"+id;
			$.ajax({
			        type: "GET",
			        url: url,
			        dataType : "JSON",
			        success: function (result) {
			        
			            setTheDistributorValuesForMapping(result.data);
			            console.log(result);
			        },
			        error: function (e) {
			            console.log("ERROR: ", e);
			        }
			    });

		}
		
		function setTheDistributorValuesForMapping(data){
			
			$("#dist_id").val(data.id).change();
			 $.each( data.users, function( key, value ) {				   
				 $.each($("input[name='user']"), function(){
				    if( value.id==$(this).val()){
				    	$(this).prop('checked',true);
				    }
				      
				   }); 
				 }); 
		}
		
		function submitDistUserMappingForm(){
			this.event.preventDefault();
			var userList=[];
			
			$('#user_id option:selected').each(function() {
				userList.push($(this).val());
			});
				var distId=$("#dist_id").val();			
				submitDistUserMappingFormToServer(userList, distId) 
			
		}
		
		function submitDistUserMappingFormToServer(data, id) {

			  let url = baseUrl + "/api/distributor-mapping/"+id;
			    $.ajax({
			        type: "POST",
			        contentType: "application/json",
			        url: url, 
			        data: JSON.stringify(data),
			        dataType : "JSON",
			        success: function (result) {
			          if(result.statusCode==500){
			        	  alert(result.message)
			          }else{
			        	  ///alert("Success!");
			        	  swal(`${result.message}`);
			        	  window.location.href = baseUrl + "/admin/distributor";
			          }
			          
			        },
			        error: function (e) {
			            console.log("ERROR: ", e);
			            swal(`${e}`);
			        }
			    });
		}	
		
		
		
		
		
		
		
		