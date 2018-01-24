(function(angular){
	app = angular.module("WebShopApp", ["ui.router", "restangular", "toastr", "ngMessages", "ui.bootstrap", "ui.select", "ngScrollbars"]);
	
	app.config(configFunction).run(runFunction);
	
	function configFunction($stateProvider, $urlRouterProvider, $locationProvider){
		$urlRouterProvider.otherwise("/");
		
		$stateProvider
				.state("root", {
					url: "/",
					views: {
						"mainView@": {
							controller: "rootCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("login", {
					views: {
						"mainView@": {
							templateUrl: "core/views/login.html",
							controller: "loginCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("home", {
					views: {
						"mainView@": {
							templateUrl: "core/views/home.html",
							controller: "homeCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("homepage", {
					parent: "home",
					views: {
						"homeView@home": {
							templateUrl: "core/views/homepage.html",
							controller: "homepageCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("profile", {
					parent: "home",
					url: "/profile",
					views: {
						"homeView@home": {
							templateUrl: "core/views/profile.html",
							controller: "profileCtrl",
							controllerAs: "vm"
						},
						"shoppingsView@profile": {
							templateUrl: "core/views/shopping-history.html"
						}
					}
				})
				.state("shopping-history", {
					parent: "home",
					url: "/shopping-history",
					views: {
						"homeView@home": {
							templateUrl: "core/views/shopping-history.html",
							controller: "profileCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("shopping-cart", {
					parent: "home",
					url: "/shopping-cart",
					views: {
						"homeView@home": {
							templateUrl: "core/views/shopping-cart.html",
							controller: "shoppingCartCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("customer-categories", {
					parent: "homepage",
					url:"/customer-categories",
					views: {
						"managerView@homepage": {
							templateUrl: "core/views/customer-categories.html",
							controller: "customerCategoriesCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("article-categories", {
					parent: "homepage",
					url: "/article-categories",
					views: {
						"managerView@homepage": {
							templateUrl: "core/views/article-categories.html",
							controller: "articleCategoriesCtrl",
							controllerAs: "vm"
						}
					}
				})
				.state("discount-events", {
					parent: "homepage",
					url: "/discount-events",
					views: {
						"managerView@homepage": {
							templateUrl: "core/views/discount-events.html",
							controller: "discountEventsCtrl",
							controllerAs: "vm"
						}
					}
				});
		
		
		
		//$locationProvider.hashPrefix('');
		//$locationProvider.html5Mode(true);
	}
	
	configFunction.$inject = ["$stateProvider", "$urlRouterProvider", "$locationProvider"];
	
	function runFunction(Restangular, authentication, $transitions, $window){
		Restangular.setDefaultHeaders({Authorization: function(){return "Bearer " + authentication.getToken()}});
		Restangular.setBaseUrl("api");
		/*
		$transitions.onSuccess({ exiting: "profile", entering: "root" }, function(transition) {
			  console.log("Now inside the admin section!");
			  $window.location.reload();
			});
		$transitions.onBefore({}, function(transition) {
			// check if the state should be protected
			if(transition.to().protected && !user.isAuthenticated()) {
				// redirect to the 'login' state
				return transition.router.stateService.target('login');
			}
		}
		*/
		$transitions.onStart({}, function(transition) {
			var user = authentication.getLoggedUser();
			console.log(user);
			console.log(transition.from().name);
			console.log(transition.to().name);
			console.log((transition.to().name == "profile" && user === null) || (transition.to().name == "profile" &&user.role[0].authority.valueOf() !== "ROLE_CUSTOMER".valueOf()));
			if ((transition.to().name.valueOf() === "profile".valueOf() || transition.to().name.valueOf() === "shopping-history".valueOf() ||
					transition.to().name.valueOf() === "shopping-cart".valueOf())
					&& (user === null || user.role[0].authority.valueOf() !== "ROLE_CUSTOMER".valueOf())) {
				console.log("qwqwqwqwqwq");
				return transition.router.stateService.target("root");
			}
		});
	}
	
	runFunction.$inject = ["Restangular", "authentication", "$transitions", "$window"];
	
})(angular);