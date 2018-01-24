(function(){
	app.factory("CustomerResource", customerResourceFunction);
	
	function customerResourceFunction(Restangular){
		retObj = {};
		
		retObj.shoppingHistory = function(){
			return Restangular.all("shopping-history").getList().then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	customerResourceFunction.$inject = ["Restangular"];
})();