(function(){
	app.service("authentication", authenticationFunction);
	
	function authenticationFunction(UserResource, $window){
		var retObj = {};
		
		retObj.getToken = function(){
			return $window.localStorage["token"];
		}
		
		retObj.saveToken = function(token){
			$window.localStorage["token"] = token;
		}
		
		retObj.logout = function(){
			$window.localStorage.removeItem("token");
		}
		
		retObj.login = function(user){
			return UserResource.login(user).then(function(response){
				retObj.saveToken(response.token);
			});
		}
		
		retObj.register = function(user){
			return UserResource.register(user).then(function(response){
				retObj.saveToken(response.token);
			});
		}
		
		retObj.isLoggedIn = function(){
			var token = retObj.getToken();
			var payload;
			console.log(token);
			if(token){
				payload = token.split(".")[1];
				console.log(payload);
				payload = $window.atob(payload);
				payload = JSON.parse(payload);
				console.log(payload);
				console.log(Date.now());
				return payload.exp > Date.now() / 1000;
			}
			return false;
		}
		
		retObj.getLoggedUser = function(){
			if(retObj.isLoggedIn()){
				var token = retObj.getToken();
				payload = token.split(".")[1];
				payload = $window.atob(payload);
				payload = JSON.parse(payload);
				return payload;
			}
			return null;
			//return UserResource.getLoggedUser().then(function(response){
			//	return response;
			//})
		}
		
		/*
		retObj.getLoggedUserRole = function(){
			if(retObj.isLoggedIn){
				var token = retObj.getToken();
				payload = token.split(".")[1];
				payload = $window.atob(payload);
				payload = JSON.parse(payload);
				return payload.role[0];
			}
			return null;
		}
		*/
		
		return retObj;
	}
	
	authenticationFunction.$inject = ["UserResource", "$window"];
})();