package com.se7en.control.btsd;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.common.util.StringUtil;
import com.se7en.model.AjaxResult;
import com.se7en.web.vo.QueryView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.se7en.control.AbstractControl;
import com.se7en.web.vo.PageView;
import com.se7en.service.btsd.BtsdCoinService;
import com.se7en.model.btsd.BtsdCoin;
/**
 *
 * BtsdCoin control
 */
@Controller
@RequestMapping("/btsdCoin")
public class BtsdCoinControl extends AbstractControl {
	@Resource
	private BtsdCoinService srv;
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdCoin");
	}
	/**
	 * page query
	 * @param req
	 * @param res
	 * @param BtsdCoin
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdCoin btsdCoin) {
        QueryView queryView = QueryView.EMPTY_QUERYVIEW;
        try {
            queryView = wrapModelQueryView(req,BtsdCoin.class);
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
			final HttpServletResponse res,final String id) {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(CLASS_NAME, "BtsdCoin");
		if(StringUtil.isNotEmpty(id)){
			BtsdCoin btsdCoin = srv.get(id);
			model.put(VIEW_OBJECT, btsdCoin);
		}
		//return new ModelAndView("btsd/btsdcoin/detail",model);
		return new ModelAndView(DETAIL_ENGINE,model);
	}
	/**
	 * save
	 * @param req
	 * @param res
	 * @param BtsdCoin
	 * @return
	 */
	@RequestMapping("/save.do")
	public @ResponseBody
    AjaxResult save(final HttpServletRequest req,
                    final HttpServletResponse res, final BtsdCoin btsdCoin) {
		final boolean sign = srv.saveOrUpdate(btsdCoin);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdCoin
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdCoin btsdCoin) {
		final boolean sign = srv.delete(btsdCoin);
		return ajaxCallback(sign);
	}
}