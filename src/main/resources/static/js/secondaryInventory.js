var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitSecondaryInventoryFrom() {
	this.event.preventDefault();
    let secondaryInventory = {};
    secondaryInventory["productId"] = $("#product_id").val();
    secondaryInventory["startingInventory"] = $("#starting_inventory").val();
    secondaryInventory["receivedInventory"] = $("#received_inventory").val();
    secondaryInventory["shippedInventory"] = $("#shipped_inventory").val();
    secondaryInventory["onHand"] = $("#on_hand").val(); 
    secondaryInventory["minimumQty"] = $("#minimum_qty").val();  
    secondaryInventory["distributorId"] = $("#distributor_id").val();
    secondaryInventory["safetyStock"] = $("#safety_stock").val()
    
    
    
    submitSecondaryInventoryFromToServer(secondaryInventory);
}

function submitSecondaryInventoryFromToServer(data) {

    let url = baseUrl +'/api/secondary-Inventory'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
            window.location.href =  baseUrl +'/admin/secondaryInventory';
            
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getSecondaryInventory(id){

	let url = baseUrl + "/api/secondary-Inventory/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheSecondaryInventoryValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheSecondaryInventoryValues(result){	
	$("#id").val(result.id);
	$("#product_id").val(result.productId);
	$("#starting_inventory").val(result.startingInventory);
	$("#received_inventory").val(result.receivedInventory);
    $("#shipped_inventory").val(result.shippedInventory);
    $("#on_hand").val(result.onHand);
    $("#minimum_qty").val(result.minimumQty);
    $("#distributor_id").val(result.distributorId);
    $("#safety_stock").val(result.safetyStock);
  
 
}



	function submitSecondaryInventoryEditFrom() {

	this.event.preventDefault();
	let secondaryInventory = {};
    let id = $("#id").val();
    secondaryInventory["id"] = id;    
    secondaryInventory["productId"] = $("#product_id").val();
    secondaryInventory["startingInventory"] = $("#starting_inventory").val();
    secondaryInventory["receivedInventory"] = $("#received_inventory").val();
    secondaryInventory["shippedInventory"] = $("#shipped_inventory").val();
    secondaryInventory["onHand"] = $("#on_hand").val();  
    secondaryInventory["minimumQty"] = $("#minimum_qty").val();  
    secondaryInventory["distributorId"] = $("#distributor_id").val();  
    secondaryInventory["safetyStock"] = $("#safety_stock").val();    
    
    
    
    submitSecondaryInventoryEditFromToServer(secondaryInventory, id);
    
     } 
     
    
	function submitSecondaryInventoryEditFromToServer(data, id) {

    let url = baseUrl + "/api/secondary-Inventory/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/secondaryInventory";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}



