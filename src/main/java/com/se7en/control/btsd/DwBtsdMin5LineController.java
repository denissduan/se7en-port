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
import com.se7en.service.btsd.DwBtsdMin5LineService;
import com.se7en.model.btsd.DwBtsdMin5Line;

/**
 * 收盘 * DwBtsdMin5Line control
 */
@Controller
@RequestMapping("/dwBtsdMin5Line")
public class DwBtsdMin5LineController extends AbstractControl {
	
	@Resource
	private DwBtsdMin5LineService srv;

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
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"DwBtsdMin5Line");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final DwBtsdMin5Line dwBtsdMin5Line) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,DwBtsdMin5Line.class));
		/*return srv.pageViewQueryS(dwBtsdMin5Line,
				wrapQueryView(req,DwBtsdMin5Line.class
					,"id"
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
		model.put(CLASS_NAME, "DwBtsdMin5Line");
		if(id != null){
			DwBtsdMin5Line dwBtsdMin5Line = srv.get(id);
			model.put(VIEW_OBJECT, dwBtsdMin5Line);
		}
		
		//return new ModelAndView("btsd/dwbtsdmin5line/detail",model);
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
			final HttpServletResponse res,final DwBtsdMin5Line dwBtsdMin5Line) {
		final boolean sign = srv.saveOrUpdate(dwBtsdMin5Line);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param dwBtsdMin5Line
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final DwBtsdMin5Line dwBtsdMin5Line) {
		final boolean sign = srv.delete(dwBtsdMin5Line);
		
		return ajaxCallback(sign);
	}

	@RequestMapping("/processData.do")
	public @ResponseBody AjaxResult processData(final HttpServletRequest req,
												final HttpServletResponse res) {
		boolean sign = srv.processData();
		return ajaxCallback(sign);
	}

	/**
	 * 创建hmm
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/createHmm.do")
	public @ResponseBody AjaxResult createHmm(final HttpServletRequest req, final HttpServletResponse res) {
		Map<String, BtsdCoin> coins = coinService.queryAll2Map();
		for(Map.Entry<String,BtsdCoin> coinEntry : coins.entrySet()) {
			BtsdCoin coin = coinEntry.getValue();

			HmmResult result = srv.processHmm(coin);
			HiddenMarkovModel hmm = result.getHmm();
			if(result.isSuccess() == false){
				log.info("------------------hmm,code=" + coin.getId() + "," + coin.getName() + "处理失败");
			}
		}

		return ajaxCallback(true);
	}

}