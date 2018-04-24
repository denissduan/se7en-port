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
import com.se7en.model.base.BaseRoleMenu;
import com.se7en.service.base.BaseRoleMenuService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 角色菜单
 * BaseRoleMenu control
 */
@Controller
@RequestMapping("/baseRoleMenu")
public class BaseRoleMenuControl extends AbstractControl {
	@Resource
	private BaseRoleMenuService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BaseRoleMenu");
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
			final HttpServletResponse res,final BaseRoleMenu baseRoleMenu,final int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapQueryView(req,BaseRoleMenu.class,"id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.pageViewQueryS(baseRoleMenu,queryView);
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
		model.put(CLASS_NAME, "BaseRoleMenu");
		if(id != null){
			final BaseRoleMenu baseRoleMenu = srv.get(id);
			model.put(VIEW_OBJECT, baseRoleMenu);
		}
		//return new ModelAndView("base/baserolemenu/detail",model);
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
					final HttpServletResponse res, final BaseRoleMenu baseRoleMenu) {
		final boolean sign = srv.saveOrUpdate(baseRoleMenu);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseRoleMenu
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseRoleMenu baseRoleMenu) {
		final boolean sign = srv.delete(baseRoleMenu);
		return ajaxCallback(sign);
	}
}