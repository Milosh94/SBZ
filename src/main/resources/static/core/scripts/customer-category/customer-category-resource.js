(function(){
	app.factory("CustomerCategoryResource", customerCategoryResourceFunction);
	
	function customerCategoryResourceFunction(Restangular){
		retObj = {};
		
		retObj.getCustomerCategories = function(){
			return Restangular.all("customer-category").getList().then(function(response){
				return response;
			})
		}
		
		retObj.updateCustomerCategoryThresholds = function(thresholds, code){
			return Restangular.all("customer-category").customPOST(thresholds, "", {code: code}, {}).then(function(response){
				return response;
			})
		}
		
		return retObj;
	}
	
	customerCategoryResourceFunction.$inject = ["Restangular"];
})();