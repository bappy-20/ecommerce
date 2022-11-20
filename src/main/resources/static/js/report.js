var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#retailandDateWiseOrder').validate({
	rules:{
	retail_id:"required",
	order_type:"required",
	},messages:{
		retail_id:"Please enter retail",
		order_type:"Please enter order",
	},
	submitHandler:function(form){
		getRetailWiseOrder();		
	}	
});

function submitExpenseForm() {
	this.event.preventDefault();	
    var carId = $("#carId").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(carId!="" && startDate!="" && endDate!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitExpenseFormToServer(carId,startDate1,endDate1);
    }else{    	
    	alert("input field must not be null");
    }	
}

function submitExpenseFormToServer(carId,startDate,endDate) {

    let url = baseUrl + "/api/get-expense/"+carId+"/"+startDate+"/"+endDate
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
            data : 'expenseType'
           }, 
           {
             data : 'note'
            },
         {
          data : 'carId'
         },
        {
            data : 'attachment'
        },
        {
            data : 'amount'
           } 
        
       
    
        ]
    });

}


function submitSalaryForm() {
	this.event.preventDefault();
	
    var carId = $("#carId").val();
    var userId = $("#userName").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(carId!="" && startDate!="" && endDate!="" && userId!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitSalaryFormToServer(carId,userId,startDate1,endDate1);
    }else{
    	alert("Input field must not be null");
    }	
}

function submitSalaryFormToServer(carId,userId,startDate,endDate) {

    let url = baseUrl + "/api/get-salary/"+carId+"/"+userId+"/"+startDate+"/"+endDate
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
            data : 'userId'
           }, 
           {
             data : 'userName'
            },
         {
          data : 'nickName'
         },
        {
            data : 'mobile'
        },
        {
            data : 'salaryType'
           },
           {
               data : 'carId'
           },
           {
               data : 'dayorMonth'
              } ,
              {
                  data : 'amount'
                 }  
        
       
    
        ]
    });

}

function submitRentForm() {
	this.event.preventDefault();	
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val(); 
   if(startDate!="" && endDate!="") {
	   var startDate1 = startDate.split("/").reverse().join("-");	
	   var endDate1 = endDate.split("/").reverse().join("-");		
		submitRentFormToServer(startDate1,endDate1);
   }else{
	   alert("Date must not be null");
   }
	
}

function submitRentFormToServer(startDate,endDate) {
    let url = baseUrl + "/api/get-car-rent/"+startDate+"/"+endDate
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
            data : 'carId'
           }, 
           {
             data : 'tripNumber'
            },
         {
          data : 'rent'
         }
        ]
    });

}
function getRentFormToServerByCurdate() {
    let url = baseUrl + "/api/get-car-rent-by-curdate"
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
            data : 'carId'
           }, 
           {
             data : 'tripNumber'
            },
         {
          data : 'rent'
         }
        ]
    });

}


function getDropDownValue(){
	$('select[name="category_id"]').on('change', function() {
	    var category_id = $(this).val();
	    
	    if(category_id!=null) {        
	    $.ajax({
	    url: baseUrl+'/api/product-by-category/'+category_id,
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

function getProductCurrentStock() {
    let url = baseUrl + "/api/current-inventory"
    var table = $('#productTable').dataTable({
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
            data : 'productName'
           }, 
           {
             data : 'sku'
            },
         {
          data : 'productCategoryName'
         }, 
           {
             data : 'mesuringType'
            },
         {
          data : 'productPrice'
         }, 
           {
             data : 'productMrpPrice'
            },
         {
          data : 'startingStock'
         }, 
           {
             data : 'safetyStock'
            },
         {
          data : 'supplier'
         }, 
           {
             data : 'brandName'
            },
         {
          data : 'totalRecieve'
         }, 
           {
             data : 'totalShipped'
            },
         {
          data : 'onHand'
         }, 
           {
             data : 'totalSaleAmount'
            }
        ]
    });

}

function getPendingOrder() {
	this.event.preventDefault();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate!="" && endDate!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitForPendingOrerToServer(startDate1,endDate1);
    }else{
    	alert("Input field must not be null");
    }
	
}

function submitForPendingOrerToServer(startDate,endDate) {
    let url = baseUrl + "/api/get-all-pending-order-by-date/"+startDate+"/"+endDate
    var table = $('#pendingOrderTable').dataTable({
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
            data : 'orderId'
           }, 
           {
             data : 'dateReceive'
            },
         {
          data : 'marketName'
         },
        {
            data : 'retailName'
        },
        {
        data : 'employeeId'
       },
       {
           data : 'paymentMethod'
       },
       {
           data : 'total'
          } ,
          {
          data : 'discount'
         }, 
         {
             data : 'grandTotal'
            },
         {
          data : 'advancedPayment'
         },
        {
            data : 'dueAmount'
        },
        {
            data : 'paymentStatus'
         },
         {
             data: 'orderId',             
             "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
             $(nTd).html("<a style='margin-right: 3px;padding: 3px 7px;' class='btn btn-info'  href='"+baseurl+"/admin/get-delivered-order-details-by-date/"+oData.orderId+"'><i class='fa fa-pencil'></i></a>");
             } 
         }
        ],
        drawCallback: function () {
            var discount = $('#pendingOrderTable').DataTable().column(7).data().sum();
            var total = $('#pendingOrderTable').DataTable().column(6).data().sum();
            var grandtotal = $('#pendingOrderTable').DataTable().column(8).data().sum();
            var advance = $('#pendingOrderTable').DataTable().column(9).data().sum();
            var due = $('#pendingOrderTable').DataTable().column(10).data().sum();
            
            $('#discount').html("Total Discount : "+discount);
            $('#total').html("Total : "+total);
            $('#grandtotal').html("Grand Total : "+grandtotal);
            $('#advance').html("Total Advance  : "+advance);
            $('#due').html("Total Due : "+due);
            
          },
          "initComplete": function(settings, json) {
              var api = this.api();
              var numRows = api.rows( ).count();
              $('#totalcount').html("Total Order : "+numRows);
          }
    
    });
}

function getdeliveredOrder() {
	this.event.preventDefault();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate!="" && endDate!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitFordeliveredOrerToServer(startDate1,endDate1);
    }else{
    	alert("Input field must not be null");
    }	
}

function submitFordeliveredOrerToServer(startDate,endDate) {
    let url = baseUrl + "/api/get-all-delivered-order-by-date/"+startDate+"/"+endDate
    var table = $('#pendingOrderTable').dataTable({
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
            data : 'orderId'
           }, 
           {
             data : 'dateReceive'
            },
         {
          data : 'marketName'
         },
        {
            data : 'retailName'
        },
        {
        data : 'employeeId'
       },
       {
           data : 'paymentMethod'
       },
       {
           data : 'total'
          } ,
          {
          data : 'discount'
         }, 
         {
             data : 'grandTotal'
            },
         {
          data : 'advancedPayment'
         },
        {
            data : 'dueAmount'
        },
        {
            data : 'paymentStatus'
         },
         {
             data: 'orderId',             
             "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
             $(nTd).html("<a style='margin-right: 3px;padding: 3px 7px;' class='btn btn-info'  href='"+baseurl+"/admin/get-delivered-order-details-by-date/"+oData.orderId+"'><i class='fa fa-pencil'></i></a>");
             } 
         }
        ],
        drawCallback: function () {
            var discount = $('#pendingOrderTable').DataTable().column(7).data().sum();
            var total = $('#pendingOrderTable').DataTable().column(6).data().sum();
            var grandtotal = $('#pendingOrderTable').DataTable().column(8).data().sum();
            var advance = $('#pendingOrderTable').DataTable().column(9).data().sum();
            var due = $('#pendingOrderTable').DataTable().column(10).data().sum();
            
            $('#discount').html("Total Discount : "+discount);
            $('#total').html("Total : "+total);
            $('#grandtotal').html("Grand Total : "+grandtotal);
            $('#advance').html("Total Advance  : "+advance);
            $('#due').html("Total Due : "+due);            
          },
          "initComplete": function(settings, json) {
              var api = this.api();
              var numRows = api.rows( ).count();
              $('#totalcount').html("Total Order : "+numRows);
          }    
    });
}

function getRetailWiseOrder() {
	this.event.preventDefault();
	 var retailId = $("#retail_id").val();
	var orderType = $("#order_type").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate!="" && endDate!="" && retailId!="" && orderType!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitForRetailWiseToServer(startDate1,endDate1,retailId,orderType);
    }else{
    	alert("Input field must not be null");
    }	
}

function submitForRetailWiseToServer(startDate,endDate,retailId,orderType) {
    let url = baseUrl + "/api/get-retail-wise-order-by-date/"+startDate+"/"+endDate+"/"+retailId+"/"+orderType
    var table = $('#retailOrderTable').dataTable({
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
            data : 'orderId'
           }, 
           {
             data : 'dateReceive'
            },
         {
          data : 'marketName'
         },
        {
            data : 'retailName'
        },
        {
        data : 'employeeId'
       },
       {
           data : 'paymentMethod'
       },
       {
           data : 'total'
          } ,
          {
          data : 'discount'
         }, 
         {
             data : 'grandTotal'
            },
         {
          data : 'advancedPayment'
         },
        {
            data : 'dueAmount'
        },
        {
            data : 'paymentStatus'
         },
         {
             data: 'orderId',             
             "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
             $(nTd).html("<a style='margin-right: 3px;padding: 3px 7px;' class='btn btn-info'  href='"+baseurl+"/admin/get-delivered-order-details-by-date/"+oData.orderId+"'><i class='fa fa-pencil'></i></a>");
             } 
         }
        ],
        drawCallback: function () {
            var discount = $('#retailOrderTable').DataTable().column(7).data().sum();
            var total = $('#retailOrderTable').DataTable().column(6).data().sum();
            var grandtotal = $('#retailOrderTable').DataTable().column(8).data().sum();
            var advance = $('#retailOrderTable').DataTable().column(9).data().sum();
            var due = $('#retailOrderTable').DataTable().column(10).data().sum();
            
            $('#discount').html("Total Discount : "+discount);
            $('#total').html("Total : "+total);
            $('#grandtotal').html("Grand Total : "+grandtotal);
            $('#advance').html("Total Advance  : "+advance);
            $('#due').html("Total Due : "+due);            
          },
          "initComplete": function(settings, json) {
              var api = this.api();
              var numRows = api.rows( ).count();
              $('#totalcount').html("Total Order : "+numRows);
          }    
    });
}


function getProductReport() {
	this.event.preventDefault();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate!="" && endDate!=""){
    	var startDate1 = startDate.split("/").reverse().join("-");	
    	var endDate1 = endDate.split("/").reverse().join("-");   		
    	submitForProductReportToServer(startDate1,endDate1);
    }else{
    	alert("Input field must not be null");
    }	
}

function submitForProductReportToServer(startDate,endDate) {
    let url = baseUrl + "/api/get-product-order-by-date/"+startDate+"/"+endDate
    var table = $('#productTable').dataTable({
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
            data : 'productName'
           }, 
           {
             data : 'productQuantity'
            },
         {
          data : 'totalPrice'
         },
        {
            data : 'discount'
        },
        {
        data : 'discountedPrice'
       }
        ]    
    });
}


function getPrimaryInventoryCurrentStock() {
    let url = baseUrl + "/api/current-primary-inventory"
    var table = $('#productTable').dataTable({
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
            data : 'productName'
           }, 
           {
             data : 'onHand'
            },
         {
          data : 'startingStock'
         }, 
           {
             data : 'receivedInventory'
            },
         {
          data : 'safetyStock'
         }, 
           {
             data : 'shippedInventory'
            }       
         ]
    });

}


function getSecondaryInventoryCurrentStock() {
    let url = baseUrl + "/api/secondary-inventory-current-stock-single-dealer"
    var table = $('#productTable').dataTable({
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
            data : 'distributorName'
           },
            {
               data : 'productName'
              },
           {
             data : 'onHand'
            },
         {
          data : 'startingInventory'
         }, 
           {
             data : 'receivedInventory'
            },
         {
          data : 'safetyStock'
         }, 
           {
             data : 'shippedInventory'
            },
           {
             data : 'minimumQty'
            }
         ]
    });

}

function getSecondaryInventoryStockofDelear() {
	this.event.preventDefault();
	
	 var distributorId = $("#distributor_id").val();
	 //alert(distributorId);
	
    let url = baseUrl + "/api/secondary-inventory-stock-of-selected-dealer/"+distributorId
    var table = $('#productTable').dataTable({
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
            data : 'distributorName'
           },
            {
               data : 'productName'
              },
           {
             data : 'onHand'
            },
         {
          data : 'startingInventory'
         }, 
           {
             data : 'receivedInventory'
            },
         {
          data : 'safetyStock'
         }, 
           {
             data : 'shippedInventory'
            },
           {
             data : 'minimumQty'
            }
         ]
    });

}




