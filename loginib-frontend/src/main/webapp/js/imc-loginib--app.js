// JS


// constants

var imc_finestra
	,imc_body
	,imc_login
	,imc_missatge;


// onReady

$(function(){

	appInicia();

});


function appInicia() {

	APP_IDIOMA = $("html").attr("lang");

	imc_finestra = $(window);
	imc_body = $("body");
	imc_login = $("#imc-login");
	imc_missatge = $("#imc-missatge");


	// login

	imc_body
		.appLogin();

}


/* appLogin */

$.fn.appLogin = function(options) {

	var settings = $.extend({
			element: ""
		}, options);

	this.each(function(){
		var element = $(this)
			,input_elms = element.find("input")
			,button_elm = element.find("button:first")
			,inicia = function() {

				element
					.off(".appMenu")
					.on('keyup.appMenu', revisa)
					.on('click.appMenu', "button", envia);

				setTimeout(
					function() {

						revisa();

					}
					,100
				);

			}
			,revisa = function() {

				if (input_elms.eq(0).val() !== "" && input_elms.eq(1).val() !== "") {

					button_elm
						.removeAttr("disabled");

					return;

				}

				button_elm
					.prop("disabled", true);

			}
			,envia = function(e) {

				var bt = $(this);

				if (bt.is(":disabled")) {
					return;
				}

				// carregant

				imc_missatge
					.attr("aria-hidden", "false")
					.focus();

				// enviem!

				element
					.submit();

			};
		
		// inicia
		inicia();
		
	});

	return this;
}

