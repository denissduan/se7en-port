package com.se7en.control.base;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.model.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se7en.control.AbstractControl;
import com.se7en.model.base.BaseMenu;
import com.se7en.model.base.BasePrivilege;
import com.se7en.service.base.BaseMenuService;
import com.se7en.service.base.BasePrivilegeService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 按钮权限
 * BasePrivilege control
 */
@Controller
@RequestMapping("/basePrivilege")
public class BasePrivilegeControl extends AbstractControl {
	@Resource
	private BasePrivilegeService srv;
	
	@Resource
	private BaseMenuService menuSrv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/baseprivilege/manage");
//		return new ModelAndView(MANAGE_ENGINE,"className","BasePrivilege");
	}
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BasePrivilege basePrivilege) {
		
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapModelQueryView(req,BasePrivilege.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.modelQuery(req, queryView);
	}
	@RequestMapping("/tree.do")
	public @ResponseBody List<BaseMenu> tree(final HttpServletRequest req,
			final HttpServletResponse res) {
		final List<BaseMenu> menus = menuSrv.queryMenuTree();
		final List<BasePrivilege> privs = srv.queryAll();
		
		final List<BaseMenu> ret = new LinkedList<BaseMenu>(menus);
		for(BaseMenu baseMenu : menus){
//			baseMenu.setOpen(1);
			for(BasePrivilege basePriv : privs){
				BaseMenu privMenu = processPrivilegeMenu(basePriv,baseMenu);
				ret.add(privMenu);
			}
		}
		
		return ret;
	}
	
	/**
	 * Process privilege menu.
	 * 权限转菜单格式
	 * @param basePriv the base priv
	 * @return the base menu
	 */
	private BaseMenu processPrivilegeMenu(final BasePrivilege basePriv,final BaseMenu pMenu) {
		final BaseMenu privMenu = new BaseMenu();
		privMenu.setName(basePriv.getName());
		privMenu.setData(basePriv.getCode());
		privMenu.setPid(pMenu.getId());
		privMenu.setLevel(10);
//		privMenu.setId((int)Math.random());
		return privMenu;
	}
	
	/**
	 * detail dialog
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(final HttpServletRequest req,
			final HttpServletResponse res,final Integer id) {
		final Map<String,Object> model = new HashMap<String,Object>();
		model.put(CLASS_NAME, "BasePrivilege");
		if(id != null){
			final BasePrivilege basePrivilege = srv.get(id);
			model.put("VIEW_OBJECT", basePrivilege);
		}
//		return new ModelAndView("base/baseprivilege/detail",model);
		return new ModelAndView(DETAIL_ENGINE,model);
	}
	
	/**
	 * save
	 * @param req
	 * @param res
	 * @return
	 
	 */
	@RequestMapping("/save.do")
	public @ResponseBody AjaxResult save(final HttpServletRequest req,
					final HttpServletResponse res, final BasePrivilege basePrivilege) {
		final boolean sign = srv.saveOrUpdate(basePrivilege);
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param basePrivilege
	 * @return
	 
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BasePrivilege basePrivilege) {
		final boolean sign = srv.delete(basePrivilege);
		return ajaxCallback(sign);
	}
}