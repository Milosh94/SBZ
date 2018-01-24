(function(){
	app.factory("DiscountEventResource", discountEventResourceFunction);
	
	function discountEventResourceFunction(Restangular){
		retObj = {}
		
		retObj.getDiscountEvents = function(){
			return Restangular.all("discount-event").customGET("").then(function(response){
				return response;
			});
		}
		
		retObj.deleteDiscountEvent = function(code){
			return Restangular.all("discount-event").remove({eventCode: code}).then(function(response){
				return response;
			});
		}
		
		retObj.postDiscountEvent = function(event){
			return Restangular.all("discount-event").post(event).then(function(response){
				return response;
			});
		}
		
		retObj.putDiscountEvent = function(event){
			return Restangular.all("discount-event").customPUT(event, "", {}, {}).then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	discountEventResourceFunction.$inject = ["Restangular"];
})();