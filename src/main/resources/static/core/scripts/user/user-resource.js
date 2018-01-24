(function(){
	app.factory("UserResource", userResourceFunction);
	
	function userResourceFunction(Restangular){
		var retObj = {};
		
		retObj.getLoggedUser = function(){
			return Restangular.all("user").customGET("").then(function(response){
				return response;
			}, function(error){
				return "error";
			});
		}
		
		retObj.login = function(user){
			return Restangular.all("login").post(user).then(function(response){
				return response;
			});
		}
		
		retObj.register = function(user){
			return Restangular.all("register").post(user).then(function(response){
				return response;
			});
		}
		
		retObj.getRoles = function(){
			return Restangular.all("role").getList().then(function(response){
				return response;
			});
		}
		
		retObj.usernameUnique = function(name){
			return Restangular.all("username-unique").customGET("", {username: name}).then(function(response){
				return response;
			});
		}
		
		retObj.getUserProfile = function(){
			return Restangular.all("user-profile").customGET("").then(function(response){
				return response;
			}, function(error){
				return "error";
			});
		}
		
		return retObj;
	}
	
	userResourceFunction.$inject = ["Restangular"]
})();