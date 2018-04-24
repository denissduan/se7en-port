package com.se7en.control.base;
import java.util.HashMap;
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
import com.se7en.model.base.BaseRoleMenuPrivilege;
import com.se7en.service.base.BaseRoleMenuPrivilegeService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 角色菜单权限
 * BaseRoleMenuPrivilege control
 */
@Controller
@RequestMapping("/baseRoleMenuPrivilege")
public class BaseRoleMenuPrivilegeControl extends AbstractControl {
	@Resource
	private BaseRoleMenuPrivilegeService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BaseRoleMenuPrivilege");
	}
	/**
	 * page query
	 * @param req
	 * @param res
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BaseRoleMenuPrivilege baseRoleMenuPrivilege,final int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapQueryView(req,BaseRoleMenuPrivilege.class,"id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.pageViewQueryS(baseRoleMenuPrivilege,queryView);
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
		model.put(CLASS_NAME, "BaseRoleMenuPrivilege");
		if(id != null){
			final BaseRoleMenuPrivilege baseRoleMenuPrivilege = srv.get(id);
			model.put(VIEW_OBJECT, baseRoleMenuPrivilege);
		}
		//return new ModelAndView("base/baserolemenuprivilege/detail",model);
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
					final HttpServletResponse res, final BaseRoleMenuPrivilege baseRoleMenuPrivilege) {
		final boolean sign = srv.saveOrUpdate(baseRoleMenuPrivilege);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseRoleMenuPrivilege
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseRoleMenuPrivilege baseRoleMenuPrivilege) {
		final boolean sign = srv.delete(baseRoleMenuPrivilege);
		return ajaxCallback(sign);
	}
}