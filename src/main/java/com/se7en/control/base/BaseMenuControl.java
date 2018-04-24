package com.se7en.control.base;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import com.se7en.model.base.BaseMenu;
import com.se7en.service.base.BaseMenuService;
import com.se7en.service.support.FontAwesomeService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
/**
 * 菜单
 * BaseMenu control
 */
@Controller
@RequestMapping("/baseMenu")
public class BaseMenuControl extends AbstractControl {
	@Resource
	private BaseMenuService srv;
	
	@Resource
	private FontAwesomeService faSrv;
	
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
		return new ModelAndView("base/basemenu/manage");
	}

	/**
	 * 菜单列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/list.do")
	public ModelAndView list(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new ModelAndView("base/basemenu/list");
	}

	/**
	 * 菜单树节点json数据
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/nodes.do")
	public @ResponseBody List<BaseMenu> nodes(final HttpServletRequest req,
			final HttpServletResponse res) {
		final List<BaseMenu> nodes = srv.queryMenuTree();
		for (BaseMenu baseMenu : nodes) {
//			baseMenu.setName("<span class='ace-icon " + baseMenu.getIcon() + "' style='font-family:FontAwesome;'></span> " + baseMenu.getName());
			baseMenu.setName(baseMenu.getName());
		}
		return nodes;
	}

	@RequestMapping("/tree.do")
	public @ResponseBody List<BaseMenu> tree(final HttpServletRequest req,
			final HttpServletResponse res) {
		final List<BaseMenu> nodes = srv.queryMenuTree();
		for (BaseMenu baseMenu : nodes) {
			baseMenu.setOpen(1);
		}
		
		return nodes;
	}
	/**
	 * page query
	 * @param req
	 * @param res
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,BaseMenu baseMenu,int curPage) {
		QueryView queryView = QueryView.EMPTY_QUERYVIEW;
		try {
			queryView = wrapQueryView(req,BaseMenu.class,"id","名称"
								,"图标"
								,"是否展开"
								,"显示顺序"
								,"状态"
								,"节点数据"
							);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv.pageViewQueryS(baseMenu,
				queryView);
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
			final HttpServletResponse res,BaseMenu baseMenu) {
		if (baseMenu.getId() == null) {
			baseMenu.setName("新增菜单");
		}else{
			int pid = baseMenu.getPid() == null ? -1 : baseMenu.getPid();
			baseMenu = srv.get(baseMenu.getId());
			if(pid != -1 && pid != baseMenu.getPid()){
				baseMenu.setPid(pid);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentName", req.getParameter("parentName"));
		model.put("baseMenu", baseMenu);
		
		//图标
		final List<String> icons = faSrv.getAllIconClass(req);
		int size = icons.size();
		model.put("count", size);
		model.put("maxPage", size % 15 == 0 ? size / 15 : size / 15 + 1);
		
		return new ModelAndView("base/basemenu/detail",model);
	}
	/**
	 * save
	 * @param req
	 * @param res
	 * @param BaseMenu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public @ResponseBody AjaxResult save(final HttpServletRequest req,
					final HttpServletResponse res, final BaseMenu baseMenu) {
		baseMenu.setState(1);
		final boolean sign = srv.saveOrUpdate(baseMenu);
		return ajaxCallback(sign);
	}
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param baseMenu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final BaseMenu baseMenu) {
		final boolean sign = srv.delete(baseMenu);
		return ajaxCallback(sign);
	}
	
	@RequestMapping("/icons.do")
	public @ResponseBody List<Map<String,String>> icons(final HttpServletRequest req,
			final HttpServletResponse res,final Integer page) {
		final List<String> icons = faSrv.getAllIconClass(req);
		final int start = 15 * (page - 1) + 1;
		int end = 15 * (page - 1) + 15;
		if(end > icons.size() - 1)
			end = icons.size() - 1;
		
		final List<Map<String,String>> ret = new LinkedList<>();
		for(int i = start;i <= end;i++){
			Map<String,String> node = new HashMap<>(1);
			String str = icons.get(i);
			node.put("name", "<span class='menu-icon fa " + str + "' style='font-family:FontAwesome;'></span> " + str);
			ret.add(node);
		}
		
		return ret;
	}
}