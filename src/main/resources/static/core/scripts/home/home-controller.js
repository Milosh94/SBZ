(function(angular){
	app.controller("rootCtrl", rootCtrlFunction);
	
	function rootCtrlFunction($state, authentication, $timeout){
		console.log("usao u root ctrl");
		if($state.is("root")){
			if(authentication.isLoggedIn()){
				$timeout(function(){
					$state.go("home");
				});
			}
			else{
				$timeout(function(){
					$state.go("login");
				});
			}
		}
	}
	
	rootCtrlFunction.$inject = ["$state", "authentication", "$timeout"];
	
	app.controller("loginCtrl", loginCtrlFunction);
	
	function loginCtrlFunction(authentication, $state, $uibModal, UserResource, $timeout){
		console.log("usao u login ctrl");
		if(!authentication.isLoggedIn()){
			var vm = this;
			vm.user = {};
			vm.roles = {};
			vm.login = function(isValid){
				if(isValid){
					authentication.login(vm.user).then(function(response){
						$state.go("home");
					});
				}
			}
			UserResource.getRoles().then(function(response){
				vm.roles = response;
			});
			vm.openRegisterModal = function(){
				var modalInstance = $uibModal.open({
					templateUrl: "core/views/modals/register.html",
					controller: "registerModalCtrl",
					controllerAs: "vm",
					resolve: {
						roles: function(){
							return vm.roles;
						}
					}
				});
				modalInstance.result.then(function(){}, function(res){});
			}
		}
		else{
			$timeout(function(){
				$state.go("home");
			});
		}
	}
	
	loginCtrlFunction.$inject = ["authentication", "$state", "$uibModal", "UserResource", "$timeout"];
	
	app.controller("registerModalCtrl", registerModalCtrlFunction);
	
	function registerModalCtrlFunction($uibModalInstance, roles, authentication, $state, UserResource){
		var vm = this;
		vm.addressRequired = false;
		vm.user = {};
		vm.roles = roles;
		console.log(vm.roles);
		vm.role = vm.roles[0];
		for(var i = 0; i < vm.roles.length; i++){
			console.log(vm.roles[i].name);
		}
		vm.selectedRole = function($item, $model){
			if($item.name !== "ROLE_CUSTOMER"){
				vm.user.deliveryAddress = "";
				vm.addressRequired = false;
			}
			else{
				vm.addressRequired = true;
			}
		}
		//vm.registerForm.usernameName.$error.usernameExists = false;
		vm.checkUsername = function(){
			if(vm.user.username && vm.user.username.trim() !== ""){
				UserResource.usernameUnique(vm.user.username).then(function(response){
					console.log(response);
					//vm.registerForm.usernameName.$error.usernameExists = !response;
					vm.registerForm.usernameName.$setValidity("usernameExists", response);
					console.log("qeqwe");
					console.log(vm.registerForm.usernameName);
					return response;
				});
			}
		}
		//vm.registerForm.confirmPasswordName.$error.passwordNotSame = false;
		vm.checkPasswords = function(){
			if(vm.confirmPassword !== ""){
				if(vm.user.password !== vm.confirmPassword){
					vm.registerForm.confirmPasswordName.$setValidity("passwordNotSame", false);
				}
				else{
					vm.registerForm.confirmPasswordName.$setValidity("passwordNotSame", true);
				}
			}
		}
		vm.register = function(isValid){
			console.log("regisret submit");
			console.log(isValid);
			/*
			if(vm.user.password !== vm.confirmPassword){
				vm.registerForm.confirmPasswordName.$error.passwordNotSame = true;
			}
			else{
				vm.registerForm.confirmPasswordName.$error.passwordNotSame = false;
			}
			*/
			if(isValid && vm.user.password === vm.confirmPassword){
				vm.user.role = vm.role.name;
				authentication.register(vm.user).then(function(response){
					$uibModalInstance.dismiss("cancel");
					$state.go("home");
				});
			}
		}
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
			console.log(vm.role);
		}
	}
	
	registerModalCtrlFunction.$inject = ["$uibModalInstance", "roles", "authentication", "$state", "UserResource"];
	
	app.controller("homeCtrl", homeCtrlFunction);
	
	function homeCtrlFunction(authentication, $window, $state, $timeout){
		var vm = this;
	
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			console.log("homecrtleowqeq");
			$timeout(function(){
				$state.go("root");
			});
		}
		console.log("usao u hoem ctrl");
		
		vm.logout = function(){
			authentication.logout();
			$window.localStorage.removeItem("shoppingCart");
			$state.go("root");
		}
		//console.log($state.current.name);
		if($state.is("home")){
			$timeout(function(){
				console.log("uqweqwneuiqwewqoiuieru");
				$state.go("homepage");
			});
		}
		
	}
	
	homeCtrlFunction.$inject = ["authentication", "$window", "$state", "$timeout"];
	
	app.controller("homepageCtrl", homepageCtrlFunction);
	
	function homepageCtrlFunction(ArticleResource, authentication, $window, CategoryResource, $state,
			toastr, toastrConfig, $timeout, CustomerCategoryResource){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
		if(vm.loggedUserRole.valueOf() === "ROLE_CUSTOMER"){
			if($window.localStorage["shoppingCart"] === undefined){
				console.log("empty");
				$window.localStorage["shoppingCart"] = JSON.stringify([]);
			}
			console.log("ima nes");
			vm.config = {
					autoHideScrollbar: false,
					theme: "dark-thin",
					scrollButtons: {
						scrollAmount: "auto",
						enable: true
					},
					advanced:{
						updateOnContentResize: true
					},
						setHeight: 200,
						scrollInertia: 0
			}
			
			console.log("usao u hoem ctrl");
			
			CategoryResource.getCategories().then(function(response){
				console.log(response);
				vm.articleCategories = response;	
			});
			vm.categories = [];
			vm.searchParams = {
					articleId: "",
					articleName: "",
					priceFrom: null,
					priceTo: null,
					categories: [],
					page: 0,
					size: 10,
					sort: "name,desc"
			}
			vm.searchParamsCopy = {};
			vm.searchOrder = {
					0: {name: "name", order: "desc"},
					1: {name: "articleCode", order: "desc"},
					2: {name: "articleCategory.name", order: "desc"},
					3: {name: "price", order: "desc"}
			}
			vm.searchArticles = function(categoryList, isPage, index = -1){
				vm.searchParams.categories = categoryList;
				if(isPage === false){
					vm.searchParamsCopy = JSON.parse(JSON.stringify(vm.searchParams));
				}
				vm.pageList = [];
				if(index !== -1){
					if(vm.searchOrder[index].order.valueOf() === "desc".valueOf()){
						vm.searchOrder[index].order = "asc";
					}
					else{
						vm.searchOrder[index].order = "desc";
					}
					vm.searchParamsCopy.sort = vm.searchOrder[index].name + "," +vm.searchOrder[index].order; 
				}
				ArticleResource.searchArticles(vm.searchParamsCopy).then(function(response){
					vm.searchedArticles = response.content;
					for(var i = 0; i < response.totalPages; i++){
						vm.pageList.push(i);
					}
					vm.currentPage = response.number;
					console.log(response);
				});
			}
			vm.searchArticles([], false);
			
			vm.clearSearch = function(){
				vm.searchParams.articleId = "";
				vm.searchParams.articleName = "";
				vm.searchParams.priceFrom = null;
				vm.searchParams.priceTo = null;
				vm.searchParams.categories = [];
				vm.searchParams.page = 0;
				vm.categories = [];
				for(var i = 0; i < vm.articleCategories.length; i++){
					vm.articleCategories[i].isChecked = false;
				}
			}
			
			vm.updateCategories = function(category){
				if(category.isChecked){
					vm.categories.push(category.articleCategoryCode);
				}
				else{
					var i = vm.categories.indexOf(category.articleCategoryCode);
					if(i != -1) {
						vm.categories.splice(i, 1);
					}
				}
			}
			
			vm.getArticlePage = function(pageNumber){
				vm.searchParamsCopy.page = pageNumber;
				vm.searchArticles([], true);
			}
			
			vm.cartArticles = [];
			vm.addToCart = function(article){
				console.log(article.cartQuantity);
				toastrConfig.maxOpened = 4;
				if(article.cartQuantity === undefined || article.cartQuantity < 0 || article.cartQuantity > article.articleCount){
					//toastrConfig.maxOpened = 4;
					toastr.error("Not a valid quantity", "Error: " + article.name, {
						closeButton: true,
						timeout: 3000
					});
				}
				else{
					var currentShoppingCart = JSON.parse($window.localStorage["shoppingCart"]);
					var dontExists = true;
					for(var i = 0; i < currentShoppingCart.length; i++){
						if(currentShoppingCart[i].articleCode.valueOf() === article.articleCode.valueOf()){
							var total = currentShoppingCart[i].cartQuantity + article.cartQuantity;
							if(total > article.articleCount){
								//toastrConfig.maxOpened = 4;
								toastr.error("Too much quantity, already added " + currentShoppingCart[i].cartQuantity + " to cart", "Error: " + article.name, {
									closeButton: true,
									timeout: 3000
								});
							}
							else{
								//toastrConfig.maxOpened = 4;
								toastr.success("Added " + article.cartQuantity + ", total in cart " + total, "Success: " + article.name, {
									closeButton: true,
									timeout: 3000
								})
								currentShoppingCart[i].cartQuantity = total;
							}
							dontExists = false;
							break;
						}
					}
					if(dontExists){
						toastr.success("Added " + article.cartQuantity + ", total in cart " + article.cartQuantity, "Success: " + article.name, {
							closeButton: true,
							timeout: 3000
						})
						currentShoppingCart.push(article);
					}
					$window.localStorage["shoppingCart"] = JSON.stringify(currentShoppingCart);
				}
			}
		}
		else if(vm.loggedUserRole.valueOf() === "ROLE_MANAGER"){
			$timeout(function(){
				$state.go("customer-categories");
			});
			
			
		}
		else if(vm.loggedUserRole.valueOf() === "ROLE_SALESMAN"){
			
		}
	}
	
	homepageCtrlFunction.$inject = ["ArticleResource", "authentication", "$window", "CategoryResource", "$state",
		"toastr", "toastrConfig", "$timeout", "CustomerCategoryResource"];
})(angular);