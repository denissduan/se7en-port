[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
%]

[%script type="Class" name="control" file="src/main/java/com/se7en/control/[%package.namespace.name%]/[%name.capitalize()%]Control.java" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%attribute.put("attrs")%][%name.firstLower().put("varName")%]
package com.se7en.control.[%package.namespace.name%];
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
import com.se7en.service.[%package.namespace.name%].[%name.capitalise()%]Service;
import com.se7en.model.[%package.namespace.name%].[%name.capitalise()%];

/**
 * [%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]
 * [%name%] control
 */
@Controller
@RequestMapping("/[%name.firstLower()%]")
public class [%name.capitalize()%]Controller extends AbstractControl {
	
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
		
		return new ModelAndView(MANAGE_ENGINE,CLASS_NAME,"[%name.capitalize()%]");
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
			final HttpServletResponse res,final [%name.capitalize()%] [%name.firstLower()%]) {
		
		return srv.modelQuery(req, wrapQueryView(req,[%name.capitalize()%].class));
		/*return srv.pageViewQueryS([%name.firstLower()%],
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
								[%}%]));*/
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
		model.put(CLASS_NAME, "[%name.capitalize()%]");
		if(id != null){
			[%name.capitalize()%] [%name.firstLower()%] = srv.get(id);
			model.put(VIEW_OBJECT, [%name.firstLower()%]);
		}
		
		//return new ModelAndView("[%package.namespace.name%]/[%name.lowerCase()%]/detail",model);
		return new ModelAndView(DETAIL_ENGINE,model);
	}
	
	/**
	 * save
	 * @param req
	 * @param res
	 * @param [%name.capitalize()%]
	 * @return
	 */
	@RequestMapping("/save.do")
	public @ResponseBody AjaxResult save(final HttpServletRequest req,
			final HttpServletResponse res,final [%name.capitalize()%] [%name.firstLower()%]) {
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
	public @ResponseBody AjaxResult delete(final HttpServletRequest req,
			final HttpServletResponse res,final [%name.capitalize()%] [%name.firstLower()%]) {
		final boolean sign = srv.delete([%name.firstLower()%]);
		
		return ajaxCallback(sign);
	}
}