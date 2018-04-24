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
import com.se7en.model.base.BaseRole;
import com.se7en.service.base.BaseRoleService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 用户角色
 * BaseRole control
 */
@Controller
@RequestMapping("/baseRole")
public class BaseRoleControl extends AbstractControl {
	@Resource
	private BaseRoleService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/baserole/manage");
		//		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BaseRole");
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
			final HttpServletResponse res,BaseRole baseRole) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapModelQueryView(req,BaseRole.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.modelQuery(req, queryView);
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
		model.put(CLASS_NAME, "BaseRole");
		if(id != null){
			final BaseRole baseRole = srv.get(id);
			model.put(VIEW_OBJECT, baseRole);
		}
		//return new ModelAndView("base/baserole/detail",model);
		return new ModelAndView(DETAIL_ENGINE,model);
	}
	/**
	 * save
	 * @param req
	 * @param res
	 */
	@RequestMapping("/save.do")
	public @ResponseBody AjaxResult save(final HttpServletRequest req,
					final HttpServletResponse res, final BaseRole baseRole) {
		final boolean sign = srv.saveOrUpdate(baseRole);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseRole
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseRole baseRole) {
		final boolean sign = srv.delete(baseRole);
		return ajaxCallback(sign);
	}
}