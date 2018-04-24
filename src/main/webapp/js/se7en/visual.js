(function($){
	/**
	 * 需要interact.js
	 */
	$.ctrl = {
		/**
		 * 拖拽控件响应事件
		 */
		dragMove : function (e){
			var target = e.target;

		    if ('SVGElement' in window && e.target instanceof SVGElement) {
		        target.dragX += e.dx;
		        target.dragY += e.dy;

		        target.parentNode.setAttribute('transform', ['translate(', target.dragX, target.dragY, ')'].join(' '));
		    } else {
		        target.style.left = parseInt(target.style.left || 0, 10) + e.dx + 'px';
		        target.style.top  = parseInt(target.style.top || 0, 10)  + e.dy + 'px';
		    }
		},
		/*
		 * 拖拽大小响应事件
		 */
		resizeMove : function(e) {
		    var target = e.target,
		        rect = target.getClientRects()[0];

		    if ('SVGElement' in window && target instanceof SVGElement) {
		        target.resizeWidth += e.dx;
		        target.resizeHeight += e.dy;

		        target.setAttribute('rx',  target.resizeWidth);
		        target.setAttribute('ry', target.resizeHeight);
		    }
		    else {
		        target.style.width  = Math.max(parseInt(target.style.width  || 0, 10) + e.dx, 100) + 'px';
		        target.style.height = Math.max(parseInt(target.style.height || 0, 10) + e.dy, 100) + 'px';
		    }
		},
		/**
		 * 初始化控件
		 */
		initCtrl : function (selector){
			interact(selector)
			.draggable(true)
		    .resizable(true)
		    .gesturable(true)
		    .on('resizemove',$.ctrl.resizeMove)
		    .on('dragmove',$.ctrl.dragMove)
			/* .draggable({
		        onmove: function (event) {
		            x += event.dx;
		            y += event.dy;

		            event.target.style.webkitTransform =
		            event.target.style.transform =
		                'translate(' + x + 'px, ' + y + 'px)';
		        },
		        onend: function (event) {
		            event.target.querySelector('p').textContent =
		                'moved a distance of '
		                + (Math.sqrt(event.dx * event.dx +
		                             event.dy * event.dy)|0) + 'px';
		        }
		    }) */
		    /* .inertia(true)
		    .resizable({
		    	onstart: function (event) {},
		        onmove : function (event) {},
		        onend  : function (event) {}
		    })  
		    .restrict({
		        drag: "parent",
		        endOnly: true,
		        resize: { x: 100, y: 100, width: 300, height: 300 }
		    }) */
		    ;
		}
	};
	/**
	 * 控件属性
	 */
	$.ctrl.property = {
			
	};
	/**
	 * 可视化
	 */
	$.visual = {
			
	}
})(jQuery)