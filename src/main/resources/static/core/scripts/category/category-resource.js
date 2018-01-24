(function(){
	app.factory("CategoryResource", categoryResourceFunction);
	
	function categoryResourceFunction(Restangular){
		retObj = {};
		
		retObj.getCategories = function(){
			return Restangular.all("category").getList().then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	categoryResourceFunction.$inject = ["Restangular"];
})()