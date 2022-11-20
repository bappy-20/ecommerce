function getMenu(){
	var role=$("#role").val();
	
    $.ajax({
        type: "GET",
        url: "http://43.224.110.59:8080/dms/api/submenu/"+role,
        dataType : "JSON",
        success: function (result) {
        	createMenu(result);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function createMenu(subMenu){
	if(subMenu.length>0){
		subMenu.forEach(function (obj){
			
			if(obj.subMenuName=='Org'){
				if(obj.canRead==true){
					$('#Org').show();
				if(obj.canCreate==true){
					$('#orgCreate').show();			
				}else{
					$('#orgCreate').hide();			
				}	
				}else{
					$('#Org').hide();
				}		
			}	
			if(obj.subMenuName=='Dist'){
				if(obj.canRead==true){
					$('#Dist').show();
				if(obj.canCreate==true){
					$('#DistCreate').show();			
				}else{
					$('#DistCreate').hide();			
				}	
				}else{
					$('#Dist').hide();
					
				}		
			}
			if(obj.subMenuName=='supplier'){
				if(obj.canRead==true){
					$('#supplier').show();
				if(obj.canCreate==true){
					$('#supplierCreate').show();			
				}else{
					$('#supplierCreate').hide();			
				}	
				}else{
					$('#supplier').hide();
					
				}		
			}
			
			if(obj.subMenuName=='webuser'){
				if(obj.canRead==true){
					$('#webuser').show();
				if(obj.canCreate==true){
					$('#webuserCreate').show();			
				}else{
					$('#webuserCreate').hide();			
				}	
				}else{
					$('#webuser').hide();
					
				}		
			}
			if(obj.subMenuName=='rolemanagement'){
				if(obj.canRead==true){
					$('#rolemanagement').show();
				if(obj.canCreate==true){
					$('#modifyRole').show();			
				}else{
					$('#modifyRole').hide();			
				}	
				}else{
					$('#rolemanagement').hide();
					
				}		
			}
			if(obj.subMenuName=='createrole'){
				if(obj.canRead==true){
					$('#createrole').show();
				if(obj.canCreate==true){
					$('#roleCreateButton').show();			
				}else{
					$('#roleCreateButton').hide();			
				}	
				}else{
					$('#createrole').hide();
					
				}		
			}
			if(obj.subMenuName=='empType'){
				
				if(obj.canRead==true){
					$('#empType').show();
				if(obj.canCreate==true){
					$('#empTypeCreate').show();			
				}else{
					$('#empTypeCreate').hide();			
				}	
				}else{
					$('#empType').hide();
					
				}		
			}
			if(obj.subMenuName=='appUser'){
				
				if(obj.canRead==true){
					$('#appUser').show();
				if(obj.canCreate==true){
					$('#appUserCreate').show();			
				}else{
					$('#appUserCreate').hide();			
				}	
				}else{
					$('#appUser').hide();
					
				}		
			}
			if(obj.subMenuName=='targetSR'){
				
				if(obj.canRead==true){
					$('#targetSR').show();
				if(obj.canCreate==true){
					$('#targetSRCreate').show();			
				}else{
					$('#targetSRCreate').hide();			
				}	
				}else{
					$('#targetSR').hide();
					
				}		
			}
			if(obj.subMenuName=='targetDE'){
				
				if(obj.canRead==true){
					$('#targetDE').show();
				if(obj.canCreate==true){
					$('#targetDECreate').show();			
				}else{
					$('#targetDECreate').hide();			
				}	
				}else{
					$('#targetDE').hide();
					
				}		
			}
			//productCategory
			if(obj.subMenuName=='productCategory'){
				
				if(obj.canRead==true){
					$('#productCategory').show();
				if(obj.canCreate==true){
					$('#productCategoryCreate').show();			
				}else{
					$('#productCategoryCreate').hide();			
				}	
				}else{
					$('#productCategory').hide();
					
				}		
			}
			//measurementUnit
			if(obj.subMenuName=='measurementUnit'){
				
				if(obj.canRead==true){
					$('#measurementUnit').show();
				if(obj.canCreate==true){
					$('#measurementUnitCreate').show();			
				}else{
					$('#measurementUnitCreate').hide();			
				}	
				}else{
					$('#measurementUnit').hide();
					
				}		
			}
			
			//company
			if(obj.subMenuName=='company'){
				
				if(obj.canRead==true){
					$('#company').show();
				if(obj.canCreate==true){
					$('#companyCreate').show();			
				}else{
					$('#companyCreate').hide();			
				}	
				}else{
					$('#company').hide();
					
				}		
			}
			//brand
			if(obj.subMenuName=='brand'){
				
				if(obj.canRead==true){
					$('#brand').show();
				if(obj.canCreate==true){
					$('#brandCreate').show();			
				}else{
					$('#brandCreate').hide();			
				}	
				}else{
					$('#brand').hide();
					
				}		
			}
			
			//product
			if(obj.subMenuName=='product'){
				
				if(obj.canRead==true){
					$('#product').show();
				if(obj.canCreate==true){
					$('#productCreate').show();			
				}else{
					$('#productCreate').hide();			
				}	
				}else{
					$('#product').hide();
					
				}		
			}
			
			//productBatch
			if(obj.subMenuName=='productBatch'){
				
				if(obj.canRead==true){
					$('#productBatch').show();
				if(obj.canCreate==true){
					$('#productBatchCreate').show();			
				}else{
					$('#productBatchCreate').hide();			
				}	
				}else{
					$('#productBatch').hide();
					
				}		
			}
			
			//mappingProductCreate
			if(obj.subMenuName=='mappingProduct'){
				
				if(obj.canRead==true){
					$('#mappingProduct').show();
				if(obj.canCreate==true){
					$('#mappingProductCreate').show();			
				}else{
					$('#mappingProductCreate').hide();			
				}	
				}else{
					$('#mappingProduct').hide();
					
				}		
			}
			
			//productPrice
			if(obj.subMenuName=='productPrice'){
				
				if(obj.canRead==true){
					$('#productPrice').show();
				if(obj.canCreate==true){
					$('#productPriceCreate').show();			
				}else{
					$('#productPriceCreate').hide();			
				}	
				}else{
					$('#productPrice').hide();
					
				}		
			}
			
			//priceHistory
			if(obj.subMenuName=='priceHistory'){		
				if(obj.canRead==true){
					$('#priceHistory').show();
				if(obj.canCreate==true){
					$('#priceHistoryCreate').show();			
				}else{
					$('#priceHistoryCreate').hide();			
				}	
				}else{
					$('#priceHistory').hide();
					
				}		
			}
			
			//productPurchaseCreate
			if(obj.subMenuName=='productPurchase'){		
				if(obj.canRead==true){
					$('#productPurchase').show();
				if(obj.canCreate==true){
					$('#productPurchaseCreate').show();			
				}else{
					$('#productPurchaseCreate').hide();			
				}	
				}else{
					$('#productPurchase').hide();
					
				}		
			}
			
			//returnPurchaseCreate
			if(obj.subMenuName=='returnPurchase'){		
				if(obj.canRead==true){
					$('#returnPurchase').show();
				if(obj.canCreate==true){
					$('#returnPurchaseCreate').show();			
				}else{
					$('#returnPurchaseCreate').hide();			
				}	
				}else{
					$('#returnPurchase').hide();
					
				}		
			}
			
			//campaignType
			if(obj.subMenuName=='campaignType'){		
				if(obj.canRead==true){
					$('#campaignType').show();
				if(obj.canCreate==true){
					$('#campaignTypeCreate').show();			
				}else{
					$('#campaignTypeCreate').hide();			
				}	
				}else{
					$('#campaignType').hide();
					
				}		
			}
			
			//campaign
			if(obj.subMenuName=='campaign'){		
				if(obj.canRead==true){
					$('#campaign').show();
				if(obj.canCreate==true){
					$('#campaignCreate').show();			
				}else{
					$('#campaignCreate').hide();			
				}	
				}else{
					$('#campaign').hide();
					
				}		
			}
			
			//cashBackCampaign
			if(obj.subMenuName=='cashBackCampaign'){		
				if(obj.canRead==true){
					$('#cashBackCampaign').show();
				if(obj.canCreate==true){
					$('#cashBackCampaignCreate').show();			
				}else{
					$('#cashBackCampaignCreate').hide();			
				}	
				}else{
					$('#cashBackCampaign').hide();
					
				}		
			}
			
		
			
			//requisition
			if(obj.subMenuName=='requisition'){		
				if(obj.canRead==true){
					$('#requisition').show();
				if(obj.canCreate==true){
					$('#requisitionCreate').show();			
				}else{
					$('#requisitionCreate').hide();			
				}	
				}else{
					$('#requisition').hide();
					
				}		
			}
			
			//requisition
			if(obj.subMenuName=='supplyChainRequisition'){		
				if(obj.canRead==true){
					$('#supplyChainRequisition').show();
				if(obj.canCreate==true){
					$('#supplyChainRequisitionCreate').show();			
				}else{
					$('#supplyChainRequisitionCreate').hide();			
				}	
				}else{
					$('#supplyChainRequisition').hide();
					
				}		
			}
			
			//operationRequisition
			if(obj.subMenuName=='operationRequisition'){		
				if(obj.canRead==true){
					$('#operationRequisition').show();
				if(obj.canCreate==true){
					$('#operationRequisitionCreate').show();			
				}else{
					$('#operationRequisitionCreate').hide();			
				}	
				}else{
					$('#operationRequisition').hide();
					
				}		
			}
			
			//approvedRequisition
			if(obj.subMenuName=='approvedRequisition'){		
				if(obj.canRead==true){
					$('#approvedRequisition').show();
				if(obj.canCreate==true){
					$('#approvedRequisitionCreate').show();			
				}else{
					$('#approvedRequisitionCreate').hide();			
				}	
				}else{
					$('#approvedRequisition').hide();
					
				}		
			}
			
			//verifiedRequisition
			if(obj.subMenuName=='verifiedRequisition'){		
				if(obj.canRead==true){
					$('#verifiedRequisition').show();
				if(obj.canCreate==true){
					$('#verifiedRequisitionCreate').show();			
				}else{
					$('#verifiedRequisitionCreate').hide();			
				}	
				}else{
					$('#verifiedRequisition').hide();
					
				}		
			}
			
			//deliveredRequisition
			if(obj.subMenuName=='deliveredRequisition'){		
				if(obj.canRead==true){
					$('#deliveredRequisition').show();
				if(obj.canCreate==true){
					$('#deliveredRequisitionCreate').show();			
				}else{
					$('#deliveredRequisitionCreate').hide();			
				}	
				}else{
					$('#deliveredRequisition').hide();
					
				}		
			}
			
			//region
			if(obj.subMenuName=='region'){		
				if(obj.canRead==true){
					$('#region').show();
				if(obj.canCreate==true){
					$('#regionCreate').show();			
				}else{
					$('#regionCreate').hide();			
				}	
				}else{
					$('#region').hide();
					
				}		
			}
			
			//area
			if(obj.subMenuName=='area'){		
				if(obj.canRead==true){
					$('#area').show();
				if(obj.canCreate==true){
					$('#areaCreate').show();			
				}else{
					$('#areaCreate').hide();			
				}	
				}else{
					$('#area').hide();
					
				}		
			}
			
			//territory
			if(obj.subMenuName=='territory'){		
				if(obj.canRead==true){
					$('#territory').show();
				if(obj.canCreate==true){
					$('#territoryCreate').show();			
				}else{
					$('#territoryCreate').hide();			
				}	
				}else{
					$('#territory').hide();
					
				}		
			}
			
			//market
			if(obj.subMenuName=='market'){		
				if(obj.canRead==true){
					$('#market').show();
				if(obj.canCreate==true){
					$('#marketCreate').show();			
				}else{
					$('#marketCreate').hide();			
				}	
				}else{
					$('#market').hide();
					
				}		
			}

			//retailTypeCreate
			if(obj.subMenuName=='retailType'){		
				if(obj.canRead==true){
					$('#retailType').show();
				if(obj.canCreate==true){
					$('#retailTypeCreate').show();			
				}else{
					$('#retailTypeCreate').hide();			
				}	
				}else{
					$('#retailType').hide();
					
				}		
			}
			
			//retail
			if(obj.subMenuName=='retail'){		
				if(obj.canRead==true){
					$('#retail').show();
				if(obj.canCreate==true){
					$('#retailCreate').show();			
				}else{
					$('#retailCreate').hide();			
				}	
				}else{
					$('#retail').hide();
					
				}		
			}
			
			//pendingRetail
			if(obj.subMenuName=='pendingRetail'){		
				if(obj.canRead==true){
					$('#pendingRetail').show();	
				}else{
					$('#pendingRetail').hide();
					
				}		
			}
			
			//retailCampaign
			if(obj.subMenuName=='retailComplain'){		
				if(obj.canRead==true){
					$('#retailComplain').show();
				if(obj.canCreate==true){
					$('#retailComplainCreate').show();			
				}else{
					$('#retailComplainCreate').hide();			
				}	
				}else{
					$('#retailComplain').hide();
					
				}		
			}
			
			//route
			if(obj.subMenuName=='route'){		
				if(obj.canRead==true){
					$('#route').show();
				if(obj.canCreate==true){
					$('#routeCreate').show();			
				}else{
					$('#routeCreate').hide();			
				}	
				}else{
					$('#route').hide();
					
				}		
			}
			
			//assignRoute
			if(obj.subMenuName=='assignRoute'){		
				if(obj.canRead==true){
					$('#assignRoute').show();
				if(obj.canCreate==true){
					$('#assignRouteCreate').show();			
				}else{
					$('#assignRouteCreate').hide();			
				}	
				}else{
					$('#assignRoute').hide();
					
				}		
			}
			
			//orderDetails
			if(obj.subMenuName=='orderDetails'){		
				if(obj.canRead==true){
					$('#orderDetails').show();	
				}else{
					$('#orderDetails').hide();
					
				}		
			}
			
			//returnOrder
			if(obj.subMenuName=='returnOrder'){		
				if(obj.canRead==true){
					$('#returnOrder').show();
				if(obj.canCreate==true){
					$('#returnOrderCreate').show();			
				}else{
					$('#returnOrderCreate').hide();			
				}	
				}else{
					$('#returnOrder').hide();
					
				}		
			}
			
			//expenseType
			if(obj.subMenuName=='expenseType'){		
				if(obj.canRead==true){
					$('#expenseType').show();
				if(obj.canCreate==true){
					$('#expenseTypeCreate').show();			
				}else{
					$('#expenseTypeCreate').hide();			
				}	
				}else{
					$('#expenseType').hide();
					
				}		
			}
			
			//expense
			if(obj.subMenuName=='expense'){		
				if(obj.canRead==true){
					$('#expense').show();
				if(obj.canCreate==true){
					$('#expenseCreate').show();			
				}else{
					$('#expenseCreate').hide();			
				}	
				}else{
					$('#expense').hide();
					
				}		
			}
			
			//approvedExpense
			if(obj.subMenuName=='approvedExpense'){		
				if(obj.canRead==true){
					$('#approvedExpense').show();
				if(obj.canCreate==true){
					$('#approvedExpenseCreate').show();			
				}else{
					$('#approvedExpenseCreate').hide();			
				}	
				}else{
					$('#approvedExpense').hide();
					
				}		
			}
			
			//dateWiseExpense
			if(obj.subMenuName=='dateWiseExpense'){		
				if(obj.canRead==true){
					$('#dateWiseExpense').show();	
				}else{
					$('#dateWiseExpense').hide();
					
				}		
			}
			
			//dateWisePendingExpense
			if(obj.subMenuName=='dateWisePendingExpense'){		
				if(obj.canRead==true){
					$('#dateWisePendingExpense').show();	
				}else{
					$('#dateWisePendingExpense').hide();
					
				}		
			}
			
			//expenseBy
			if(obj.subMenuName=='expenseBy'){		
				if(obj.canRead==true){
					$('#expenseBy').show();	
				}else{
					$('#expenseBy').hide();
					
				}		
			}
			//dateWiseAttendance
			if(obj.subMenuName=='dateWiseAttendance'){		
				if(obj.canRead==true){
					$('#dateWiseAttendance').show();	
				}else{
					$('#dateWiseAttendance').hide();
					
				}		
			}
			
			//employeWiseAttedance
			if(obj.subMenuName=='employeWiseAttedance'){		
				if(obj.canRead==true){
					$('#employeWiseAttedance').show();	
				}else{
					$('#employeWiseAttedance').hide();
					
				}		
			}
			//employeWiseAttedance
			if(obj.subMenuName=='dailyAttendance'){		
				if(obj.canRead==true){
					$('#dailyAttendance').show();	
				}else{
					$('#dailyAttendance').hide();
					
				}		
			}
			
			//leave
			if(obj.subMenuName=='leave'){		
				if(obj.canRead==true){
					$('#leave').show();
				if(obj.canCreate==true){
					$('#leaveCreate').show();			
				}else{
					$('#leaveCreate').hide();			
				}	
				}else{
					$('#leave').hide();
					
				}		
			}
			
			//pendingLeave
			if(obj.subMenuName=='pendingLeave'){		
				if(obj.canRead==true){
					$('#pendingLeave').show();	
				}else{
					$('#pendingLeave').hide();
					
				}		
			}
				
			//approvedLeave
			if(obj.subMenuName=='approvedLeave'){		
				if(obj.canRead==true){
					$('#approvedLeave').show();	
				}else{
					$('#approvedLeave').hide();
					
				}		
			}
			
			
			//trackUser
			if(obj.subMenuName=='trackUser'){		
				if(obj.canRead==true){
					$('#trackUser').show();	
				}else{
					$('#trackUser').hide();
					
				}		
			}
			
			//trackingAllUsers
			if(obj.subMenuName=='trackingAllUsers'){		
				if(obj.canRead==true){
					$('#trackingAllUsers').show();	
				}else{
					$('#trackingAllUsers').hide();
					
				}		
			}
			
			//employeeVisitReport
			if(obj.subMenuName=='employeeVisitReport'){		
				if(obj.canRead==true){
					$('#employeeVisitReport').show();	
				}else{
					$('#employeeVisitReport').hide();
					
				}		
			}
				
			//employeeVisitReport
			if(obj.subMenuName=='employeeVisitReport'){		
				if(obj.canRead==true){
					$('#employeeVisitReport').show();	
				}else{
					$('#employeeVisitReport').hide();
					
				}		
			}
			
			//notificationCreate
			if(obj.subMenuName=='notification'){		
				if(obj.canRead==true){
					$('#notification').show();
				if(obj.canCreate==true){
					$('#notificationCreate').show();			
				}else{
					$('#notificationCreate').hide();			
				}	
				}else{
					$('#notification').hide();
					
				}		
			}
			//productInventory
			if(obj.subMenuName=='productInventory'){		
				if(obj.canRead==true){
					$('#productInventory').show();	
				}else{
					$('#productInventory').hide();
					
				}		
			}
			//pendingOrder
			if(obj.subMenuName=='pendingOrder'){		
				if(obj.canRead==true){
					$('#pendingOrder').show();	
				}else{
					$('#pendingOrder').hide();
					
				}		
			}
			//deliveredOrder
			if(obj.subMenuName=='deliveredOrder'){		
				if(obj.canRead==true){
					$('#deliveredOrder').show();	
				}else{
					$('#deliveredOrder').hide();
					
				}		
			}
			//deliveredOrder
			if(obj.subMenuName=='retailWiseOrder'){		
				if(obj.canRead==true){
					$('#retailWiseOrder').show();	
				}else{
					$('#retailWiseOrder').hide();
					
				}		
			}
			//dateWiseProductDelivery
			if(obj.subMenuName=='dateWiseProductDelivery'){		
				if(obj.canRead==true){
					$('#dateWiseProductDelivery').show();	
				}else{
					$('#dateWiseProductDelivery').hide();
				}		
			}
			});
	}else{
		$('#Dist').hide();
		$('#supplier').hide();
		$('#webuser').hide();
		$('#rolemanagement').hide();
		$('#createrole').hide();
		$('#empType').hide();
		$('#appUser').hide();
		$('#targetSR').hide();
		$('#targetDE').hide();
		$('#productCategory').hide();
		$('#measurementUnit').hide();
		$('#company').hide();
		$('#brand').hide();
		$('#product').hide();
		$('#productBatch').hide();
		$('#mappingProduct').hide();
		$('#productPrice').hide();
		$('#priceHistory').hide();
		$('#productPurchase').hide();
		$('#returnPurchase').hide();
		$('#campaignType').hide();
		$('#campaign').hide();
		$('#cashBackCampaign').hide();
		$('#requisition').hide();
		$('#supplyChainRequisition').hide();
		$('#operationRequisition').hide();
		$('#approvedRequisition').hide();
		$('#verifiedRequisition').hide();
		$('#deliveredRequisition').hide();
		$('#region').hide();
		$('#area').hide();
		$('#territory').hide();
		$('#market').hide();
		$('#retailType').hide();
		$('#retail').hide();
		$('#pendingRetail').hide();
		$('#retailComplain').hide();
		$('#route').hide();
		$('#assignRoute').hide();
		$('#orderDetails').hide();
		$('#returnOrder').hide();
		$('#expenseType').hide();
		$('#expense').hide();
		$('#approvedExpense').hide();
		$('#dateWiseExpense').hide();
		$('#dateWisePendingExpense').hide();
		$('#expenseBy').hide();
		$('#dateWiseAttendance').hide();
		$('#employeWiseAttedance').hide();
		$('#dailyAttendance').hide();
		$('#leave').hide();
		$('#pendingLeave').hide();
		$('#approvedLeave').hide();
		$('#trackUser').hide();
		$('#trackingAllUsers').hide();
		$('#employeeVisitReport').hide();
		$('#employeeVisitReport').hide();
		$('#notification').hide();
		$('#productInventory').hide();
		$('#pendingOrder').hide();
		$('#deliveredOrder').hide();
		$('#retailWiseOrder').hide();
		$('#dateWiseProductDelivery').hide();
	}

}