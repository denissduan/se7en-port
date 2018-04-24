package com.se7en.control.base;
import java.util.HashMap;
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
import com.se7en.model.base.BaseUnit;
import com.se7en.service.base.BaseUnitService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 组织
 * BaseUnit control
 */
@Controller
@RequestMapping("/baseUnit")
public class BaseUnitControl extends AbstractControl {
	@Resource
	private BaseUnitService srv;
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
		return new ModelAndView("base/baseunit/manage");
	}
	@RequestMapping("/list.do")
	public ModelAndView list(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/baseunit/list");
	}
	@RequestMapping("/nodes.do")
	public @ResponseBody List<BaseUnit> nodes(final HttpServletRequest req,
			final HttpServletResponse res) {
		List<BaseUnit> nodes = srv.queryAll();
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
			final HttpServletResponse res,final BaseUnit baseUnit,final int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapModelQueryView(req,BaseUnit.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.modelQuery(req, queryView);
		/*return srv.pageViewQueryS(baseUnit,
				wrapModelQueryView(req,BaseUnit.class,"id"							,"name"
						,"phone"
						,"state"
						));*/
	}
	/**
	 * detail dialog
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(final HttpServletRequest req,
			final HttpServletResponse res,BaseUnit baseUnit) {
		if (baseUnit.getId() == null) {
			baseUnit.setName("新增组织");
		}else{
			int pid = baseUnit.getPid();
			baseUnit = srv.get(baseUnit.getId());
			if(pid != baseUnit.getPid()){	//层级切换
				baseUnit.setPid(pid);
			}
		}
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentName", req.getParameter("parentName"));
		model.put("baseUnit", baseUnit);
		return new ModelAndView("base/baseunit/detail",model);
	}
	/**
	 * save
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public @ResponseBody
	AjaxResult save(final HttpServletRequest req,
					final HttpServletResponse res, final BaseUnit baseUnit) {
		final boolean sign = srv.saveOrUpdate(baseUnit);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseUnit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseUnit baseUnit) {
		final boolean sign = srv.delete(baseUnit);
		return ajaxCallback(sign);
	}
}