import Swiper from 'swiper';

var swiper1 = new Swiper('.swiper1', {
  navigation: {
    prevEl: '.swiper-button-prev',
    nextEl: '.swiper-button-next',
  },
});

console.log(swiper1)

// 세로 스크롤
// function preventDefault(e) {
//   e.preventDefault();
// }


// window.onwheel = preventDefault; // modern standard
// window.onmousewheel = document.onmousewheel = preventDefault; // older browsers, IE
// window.ontouchmove = preventDefault; // mobile

console.log(document)

const fpButtons = document.querySelectorAll(".fp-btn");
console.log(fpButtons)
console.log(fpButtons.length)

for (let i = 0; i < fpButtons.length; i++) {
  fpButtons[i].addEventListener("click", e => {

    const pageNum = e.target.getAttribute("data-page");

    console.log(pageNum)
    
    window.scrollTo({
      behavior: "smooth",
      top: window.innerHeight * pageNum
    });
  }

  );
}




// (function() {
// 	"use strict";
// 	/*[pan and well CSS scrolls]*/
// 	var pnls = document.querySelectorAll('.panel').length,
// 		scdir, hold = false;

// 	function _scrollY(obj) {
// 		var slength, plength, pan, step = 100,
// 			vh = window.innerHeight / 100,
// 			vmin = Math.min(window.innerHeight, window.innerWidth) / 100;
// 		if ((this !== undefined && this.id === 'full-page') || (obj !== undefined && obj.id === 'full-page')) {
// 			pan = this || obj;
// 			plength = parseInt(pan.offsetHeight / vh);
// 		}
// 		if (pan === undefined) {
// 			return;
// 		}
// 		plength = plength || parseInt(pan.offsetHeight / vmin);
// 		slength = parseInt(pan.style.transform.replace('translateY(', ''));
// 		if (scdir === 'up' && Math.abs(slength) < (plength - plength / pnls)) {
// 			slength = slength - step;
// 		} else if (scdir === 'down' && slength < 0) {
// 			slength = slength + step;
// 		} else if (scdir === 'top') {
// 			slength = 0;
// 		}
// 		if (hold === false) {
// 			hold = true;
// 			pan.style.transform = 'translateY(' + slength + 'vh)';
// 			setTimeout(function() {
// 				hold = false;
// 			}, 1000);
// 		}
// 		console.log(scdir + ':' + slength + ':' + plength + ':' + (plength - plength / pnls));
// 	}
// 	/*[swipe detection on touchscreen devices]*/
// 	function _swipe(obj) {
// 		var swdir,
// 			sX,
// 			sY,
// 			dX,
// 			dY,
// 			threshold = 100,
// 			/*[min distance traveled to be considered swipe]*/
// 			slack = 50,
// 			/*[max distance allowed at the same time in perpendicular direction]*/
// 			alT = 500,
// 			/*[max time allowed to travel that distance]*/
// 			elT, /*[elapsed time]*/
// 			stT; /*[start time]*/
// 		obj.addEventListener('touchstart', function(e) {
// 			var tchs = e.changedTouches[0];
// 			swdir = 'none';
// 			sX = tchs.pageX;
// 			sY = tchs.pageY;
// 			stT = new Date().getTime();
// 			//e.preventDefault();
// 		}, false);

// 		obj.addEventListener('touchmove', function(e) {
// 			e.preventDefault(); /*[prevent scrolling when inside DIV]*/
// 		}, false);

// 		obj.addEventListener('touchend', function(e) {
// 			var tchs = e.changedTouches[0];
// 			dX = tchs.pageX - sX;
// 			dY = tchs.pageY - sY;
// 			elT = new Date().getTime() - stT;
// 			if (elT <= alT) {
// 				if (Math.abs(dX) >= threshold && Math.abs(dY) <= slack) {
// 					swdir = (dX < 0) ? 'left' : 'right';
// 				} else if (Math.abs(dY) >= threshold && Math.abs(dX) <= slack) {
// 					swdir = (dY < 0) ? 'up' : 'down';
// 				}
// 				if (obj.id === 'well') {
// 					if (swdir === 'up') {
// 						scdir = swdir;
// 						_scrollY(obj);
// 					} else if (swdir === 'down' && obj.style.transform !== 'translateY(0)') {
// 						scdir = swdir;
// 						_scrollY(obj);

// 					}
// 					e.stopPropagation();
// 				}
// 			}
// 		}, false);
// 	}
// 	/*[assignments]*/
// 	var fullPage = document.getElementById('full-page');
// 	fullPage.style.transform = 'translateY(0)';
// 	fullPage.addEventListener('wheel', function(e) {
// 		if (e.deltaY < 0) {
// 			scdir = 'down';
// 		}
// 		if (e.deltaY > 0) {
// 			scdir = 'up';
// 		}
// 		e.stopPropagation();
// 	});
// 	fullPage.addEventListener('wheel', _scrollY);
// 	_swipe(fullPage);
// 	var tops = document.querySelectorAll('.top');
// 	for (var i = 0; i < tops.length; i++) {
// 		tops[i].addEventListener('click', function() {
// 			scdir = 'top';
// 			_scrollY(fullPage);
// 		});
// 	}
// })();
