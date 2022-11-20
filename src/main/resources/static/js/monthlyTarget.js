var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
function submitMonthlyTargetForm() {
	this.event.preventDefault();
    let monthlyTarget = {};
    if($("#emp_id").val()==null){
    	alert("Please select an employee first");
    }else if($("#target_month").val()==null){
    	alert("Please select targated month");
    }else{
    	 monthlyTarget["empId"] = $("#emp_id").val();
    	    monthlyTarget["productId"] = $("#product_id").val();
    	    monthlyTarget["category"] = $("#category_id").val();
    	    monthlyTarget["quantity"] = $("#quantity").val(); 
    	    monthlyTarget["totalValue"] = $("#total_value").val();
    	    monthlyTarget["targetMonth"] = $("#target_month").val();

    	    submitMonthlyTargetFormToServer(monthlyTarget);
    }
   
}
function submitMonthlyTargetFormToServer(data) {

    let url = baseUrl +'/api/monthly-Target'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    		swal(`${result.message}`);
        	window.history.back();
                        
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}

function getMonthlyTarget(id){

	let url = baseUrl + "/api/monthly-Target/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheMonthlyTargetValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}

function setTheMonthlyTargetValues(result){	
	$("#id").val(result.id);
	$("#emp_id").val(result.empId).change();
	$("#product_id").val(result.productId).change();
	$("#category_id").val(result.category).change();	 
    $("#quantity").val(result.quantity);
    $("#total_value").val(result.totalValue);
    $("#target_month").val(result.targetMonth);   
}
	function submitMonthlyTargetEditForm() {
	this.event.preventDefault();
	let monthlyTarget = {};
    let id = $("#id").val();
    monthlyTarget["id"] = id;    
    monthlyTarget["empId"] = $("#emp_id").val();
    monthlyTarget["productId"] = $("#product_id").val();
    monthlyTarget["category"] = $("#category_id").val();
    monthlyTarget["quantity"] = $("#quantity").val();  
    monthlyTarget["totalValue"] = $("#total_value").val(); 
    monthlyTarget["targetMonth"] = $("#target_month").val();
   
    submitMonthlyTargetEditFormToServer(monthlyTarget, id);
    
     } 
         
	function submitMonthlyTargetEditFormToServer(data, id) {
    let url = baseUrl + "/api/monthly-Target/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	swal(`${result.message}`);
        	window.history.back();
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/monthly-Target/"+id;
		
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
	
	function getDropDownValue(){
		$('select[name="category_id"]').on('change', function() {
		    var category_id = $(this).val();
		    
		    if(category_id!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/product-by-categoryName/'+category_id,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    
		    $('select[name="product_id"]').empty();
		    $('select[name="product_id"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(data, function(idx, obj){ 
		         $('select[name="product_id"]')
		         .append('<option value="'+ obj.id +'">'+ obj.productName +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="product_id"]').append('<option value="">Product Not found</option>');
		    } 
		    });	
	}
	
	function submitOrderTargetForSR() {
		this.event.preventDefault();	
	    var startDate = $("#target_month2").val();	   
	    if(startDate!="" ){
	    	var startDate1 = startDate.split("/").reverse().join("-");	
	    	submitSROrderTargetToServer(startDate1);
	    }else{    	
	    	alert("input field must not be null");
	    }	
	}
	
	function submitSROrderTargetToServer(startDate) {
		//alert(baseUrl)
		let url = baseUrl + "/api/monthy-target-summary8/"+startDate	 
	    var table = $('#empTable').dataTable({
	        "deferRender" : true,
	        "destroy":true,
	        "sAjaxSource" : url,
	        "sAjaxDataProp" : "",
	        dom: 'Bfrtip',
	        buttons: [
	             'excel', 'pdf', 'print'
	        ],
	        "columns" : [        	
	                     {
	                         data: 'empId',
	                         "render": function (nTd, sData, oData, iRow, iCol) {
	                           return "<a href='"+baseurl+"/admin/monthlyTarget/"+oData.empId+"'>"+oData.empId+"</a>";
	                         }
	                     },
	                     {
	                         data : 'empName'
	                        },
	                   
	                    {
	                      data : 'targetquantity'
	                     },
	                    {
	                      data : 'targetTotalValue'
	                     }, 
	                    
	                    {
	                     data : 'targetMonth'
	                     }
	                 ]
	    });

	}
	
	function submitOrderTarget() {
		this.event.preventDefault();		    
	    var startDate = $("#target_month").val();	   
	    if(startDate!="" ){
	    	var startDate1 = startDate.split("/").reverse().join("-");		    	  		
	    	submitOrderTargetToServer(startDate1);
	    }else{    	
	    	alert("input field must not be null");
	    }	
	}

	function submitOrderTargetToServer(startDate) {
	    let url = baseUrl + "/api/get-order-target-datewise2/"+startDate
	    var table = $('#empTable').dataTable({
	        "deferRender" : true,
	        "destroy":true,
	        "sAjaxSource" : url,
	        "sAjaxDataProp" : "",
	        dom: 'Bfrtip',
	        buttons: [
	             'excel', 'pdf', 'print'
	        ],
	        "columns" : [
	             {
	                 data : 'empId'
	                },           
	                {
	                 data : 'empName'
	                },                
	               {
	                 data : 'orderQuantity'
	                },
	               {
	                 data : 'orderValue'
	                },
	               {
	                 data : 'targetMonth'
	                },                             
	                {
	                    data: 'id',
	                    
	                    "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
	                    $(nTd).html("<a style='margin-right: 3px;padding: 3px 7px;' class='btn btn-danger' onclick='deleteEntry("+oData.id+");' href='#'><i class='fa fa-trash'></i></a>");
	               
	                    } 
	                } 
	            
	        
	            ]
	    });

	}
	
	
	function deleteEntryMonthlyOrderTarget(id) {		
		let url = baseurl+"/api/delete-monthly-order-target/"+id;
		
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
	