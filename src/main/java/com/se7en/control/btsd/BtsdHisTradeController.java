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
import com.se7en.service.btsd.BtsdHisTradeService;
import com.se7en.model.btsd.BtsdHisTrade;

/**
 * 交易方式 * BtsdHisTrade control
 */
@Controller
@RequestMapping("/btsdHisTrade")
public class BtsdHisTradeController extends AbstractControl {
	
	@Resource
	private BtsdHisTradeService srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdHisTrade");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHisTrade btsdHisTrade) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,BtsdHisTrade.class));
		/*return srv.pageViewQueryS(btsdHisTrade,
				wrapModelQueryView(req,BtsdHisTrade.class
					,"id"
					,"coin.id"
					,"coin.name"
					,"date"
					,"price"
					,"volume"
					,"type"
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
		model.put(CLASS_NAME, "BtsdHisTrade");
		if(id != null){
			BtsdHisTrade btsdHisTrade = srv.get(id);
			model.put(VIEW_OBJECT, btsdHisTrade);
		}
		
		//return new ModelAndView("btsd/btsdhistrade/detail",model);
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
			final HttpServletResponse res,final BtsdHisTrade btsdHisTrade) {
		final boolean sign = srv.saveOrUpdate(btsdHisTrade);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdHisTrade
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHisTrade btsdHisTrade) {
		final boolean sign = srv.delete(btsdHisTrade);
		
		return ajaxCallback(sign);
	}
}