(function(){
	var zTree;
	var setting = {
		async : {
			enable : true,
			url : "basePrivilege/tree.do"
		},
		edit : {
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		check : {
			chkboxType : { "Y" : "ps", "N" : "ps" },
			chkStyle : "checkbox",
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : 1
			}
		},
		callback : {
		}
		/*view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		}*/
	}
	$.fn.zTree.init($("#tree"), setting);
	
	/*zTree = $('#tree').asyncPageCapability({
		url : "basePrivilege/tree.do",
		setting : {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			check : {
				chkboxType : { "Y" : "ps", "N" : "ps" },
				chkStyle : "checkbox",
				enable : true
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid",
					rootPId : 1
				}
			}
		}
	});*/
	
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<span class='ace-icon fa fa-edit' id='addBtn_" + treeNode.id
			+ "' title='权限设置' onfocus='this.blur();' style='font-family:FontAwesome;'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			relOpen('basePrivilege/query.do?cols=id:check,name');
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.id).unbind().remove();
	};
})();