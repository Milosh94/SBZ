(function(){
	app.directive("articleCategoryValidity", articleCategoryValidityFunction);
	
	function articleCategoryValidityFunction($parse){
		return {
			require: "^form",
			link: function(scope, element, attrs, ctrl){
				var config = $parse(attrs["articleCategoryValidity"])(scope);
				if(config.index === 0 && config.isNew === true){
					ctrl["articleCategoriesName0"].$setValidity("requiredError", false);
				}
			},
			restrict: "A"
		};
	}
	
	articleCategoryValidityFunction.$inject = ["$parse"]
})();