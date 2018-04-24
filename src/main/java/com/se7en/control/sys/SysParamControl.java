package com.se7en.control.sys;
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
import com.se7en.model.sys.SysParam;
import com.se7en.service.sys.SysParamService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 系统参数
 * SysParam control
 */
@Controller
@RequestMapping("/sysParam")
public class SysParamControl extends AbstractControl {
	@Resource
	private SysParamService srv;
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
//		return new ModelAndView("sys/sysparam/manage");
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"SysParam");
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
			final HttpServletResponse res,final SysParam sysParam,final int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapQueryView(req,SysParam.class,"id"							,"module"
								,"pname"
								,"pvalue"
								,"describ"
								,"pindex"
							);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.pageViewQueryS(sysParam,queryView);
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
			final HttpServletResponse res,final Integer id) {
		final Map<String,Object> model = new HashMap<String,Object>();
		model.put(CLASS_NAME, "SysParam");
		if(id != null){
			final SysParam sysParam = srv.get(id);
			model.put(VIEW_OBJECT, sysParam);
		}
//		return new ModelAndView("sys/sysparam/detail",model);
		return new ModelAndView(DETAIL_ENGINE,model);
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
					final HttpServletResponse res, final SysParam sysParam) {
		final boolean sign = srv.saveOrUpdate(sysParam);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param sysParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final SysParam sysParam) {
		final boolean sign = srv.delete(sysParam);
		return ajaxCallback(sign);
	}
}