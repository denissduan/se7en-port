package com.se7en.control.btsd;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.se7en.control.AbstractControl;
import com.se7en.web.vo.PageView;
import com.se7en.model.AjaxResult;
import com.se7en.service.btsd.BtsdHmmStateService;
import com.se7en.model.btsd.BtsdHmmState;

/**
 * 最大边界 * BtsdHmmState control
 */
@Controller
@RequestMapping("/btsdHmmState")
public class BtsdHmmStateController extends AbstractControl {
	
	@Resource
	private BtsdHmmStateService srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"BtsdHmmState");
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHmmState btsdHmmState) {
		
		return srv.modelQuery(req, wrapModelQueryView(req,BtsdHmmState.class));
		/*return srv.pageViewQueryS(btsdHmmState,
				wrapQueryView(req,BtsdHmmState.class
					,"id"
					,"id"
					,"type"
					,"coin.id"
					,"coin.name"
					,"name"
					,"no"
					,"minBound"
					,"maxBound"
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
		model.put(CLASS_NAME, "BtsdHmmState");
		if(id != null){
			BtsdHmmState btsdHmmState = srv.get(id);
			model.put(VIEW_OBJECT, btsdHmmState);
		}
		
		//return new ModelAndView("btsd/btsdhmmstate/detail",model);
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
			final HttpServletResponse res,final BtsdHmmState btsdHmmState) {
		final boolean sign = srv.saveOrUpdate(btsdHmmState);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param btsdHmmState
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BtsdHmmState btsdHmmState) {
		final boolean sign = srv.delete(btsdHmmState);
		
		return ajaxCallback(sign);
	}
}