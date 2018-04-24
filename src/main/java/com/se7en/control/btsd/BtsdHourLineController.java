package com.se7en.control.btsd;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.se7en.control.AbstractControl;
import com.se7en.web.vo.PageView;
import com.se7en.model.AjaxResult;
import com.se7en.service.btsd.BtsdHourLineService;
import com.se7en.model.btsd.BtsdHourLine;

/**
 * 收盘 * BtsdHourLine control
 */
@Controller
@RequestMapping("/btsdHourLine")
public class BtsdHourLineController extends AbstractControl {
	
	@Resource
	private BtsdHourLineService srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdHourLine");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHourLine btsdHourLine) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,BtsdHourLine.class));
		/*return srv.pageViewQueryS(btsdHourLine,
				wrapModelQueryView(req,BtsdHourLine.class
					,"id"
					,"coin.id"
					,"coin.name"
					,"date"
					,"datefmt"
					,"kp"
					,"zg"
					,"zd"
					,"sp"
					));*/
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
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(CLASS_NAME, "BtsdHourLine");
		if(id != null){
			BtsdHourLine btsdHourLine = srv.get(id);
			model.put(VIEW_OBJECT, btsdHourLine);
		}
		
		//return new ModelAndView("btsd/btsdhourline/detail",model);
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
			final HttpServletResponse res,final BtsdHourLine btsdHourLine) {
		final boolean sign = srv.saveOrUpdate(btsdHourLine);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdHourLine
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHourLine btsdHourLine) {
		final boolean sign = srv.delete(btsdHourLine);
		
		return ajaxCallback(sign);
	}
}