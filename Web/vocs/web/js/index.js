  /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////CACHER MENU/////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

var precedent = 0;
var $window = $(window);
var nav = $('#barre-nav');

$window.on('scroll', function(){
  var niveauDeScroll = $window.scrollTop();
  nav.toggleClass('cache', niveauDeScroll >= precedent && niveauDeScroll > 0 );
  precedent = niveauDeScroll;
});

  /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////MENU HAMBURGER//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

$(function () {

         $( '#menu-burger' ).on( 'touchstart click', function() {
          $(this).toggleClass("active");

          var $body = $( 'body' ),
              $page = $( '#page-interieur' ),
              $menu = $( '#menu-mobile' ),
              finDeTransistion = 'transitionend';


          $body.addClass( 'animer' );



          if ( $body.hasClass( 'menu-visible' ) ) {
           $body.addClass( 'droite' );
          } else {
           $body.addClass( 'gauche' );
          }


          $page.on( finDeTransistion, function() {
           $body
            .removeClass( 'animer gauche droite' )
            .toggleClass( 'menu-visible' );

           $page.off( finDeTransistion );
          } );

         } );			

});



  /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /////////////////////////////////////////////////////FORMULAIRE CONNEXION/INSCRIPTION///////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

$('#formulaire').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active surligne');
        } else {
          label.addClass('active surligne');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active surligne'); 
			} else {
		    label.removeClass('surligne');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('surligne'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('surligne');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  cible = $(this).attr('href');

  $('#tab-contenu > div').not(cible).hide();
  
  $(cible).fadeIn(600);
  
});





  /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /////////////////////////////////////////////////////////////ANIMATION ECOLE////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

$(window).on('mousemove', function(e) {
        var largeur = $(window).width();
        var hauteur = $(window).height();
        var offsetX = 0.5 - e.pageX / largeur;
        var offsetY = 0.5 - e.pageY / hauteur;
        var positionSourisActuelle = { x: -1, y: -1 };
        positionSourisActuelle.x = event.pageX;
        positionSourisActuelle.y = event.pageY;
        var positionSourisActuelleWindow = { x: -1, y: -1 };
        positionSourisActuelleWindow.x = event.clientX;
        positionSourisActuelleWindow.y = event.clientY;
        var barreNavCacher = $('#barre-nav').hasClass("cache");
        var parallaxActive=false;
        if(positionSourisActuelle.y < 828){
          if(!barreNavCacher && positionSourisActuelleWindow.y >=70){
            parallaxActive=true;
          }else if (barreNavCacher){
            parallaxActive=true;
          }
        }
        if (parallaxActive==true) {
          $(".parallax").each(function(i, el) {
              var offset = parseInt($(el).data('offset'));
              var translate = "translate3d(" + Math.round(offsetX * offset) + "px," + Math.round(offsetY * offset) + "px, 0px)";

              $(el).css({
                  '-webkit-transform': translate,
                  'transform': translate,
                  'moz-transform': translate
              });
              parallaxActive=false;
          });
        }
    });



  /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////PARALLAX1///////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

jQuery(document).ready(function(){
  $(window).scroll(function(e){
    parallaxScroll();
  });
   
  function parallaxScroll(){
    var scrolle = $(window).scrollTop();
    if (scrolle<=1300){
      $('#parallax-fond1').css('top',(0)+'px');
      $('#parallax-fond2').css('top',(0-(scrolle*1))+'px');
      $('#parallax-fond3').css('top',(0-(scrolle*0.5))+'px');
    }else{
      $('#parallax-fond1').css('top',(0)+'px');
      $('#parallax-fond2').css('top',(0-(scrolle*1))+'px');
      $('#parallax-fond3').css('top',(0-(scrolle*0.5))+'px');
    }

  }
 
 }); 




    /*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////BOUTON REMONTER///////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/




if ($('#bouton-remonter').length) {
  var niveauBoutonApparait = 100, // px
    backToTop = function() {
      var niveauDeScroll = $(window).scrollTop();
      if (niveauDeScroll > niveauBoutonApparait) {
        $('#bouton-remonter').addClass('visible');
      } else {
        $('#bouton-remonter').removeClass('visible');
      }
    };
  backToTop();
  $(window).on('scroll', function() {
    backToTop();
  });
  $('#bouton-remonter').on('click', function(e) {
    e.preventDefault();
    $('html,body').animate({
      scrollTop: 0
    }, 700);
  });
}







    /*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////ANIMATION ARRIVEE ELEMENTS//////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/


jQuery(function($) {
  
  // Function which adds the 'a-ete-anime' class to any '.animable' in view
  var faireAnimations = function() {
        
    // Calc current offset and get all animables
    var offset = $(window).scrollTop() + $(window).height(),
        $animables = $('.animable');
    
    // Unbind scroll handler if we have no animables
    if ($animables.size() == 0) {
      $(window).off('scroll', faireAnimations);
    }
    
    // Check all animables and animate them if necessary
    $animables.each(function(i) {
       var $animable = $(this);
      if (($animable.offset().top + $animable.height() - 180) < offset) {
        $animable.removeClass('animable').addClass('a-ete-anime');
      }
    });

  };
  
  // Hook faireAnimations on scroll, and trigger a scroll
  $(window).on('scroll', faireAnimations);
  $(window).trigger('scroll');

});





    /*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////HOME ANIMATION MENU HAMBURGER//////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/



$(document).ready(function() {
  $('.home-bouton-menu').on('click', function() {
    $('.contenue').toggleClass('active');
  });
});










  $('.expand').click(function() {
      $(this).parent().toggleClass('selected');

  });









/*LISTES*/


  if (!Array.prototype.last){
      Array.prototype.last = function(){
          return this[this.length - 1];
      };
  };

  (function ($) {
      $(function () {
          var $wrapper = $(".list-wrapper");
          var $back = $wrapper.find("a.back-link");
          var $lists = $wrapper.find(".list-body-container ul")
          var $links = $lists.find("a.list-link");

          var $listPath = [$lists.filter(".active-list").eq(0)];

          function onBackClick (e) {
              e.preventDefault();
              e.stopPropagation();

              if ($listPath.length < 2) {
                  return false;
              }

              var $cl = $listPath.pop();

              $cl.removeClass("active-list");
              $listPath.last().removeClass("parent-list");
              $cl.siblings(".list-link").removeClass("active-link");

              window.setTimeout(function () {
                  $cl.addClass("hidden");
              }, 310);
          };

          function onLinkClick (e) {
              e.preventDefault();
              e.stopPropagation();

              var $link = $(this);
              var $list = $($link.attr("href"));

              if (!$list.length) {
                  return false;
              }

              $link.addClass("active-link");
              $list.removeClass("hidden");

              window.setTimeout(function () {
                  $list.addClass("active-list");
              }, 10);

              $listPath.last().addClass("parent-list");
              $listPath.push($list);
          };

          // click on back button
          $back.on("click", onBackClick);

          // click on list links
          $links.on("click", onLinkClick);
      });
  })(jQuery);
