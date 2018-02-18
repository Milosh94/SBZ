(function(){
	app.factory("BillResource", billResourcefunction);
	
	function billResourcefunction(Restangular){
		var retObj = {};
		
		retObj.getBills = function(){
			return Restangular.all("bill").customGET("").then(function(response){
				return response;
			});
		}
		
		retObj.approveBill = function(code){
			return Restangular.all("approve-bill").customPUT({}, "", {code: code}, {}).then(function(response){
				return response;
			});
		}
		
		retObj.refuseBill = function(code){
			return Restangular.all("refuse-bill").customPUT({}, "", {code: code}, {}).then(function(response){
				return response;
			});
		}
		
		return retObj;
	}
	
	billResourcefunction.$inject = ["Restangular"];
})();