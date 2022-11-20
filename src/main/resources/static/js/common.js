

let backendRequest =(url="", method="GET", successCallBack, errorCallBack, data =[]) => {
		
		if(method === "GET"){
			$.ajax({
        type: method,
        contentType: "application/json",
        url: url,
        dataType : "json",
        success: successCallBack,
        error: errorCallBack,
		});
		
    }
    
    if(method =="POST")
    {
    $.ajax({
        type: method,
        contentType: "application/json",
        url: url,
        data:JSON.stringify(data),
        dataType : "json",
        success: successCallBack,
        error: errorCallBack,
		});
    }
    if(method == "DELETE")
    {
    $.ajax({
    url: url,
    type: 'DELETE',
     success: successCallBack,
        error: errorCallBack,
});
    }
    
    
  }

