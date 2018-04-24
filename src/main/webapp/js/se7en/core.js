/**
 * 表格定义
 * init: 构造函数
 * getPage : 获取分页控件
 * getForm : 获取分页表单
 * checkSelect : 检查是否点击一行记录
 * addTdRightClickListener : 右键菜单相应事件 arguments : rightMenu配置
 * addThClickListener : 表头点击相应事件
 * _addTdClickListener : 单元格点击事件
 * _addTdMouseOverListener : 单元格移动响应事件
 * getThead : 获取表头 配置信息
 * getTfoot : 获取分页span
 * selectCheckbox : 全选（取消）复选框
 * getChecks : 获取选中的复选框数组
 * getCheckIds ：获取勾选的id
 * getTable : 获取表格元素
 * getColInfo(td) : 获取列头信息 
 * getTdText(colName) : 根据名称获取单元格文本
 * getColIndexByName(colName) : 根据字段名获取列名,如果不存在返回-1
 */
(function($){
	$.core = $.core || {};
	$.extend($.core,{
		pages : [],
		grids : [],
		trees : [],
		pageIndex : 0,
		gridIndex : 0,
		treeIndex : 0,
		acceptPage : function(pageNav){
			if($.core.pageIndex > 0){
				for(var i = 0;i < $.core.pageIndex;i++){
					if($.core.pages[i] == pageNav)
						break;
				}
				if(i == $.core.pageIndex){
					$.core.pages[$.core.pageIndex++] = pageNav;
				}
			}else{
				$.core.pages[$.core.pageIndex++] = pageNav;
			}
		},
		acceptGrid : function(grid){
			if($.core.gridIndex > 0){
				for(var i = 0;i < $.core.gridIndex;i++){
					if($.core.grids[i].table.context == grid.table.context){
						$.core.grids[i] = grid;
						break;
					}
				}
				if(i == $.core.gridIndex){
					var gridIndex = $.core.gridIndex;
					$.core.grids[$.core.gridIndex++] = grid;
					grid.gridIndex = gridIndex;
				}
			}else{
				var gridIndex = $.core.gridIndex;
				$.core.grids[$.core.gridIndex++] = grid;
				grid.gridIndex = gridIndex;
			}
		},
		acceptTree : function(tree){
			if($.core.treeIndex > 0){
				for(var i = 0;i < $.core.treeIndex;i++){
					if($.core.trees[i] == tree)
						break;
				}
				if(i == $.core.treeIndex){
					$.core.trees[$.core.treeIndex++] = tree;
				}
			}else{
				$.core.trees[$.core.treeIndex++] = tree;
			}
		},
		clear : function(){
			$.core.pages = [];
			$.core.grids = [];
			$.core.trees = [];
			$.core.pageIndex = 0;
			$.core.gridIndex = 0;
			$.core.treeIndex = 0;
		},
		checkClick : function(check){
			var self = $(check);
			if(self.is(':checked')){
				self.val(1);
			}else{
				self.val(0);
			}
		}
	});
	$.page = $.page || {};
	$.extend($.page,{
		create : function(config){
			var self = config.target;
			/**
			 * 分页封装
			 * @returns {page}
			 */
			return new function(){
				this.form = null;		//查询表单
				this.target = self;
				var index = $.core.pageIndex;
				this.fn = null;
				this.isInit = true;
				this.pre = "上一页";
				this.next = "下一页";
				/**
				 * 获取查询表单
				 */
				this.getForm = function(){
					return this.form;
				};
				//分页脚本生成
				this.nav = function (p,pn){
					if(pn <= 1){
						this.p = 1;this.pn=1;
						return this.pHtml2(1);
					}
					if (pn<p) {p=pn;};
					var re = "";
					//first page
					if(p <= 1){
						p = 1;
					}else{
						re+=this.pHtml(p-1,pn,this.pre);
						re+=this.pHtml(1,pn,"1");
					}
					this.p = p;this.pn=pn;
					var start = 2;
					var end = (pn<9)?pn:9;
					if(p>=7){
						re+="<li class='disabled'><a>...</a></li>";
						start = p-4;
						var e = p+4;
						end = (pn<e)?pn:e;
					}
					for (var i=start; i < p; i++) {
						re+=this.pHtml(i,pn);
					};
					re+=this.pHtml2(p);
					for (var i=p+1; i <= end; i++) {
						re+=this.pHtml(i,pn);
					};
					if (end<pn) {
						re+="<li class='disabled'><a>...</a></li>";
						//show last page. if you don't need ,just remove the following line.
						re+=this.pHtml(pn,pn);
					};
					if (p<pn) {re+=this.pHtml(p+1,pn,this.next);};
//					re += this.location();
					return re;
				};
				//精确页码定位
				this.location = function(){
					var s = "<li><input id='location' style='width:20px' class='pageNum' onblur='$.core.pages["+index+"].locating();' /></li>";
					return s;
				};
				//精确页码定位相应函数
				this.locating = function(){
					var str = $("#location").val();
					if(!isNaN(str)){
						str = parseInt(str);
						if(str < this.pn && str > 0){		//小于总页数
							$.core.pages[index].go(str,this.pn);
						}
					}
				};
				//not current page
				this.pHtml = function(pageNo,pn,showPageNo){
				  	showPageNo = showPageNo || pageNo;
					var H = " <li><a href='#' onclick='javascript:$.core.pages["+index+"].go("+ pageNo+","+ pn+  ");' >"+ showPageNo+"</a></li> ";
					return H;
					
				};
				//current page
				this.pHtml2 = function(pageNo){
					var H = " <li class='active'><a>"+pageNo+"</a></li> ";
					return H;
				};
				//goto page and do your function.Modify this can remove the jquery dependence.
				this.go = function (p,pn){
					//document.getElementById("pageNav").innerHTML = this.nav(p,pn);
					var html = this.nav(p,pn);
					this.target.html(html);
					//$("#pageNav").html(this.nav(p,pn));
					if(this.isInit == true){
						this.isInit = false;
						return;
					}
					if(this.isInit == false){
						if (this.fn != null) {
							this.fn(this.p,this.pn);
						}
					}
				};
			};
		}
	});
	$.grid = $.grid || {};
	$.extend($.grid,{
		create : function(config){
			return new function(){
				this.rowCount = 15;		//总记录行数
				this.curTr = null;		//点击tr
				this.curId = null;
				this.existId = false;	//存在主键
				this.oldClick = null;
				this.clickSign = false;
				this.checkAll = false;	//是否全选标志
				this.table = null;		//dom节点
				this.tbody = null;		//表体
				this.gridIndex = null;		//表示全局环境的第几个表格
				//表头属性
				this.colNums;		//列数
				this.thead = null;		//表头
				this.sort = null;	//排序字段名
				this.order = 0;	//升降序,0：未排，1：升序，2：降序
				this.sortTh = null;
				this.pkFormat = null;	//主键显示类型,1.id,2.radio,3.check
				//事件
				this.tdClick = null;
				this.tdMouseover = null;
				//右键菜单;
				this.rightMenu = null;
				this.rightClickTd = null;
				//编辑单元格
				this.editCell = null;
				this.oldCellHtml = null;
				//page
				this.page = null;		//分页控件
				var entry = this;
				
				//构造函数
				this.init = function(config){
					if(config.pkFormat){		//主键展示形式
						entry.pkFormat = config.pkFormat;
					}
					entry.table = config.target;		//dom节点
					var table = entry.table;
					table.empty();			//如果表已存在先清空
					//表头
					var thead = $("<thead>");
					thead.appendTo(table);
					if(config.thead){
						entry.thead = config.thead;
					}
					//表体
					var tbody = $("<tbody>").appendTo(table);
					entry.tbody = tbody;
					//click,hover响应事件
					
					//单元格click事件占位符
					if(config.events){
						if(config.events.tdClick)
							entry.tdClick = config.events.tdClick;
						if(config.events.tdMouseover)	//单元格mouseover事件占位符
							entry.tdMouseover = config.events.tdMouseover;	
					}
					entry._addTdClickListener(table);
					entry._addTdMouseOverListener(table);
					if(config.rightMenu && config.rightMenu.length > 0)
						entry._addTdRightClickListener(config.rightMenu);
					//编辑事件
					if(config.editFunction){
						entry._addTdEditListener(config.editFunction);
					}else{
						entry._addTdEditListener(null);
					}
					entry.table.data('grid',entry);
				};
				this.getPage = function(){	//获取分页控件
					return entry.page;
				};
				this.getForm = function(){	//获取分页表单
					return entry.page.form;
				};
				this.getTbody = function(){	//获取表体
					return entry.tbody;
				};
				/**
				 * 判断该Grid是否绑定目标dom
				 */
				this.isAttached = function(tableTarget){
					return entry.table.attr('id') == tableTarget.attr('id');
				};
				/**
				 * 单元格处理
				 * @param colInfo
				 * @param data
				 * @param isId 是否主键
				 * @returns
				 */
				this.handleCell = function(colInfo,data,isId){
					var td = $("<td align='left'>");
					if(isId){		//主键
						return entry.handlePkCell(colInfo,data,td);
					} 
					if(!isNull(data)){
						if(data.length > 30){			//字数太长,截取子字符串
							td.text(data.substring(0,30));
							td.attr("title",data);
						}else{
							td.text(data);
						}
					}else{
						td.css({"height":"35"});		//空文本,手动写入高度
					}
					/*if(td){			//绑定td事件
						if(config.events){
							if(config.events.tdClick){
								td.click(function(e){	//单元格点击事件
									entry.tdClick(e);
								});
							}
							if(config.events.tdMouseover){
								td.mouseover(function(e){		//mouseover事件
									entry.tdMouseover(e);
								});
							}
						}
					}*/
					return td;
				};
				/**
				 * 处理主键单元格
				 */
				this.handlePkCell = function(colInfo,data,td){
					td.css("text-align","center");
					var format = entry.pkFormat;
					if(colInfo.format){
						format = colInfo.format;
					}
					if(isNull(data)){
						if(isNull(format)){
							td.css("display","none");
						}
						return td;
					}
					switch(format){
						case "radio":
							td.html("<input type='radio' name='id' value='" + data + "'>");
							break;
						case "check":
							td.html("<input type='checkbox' name='id' value='" + data + "'>");
							break;
						default:
							td.css("display","none").html("<input type='hidden' name='id' value='" + data + "'>");
						break;
					}
					return td;
				};
				this.checkSelect = function(){
					if(isNull(this.curId)){
						alert("请选择一条记录");
						return false;
					}
					return true;
				};
				/**
				 * 右键菜单
				 * 需要contextMenu.js
				 */
				this._addTdRightClickListener = function(options){
					//禁止系统菜单弹出
					document.oncontextmenu = function(ev) {
						return false;
					};  
					entry.table.contextMenu({
				        selector: "td", 
				        items: entry.createRightMenuJson(options),
				        position: function($menu, x, y){
				        	var td = $menu.$trigger;
				        	var offset = $menu.$trigger.offset();
							var width = $menu.$trigger.outerWidth();
							var x = offset.left + width,y = offset.top - 5;
							$menu.$menu.css({top: y, left: x});
				        },
				        events : {
				        	show : function(opt){
				        		var td = opt.$trigger;
				        		td.addClass("tdFocus");
				        		entry.rightClickTd = td;
				        	},
				        	hide : function(opt){
				        		var td = opt.$trigger;
				        		td.removeClass("tdFocus");
				        		entry.rightClickTd = null;
				        	}
				        }
				    });
//					var tbody = entry.table.children("tbody");
//					tbody.on("mousedown",function(e){
//						var td = $(e.target);
//						if(td.context.nodeName != 'TD'){
//							return;
//						}
//						if(e.button == 2){		//鼠标右键
//							if(!entry.rightMenu){		//右键菜单不存在
//								if(options && options.length > 0){
//									var gridMenu = '<div id="gridMenu" class="context-menu-sub">';
//									var liHeight = td.outerHeight();
//									var liHtml = entry.createRightMenuScript(options,liHeight);
//									gridMenu += liHtml;
//									gridMenu += '</div>';
//									entry.rightMenu = gridMenu = $(gridMenu);
//									gridMenu.appendTo($('body'));
//									
//								}
//							}
//							if(entry.rightClickTd)
//								entry.rightClickTd.removeClass("tdFocus");
//							/**
//							 * 显示右键菜单
//							 */
//							entry.showRightMenu(e,td);
//						}
//						
//					});
				};
				/**
				 * 创建右键菜单json数组
				 */
				this.createRightMenuJson = function(options){
					var ret = {};
					$.each(options,function(){
						ret[this.text] = {};
						var item = ret[this.text];
						item.name = this.text;
						if(this.click){
							if(typeof this.click == "string"){
								item.callback = function(){
									eval(this.click);
								};
							}else{
								item.callback = this.click;
							}
						}
						if(this.subs && this.subs.length > 0){
							item.items = entry.createRightMenuJson(this.subs);
						}
					});
					return ret;
				};
				/**
				 * 创建右键菜单脚本
				 * 老版本
				 */
				this.createRightMenuScript = function(options,liHeight){
					var liHtml = '<ul>';
					$.each(options,function(index,opt){
						if(opt.subs != null && opt.subs.length > 0){		//存在子菜单
							liHtml += '<li><span>' + opt.text + '</span>';
							liHtml += entry.createRightMenuScript(opt.subs,liHeight);
							liHtml += "</li>";
						}else{
							liHtml += '<li style="height:' + liHeight + 'px"><a href="javascript:;" onclick="'
								+ (opt.click == undefined ? '' : opt.click) + '">' + opt.text + '</a></li>';
						}
					});
					liHtml += '</ul>';
					return liHtml;
				};
				/**
				 * 显示右键菜单
				 */
				this.showRightMenu = function(e,td){
					var offset = td.offset();
					var width = td.outerWidth();
					var x = offset.left + width,y = offset.top - 5;
					entry.rightMenu.css({
						"top" : y + "px",
						"left" : x + "px",
						"visibility" : "visible"
					});
					td.addClass("tdFocus");
					entry.rightClickTd = td;
					$("body").bind("mousedown", entry.onBodyMouseDown);
				};
				/**
				 * 页面右键菜单弹出事件
				 */
				this.onBodyMouseDown = function(e) {
					if(e.button != 2){
						if (!(e.target.id == "gridMenu" || $(e.target).parents("#gridMenu").length > 0)) {
							entry.rightMenu.css({
								"visibility" : "hidden"
							});
							entry.rightClickTd.removeClass("tdFocus");
							$("body").unbind("mousedown", entry.onBodyMouseDown);
						}
					}
				};
				/**
				 * click表头响应事件
				 */
				this.addThClickListener = function(){
					var thead = entry.table.children("thead");
					thead.on("click",function(e){
						var th = $(e.target);
						if(th.context.nodeName != 'TH'){
							th = $(th.parents("th")[0]);
						}
						if(th.children().is(':radio') || th.children().is(':checkbox')){		//主键单元格点击屏蔽
							return;
						}
						entry.updateThSortInfo(entry.sortTh,0);
						if(!isNull(th.data("colInfo").order)){
							var order = th.data("colInfo").order;
							if(order == "0" || order == "sort"){
								th.data("colInfo").order = 2;
							}else if(order == "1" || order == "asc"){
								th.data("colInfo").order = 0;
							}else if(order == "2" || order == "desc"){
								th.data("colInfo").order = 1;
							}
							entry.updateThSortInfo(th,th.data("colInfo").order);
							entry.sortTh = th;
							entry.sort = th.data("colInfo").sort;
							entry.order = th.data("colInfo").order;
						}
						//排序表单提交
						var form = entry.page.form;
						entry.sortColumn(form);
					});
				};
				/**
				 * 表头排序点击事件
				 */
				this.sortColumn = function(form){
					var url = form.attr("action");
					if(url.indexOf('?') == -1){
						url += "?curPage=1";
					}else{
						url += "&curPage=1";
					}
					url += "&head=false";
					if(!isNull(entry.sort)){
						url += "&sort=" + entry.sort;
					}
					if(!isNull(entry.order) && entry.order != 0){
						var order = "desc";
						if(entry.order == 1 || entry.order == "asc"){
							order = "asc";
						}else if(entry.order == 2 || entry.order == "desc"){
							order = "desc";
						}
						url += "&order=" + order;
					}
					entry.page.form.ajaxSubmit({
						url : url,
						type : 'post',
						dataType : 'json',
						success : function(data){
							entry.refresh(data);
						}	
					});
				};
				this.refresh = function(pageView){
					var table = this.table;
					var tbody = null;		//表体
					this.curId = null;		//当前id
					this.curTr = null;
					this.oldClick = null;
					this.clickSign = false;
					
//					if(table.attr("class") == null)		//附加默认css样式
//						table.attr("class","grid_v2");
					//不存在表头
					if(pageView.thead != null && pageView.thead.length > 0){	//页面返回表头信息
						//处理表头
						if(entry.thead == null)		//表头组件为空
							entry.refreshThead(table,pageView);
					}
					//清除表格
					var tbody = table.children("tbody");
					if(!tbody || tbody.length == 0){
						//生成表体,datas数据数组
						tbody = $("<tbody>");
						tbody.appendTo(table);
					}
					tbody.empty();
					entry.refreshTbody(tbody,pageView);
					//如果未满或无数据,自动填充表格
					var fillThead = entry._getThead(pageView);
					if(fillThead != null && fillThead.length > 0){	//存在表头才进行填充
						entry.autoFillTd(tbody,pageView);
					}
				};
				/**
				 * 获取表头
				 */
				this._getThead = function(pageView){
					var thead = null;
					if(pageView.thead && pageView.thead.length > 0){
						thead = pageView.thead[0];
					}else if(entry.thead && entry.thead.length > 0){
						thead = entry.thead;
					}
					return thead;
				};
				/**
				 * 获取表头
				 */
				this.getTheadInfo = function(){
					return entry.thead;
				};
				/**
				 * 获取列信息
				 */
				this.getColInfo = function(td){
					var tdIndex = $(td).index();
					return entry.thead[tdIndex];
				};
				/**
				 * 根据字段名获取选中行单元格文本
				 */
				this.getTdText = function(colName){
					var index = entry.getColIndexByName(colName);
					if(index != -1){
						return $(entry.curTr).children('td').eq(index).text();
					}
					return null;
				};
				/**
				 * 根据字段名获取索引,如果不存在返回1
				 */
				this.getColIndexByName = function(colName){
					var index = 0;
					for(index = 0;index < entry.thead.length;index++){
						if(entry.thead[index].name == colName){
							break;
						}
					}
					if(index == entry.thead.length){
						index = -1;
					}
					return index;
				};
				/**
				 * 判断是否主键
				 */
				this.judgeIsId = function(colInfo){
					if(colInfo.name){
						return colInfo.name.toLowerCase() == "id";
					}
					return false;
				};
				/**
				 * 生成表头
				 */
				this.refreshThead = function(table,pageView){
					var thead = table.children("thead");
					if(!thead || thead.size() == 0){
						thead = $("<thead>");
						thead.appendTo(table);
					}
					table.children("thead").empty();		//清除表头
					//更新属性
					var head = pageView.thead;
					entry.colNums = head[0].length;
					entry.thead = head[0];	
					//遍历生成表头
					$.each(head,function(trIndex,head){
						var tr = $("<tr>");
						$.each(head,function(index,colInfo){
							//判断主键信息
							if(index == 0 && entry.judgeIsId(colInfo)){	//包含主键
								entry.existId = true;		//更新存在主键信息
								if(colInfo.format){
									entry.pkFormat = colInfo.format;
								}
								if(entry.pkFormat != null){		//主键显式存在
									var th = $("<th align='center' style='text-align:center;'>");
									switch(entry.pkFormat){
										case "radio":
											th.text("选择");
											break;
										case "check":
											th.html('<input type="checkbox" />');
											th.children('input').click(function(){
												entry.selectCheckbox();
											});
											break;
										default:
											th.css("display","none");
										break;
									}
									colInfo.name = "id";
									th.appendTo(tr);
								}
								return true;
							}
							var th;
							if(!isNull(colInfo.sort) && !isNull(colInfo.order)){	//存在排序列名
								th = $("<th align='left'></th>");
								th.html("<span>" + colInfo.title + "</span><span style='float:right;'><i class='ace-icon fa fa-unsorted'></i></span>");
								th.css("cursor", "pointer");		//可排序
								entry.order = colInfo.order;
								entry.updateThSortInfo(th,colInfo.order);		//更新图标
							}else{
								th = $("<th align='left'></th>");
								th.html("<span>" + colInfo.title + "</span>");
							}
							th.data("colInfo",colInfo);
							th.appendTo(tr);
						});
						tr.appendTo(thead);
					});
					entry.addThClickListener();		//添加头部点击事件
				};
				/**
				 * 更新表头排序图标
				 */
				this.updateThSortInfo = function(th,order){
					if(th == null || th == undefined)
						return;
					switch(order){
					case 0:
					case "sort":
						th.find("i").attr("class","ace-icon fa fa-unsorted");
						break;
					case 1:
					case "asc":
						th.find("i").attr("class","ace-icon fa fa-sort-up");
						break;
					case 2:
					case "desc":
						th.find("i").attr("class","ace-icon fa fa-sort-down");
						break;
					}
				};
				/**
				 * 生成表体
				 */
				this.refreshTbody = function(tbody,pageView){
					if(pageView.tbody != null && pageView.tbody.length > 0){
						$.each(pageView.tbody,function(index,datas){
							var tr = $("<tr>");
							if(index == 0){
								entry.colNums = datas.length;
							}
							var thead;
							if(pageView.thead != null){
								thead = pageView.thead[0];
							}else{
								thead = entry.thead;
							}
							$.each(datas,function(index,data){
								//主键处理
								if(index == 0 && entry.judgeIsId(thead[index])){	
									entry.handleCell(thead[index],data,true).appendTo(tr);
									return true;
								}
								//单元格处理
								var td = entry.handleCell(thead[0][index],data);
								td.appendTo(tr);
							});
							tr.appendTo(tbody);
						});
						entry.resetProperty();		//重置属性
					}
				};
				/**
				 * 自动填充单元格
				 */
				this.autoFillTd = function(tbody,pageView){
					if(pageView.tbody == null || pageView.tbody.length < this.rowCount){
						var fillNum = 0;
						if(pageView.tbody != null){
							fillNum = this.rowCount - pageView.tbody.length;
						}else{
							fillNum = this.rowCount;
						}
						for(var trIndex = 0;trIndex < fillNum;trIndex++){
							var tr = $("<tr>");
							var thead = entry._getThead(pageView);
							$.each(thead,function(index,colInfo){
								//主键处理
								if(index == 0 && entry.judgeIsId(colInfo)){	
									entry.handleCell(thead[index],"",true).appendTo(tr);
									return true;
								}
								//单元格处理
								var td = entry.handleCell(thead[0][index],"");
								td.appendTo(tr);
							});
							tr.appendTo(tbody);
						}
					}
				};
				/**
				 * 获取表底,分页,状态栏信息容器
				 */
				this.getTfoot = function(){
					var table = this.table;
					var pageCon;
					var tfoot = table.children("tfoot");
					if(tfoot.length == 0){		//不存在延迟创建
						tfoot = $("<tfoot>");
						var tr = $("<tr>");
						var td = null;
						if(entry.pkFormat != null){
							td = $("<td colspan='" + entry.colNums + "' >");
						}else{
							td = $("<td colspan='" + (entry.colNums) + "' >");
						}
						var pageSpan = $("<ul style='float: right;'>");
						pageSpan.addClass("pagination");
						pageSpan.appendTo(td);
						tr.append(td);
						tfoot.append(tr);
						table.append(tfoot);
						pageCon = pageSpan;
						return pageCon;
					}
					pageCon = tfoot.children("tr").children("td").children("span");
					return pageCon;
				};
				/**
				 * click响应事件
				 */
				this._addTdClickListener = function(table){
					var tbody = table.children("tbody");
					tbody.on("click",function(e){
						//系统默认单元格点击事件
						var tr = $(e.target.parentNode);
						if($(e.target).context.nodeName == 'TR'){
							tr = $(e.target);
						}
						entry.curTr = tr.context;		//记录点击tr
						if(entry.existId == true){			//存在主键
							entry.curId = tr.children().first().children().val();
						}
						if(entry.tdClick){
							entry.tdClick(e);
						}else{
							tr.addClass('info');
							tr.siblings().removeClass('info');
						}
					});
				};
				this._addTdMouseOverListener = function(table){
					var tbody = table.children("tbody");
					tbody.on("mouseover",function(e){
						var tr = $(e.target.parentNode);
						if($(e.target).context.nodeName == 'TR'){
							tr = $(e.target);
						}
						if(entry.tdMouseover){
							entry.tdMouseover(e);
						}
					});
				};
				/*****************************************可编辑单元格***********************************
				/**
				 * 编辑单元格
				 */
				this._addTdEditListener = function(editFunc){
					//可编辑单元格
					entry.table.dblclick(function(e){
						var target = e.target;
						if($(target).context.tagName == "TD"){
							if(entry.getColInfo(target).name == 'id'){		//主键不编辑
								return;
							}
							if(editFunc){		//自定义双击事件
								editFunc(e);
							}else{				//编辑事件
								var colInfo = entry.getColInfo(target);
								if(colInfo.editable == true){
									$(target).data('grid',entry);
									entry._injectInput(target);
								}
							}
						}
					});
				};
				/**
				 * 注入编辑框
				 */
				this._injectInput = function(target){
					var td = $(target);
					var tdWidth = td.width();
					var tdHeight = td.height();
					entry.oldCellHtml = td.html();
					entry.editCell = td;
					var inputHtml = '<input style="width:' + (tdWidth - 2) + 'px;height:' + (tdHeight - 2) + 'px;border:none;" />';
					td.html(inputHtml);
					var input = td.children('input');
					input.focus();
					entry._bindEditTdBlurListener(input);
				};
				/**
				 * 编辑框失去焦点事件
				 */
				this._bindEditTdBlurListener = function(targetInput){
					$(targetInput).blur(function(){
						var td = entry.editCell;
						var grid = td.data('grid');
						var thead = grid.getTheadInfo();
						var tdIndex = td.index();
						var colInfo = thead[tdIndex];
						if(!isNull(colInfo.name) && thead[0].name == 'id'){	//列字段名非空,且存在主键
							confirm('确定保存?',function(){
								var id = td.siblings().eq(0).find('input').val();
								var tableName = null;
								if(!isNull(colInfo.tableName)){
									tableName = colInfo.tableName;
								}
								var name = colInfo.name;
								var value = $(targetInput).val();
								if(!isNull(id) && !isNull(tableName) && !isNull(name)){
									var data = {
											id : id,
											tableName : tableName,
											name : name,
											value : value
									};
									ajaxGet("grid/saveCell.do",data,function(data){
										if(data.sign){
											alert("保存成功");
										}else{
											alert("执行失败");
										}
										entry.storeTdHtml(td,targetInput);
									});
								}
							},function(){
								entry._restoreTdHtml(td,targetInput);
							});
						}else{
							entry._restoreTdHtml(td,targetInput);
						}
					});
				};
				/**
				 * 置单元格为改变后的值
				 */
				this.storeTdHtml = function(td,targetInput){
					var value = $(targetInput).val();
					$(td).text(value);
					$(targetInput).remove();
					entry._resetEditState();
				};
				/**
				 * 重置编辑单元格属性
				 */
				this._restoreTdHtml = function(td,targetInput){
					$(targetInput).remove();
					var td = $(td);
					var grid = td.data('grid');
					td.html(entry.oldCellHtml);
					entry._resetEditState();
				};
				/**
				 * 重置单元格编辑状态
				 */
				this._resetEditState = function(){
					entry.oldCellHtml = null;
					entry.editCell = null;
				};
				/*******************************************************************************
				/**
				 * 选择复选框
				 */
				this.selectCheckbox = function(){
					var table = entry.table;
					if(entry.checkAll){
						table.find('input[name="id"]').attr('checked',false);
						entry.checkAll = false;
					}else{
						var checks = table.find(':checkbox[name="id"][value!=""]').attr('checked',true);
						entry.checkAll = true;
					}
				};
				/**
				 * 获取check的id
				 */
				this.getCheckIds = function(){
					var ids = '';
					var checks = entry.getChecks();
					ids = checks.serialize();
					return ids;
				};
				/**
				 * 获取选中的复选框数组
				 */
				this.getChecks = function(){
					return entry.table.find('input[name="id"]:checked');
				};
				/**
				 * 重置属性
				 */
				this.resetProperty = function(){
					entry.checkAll = false;
				};
				/**
				 * 获取表格元素
				 */
				this.getTable = function(){
					return entry.table;
				};
				this.getGridParam = function(field){
					return entry[field];
				};
				//最后调用构造函数
				this.init(config);
			};
		}
	});
	$.fn.getTdText = function(colName){
		var grid = $(this).data('grid');
		return grid.getTdText(colName);
	}
	$.fn.getGridParam = function(field){
		var grid = $(this).data('grid');
		return grid.getGridParam(field);
	}
	//*********************************************************扩展方法***************************************************
	/**
	 * 创建表格
	 */
	$.fn.createGrid = function(config){
		var self = $(this);
		config.target = self;
		var grid = $.grid.create(config);
		
		return grid;
	};
	/**
	 * 创建分页
	 */
	$.fn.createPage = function(){
		var self = $(this);
		var config = {};
		config.target = self;
		var page = $.page.create(config);
		
		return page;
	};
	/**
	 * 初始化下拉框
	 * @param data 数据json对象
	 * @param select 下拉框jquery对象
	 * @param val 需要初始化值
	 */
	$.fn.initCombo = function(data,val){
		var self = $(this);
		self.empty();
		self.append($("<option>").text("请选择").val("-1"));
		$.each(data,function(index,item){
			var opt = $("<option>").text(item.text).val(item.value);
			if(val != null){
				if(item.value == val)
					opt.attr("selfed","selfed");
			}
			self.append(opt);				
		});
	};
	/**
	 * 给dom附加表格能力
	 * config : {
	 * 	pageView : 页面数据
	 *  rightMenu : [
	 *    {text : '',click : '',icon : ''},
	 *    {text : '',click : '',icon : ''}
	 *  ]
	 * }
	 */
	$.fn.gridCapability = function(config,pageView){
		var self = $(this);
		var grid;
		if($.core.grids.length > 0){
			for(var i = 0;i < $.core.grids.length;i++){
				if($.core.grids[i].isAttached(self)){
					$.core.grids.remove(i);
					break;
				}
			}
		}
		config.target = self;
		grid = $.grid.create(config);
		$.core.acceptGrid(grid);
		if(pageView)
			grid.refresh(pageView);
		
		return grid;
	};
	/**
	 * 使dom节点附加分页能力
	 * 需要jquery.form.js
	 * config : {
	 *  pageView : 数据
	 *  form : 查询表单
	 *  grid : 表格封装
	 *  url : action请求 (保留)
	 * }
	 */
	$.fn.pageCapability = function(config){
		var self = $(this);
		//收纳容器
		var pageNav = self.createPage();
		$.core.acceptPage(pageNav);
		//初始化
		self.children('span').empty();
		//绑定表单
		pageNav.form = config.form;
		//绑定表格
		if(config.grid){
			config.grid.page = pageNav;
		}
		if(config.pageView.totalPage > 1){
			pageNav.isInit = true;
			pageNav.fn = function(curPage,totalPage){
				if(config.form != null){
					var url = config.form.attr("action");
					url += "?curPage=" + curPage + "&head=false";
					if(!isNull(config.grid.sort)){
						url += "&sort=" + config.grid.sort;
					}
					if(!isNull(config.grid.order) && config.grid.order != 0){
						var order = "desc";
						if(config.grid.order == 1){
							order = "asc";
						}else if(config.grid.order == 2){
							order = "desc";
						}
						url += "&order=" + order;
					}
					config.form.ajaxSubmit({
						url : url,
						type : 'post',
						dataType : 'json',
						success : function(data){
							config.grid.refresh(data);
							if(config.callback){		//ajax分页回调
								config.callback();
							}
						}	
					});
				}
			};
			pageNav.go(config.pageView.curPage,config.pageView.totalPage);
		}
		return pageNav;
	};
	/**
	 * 查询面板,表格，分页功能封装
	 * 需要jquey.js
	 * config : {
	 * 	tableTarget : 表格容器
	 *  pageTarger : 分页对象
	 *  searchForm : 查询表单
	 *  gridEvents : 表格相应事件集合
	 * }
	 */
	$.core.commonCapability = function(config,callback){
		var form = null;
		var grid = null;
		var page = null;
		if(config.searchForm != null){
			form = config.searchForm;
		}else{
			var formIds = ["#relSearchForm","#searchForm"];
			for(var index = 0;index < formIds.length;index++){
				form = $(formIds[index]);
                if(form.length > 0){
                	break;
                }
			}
            //查询按钮事件绑定
            var subt = $('body').find("#searchBtn");
            //不存在创建一个
            if(subt == null || subt.length == 0){
                subt = $('<button id="searchBtn" class="btn btn-sm btn-primary"><i class="ace-icon fa fa-search bigger-120"></i>查询</button>');
                subt.appendTo(form);
            }
            if(subt.length == 1){
                subt.bind("click",function(){
                    form.submit();
                    return false;
                });
            }
			/*form = $("#relSearchForm");
			if(form.length == 0){
				form = $("#searchForm");
				//查询按钮事件绑定
				var subt = $('body').find("#searchBtn");
				//不存在创建一个
				if(subt == null || subt.length == 0){
					subt = $('<button id="searchBtn" class="btn btn-sm btn-primary"><i class="ace-icon fa fa-search bigger-120"></i>查询</button>');
					subt.appendTo(form);
				}
				if(subt.length == 1){
					subt.bind("click",function(){
						form.submit();
						return false;
					});
				}
			}*/
		}
		var url = form.attr("action");
		if(url.indexOf('?') == -1){
			url += "?curPage=1";
		}else{
			url += "&curPage=1";
		}
		form.submit(function(){
			form.ajaxSubmit({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(pageView){
					//处理表格
					if(config.grid){
						grid = config.tableTarget.gridCapability(config.grid,pageView);
					}else if(config.tableTarget != null){
						grid = config.tableTarget.gridCapability({events : config.gridEvents},pageView);
					}
					//处理分页
					var pageCon;
					if(grid){			//如果指定分页位置，调用分页位置
						pageCon = grid.getTfoot();
					}else if(config.pageTarget){				//自动添加到表格末行
						pageCon = config.pageTarget;
					}
					var comCallback = null;		//通用组件回调
					var pageCallback = null;	//分页回调
					if(callback){
						if(typeof callback == 'function'){	//默认回调
							comCallback = callback;
						}else if(typeof callback == 'object'){	//json 对象
							if(callback.callback)
								comCallback = callback.callback;
							if(callback.pageCallback)
								pageCallback = callback.pageCallback;
						}
					}
					page = pageCon.pageCapability({pageView : pageView,form : form,grid : grid,callback : pageCallback});
					//回调函数执行
					if(comCallback){
						comCallback();
					}
				}	
			});
			return false;
		});
		form.submit();
	};
	/**
	 * 装饰ztree配置项
	 * 需要1.zTree.js组件,2.ZTree对象
	 * @param config{
	 * 	zTree : zTree对象(可选,zTee已初始化情况下)
	 *  treeId 树DOM id(可选,zTee已初始化情况下)
	 * 	target : 下拉目标标签<input> (必须)
	 *  nodes : 树节点数组(可选,zTree或treeId不存在情况下),
	 *  setting : { 深度复制ztree选项
	 *  }
	 * }
	 */
	$.fn.decorateTree = function(standSetting,config){
		var self = $(this);
		var treeId = self.attr('id');
		var zTree;
		var div;
		
		if($.fn.zTree.getZTreeObj(treeId) != null){	//已存在树对象
			zTree = $.fn.zTree.getZTreeObj(treeId);	//根据id获取
			treeId = zTree.setting.treeId;
			div = $("#"+zTree.setting.treeId).parent();
			var setting = zTree.setting;
			if(standSetting){
				$.extend(true,setting,standSetting);		//深度复制标准配置项
			}
			if(config && config.setting)
				$.extend(true,setting,config.setting);		//深度复制配置项
			if(config && config.nodes){
				zTree = $.fn.zTree.init($("#" + treeId), setting,config.nodes);
			}else if(zTree.getNodes().length > 0){
				zTree = $.fn.zTree.init($("#" + treeId), setting,zTree.getNodes());
			}else if(zTree.getNodes().length == 0){
				zTree = $.fn.zTree.init($("#" + treeId), setting);
			}
		}else{
			var setting = {
					data: {
						simpleData: {
							enable: true
						}
					}
			};
			if(standSetting){
				$.extend(true,setting,standSetting);		//深度复制标准配置项
			}
			if(config && config.setting)
				$.extend(true,setting,config.setting);		//深度复制配置项
			
			if(treeId != null){	//已存在树id,不一定存在树对象
				zTree = $.fn.zTree.getZTreeObj(treeId);
				treeId = treeId;
				if(zTree == null){		//zTree对象不存在,建立对象
					if(config.nodes == null){
						//判断是否存在异步配置
						if(setting.async && !isNull(setting.async.url)){
							zTree = $.fn.zTree.init($("#"+config.treeId), setting);
						}else{
							console.log('节点数据不存在');
							return;
						}
					}
					zTree = $.fn.zTree.init($("#" + treeId), setting,config.nodes);
				}
				div = $("#" + treeId).parent();
			}else{
				div = $('<div id="ztree_d">')
				.css({"display":"none","position":"absolute","background-color":"#ffffff",
					"border":"1px solid #000000"});
				treeId = generateGuid();
				var ul = $('<ul id="' + treeId + '" class="ztree" style="margin-top:0px; width:160px;height:300px">');
				ul.appendTo(div);
				config.target.after(div);
				zTree = $.fn.zTree.init(ul, setting,config.nodes);
			}
		}
		$.core.acceptTree(zTree);
		return [zTree,treeId,div];
	};
	/**
	 * 下拉菜单功能附加
	 * 需要1.zTree.js组件,2.ZTree对象
	 * @param config{
	 * 	zTree : zTree对象(可选,zTee已初始化情况下)
	 *  treeId 树DOM id(可选,zTee已初始化情况下)
	 * 	target : 下拉目标标签<input> (必须)
	 *  nodes : 树节点数组(可选,zTree或treeId不存在情况下),
	 *  setting : { 深度复制ztree选项
	 *  }
	 * }
	 */
	$.fn.slideTreeCapability = function(config){
		var self = $(this);
		var ret = self.decorateTree(null,config);
		
		var zTree = ret[0];
		var treeId = ret[1];
		var div = ret[2];
		
		var Menu = {
			/**
			 * 显示下拉菜单
			 */
			showMenu : function(){
				var target = config.target;
				var offset = target.position();
				div.css({left:offset.left + "px", top:offset.top + target.outerHeight() + "px"}).slideDown("fast");
				$("body").bind("mousedown", Menu.onBodyDown);
			},
			/**
			 * 隐藏菜单
			 */
			hideMenu : function(){
				div.fadeOut("fast");
				$("body").unbind("mousedown",Menu.onBodyDown);
			},
			/**
			 * @param event
			 */
			onBodyDown : function(event){
				if ($(event.target).parents("#"+treeId).length == 0) {
					Menu.hideMenu();
				}
			}
		};
		zTree.hideMenu = Menu.hideMenu;
		//绑定监听事件
		config.target.on("click",function(){
			Menu.showMenu();
		});
		return zTree;
	};
	/**
	 * 异步树节点
	 * 需要zTree.js组件
	 * @param config{
	 * 	zTree/treeId : zTree对象/树DOM id(必选,zTee已初始化情况下)
	 *  async : {
	 *		enable : true,
	 *		url : getUrl
	 *	}
	 * }
	 */
	$.fn.asyncTreeCapability = function(config){
		$.extend(true,config.zTree.setting,config.setting);		//深度复制配置项
		return config.zTree;
	};
	/**
	 * 右键菜单功能附加
	 * 需要1.zTree.js组件,2.ZTree对象
	 * @param config{
	 * 	zTree : zTree对象(可选,zTee已初始化情况下)
	 *  treeId 树DOM id(可选,1.zTee已存在情况下或2.zTree对象已初始化情况下)
	 *  nodes : 树节点数组(可选,zTree不存在或treeId第一种情况下),
	 *  setting : { 需要添加的深度复制ztree选项
	 *  }
	 *  menus : [	菜单项数组
	 *		{id : 菜单id,name : 菜单名称,click : 菜单点击相应函数,showType : 节点显示方式(node,root,both)}	
	 *  ]
	 * }
	 */
	$.fn.rightMenuTreeCapability = function(config){
		var self = $(this);
		var standSetting = {
			view : {
				selectedMulti: false
			}
		};
		var ret = self.decorateTree(standSetting,config);
		var zTree = ret[0];
		var treeId = ret[1];
		var div = ret[2];
		var menuDiv;
		//创建菜单
		if(config.menus && config.menus.length > 0){
			var rootShows = new Array();		//区域点击显示菜单
			var nodeShows = new Array();		//节点点击显示菜单
			menuDiv = $('<div id="ztree_rmenu" class="dropdown dropdown-preview">');
			menuDiv.css({"position":"fixed","visibility":"hidden","top":0,
				"padding":"2px"});
			var ul = $('<ul class="dropdown-menu">');
			ul.css({"margin" : "0 0 0 0","padding" : "0 0 0 0"});
			var menus = config.menus;
			for(var index in menus){
				var menu = menus[index];
				var li = $("<li>");
				var name = menu.name;
				var a = $('<a>').appendTo(li).attr("id",menu.id).text(name);
				a.click(menu.click);
				li.appendTo(ul);
				if(menu.showType == "node"){
					nodeShows.push(li);
				}else if(menu.showType == "root"){
					rootShows.push(li);
				}else if(menu.showType == "both"){
					nodeShows.push(li);
					rootShows.push(li);
				}
			}
			ul.appendTo(menuDiv);
			menuDiv.appendTo($('#' + treeId).parent());
			//右键菜单
			var RightMenu = {
				/**
				 * 鼠标右键点击响应事件
				 */
				onRightClick : function(event, treeId, treeNode) {
					if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
						zTree.cancelSelectedNode();
						RightMenu.showRMenu("root", event.clientX, event.clientY);
					}else if (treeNode) {
						zTree.selectNode(treeNode);
						RightMenu.showRMenu("node", event.clientX, event.clientY);
					}
				},
				/**
				 * 显示右键菜单
				 */
				showRMenu : function(type, x, y) {
					$("#ztree_rmenu ul").show();
					if (type=="root") {
						for(var index = 0;index < nodeShows.length;index++){
							nodeShows[index].hide();
						}
						for(var index = 0;index < rootShows.length;index++){
							rootShows[index].show();
						}
					}else if(type == "node") {
						for(var index = 0;index < rootShows.length;index++){
							rootShows[index].hide();
						}
						for(var index = 0;index < nodeShows.length;index++){
							nodeShows[index].show();
						}
					}
					menuDiv.css({
						"top" : y + "px",
						"left" : x + "px",
						"visibility" : "visible"
					});

					$("body").bind("mousedown", RightMenu.onBodyMouseDown);
				},
				/**
				 * 菜单取消
				 * @param event
				 */
				onBodyMouseDown : function(event) {
					if (!(event.target.id == "ztree_rmenu" || $(event.target).parents("#ztree_rmenu").length > 0)) {
						menuDiv.css({
							"visibility" : "hidden"
						});
					}
				}
			};
			zTree.setting.callback.onRightClick = RightMenu.onRightClick;	//绑定右键事件
		}
		return [zTree,menuDiv];
	};
	/**
	 * 异步分页树
	 * @param config {
	 * 	zTree/treeId : zTree对象/树DOM id(必选,zTee已初始化情况下)
	 *  url : 异步加载连接,返回时需要格式化树节点数组
	 * }
	 */
	$.fn.asyncPageCapability = function(config){
		var self = $(this);
		var treeId = self.attr('id');
		var curPage = 1;
		var Page = {
			goPage : function(treeNode, page) {
				treeNode.page = eval(page);
				if (treeNode.page<1) treeNode.page = 1;
				if (treeNode.page>treeNode.maxPage) treeNode.page = treeNode.maxPage;
				if (curPage == treeNode.page) return;
				curPage = treeNode.page;
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.reAsyncChildNodes(treeNode, "refresh");
			}
		};
		var standSetting = {
			async: {
				enable: true,
				url: function(treeId, treeNode) {
					var param = "page=" + treeNode.page;
					aObj = $("#" + treeNode.tId + "_a");
					aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage + " 页");
					return config.url + "?" + param;
				}
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				addDiyDom: function(treeId, treeNode) {
					if (treeNode.level>0) return;
					var aObj = $("#" + treeNode.tId + "_a");
					if ($("#addBtn_"+treeNode.id).length>0) return;
					var addStr = "<span class='button firstPage' id='firstBtn_" + treeNode.id + "' title='first page' onfocus='this.blur();'></span>" +
						"<span class='button prevPage' id='prevBtn_" + treeNode.id + "' title='prev page' onfocus='this.blur();'></span>" + 
						"<span class='button nextPage' id='nextBtn_" + treeNode.id + "' title='next page' onfocus='this.blur();'></span>" + 
						"<span class='button lastPage' id='lastBtn_" + treeNode.id + "' title='last page' onfocus='this.blur();'></span>" +
						"<input id='jPage' style='width:30px;margin-left:2px' onblur='jumpPage()' />";
					aObj.after(addStr);
					var first = $("#firstBtn_"+treeNode.id);
					var prev = $("#prevBtn_"+treeNode.id);
					var next = $("#nextBtn_"+treeNode.id);
					var last = $("#lastBtn_"+treeNode.id);
					first.css({"margin-left":"2px","margin-right": "0","background-position":"-144px -16px","vertical-align":"top"});
					prev.css({"margin-left":"2px","margin-right": "0","background-position":"-144px -48px","vertical-align":"top"});
					next.css({"margin-left":"2px","margin-right": "0","background-position":"-144px -64px","vertical-align":"top"});
					last.css({"margin-left":"2px","margin-right": "0","background-position":"-144px -32px","vertical-align":"top"});
					first.bind("click", function(){
						if (!treeNode.isAjaxing) {
							Page.goPage(treeNode, 1);
						}
					});
					last.bind("click", function(){
						if (!treeNode.isAjaxing) {
							Page.goPage(treeNode, treeNode.maxPage);
						}
					});
					prev.bind("click", function(){
						if (!treeNode.isAjaxing) {
							Page.goPage(treeNode, treeNode.page-1);
						}
					});
					next.bind("click", function(){
						if (!treeNode.isAjaxing) {
							Page.goPage(treeNode, treeNode.page+1);
						}
					});
					$("#jPage").bind("blur",function(){
						if (!treeNode.isAjaxing) {
							var jPage = $("#jPage").val().trim();
							if(!isNull(jPage)){
								Page.goPage(treeNode, jPage);
							}
						}
					});
				}
			},
			callback: {
				beforeExpand: function(treeId, treeNode) {
					if (treeNode.page == 0) treeNode.page = 1;
					return !treeNode.isAjaxing;
				},
				onAsyncSuccess: function(event, treeId, treeNode, msg) {
				},
				onAsyncError: function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					alert("异步获取数据出现异常。");
					treeNode.icon = "";
					zTree.updateNode(treeNode);
				}
			}
		};
		var ret = self.decorateTree(standSetting,config);
		var zTree = ret[0];
		var treeId = ret[1];
		var div = ret[2];
		
		return zTree;
	};
	/**
	 * 菜单内部拖拽功能附加
	 * 需要1.zTree.js组件,2.ZTree对象
	 * 外围函数需要修改拖拽节点状态
	 * @param config {
	 * 	zTree : zTree对象(可选,zTee已初始化情况下)
	 *  treeId 树DOM id(可选,1.zTee已存在情况下或2.zTree对象已初始化情况下)
	 *  nodes : 树节点数组(可选,zTree不存在或treeId第一种情况下),
	 *  setting : { 需要添加的深度复制ztree选项
	 *  }
	 * }
	 */
	$.fn.dragTreeCapability = function(config){
		var self = $(this);
		var standSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false,
				drag : {
					autoExpandTrigger : true,
					prev : true,
					next : true,
					inner : true,
					isCopy : false,
					isMove : true
				}
			},
			view: {
				selectedMulti: false
			}
		};
		var ret = self.decorateTree(standSetting,config);
		var zTree = ret[0];
		var treeId = ret[1];
		var div = ret[2];
		
		zTree.setting.callback.beforeDrop = function(treeId, treeNodes, targetNode, moveType){
			if(treeNodes.length > 1){
				alert("只允许拖拽一个节点");
				return false;
			}
			if(treeNodes.length == 1){
				var node = treeNodes[0];
				if(targetNode.id == null){
					alert("请先保存父节点");
					return false;
				}
				/*if(moveType == "inner"){
					return true;
				}else{
					return false;
				}*/
			}
			return true;
		};
		
		return zTree;
	};
	/**
	 * 添加outlook样式
	 * @param config
	 */
	$.fn.outlookTreeCapability = function(config){
		var self = $(this);
		var standSetting = {
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false
			}
		};
		var ret = self.decorateTree(standSetting,config);
		var zTree = ret[0];
		var treeId = ret[1];
		var div = ret[2];
		
		var treeId = config.treeId;
		$('#' + treeId).css({'font-size': '20pt','font-family':'"Microsoft Yahei","Verdana","Simsun","Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue","Arial"'});
		$('#' + treeId + ' li ul ').css({'margin':'0', 'padding':'0'});
		$('#' + treeId + ' li ').css({'line-height' : '30px'});
		$('#' + treeId + ' li a ').css({'width':'200px','height':'30px','padding-top': '0px'});
		$('#' + treeId + ' li a:hover ').css({'text-decoration':'none', 'background-color': '#E7E7E7'});
		$('#' + treeId + ' li a span.button.switch ').css({'visibility':'hidden'});
		$('#' + treeId + ' .showIcon li a span.button.switch ').css({'visibility':'visible'});
		$('#' + treeId + ' li a.curSelectedNode ').css({'background-color':'#D4D4D4','border':'0','height':'30px'});
		$('#' + treeId + ' li span ').css({'line-height':'30px'});
		$('#' + treeId + ' li span.button ').css({'margin-top': '-7px'});
		$('#' + treeId + ' li span.button.switch ').css({'width': '16px','height': '16px'});
		$('#' + treeId + ' li a.level0 span ').css({'font-size': '150%','font-weight': 'bold'});
		$('#' + treeId + ' li span.button ').css({'background-image':'url("./left_menuForOutLook.png")', '*background-image':'url("./left_menuForOutLook.gif")'});
		$('#' + treeId + ' li span.button.switch.level0 ').css({'width': '20px', 'height':'20px'});
		$('#' + treeId + ' li span.button.switch.level1 ').css({'width': '20px', 'height':'20px'});
		$('#' + treeId + ' li span.button.noline_open ').css({'background-position': '0 0'});
		$('#' + treeId + ' li span.button.noline_close ').css({'background-position': '-18px 0'});
		$('#' + treeId + ' li span.button.noline_open.level0 ').css({'background-position': '0 -18px'});
		$('#' + treeId + ' li span.button.noline_close.level0 ').css({'background-position': '-18px -18px'});
		
	};
	/**
	 * @param config{
	 * 	target : 赋予功能对象
	 *  targetId : 对象id
	 *  showType : 完成回调显示类型
	 * }
	 */
	$.fn.stockAutoCompleteCapability = function(config){
		var self = $(this);
		//股票自动完成
		var target = self;
		if(target == null){
			target = $("#" + config.targetId);
		}
		$.getJSON("sdmbasstock/autoComplete.do",function(stocks){
			target.autocomplete(stocks,{ 	
				width: 150,
				max: 10,
				highlight: false,
				multiple: true,
				multipleSeparator: " ",
				scroll: true,
				scrollHeight: 300,
				formatItem : function(data,i,total){
					return data[1];
				},
				formatMatch : function(data,i,total){
					return data[0];
				},
				formatResult : function(data,i,total){
					return data[2];
				}
			}).result(function(event, item) {
				if(config.showType == 'name'){
					target.val(item[1]);
				}else if(config.showType == 'code'){
					target.val(item[2]);
				}
			});
		});
		
	};
})(jQuery);

/**
 * 适配器alert
 * @param info
 */
function xAlert(info){
	alert(info);
}

/**
 * 适配器confirm
 * @param info
 * @returns
 */
function xConfirm(info){
	return confirm(info);
}

/**
 * 适配器prompt
 * @param info
 * @returns
 */
function xPrompt(info){
	
}

/**
 * 判断字符串是否为空,为空进行提示，并聚焦
 * @param obj jquery dom 对象
 * 		  label 提示标签
 */
function notNull(obj,label){
	var val = obj.val();
	if(val == null || val.length == 0){
		var info = "";
		if(label != null){
			info = label + "不能为空";
		}else{
			info = "不能为空";
		}
		alert(info);
		obj.focus();
		return false;
	}
	return true;
}

/**
 * 判断字符串是否为空
 * @param str
 */
function isNull(str){
	if(str == null || str.length == 0){
		return true;
	}
	return false;
}

/**
 * 转换json关键字
 * @param str
 */
function toJsonStr(str){
	str = str.replace(/\r/g,"\\r");
	str = str.replace(/\n/g,"\\n");
	return str;
}

/**
 * 生成guid
 * @returns
 */
function generateGuid(){
	this.s4 = function(){
		return Math.floor((1 + Math.random()) * 0x10000)
		        .toString(16)
		        .substring(1);
	};
	var gid = "";
	for(var index = 0;index < 8;index++){
		gid += s4();
	}
	return gid;
}

/**
 * ajax Get 请求
 * @param url
 * @param data
 * @param callback 执行成功回调函数名
 */
function ajaxGet(url,data,callback){
	var ul = url,dt = data,call = callback;
	//可变参数处理
	var args = arguments.length;
	if(args == 2){
		if(typeof arguments[1] == "function"){
			dt = null;
			call = arguments[1];
		}
	}
	$.getJSON(ul,dt,function(data){
		ajaxSucCallback(data,call);
	});
}

/**
 * ajax post请求
 * @param url
 * @param data
 */
function ajaxPost(url,data,callback){
	var ul = url,dt = data,call = callback;
	//可变参数处理
	var args = arguments.length;
	if(args == 2){
		if(typeof arguments[1] == "function"){
			dt = null;
			call = arguments[1];
		}
	}
	$.post(ul,dt,function(data){
		ajaxSucCallback(data,call);
	},"json");
}

/**
 * 通用form表单提交,需要jquery.form.js
 * @param form
 */
function formSubmit(form,call){
	if(typeof form == 'string'){
		if(form.indexOf('#') == -1){
            form = $('#' + form);
		}else{
            form = $(form);
		}
	}
	$(form).ajaxSubmit({
		dataType:'json',
		type:'post',
		success:function(data){
			ajaxSucCallback(data,call);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("表单提交失败:" + XMLHttpRequest.status);
        }
	});
	return false;
}

/**
 * 弹出窗表单提交
 * @param form
 * @param call
 * @returns {Boolean}
 */
function windowSubmit(form,call){
	$(form).ajaxSubmit({
		dataType:'json',
		type:'post',
		success:function(data){
			ajaxWinCallback(data,call);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("表单提交失败:" + XMLHttpRequest.status);
        }
	});
	return false;
}

/**
 * 异步成功回调
 * @param data
 * @param call
 */
function ajaxSucCallback(data,call){
	var msg = "";
	if(data.msg){
		msg += data.msg;
	}
	if(call == null && data.sign == true){
		alert("执行成功" + msg);
	}else if(call == null && data.sign == false){
		alert("执行失败" + data.msg);
	}else if(call){
		call(data);
	}else if(data.msg){
		alert(msg);
	}
}

/**
 * 弹出窗表单提交成功回调
 * @param data
 * @param call
 */
function ajaxWinCallback(data,call){
	ajaxSucCallback(data,call);
	closeWindow();
}

/**
 * 打开窗口
 * @param url
 */
function winOpen(url,openType){
	var otype = openType;
	if(openType == null)
		otype = 'ajax';
	$.fancybox.open({
		width : '80%',
		height : '80%',
		autoSize : false,
		scrolling : 'no',
		type : otype,
		href : url,
		padding : 5
	});
}

function relOpen(arg,injectId,showTdName){
	var url = arg,injectId = injectId,showTdName = showTdName;
	if(typeof arg == 'object'){
		url = arg.url,
		injectId = arg.injectId,
		showTdName = arg.showTdName;
	}
	$.fancybox.open({
		width : '80%',
		height : '95%',
		autoSize : false,
		scrolling : 'yes',
		type : 'inline',
		modal : false,
		content : 	'<div class="widget-box">' +
						'<div class="widget-body">' +
							'<div class="widget-main">' +
								'<form id="relSearchForm" action="' + url + '" method="post" class="form-inline" role="form">' +
									'<div class="row">' +
										'<div class="col-xs-9"></div>' +
										'<div class="col-xs-3">' +
											'<div class="input-group">' +
												'<input type="text" class="form-control search-query" placeholder="请输入..." />' +
												'<span class="input-group-btn">' +
													'<button type="button" class="btn btn-purple btn-sm">' +
														'查询' +
														'<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>' +
													'</button>' +
												'</span>' +
											'</div>' +
										'</div>' +
									'</div>' +
								'</form>' +
								'<hr>' +
								'<table id="relGrid" class="table table-striped table-bordered table-hover"></table>' +
								'<ul id="relPageNav" class="pagination"></ul>' +
							'</div>' +
						'</div>' +
					'</div>',
//		href : url,
		padding : 5
	});
	$.core.commonCapability({tableTarget : $("#relGrid"),pageTarget : $("#relPageNav"),gridEvents : {
		tdClick : function(e){
			var td = $(e.target);
			var id = td.parent().children().first().children('input').val();
			var text = null;
			if(showTdName){
				text = $('#relGrid').getTdText(showTdName);
			}else{
				text = $('#relGrid').getTdText('name');
			}
            $('#' + injectId).val(id).trigger('change');
            $('#' + injectId + '_el').val(text).trigger('change');
			$.fancybox.close();
		}
	}});
}

/**
 * 打开标签页
 * @param url
 * @param tabId
 * @param title
 */
function tabOpen(url, tabId, title) {
	var tabs = window.parent.Ext.getCmp("tabs");
	if(tabs == null)
		tabs = window.parent.parent.Ext.getCmp("tabs");
	if(tabs != null){
		var n = tabs.getComponent(tabId);
		var iframeId = 'iframe_' + tabId;
		if(!n){
			var frame = '<iframe id="' + iframeId + '" scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>';
			n = tabs.add({
				id : tabId,
				title : title,
				closable : true,
				autoScroll : false,
				html : frame
			});
		}else{
			var frame = $("#" + iframeId);
			if(frame.length == 0){
				frame = window.parent.$("#" + iframeId);
			}
			var furl = frame.attr("src");
			if(furl != url)
				frame.attr("src",url);
		}
		tabs.setActiveTab(n);
	}
}

/**
 * 弹出窗口关闭
 */
function closeWindow(){
	window.parent.$.fancybox.close();
	window.parent.location.reload();
}

/**
 * 刷新页面
 */
function refreshPage(){
	// location.reload();
	$("#searchForm").submit();
}

/**
 * 指定位置插入元素
 */
Array.prototype.insert = function(index, value) {  
    if (index < 0) {  
        index = this.length;  
    }  
    var part1 = this.slice(0, index);  
    var part2 = this.slice(index, this.length);  
    part1.push(value);  
    return part1.concat(part2);  
} 

/**
 * 指定位置删除元素
 */
Array.prototype.remove = function(index) {  
    if (index >= 0 && index <= this.length) {  
        var part1 = this.slice(0, index);  
        var part2 = this.slice(index);  
        part1.pop();  
        return part1.concat(part2);  
    }  
}