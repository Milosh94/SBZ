(function(){
	app.directive("datepicker", datepickerFunction);
	
	function datepickerFunction($parse){
		return {
			link: function(scope, element, attrs){
				var config = $parse(attrs["datepicker"])(scope);
				var picker = element.datepicker({
					format: "dd-mm-yyyy"
				});
				if(!config.isNew){
					var first = true;
					picker.on("show", function(e){
						if(first){
							var dates = element[0].querySelectorAll("input");
							for(var i = 0; i < dates.length; i++){
								var stringDateParts = $(dates[i])[0].value.split("-");	
								$(dates[i]).datepicker("setDate", new Date(stringDateParts[2], stringDateParts[1] - 1, stringDateParts[0]));
							}
						}
						first = false;
					});
				}
			},
			restrict: "A"
		};
	}
	
	datepickerFunction.$inject = ["$parse"];
})();