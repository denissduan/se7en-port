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
import com.se7en.service.btsd.BtsdDayLineService;
import com.se7en.model.btsd.BtsdDayLine;

/**
 * 收盘 * BtsdDayLine control
 */
@Controller
@RequestMapping("/btsdDayLine")
public class BtsdDayLineController extends AbstractControl {
	
	@Resource
	private BtsdDayLineService srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdDayLine");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdDayLine btsdDayLine) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,BtsdDayLine.class));
		/*return srv.pageViewQueryS(btsdDayLine,
				wrapModelQueryView(req,BtsdDayLine.class
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
		model.put(CLASS_NAME, "BtsdDayLine");
		if(id != null){
			BtsdDayLine btsdDayLine = srv.get(id);
			model.put(VIEW_OBJECT, btsdDayLine);
		}
		
		//return new ModelAndView("btsd/btsddayline/detail",model);
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
			final HttpServletResponse res,final BtsdDayLine btsdDayLine) {
		final boolean sign = srv.saveOrUpdate(btsdDayLine);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdDayLine
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdDayLine btsdDayLine) {
		final boolean sign = srv.delete(btsdDayLine);
		
		return ajaxCallback(sign);
	}
}