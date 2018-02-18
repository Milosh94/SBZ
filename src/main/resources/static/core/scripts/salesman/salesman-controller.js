(function(){
	app.controller("processBillsCtrl", processBillsCtrlFunction);
	
	function processBillsCtrlFunction(authentication, $timeout, $state, BillResource, $uibModal, toastr, toastrConfig){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
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
					setHeight: 150,
					scrollInertia: 0
		}
		
		BillResource.getBills().then(function(response){
			vm.bills = response;
			console.log(vm.bills);
		});
		vm.realized = false;
		vm.ordered = false;
		vm.canceled = false;
		vm.filterBills = function(){
			return function(bill){
				var billsToShow = [];
				if(vm.realized === false && vm.ordered === false && vm.canceled === false){
					return true;
				}
				if(vm.realized === true){
					billsToShow.push(1);
				}
				if(vm.ordered === true){
					billsToShow.push(0);
				}
				if(vm.canceled === true){
					billsToShow.push(-1);
				}
				if(billsToShow.includes(bill.status)){
					return true;
				}
				else{
					return false;
				}
			}
		}
		
		vm.orderByDate = function(item){
			var parts = item.created.split(" ");
			return new Date(parts[1]+"T"+parts[0]+"Z");
		}
		
		vm.processBill = function(bill){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/process-bill.html",
				controller: "processBillModalCtrl",
				controllerAs: "vm",
				size: "lg",
				resolve: {
					bill: function(){
						return bill;
					}
				}
			});
			modalInstance.result.then(function(res){
				toastrConfig.maxOpened = 4;
				if(res === "success"){
					BillResource.getBills().then(function(response){
						vm.bills = response;
					});
					toastr.success("Bill Processed", "Success", {
						closeButton: true,
						timeout: 3000
					})
				}
				if(res === "error"){
					BillResource.getBills().then(function(response){
						vm.bills = response;
					});
					toastr.error("Bill Not Processed", "Error", {
						closeButton: true,
						timeout: 3000
					});
				}
			}, function(res){});
		}
	}
	
	processBillsCtrlFunction.$inject = ["authentication", "$timeout", "$state", "BillResource", "$uibModal", "toastr", "toastrConfig"];
	
	app.controller("processBillModalCtrl", processBillModalCtrlFunction);
	
	function processBillModalCtrlFunction($uibModalInstance, bill, $uibModal, BillResource){
		var vm = this;
		
		vm.bill = bill;
		
		vm.approve = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/approve-bill.html",
				controller: "approveBillModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					BillResource.approveBill(vm.bill.billCode).then(function(response){
						$uibModalInstance.close("success");
					}, function(error){
						$uibModalInstance.close("error");
					})
				}
			}, function(res){});
		}
		
		vm.refuse = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/refuse-bill.html",
				controller: "refuseBillModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					BillResource.refuseBill(vm.bill.billCode).then(function(response){
						$uibModalInstance.close("success");
					}, function(error){
						$uibModalInstance.close("error");
					})
				}
			}, function(res){});
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	processBillModalCtrlFunction.$inject = ["$uibModalInstance", "bill", "$uibModal", "BillResource"];
	
	app.controller("approveBillModalCtrl", approveBillModalCtrlFunction);
	
	function approveBillModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.yes = function(){
			$uibModalInstance.close("success");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	approveBillModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("refuseBillModalCtrl", refuseBillModalCtrlFunction);
	
	function refuseBillModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.yes = function(){
			$uibModalInstance.close("success");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	refuseBillModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("orderArticlesCtrl", orderArticlesCtrlFunction);
	
	function orderArticlesCtrlFunction(authentication, $timeout, $state, ArticleResource){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
		ArticleResource.getOrderArticles().then(function(resp){
			vm.orderArticles = resp;
			console.log(resp);
			ArticleResource.getAllArticles().then(function(response){
				for(var i = response.length -1; i >= 0; i--){
					for(var j = 0; j < vm.orderArticles.length; j++){
						if(response[i].articleCode === vm.orderArticles[j].articleCode){
							response.splice(i, 1);
							break;
						}
					}
				}
				vm.articles = response;
			});
		});
		
		vm.updateArt = function(article){
			if(article.updatedQuantity > 0){
				ArticleResource.putOrderArticles(article.articleCode, article.updatedQuantity).then(function(response){
					ArticleResource.getOrderArticles().then(function(resp){
						vm.orderArticles = resp;
						console.log(resp);
						ArticleResource.getAllArticles().then(function(response){
							for(var i = response.length -1; i >= 0; i--){
								for(var j = 0; j < vm.orderArticles.length; j++){
									if(response[i].articleCode === vm.orderArticles[j].articleCode){
										response.splice(i, 1);
										break;
									}
								}
							}
							vm.articles = response;
						});
					});
				});
			}
		}
	}
	
	orderArticlesCtrlFunction.$inject = ["authentication", "$timeout", "$state", "ArticleResource"];
})();