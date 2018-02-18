(function(){
	app.controller("customerCategoriesCtrl", customerCategoriesCtrlFunction);
	
	function customerCategoriesCtrlFunction(CustomerCategoryResource, authentication, $timeout, $state, $uibModal){
		var vm = this;
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
		vm.customerCategories = null;
		CustomerCategoryResource.getCustomerCategories().then(function(response){
			console.log(response.plain());
			vm.customerCategories = response;
		});
		
		vm.updateCategory = function(cat, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/update-customer-category.html",
				controller: "updateCategoryModalCtrl",
				controllerAs: "vm",
				size: "lg",
				resolve: {
					category: function(){
						return cat;
					}
				}
			});
			modalInstance.result.then(function(res){
				if(res !== undefined){
					vm.customerCategories[index].consumptionThresholds = res;
				}
			}, function(res){});
		}
	}
	
	customerCategoriesCtrlFunction.$inject = ["CustomerCategoryResource", "authentication", "$timeout", "$state", "$uibModal"];
	
	app.controller("updateCategoryModalCtrl", updateCategoryModalCtrlFunction);
	
	function updateCategoryModalCtrlFunction(category, $uibModalInstance, $uibModal, CustomerCategoryResource){
		var vm = this;
		
		vm.category = JSON.parse(JSON.stringify(category));
		
		vm.newThreshold = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-threshold.html",
				controller: "newUpdateThresholdModalCtrl",
				controllerAs: "vm",
				resolve: {
					threshold: function(){
						return {};
					}
				}
			});
			modalInstance.result.then(function(res){
				if(res !== undefined){
					vm.category.consumptionThresholds.push(res);
				}
			}, function(res){});
		}
		
		vm.updateThreshold = function(t, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-threshold.html",
				controller: "newUpdateThresholdModalCtrl",
				controllerAs: "vm",
				resolve: {
					threshold: function(){
						return t;
					}
				}
			});
			modalInstance.result.then(function(res){
				if(res !== undefined){
					vm.category.consumptionThresholds[index] = res;
				}
			}, function(res){});
		}
		
		vm.deleteThreshold = function(index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/delete-threshold.html",
				controller: "deleteThresholdModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "delete"){
					vm.category.consumptionThresholds.splice(index, 1);
				}
			}, function(res){});
		}
		
		vm.updateAll = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/update-category-thresholds.html",
				controller: "updateCategoryThresholdsModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "update"){
					CustomerCategoryResource.updateCustomerCategoryThresholds(vm.category.consumptionThresholds, vm.category.categoryCode).then(function(response){
						if(response.status === "Updated"){
							$uibModalInstance.close(vm.category.consumptionThresholds);
						}
					})
				}
			}, function(res){});
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	updateCategoryModalCtrlFunction.$inject = ["category", "$uibModalInstance", "$uibModal", "CustomerCategoryResource"];
	
	app.controller("newUpdateThresholdModalCtrl", newUpdateThresholdModalCtrlFunction);
	
	function newUpdateThresholdModalCtrlFunction($uibModalInstance, threshold){
		var vm = this;
		
		if(Object.keys(threshold).length === 0){
			vm.isNew = true;
		}
		else{
			vm.isNew = false;
		}
		
		vm.threshold = threshold;
		
		vm.addThreshold = function(isValid){
			if(isValid){
				$uibModalInstance.close(vm.threshold);
			}
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
		
		vm.checkPrice = function(){
			if((vm.threshold.priceFrom || vm.threshold.priceFrom >= 0) && (vm.threshold.priceTo || vm.threshold.priceTo >= 0)){
				if(vm.threshold.priceTo < vm.threshold.priceFrom){
					vm.thresholdForm.priceToName.$setValidity("priceError", false);
					vm.thresholdForm.priceFromName.$setValidity("priceError", false);
				}
				else{
					vm.thresholdForm.priceToName.$setValidity("priceError", true);
					vm.thresholdForm.priceFromName.$setValidity("priceError", true);
				}
			}
		}
	}
	
	newUpdateThresholdModalCtrlFunction.$inject = ["$uibModalInstance", "threshold"];
	
	app.controller("deleteThresholdModalCtrl", deleteThresholdModalCtrlFunction);
	
	function deleteThresholdModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.deleteThreshold = function(){
			$uibModalInstance.close("delete");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	deleteThresholdModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("updateCategoryThresholdsModalCtrl", updateCategoryThresholdsModalCtrlFunction);
	
	function updateCategoryThresholdsModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.updateAll = function(){
			$uibModalInstance.close("update");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	updateCategoryThresholdsModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("articleCategoriesCtrl", articleCategoriesCtrlFunction);
	
	function articleCategoriesCtrlFunction(authentication, $timeout, $state, ArticleCategoryResource, $uibModal){
		var vm = this;
		
		ArticleCategoryResource.getArticleCategories().then(function(response){
			vm.categories = response;
		});
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
		vm.deleteCategory = function(category, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/delete-article-category.html",
				controller: "deleteArticleCategoryModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "delete"){
					ArticleCategoryResource.deleteArticleCategory(category.articleCategoryCode).then(function(response){
						ArticleCategoryResource.getArticleCategories().then(function(response){
							vm.categories = response;
						});
					})
				}
			}, function(res){});
		}
		
		vm.newCategory = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-article-category.html",
				controller: "newArticleCategoryModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					ArticleCategoryResource.getArticleCategories().then(function(response){
						vm.categories = response;
					});
				}
			}, function(res){});
		}
		
		vm.updateCategory = function(category, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-article-category.html",
				controller: "updateArticleCategoryModalCtrl",
				controllerAs: "vm",
				resolve: {category: function(){
					return category;
				}}
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					ArticleCategoryResource.getArticleCategories().then(function(response){
						vm.categories = response;
					});
				}
			}, function(res){});
		}
	}
	
	articleCategoriesCtrlFunction.$inject = ["authentication", "$timeout", "$state", "ArticleCategoryResource", "$uibModal"];
	
	app.controller("deleteArticleCategoryModalCtrl", deleteArticleCategoryModalCtrlFunction);
	
	function deleteArticleCategoryModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.deleteCategory = function(){
			$uibModalInstance.close("delete");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	deleteArticleCategoryModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("newArticleCategoryModalCtrl", newArticleCategoryModalCtrlFunction);
	
	function newArticleCategoryModalCtrlFunction($uibModalInstance, ArticleCategoryResource){
		var vm = this;
		
		vm.category = {};
		vm.isNew = true;
		vm.categoryChecked = false;
		
		vm.newCategory = function(isValid){
			if(isValid){
				ArticleCategoryResource.postArticleCategory(vm.category, vm.categoryChecked).then(function(response){
					$uibModalInstance.close("success");
				}, function(response){
					if(response.status === 409){
						vm.categoryForm.articleCategoryCodeName.$setValidity("uniqueError", false);
					}
				});
			}
		}
		
		vm.codeChanged = function(){
			vm.categoryForm.articleCategoryCodeName.$setValidity("uniqueError", true);
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	newArticleCategoryModalCtrlFunction.$inject = ["$uibModalInstance", "ArticleCategoryResource"];
	
	app.controller("updateArticleCategoryModalCtrl", updateArticleCategoryModalCtrlFunction);
	
	function updateArticleCategoryModalCtrlFunction($uibModalInstance, category, ArticleCategoryResource){
		var vm = this;
		
		vm.category = JSON.parse(JSON.stringify(category));
		vm.isNew = false;
		if(vm.category.parentCategory !== null && vm.category.parentCategory.name === "Consumer Goods"){
			vm.categoryChecked = true;
		}
		else{
			vm.categoryChecked = false;
		}
		
		vm.updateCategory = function(isValid){
			if(isValid){
				ArticleCategoryResource.putArticleCategory(vm.category, vm.categoryChecked).then(function(response){
					$uibModalInstance.close("success");
				});
			}
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	updateArticleCategoryModalCtrlFunction.$inject = ["$uibModalInstance", "category", "ArticleCategoryResource"];
	
	app.controller("discountEventsCtrl", discountEventsCtrlFunction);
	
	function discountEventsCtrlFunction(authentication, $timeout, $state, DiscountEventResource, $uibModal){
		var vm = this;
		
		DiscountEventResource.getDiscountEvents().then(function(response){
			vm.discountEvents = response;
		});
		
		vm.loggedUser = authentication.getLoggedUser();
		if(vm.loggedUser === null){
			$timeout(function(){
				$state.go("root");
			});
		}
		
		vm.loggedUserRole = vm.loggedUser.role[0].authority;
		
		vm.deleteDiscountEvent = function(discount, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/delete-discount-event.html",
				controller: "deleteDiscountEventModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "delete"){
					DiscountEventResource.deleteDiscountEvent(discount.eventCode).then(function(response){
						DiscountEventResource.getDiscountEvents().then(function(response){
							vm.discountEvents = response;
						});
					})
				}
			}, function(res){});
		}
		
		vm.newDiscountEvent = function(){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-discount-event.html",
				controller: "newDiscountEventModalCtrl",
				controllerAs: "vm"
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					DiscountEventResource.getDiscountEvents().then(function(response){
						vm.discountEvents = response;
					});
				}
			}, function(res){});
		}
		
		vm.updateDiscountEvent = function(discount, index){
			var modalInstance = $uibModal.open({
				templateUrl: "core/views/modals/new-update-discount-event.html",
				controller: "updateDiscountEventModalCtrl",
				controllerAs: "vm",
				resolve: {discountEvent: function(){
					return discount;
				}}
			});
			modalInstance.result.then(function(res){
				if(res === "success"){
					DiscountEventResource.getDiscountEvents().then(function(response){
						vm.discountEvents = response;
					});
				}
			}, function(res){});
		}
	}
	
	discountEventsCtrlFunction.$inject = ["authentication", "$timeout", "$state", "DiscountEventResource", "$uibModal"];
	
	app.controller("deleteDiscountEventModalCtrl", deleteDiscountEventModalCtrlFunction);
	
	function deleteDiscountEventModalCtrlFunction($uibModalInstance){
		var vm = this;
		
		vm.deleteDiscountEvent = function(){
			$uibModalInstance.close("delete");
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	deleteDiscountEventModalCtrlFunction.$inject = ["$uibModalInstance"];
	
	app.controller("newDiscountEventModalCtrl", newDiscountEventModalCtrlFunction);
	
	function newDiscountEventModalCtrlFunction($uibModalInstance, DiscountEventResource, CategoryResource, $uibModal){
		var vm = this;
		
		vm.discountEvent = {};
		vm.isNew = true;
		
		CategoryResource.getCategoriesWithoutGoods().then(function(response){
			vm.articleCategories = response;	
		});
		
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
		
		vm.selectedCategoriesLength = 0;
		vm.first = true;
		vm.updateCategories = function(category){
			if(vm.first){
				vm.discountEventForm["articleCategoriesName0"].$setTouched();
			}
			vm.first = false;
			if(category.isChecked){
				vm.selectedCategoriesLength += 1;
			}
			else{
				vm.selectedCategoriesLength -= 1;
			}
			if(vm.selectedCategoriesLength === 0){
				vm.discountEventForm["articleCategoriesName0"].$setValidity("requiredError", false);
			}
			else{
				vm.discountEventForm["articleCategoriesName0"].$setValidity("requiredError", true);
			}
		}
		
		vm.newDiscountEvent = function(isValid){
			vm.discountEvent.articleCategories = [];
			if(isValid){
				for(var i = 0; i < vm.articleCategories.length; i++){
					if(vm.articleCategories[i].isChecked){
						vm.discountEvent.articleCategories.push(vm.articleCategories[i]);
					}
				}
				DiscountEventResource.postDiscountEvent(vm.discountEvent).then(function(response){
					$uibModalInstance.close("success");
				}, function(response){
					if(response.status === 409){
						vm.discountEventForm.discountEventCodeName.$setValidity("uniqueError", false);
					}
					if(response.status === 417){
						var modalInstance = $uibModal.open({
							templateUrl: "core/views/modals/error-discount-event.html"
						})
					}
				});
			}
		}
		
		vm.codeChanged = function(){
			vm.discountEventForm.discountEventCodeName.$setValidity("uniqueError", true);
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	newDiscountEventModalCtrlFunction.$inject = ["$uibModalInstance", "DiscountEventResource", "CategoryResource", "$uibModal"];
	
	app.controller("updateDiscountEventModalCtrl", updateDiscountEventModalCtrlFunction);
	
	function updateDiscountEventModalCtrlFunction($uibModalInstance, discountEvent, DiscountEventResource, CategoryResource, $uibModal){
		var vm = this;
		
		vm.discountEvent = JSON.parse(JSON.stringify(discountEvent));
		vm.isNew = false;
		
		CategoryResource.getCategoriesWithoutGoods().then(function(response){
			vm.articleCategories = response;
			for(var i = 0; i < vm.discountEvent.articleCategories.length; i++){
				for(var j = 0; j < vm.articleCategories.length; j++){
					if(vm.discountEvent.articleCategories[i].articleCategoryCode === vm.articleCategories[j].articleCategoryCode){
						vm.articleCategories[j].isChecked = true;
						break;
					}
				}
			}
		});
		
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
		
		vm.selectedCategoriesLength = vm.discountEvent.articleCategories.length;
		vm.first = true;
		vm.updateCategories = function(category){
			if(vm.first){
				vm.discountEventForm["articleCategoriesName0"].$setTouched();
			}
			vm.first = false;
			if(category.isChecked){
				vm.selectedCategoriesLength += 1;
			}
			else{
				vm.selectedCategoriesLength -= 1;
			}
			if(vm.selectedCategoriesLength === 0){
				vm.discountEventForm["articleCategoriesName0"].$setValidity("requiredError", false);
			}
			else{
				vm.discountEventForm["articleCategoriesName0"].$setValidity("requiredError", true);
			}
		}
		
		vm.updateDiscountEvent = function(isValid){
			vm.selectedArticleCategories = [];
			if(isValid){
				for(var i = 0; i < vm.articleCategories.length; i++){
					if(vm.articleCategories[i].isChecked){
						vm.selectedArticleCategories.push(vm.articleCategories[i]);
					}
				}
				vm.discountEvent.articleCategories = vm.selectedArticleCategories;
				DiscountEventResource.putDiscountEvent(vm.discountEvent).then(function(response){
					$uibModalInstance.close("success");
				}, function(response){
					if(response.status === 417){
						var modalInstance = $uibModal.open({
							templateUrl: "core/views/modals/error-discount-event.html"
						})
					}
				});
			}
		}
		
		vm.cancel = function(){
			$uibModalInstance.dismiss("cancel");
		}
	}
	
	updateDiscountEventModalCtrlFunction.$inject = ["$uibModalInstance", "discountEvent", "DiscountEventResource", "CategoryResource", "$uibModal"];
})();