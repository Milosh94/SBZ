(function(angular){
	app.controller("profileCtrl", profileCtrlFunction);
	
	function profileCtrlFunction(authentication, $state, UserResource, CustomerResource, $timeout){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				console.log("uqweqwneuiqwewqoiuieru");
				$state.go("root", {reload: true});
			});
		}
		else{
			
			vm.user = null;
			
			UserResource.getLoggedUser().then(function(response){
				vm.user = response;
			});
			
			vm.profile = null;
			vm.shoppingHistory = null;
			vm.userRole = vm.loggedUser.role[0].authority;
			if(vm.userRole.valueOf() === "ROLE_CUSTOMER".valueOf()){
				UserResource.getUserProfile().then(function(response){
					vm.profile = response;
					console.log(vm.profile);
				});
				CustomerResource.shoppingHistory().then(function(response){
					vm.shoppingHistory = response;
					console.log(vm.shoppingHistory.plain());
				});
			}
		}
		
	}
	
	profileCtrlFunction.$inject = ["authentication", "$state", "UserResource", "CustomerResource", "$timeout"];
	
	app.controller("shoppingCartCtrl", shoppingCartCtrlFunction);
	
	function shoppingCartCtrlFunction(authentication, $state, $window, UserResource, toastr, toastrConfig, $uibModal, CustomerResource){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				console.log("uqweqwneuiqwewqoiuieru");
				$state.go("root", {reload: true});
			});
		}
		else{
			
			vm.user = null;
			vm.userProfile = null;
			vm.rewardPointsToSpend = 0;
			UserResource.getLoggedUser().then(function(response){
				vm.user = response;
			});
			UserResource.getUserProfile().then(function(response){
				vm.userProfile = response;
				console.log(response);
			});
			vm.currentShoppingCart = [];
			if($window.localStorage["shoppingCart"] === undefined){
				console.log("empty");
				$window.localStorage["shoppingCart"] = JSON.stringify([]);
			}
			else {
				vm.currentShoppingCart = JSON.parse($window.localStorage["shoppingCart"]);
				console.log(vm.currentShoppingCart);
			}
			
			vm.updateCart = function(article){
				toastrConfig.maxOpened = 4;
				if(article.updatedCartQuantity === undefined || article.updaredCartQuantity < 0){
					toastr.error("Not a valid quantity", "Error: " + article.name, {
						closeButton: true,
						timeout: 3000
					});
				}
				else{
					article.cartQuantity = article.updatedCartQuantity;
					toastr.success("Total: " + article.cartQuantity, "Success: " + article.name, {
						closeButton: true,
						timeout: 3000
					});
					article.updatedCartQuantity = undefined;
					$window.localStorage["shoppingCart"] = JSON.stringify(vm.currentShoppingCart);
				}
			}
			
			vm.removeFromCart = function(article){
				var exists = false;
				for(var i = 0; i < vm.currentShoppingCart.length; i++){
					if(vm.currentShoppingCart[i].articleCode.valueOf() === article.articleCode.valueOf()){
						vm.currentShoppingCart.splice(i, 1);
						exists = true;
						break;
					}
				}
				if(exists){
					toastr.success("Successfully removed: " + article.name, {
						closeButton: true,
						timeout: 3000
					});
					$window.localStorage["shoppingCart"] = JSON.stringify(vm.currentShoppingCart);
				}
				else{
					toastr.error("Could not remove from cart", "Error: " + article.name, {
						closeButton: true,
						timeout: 3000
					});
				}
			}
			
			vm.emptyCart = function(){
				var modalInstance = $uibModal.open({
					templateUrl: "core/views/modals/empty-cart.html",
					controller: "emptyCartModalCtrl",
					controllerAs: "vm",
					resolve: {
						cart: function(){
							return vm.currentShoppingCart;
						}
					}
				});
				modalInstance.result.then(function(res){
					if(res !== undefined && res.length === 0){
						vm.currentShoppingCart = [];
						$window.localStorage["shoppingCart"] = JSON.stringify([]);
					}
				}, function(res){});
			}
			
			vm.checkout = function(isValid){
				if(isValid){
					var modalInstance = $uibModal.open({
						templateUrl: "core/views/modals/checkout.html",
						controller: "checkoutModalCtrl",
						controllerAs: "vm"
					});
					modalInstance.result.then(function(res){
						if(res !== undefined && res.length === 0){
							console.log(vm.rewardPointsToSpend);
							console.log(vm.currentShoppingCart);
							var cart = {};
							cart.rewardPoints = vm.rewardPointsToSpend;
							cart.items = [];
							for(var i = 0; i < vm.currentShoppingCart.length; i++){
								cart.items.push({
									articleCode: vm.currentShoppingCart[i].articleCode,
									quantity: vm.currentShoppingCart[i].cartQuantity
								});
							}
							CustomerResource.checkout(cart).then(function(response){
								vm.currentShoppingCart = [];
								$window.localStorage["shoppingCart"] = JSON.stringify([]);
								var modalInstanceBill = $uibModal.open({
									templateUrl: "core/views/modals/bill-checkout-info.html",
									controller: "billCheckoutInfoModalCtrl",
									controllerAs: "vm",
									size: "lg", 
									resolve: {
										bill: function(){
											return response;
										}
									}
								});
							}, function(error){
								toastrConfig.maxOpened = 4;
								toastr.error("Could not checkout cart", "Error", {
									closeButton: true,
									timeout: 3000
								});
							});
						}
					}, function(res){});
				}
			}
		}
	}
	
	shoppingCartCtrlFunction.$inject = ["authentication", "$state", "$window", "UserResource", "toastr", "toastrConfig", "$uibModal", "CustomerResource"];
	
	app.controller("billCheckoutInfoModalCtrl", billCheckoutInfoModalCtrlFunction);
	
	function billCheckoutInfoModalCtrlFunction(bill, $uibModalInstance){
		var vm = this;
		
		vm.bill = bill;
		
		vm.okay = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	billCheckoutInfoModalCtrlFunction.$inject = ["bill", "$uibModalInstance"];
	
	app.controller("emptyCartModalCtrl", emptyCartModalCtrlFunction);
	
	function emptyCartModalCtrlFunction(cart, $uibModalInstance){
		var vm = this;
		
		vm.cart = cart;
		
		vm.empty = function(){
			$uibModalInstance.close([]);
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	emptyCartModalCtrlFunction.$inject = ["cart", "$uibModalInstance"];
	
	app.controller("checkoutModalCtrl", checkoutModalCtrlFunction);
	
	function checkoutModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.yes = function(){
			$uibModalInstance.close([]);
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	checkoutModalCtrlFunction.$inject = ["$uibModalInstance"];
})(angular);