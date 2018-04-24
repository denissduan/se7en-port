package com.se7en.control;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.model.AjaxResult;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se7en.common.ColInfo;
import com.se7en.service.GridService;
import com.se7en.common.util.StringUtil;
import com.se7en.web.vo.QueryView;

public class AbstractControl {
	
	public final static String MANAGE_ENGINE = "managerEngine";
	public final static String DETAIL_ENGINE = "detailEngine";
	public final static String CLASS_NAME = "className";
	public final static String VIEW_OBJECT = "vo";
	
	public final static String PARAM_SORT = "sort";
	public final static String PARAM_ORDER = "order";
	public final static String PARAM_CURPAGE = "curPage";
	public final static String PARAM_HEAD = "head";
	
	public final static String QUERY_PARAM_PKFORMAT = "pkFormat";

	protected AjaxResult json = new AjaxResult();

	protected final Logger log = Logger.getRootLogger();
	
	@Resource
	private GridService gridSrv;

	/**
	 * 处理json格式Ajax的请求
	 */
	public @ResponseBody
	Object jsonRequest(final HttpServletRequest req, final HttpServletResponse res) {

		return null;
	}

	/**
	 * 处理请求
	 */
	public ModelAndView viewRequest(final HttpServletRequest req,
			final HttpServletResponse res) throws Exception {

		return null;
	}

	/**
	 * 简单结果回调
	 * @param sign
	 * @return
	 */
	protected AjaxResult ajaxCallback(final boolean sign) {
		json.setSign(sign);
		return json;
	}
	
	/**
	 * 简单结果回调
	 * @param sign
	 * @param msg
	 * @return
	 */
	protected AjaxResult ajaxCallback(final boolean sign,final String msg){
		ajaxCallback(sign);
		json.setMessage(msg);
		return json;
	}
	
	/**
	 * 简单函数回调
	 * @param msg
	 * @return
	 */
	protected AjaxResult ajaxCallback(final Object msg){
		json.setMessage(msg.toString());
		return json;
	}

	/**
	 * 封装表头信息
	 * @param titles
	 * @return
	 */
	protected List<List<ColInfo>> wrapSimpleThead(final Collection<String> titles) {
		
		return gridSrv.createSimpleThead(titles);
	}
	
	/**
	 * 封装表头信息
	 * @param titles
	 * @return
	 */
	protected List<List<ColInfo>> wrapSimpleThead(final String... titles){
		
		return gridSrv.createSimpleThead(titles);
	}
	
	/**
	 * 封装表头信息
	 * @param titles
	 * @return
	 * @throws Exception 
	 */
	protected List<List<ColInfo>> wrapModelThead(final Class<?> cls,final String... titles) throws Exception{
		
		return gridSrv.createModelThead(cls,titles);
	}
	
	/**
	 * 封装表头信息
	 * @param titles
	 * @return
	 * @throws SQLException 
	 */
	protected List<List<ColInfo>> wrapModelThead(Class<?> cls,Collection<String> titles) throws SQLException{
		
		return gridSrv.createModelThead(cls,titles);
	}
	
	/**
	 * 封装表头信息
	 * @return
	 * @throws Exception 
	 */
	protected List<List<ColInfo>> wrapModelThead(Class<?> cls) throws Exception{
		
		return gridSrv.createModelThead(cls);
	}	
	
	/**
	 * Wrap query view.
	 * 分页查询视图配置
	 * @param req the req
	 * @param cls the cls
	 * @param titles the titles
	 * @return the query view
	 * @throws Exception the exception
	 */
	protected QueryView wrapQueryView(final HttpServletRequest req,Class<?> cls,String... titles) {
		String pkFormat = req.getParameter(QUERY_PARAM_PKFORMAT);
		if(StringUtil.isNotBlank(pkFormat)){
			if(ArrayUtils.isNotEmpty(titles)){
				if(!titles[0].contains(pkFormat)){
					titles[0] = titles[0].split(":")[0] + ":" + pkFormat;
				}
			}
		}
		QueryView queryView = null;
		try {
			queryView = wrapQueryView(req, cls, Arrays.asList(titles));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryView;
	}
	
	/**
	 * Wrap query view.
	 * 分页查询视图配置
	 * @param req the req
	 * @param cls the cls
	 * @param titles the titles
	 * @return the query view
	 * @throws Exception the exception
	 */
	protected QueryView wrapQueryView(final HttpServletRequest req,Class<?> cls,Collection<String> titles) throws SQLException{
		QueryView view = new QueryView();
		
		view.setThead(wrapModelThead(cls, titles));
		
		handleParameter(view, req);
		
		return view;
	}
	
	protected void handleParameter(QueryView view, final HttpServletRequest req){
		String sort = req.getParameter(PARAM_SORT);
		//排序
		if(StringUtils.isNotEmpty(sort)){
			view.setSort(sort);
			String order = req.getParameter(PARAM_ORDER);
			if(StringUtils.isNotEmpty(order)){
				view.setOrder(order);
			}
		}
		String curPage = req.getParameter(PARAM_CURPAGE);
		if(StringUtils.isNotEmpty(curPage)){
			view.setCurPage(Integer.parseInt(curPage));
		}
	}
	
	protected QueryView wrapQueryView(final HttpServletRequest req,Collection<String> titles){
		QueryView view = new QueryView();
		
		String head = req.getParameter(PARAM_HEAD);
		if(StringUtils.isEmpty(head) || !"false".equals(head)){
			view.setThead(wrapSimpleThead(titles));
		}
		handleParameter(view, req);
		
		return view;
	}
	
	protected QueryView wrapQueryView(final HttpServletRequest req,String... titles){
		
		return wrapQueryView(req, Arrays.asList(titles));
	}
	
	protected QueryView wrapModelQueryView(final HttpServletRequest req, Class<?> cls) {
		QueryView view = new QueryView();

		try {
			view.setThead(wrapModelThead(cls));

			handleParameter(view, req);

			view.setSort(gridSrv.parseModelSort(cls));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
