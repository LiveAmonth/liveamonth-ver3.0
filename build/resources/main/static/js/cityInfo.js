function selectedCity(selectedCity){
	$.ajax({
		url: "selectCityInfo",
		type: "get",
		data: {cityName: selectedCity},
		success:
			function (data) {
				if(data.size == 1){
					$("#cityGrid").css('display','block');
					$("#cityInfo").css('display','none');
				} else {
					console.log(data.cityName);
					$("#cityGrid").css('display','none');
					$("#cityInfo").css('display','block');
					// $("#cityImg").attr('data-setbg',data.cityIntroList[0].getCityInfoImageURL());
					// $(".cityLabel").html(selectedCity);
					// $(".cityDesc").html(data.cityIntroList[0].cityInfoDesc);
				}
				return data;
			},
		error:
			function (request, status, error) {
				alert("ajax실패")
			}
	});
}