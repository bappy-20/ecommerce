var baseUrl= "";
var catId1="";
function setBaseUrl(url,catId){
	baseUrl = url;
	catId1=catId;
}

function submitCategoryForm() {
	this.event.preventDefault();
    let category = {};  
    category["name"] = $("#name").val();
    
    submitCategoryFormToServer(category);
}


function submitCategoryFormToServer(data) {

    let url = baseUrl +'/api/sub-category'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
        
            window.location.href =  baseUrl+'/admin/sub-category/'+catId1;
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}




function getCategory(id){

	let url = baseUrl + "/api/sub-category/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheCategoryValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheCategoryValues(result){	
	$("#id").val(result.id);
	$("#name").val(result.name);
	
 
}


function submitCategoryEditForm() {

	this.event.preventDefault();
	let category = {};
    let id = $("#id").val();
    category["id"] = id;    
    category["name"] = $("#name").val();    
    submitCategoryEditFormToServer(category, id);
    
     }     

	function submitCategoryEditFormToServer(data, id) {
    let url = baseUrl + "/api/sub-category/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/sub-category/"+catId1;
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


