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
import com.se7en.service.btsd.BtsdMin5LineService;
import com.se7en.model.btsd.BtsdMin5Line;

/**
 * 收盘 * BtsdMin5Line control
 */
@Controller
@RequestMapping("/btsdMin5Line")
public class BtsdMin5LineController extends AbstractControl {
	
	@Resource
	private BtsdMin5LineService srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdMin5Line");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdMin5Line btsdMin5Line) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,BtsdMin5Line.class));
		/*return srv.pageViewQueryS(btsdMin5Line,
				wrapModelQueryView(req,BtsdMin5Line.class
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
		model.put(CLASS_NAME, "BtsdMin5Line");
		if(id != null){
			BtsdMin5Line btsdMin5Line = srv.get(id);
			model.put(VIEW_OBJECT, btsdMin5Line);
		}
		
		//return new ModelAndView("btsd/btsdmin5line/detail",model);
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
			final HttpServletResponse res,final BtsdMin5Line btsdMin5Line) {
		final boolean sign = srv.saveOrUpdate(btsdMin5Line);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdMin5Line
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdMin5Line btsdMin5Line) {
		final boolean sign = srv.delete(btsdMin5Line);
		
		return ajaxCallback(sign);
	}
}