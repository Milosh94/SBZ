(function(){
	app.factory("ArticleResource", articleResourceFunction);
	
	function articleResourceFunction(Restangular){
		var retObj = {};
		
		retObj.searchArticles = function(searchParams){
			return Restangular.all("search").customGET("", searchParams).then(function(response){
				return response;
			});
		}
		
		retObj.getAllArticles = function(){
			return Restangular.all("articles").getList().then(function(response){
				return response;
			});
		}
		
		retObj.getOrderArticles = function(){
			return Restangular.all("order-articles").getList().then(function(response){
				return response;
			});
		}
		
		retObj.putOrderArticles = function(code, quantity){
			return Restangular.all("order-articles").customPUT({}, "", {code: code, quantity: quantity}, {}).then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	articleResourceFunction.$inject = ["Restangular"];
})();