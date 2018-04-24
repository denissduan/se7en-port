package com.se7en.control.btsd;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.model.btsd.BtsdCoin;
import com.se7en.service.btsd.BtsdCoinService;
import com.se7en.service.btsd.common.HmmResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.encog.ml.hmm.HiddenMarkovModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.se7en.control.AbstractControl;
import com.se7en.web.vo.PageView;
import com.se7en.model.AjaxResult;
import com.se7en.service.btsd.HmmBtsdService;
import com.se7en.model.btsd.HmmBtsd;

/**
 *  * HmmBtsd control
 */
@Controller
@RequestMapping("/hmmBtsd")
public class HmmBtsdController extends AbstractControl {
	
	@Resource
	private HmmBtsdService srv;

	@Resource
	private BtsdCoinService coinService;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"HmmBtsd");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final HmmBtsd hmmBtsd) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,HmmBtsd.class));
		/*return srv.pageViewQueryS(hmmBtsd,
				wrapQueryView(req,HmmBtsd.class
					,"id"
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
		model.put(CLASS_NAME, "HmmBtsd");
		if(id != null){
			HmmBtsd hmmBtsd = srv.get(id);
			model.put(VIEW_OBJECT, hmmBtsd);
		}
		
		//return new ModelAndView("btsd/hmmbtsd/detail",model);
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
			final HttpServletResponse res,final HmmBtsd hmmBtsd) {
		final boolean sign = srv.saveOrUpdate(hmmBtsd);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param hmmBtsd
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final HmmBtsd hmmBtsd) {
		final boolean sign = srv.delete(hmmBtsd);
		
		return ajaxCallback(sign);
	}

	@RequestMapping("/testHmm.do")
	public @ResponseBody AjaxResult testHmm(final HttpServletRequest req, final HttpServletResponse res) {
		Map<String, BtsdCoin> coins = coinService.queryAll2Map();
		for(Map.Entry<String,BtsdCoin> coinEntry : coins.entrySet()) {
			BtsdCoin coin = coinEntry.getValue();

			srv.testHmm(coin);
		}

		return ajaxCallback(true);
	}

}