var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitpriceUpdate() {
    this.event.preventDefault();       
    var productId=$("#product_id").val();
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");    
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/price-update-history'+"/"+productId+"/"+startDate+"/"+endDate,
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
                 data : 'productPrice'
                },
                 {
                 data : 'dealerPrice'
                },
               
               {
                 data : 'mrp'
                }
            
        
            ]
        });    
}