var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#expenseAddForm').validate({
	rules:{
	expenseType:"required",
	amount:"required",
	expenseBy:"required",
	},messages:{
		expenseType:"Please enter expense type",
		amount:"Please enter amount",
		expenseBy:"Please enter expense by",
	},
	submitHandler:function(form){
		
		submitExpenseForm();		
	}	
});

jQuery('#expenseEditForm').validate({
	rules:{
	expenseType:"required",
	amount:"required",
	expenseBy:"required",
	},
	messages:{
		expenseType:"Please enter expense type",
		amount:"Please enter amount",
		expenseBy:"Please enter expense by",
	},
	submitHandler:function(form){
		
		submitExpenseEditForm();
	}	
});

function submitExpenseForm() {
	this.event.preventDefault();
    let expense = {};
    expense["expenseType"] = $("#expenseType1").val();
    expense["amount"] = $("#amount").val();
    expense["note"] = $("#note").val();
    expense["expenseBy"] = $('#expenseByNew').val();
    expense["approvedAmount"] = $("#approvedAmountvalue").val();
    expense["attachment"] = $("#logo").val();
    expense["expenseDate"] = $("#expenseDate").val();
    submitExpenseFormToServer(expense);
}

function submitExpenseFormToServer(data) {
    let url = baseUrl +'/api/save-expense'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/expense';
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}

function getExpense(id){
	let url = baseUrl + "/api/get-expense/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheExpenseValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}

function setTheExpenseValues(result){	
	$("#id").val(result.id);
	$("#expenseType1").val(result.expenseType);
	$("#amount").val(result.amount);
	$("#note").val(result.note);
	$("#expenseBy1").val(result.expenseBy);
	$("#approvedBy").val(result.approvedBy);
	/*$("#status").val(result.status);*/
	$("#approvedAmount").val(result.approvedAmount);
	$("#expenseDate").val(result.expenseDate);
	expense["attachment"] = $("#logo").val();
	//alert(result.expenseBy)
}

function submitExpenseEditForm() {
	this.event.preventDefault();
	let expense = {};
    let id = $("#id").val();
    expense["id"] = id;    
    expense["expenseType"] = $("#expenseType1").val();
    expense["amount"] = $("#amount").val();
    expense["note"] = $("#note").val(); 
    expense["expenseBy"] = $('#expenseBy1').val();   
    expense["approvedAmount"] = $("#approvedAmount").val();
    expense["attachment"] = $("#logo").val(); 
    expense["expenseDate"] = $("#expenseDate").val();
    submitExpenseEditFormToServer(expense, id);
} 

	function submitExpenseEditFormToServer(data, id) {
    let url = baseUrl + "/api/update-expense/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          if(result.statusCode==500){
        	 // alert(result.message)
        	  swal(`${result.message}`);
          }else{
        	  swal(`${result.message}`);
        	  window.location.href = baseUrl + "/admin/expense";
          }  
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-expense/"+id;
		
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

	
function submitExpenseReportByExpTypeAndDate() {
    this.event.preventDefault();       
    var expenseType=$("#expenseType_id").val();
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");    
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/expense-ReportByExpTypeAndDateRange'+"/"+expenseType+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
         dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'expenseType'
            },
         {
          data : 'amount'
          },
        {
            data : 'note'
        },
          {
            data : 'expenseBy'
        },              
        {
           data : 'approvedBy'
           },
            {
             data : 'status'
            },
            {
             data : 'approvedAmount'
            },
         
            {
                data: 'attachment',
                "render": function ( data, type, full, meta ) {
                    if(data!="") {
                        return "<a target='_blank' href='"+baseurl+"/api/downloadFile1/"+data+"'><i class='fa fa-paperclip' style='font-size:36px;'> </i></a>";
                    }
                    return 'no attachment';
            }
            }
        ]
    });
     
}

function submitApprovedExpRptByDateRange() {
    this.event.preventDefault();   
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/expense-ReportOfApprovedByDateRange'+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'expenseType'
            },
         {
          data : 'amount'
          },
        {
            data : 'note'
        },
          {
            data : 'expenseBy'
        },              
        {
           data : 'approvedBy'
           },
            {
             data : 'status'
            },
            {
             data : 'approvedAmount'
            },
            
            {
                data: 'attachment',
                "render": function ( data, type, full, meta ) {
                    if(data!=null) {
                        return "<a target='_blank' href='"+baseurl+"/api/downloadFile1/"+data+"'><i class='fa fa-paperclip' style='font-size:36px;'> </i></a>";
                    }
                    return 'No attachment';
            }
            }
        ]
    });
     
}

function submitPendingExpRptByDateRange() {
    this.event.preventDefault();   
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/expense-ReportOfPendingByDateRange'+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'expenseType'
            	},
            {
         	 data : 'amount'
          		},
       		{
             data : 'note'
        		},
            {
             data : 'expenseBy'
        		},              
        	{
             data : 'approvedBy'
           		},
            {
             data : 'status'
           	    },
            {
             data : 'approvedAmount'
            }
           	 ,
             
             {
                 data: 'attachment',
                 "render": function ( data, type, full, meta ) {
                     if(data!=null) {
                         return "<a target='_blank' href='"+baseurl+"/api/downloadFile1/"+data+"'><i class='fa fa-paperclip' style='font-size:36px;'> </i></a>";
                     }
                     return 'No Attachment';
             }
             },
         		
          	{
          	 data: 'id',
                    
                    "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                   $(nTd).html("<a style='margin-right: 3px;padding: 3px 7px;' class='btn btn-info' onclick='changeStatus("+oData.id+");' href='#'><i class='fa fa-check'></i></a>");
              } 
                }                        
        ]
    });
     
}


function getDropDownValue(){
	$('select[name="expenseUser"]').on('change', function() {
	    var expenseUser = $(this).val();
	    
	    if(expenseUser!=null) {        
	    $.ajax({
	    url: baseUrl+'/api/expense-by-user-type/'+expenseUser,
	    type: "GET",
	    dataType: "json",
	    success:function(data) {
	    
	    $('select[name="expenseType"]').empty();
	    $('select[name="expenseType"]').append('<option value=""> -- Please Select -- </option>');
	    $.each(data, function(idx, obj){ 
	         $('select[name="expenseType"]')
	         .append('<option value="'+ obj.expenseType +'">'+ obj.expenseType +'</option>');
	    });
	        
	    }
	    });
	    }else{
	        $('select[name="expenseType"]').append('<option value=""> Not found</option>');
	    } 
	    });	
}
