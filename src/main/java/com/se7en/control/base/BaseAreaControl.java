package com.se7en.control.base;
import java.util.List;
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
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.service.base.BaseAreaService;
import com.se7en.model.base.BaseArea;
/**
 * 
 * BaseArea control
 */
@Controller
@RequestMapping("/baseArea")
public class BaseAreaControl extends AbstractControl {
	@Resource
	private BaseAreaService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/basearea/manage");
	}
	@RequestMapping("/list.do")
	public ModelAndView list(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/basearea/list");
	}
	@RequestMapping("/nodes.do")
	public @ResponseBody List<BaseArea> nodes(final HttpServletRequest req,
			final HttpServletResponse res) {
		List<BaseArea> nodes = srv.queryAll();
		return nodes;
	}
	/**
	 * page query
	 * @param req
	 * @param res
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BaseArea baseArea,final int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapQueryView(req,BaseArea.class,"id","name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.pageViewQueryS(baseArea,
				queryView);
	}
	/**
	 * detail dialog
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(final HttpServletRequest req,
			final HttpServletResponse res,BaseArea baseArea) {
		if (baseArea.getId() == null) {
			baseArea.setName("新增");
		}else{
			baseArea = srv.get(baseArea.getId());
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentName", req.getParameter("parentName"));
		model.put("baseArea", baseArea);
		return new ModelAndView("base/basearea/detail",model);
	}
	/**
	 * save
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public @ResponseBody AjaxResult save(final HttpServletRequest req,
					final HttpServletResponse res, final BaseArea baseArea) {
		final boolean sign = srv.saveOrUpdate(baseArea);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseArea
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseArea baseArea) {
		final boolean sign = srv.delete(baseArea);
		return ajaxCallback(sign);
	}
}