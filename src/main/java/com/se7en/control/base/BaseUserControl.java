package com.se7en.control.base;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.common.util.AESUtil;
import com.se7en.model.AjaxResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se7en.control.AbstractControl;
import com.se7en.model.base.BaseUser;
import com.se7en.service.base.BaseUserService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 
 * BaseUser control
 */
@Controller
@RequestMapping("/baseUser")
public class BaseUserControl extends AbstractControl {
	@Resource
	private BaseUserService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/baseuser/manage");
//		return new ModelAndView(MANAGE_ENGINE,"className","BaseUser");
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
			final HttpServletResponse res,final BaseUser baseUser) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapModelQueryView(req,BaseUser.class);
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
		model.put(CLASS_NAME, "BaseUser");
		if(id != null){
			final BaseUser baseUser = srv.get(id);
			String password = baseUser.getPassword();
			baseUser.setPassword(AESUtil.AESDecode(password));
			model.put(VIEW_OBJECT, baseUser);
		}
//		return new ModelAndView("base/baseuser/detail",model);
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
					final HttpServletResponse res, final BaseUser baseUser) {
		String password = baseUser.getPassword();
		String aesPassword = AESUtil.AESEncode(password);
		baseUser.setPassword(aesPassword);
		final boolean sign = srv.saveOrUpdate(baseUser);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseUser
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseUser baseUser) {
		final boolean sign = srv.delete(baseUser);
		return ajaxCallback(sign);
	}
}