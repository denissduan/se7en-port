[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
%]

[%script type="Class" name="control" file="src/main/java/com/se7en/control/[%package.namespace.name%]/[%name.capitalize()%]Control.java" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%getAllAttributes().put("attrs")%][%name.firstLower().put("varName")%]
package com.se7en.control.[%package.namespace.name%];
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.se7en.control.AbstractControl;
import com.se7en.web.vo.PageView;
import com.se7en.service.[%package.namespace.name%].[%name.capitalise()%]Service;
import com.se7en.model.[%package.namespace.name%].[%name.capitalise()%];

/**
 * [%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]
 * [%name%] control
 */
@Controller
@RequestMapping("/[%name.firstLower()%]")
public class [%name.capitalize()%]Control extends AbstractControl {
	
	@Resource
	private [%name.capitalize()%]Service srv;
	
	/**
	 * init page
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/init.do")
	public ModelAndView init(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView("[%package.namespace.name%]/[%name.lowerCase()%]/manage");
	}
	
	@RequestMapping("/list.do")
	public ModelAndView list(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		return new ModelAndView("[%package.namespace.name%]/[%name.lowerCase()%]/list");
	}
	
	@RequestMapping("/nodes.do")
	public @ResponseBody List<[%name.capitalize()%]> nodes(final HttpServletRequest req,
			final HttpServletResponse res) {
		List<[%name.capitalize()%]> nodes = srv.queryAll();
		
		return nodes;
	}
	
	/**
	 * page query
	 * @param req
	 * @param res
	 * @param [%name%]
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/query.do")
	public @ResponseBody PageView query(final HttpServletRequest req,
			final HttpServletResponse res,[%name.capitalize()%] [%name.firstLower()%],int curPage) {
			
		return srv.pageViewQueryS([%name.firstLower()%],
				wrapQueryView(req,[%name.capitalize()%].class,"id"[%for (ownedOperation){%]
									[%if (name.lowerCase().contains("list")){%]
										[%for (ownedParameter){%]
											[%push()%]
												[%for (get("attrs")){%]
													[%if (peek().name == name){%]
														[%if (ownedComment != null){%]
							,"[%name.firstLower()%]"
														[%}else{%]
							,"[%name.firstLower()%]"
														[%}%]
													[%}%]
												[%}%]
											[%pop()%]	
										[%}%]
									[%}%]
								[%}%]
						));
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
			final HttpServletResponse res,[%name.capitalize()%] [%name.firstLower()%]) {
		if ([%name.firstLower()%].getId() != null) {
			int pid = [%name.firstLower()%].getPid() == null ? -1 : [%name.firstLower()%].getPid();	//已存在情况下,无pid编辑，有pid拖拽
			[%name.firstLower()%] = srv.get([%name.firstLower()%].getId());
			if(pid != -1 && pid != [%name.firstLower()%].getPid()){
				[%name.firstLower()%].setPid(pid);
			}
		}else{
			[%name.firstLower()%].setName("新增[%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentName", req.getParameter("parentName"));
		model.put("[%name.firstLower()%]", [%name.firstLower()%]);

		return new ModelAndView("[%package.namespace.name%]/[%name.lowerCase()%]/detail",model);
	}
	
	/**
	 * save
	 * @param req
	 * @param res
	 * @param [%name.capitalize()%]
	 * @return
	 */
	@RequestMapping("/save.do")
	public @ResponseBody JSONObject save(final HttpServletRequest req,
			final HttpServletResponse res,[%name.capitalize()%] [%name.firstLower()%]) {
		final boolean sign = srv.saveOrUpdate([%name.firstLower()%]);
		
		return ajaxCallback(sign);
	}
	
	/**
	 * delete
	 * @param req
	 * @param res
	 * @param [%name.firstLower()%]
	 * @return
	 */
	@RequestMapping("/delete.do")
	public @ResponseBody JSONObject delete(final HttpServletRequest req,
			final HttpServletResponse res,[%name.capitalize()%] [%name.firstLower()%]) {
		final boolean sign = srv.delete([%name.firstLower()%]);
		
		return ajaxCallback(sign);
	}
}