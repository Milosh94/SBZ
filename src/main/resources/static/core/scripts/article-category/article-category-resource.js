(function(){
	app.factory("ArticleCategoryResource", articleCategoryResourceFunction);
	
	function articleCategoryResourceFunction(Restangular){
		var retObj = {};
		
		retObj.getArticleCategories = function(){
			return Restangular.all("article-category").customGET("").then(function(response){
				return response;
			});
		}
		
		retObj.deleteArticleCategory = function(code){
			return Restangular.all("article-category").remove({code: code}).then(function(response){
				return response;
			});
		}
		
		retObj.postArticleCategory = function(article, isGoods){
			return Restangular.all("article-category").post(article, {isGoods: isGoods}).then(function(response){
				return response;
			});
		}
		
		retObj.putArticleCategory = function(article, isGoods){
			return Restangular.all("article-category").customPUT(article, "", {isGoods: isGoods}, {}).then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	articleCategoryResourceFunction.$inject = ["Restangular"];
})();