(function(){
	app.factory("ArticleResource", articleResourceFunction);
	
	function articleResourceFunction(Restangular){
		var retObj = {};
		
		retObj.searchArticles = function(searchParams){
			return Restangular.all("search").customGET("", searchParams).then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	articleResourceFunction.$inject = ["Restangular"];
})();