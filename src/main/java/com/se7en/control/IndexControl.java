package com.se7en.control;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.se7en.model.base.BaseMenu;
import com.se7en.service.base.BaseMenuService;

@Controller
public class IndexControl extends AbstractControl {
	
	@Resource
	private BaseMenuService menuSrv;
	
	@RequestMapping("/index.do")
	public ModelAndView desk(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		//记录登录日志
		String ip = getIpAddr(req);
		log.info("user login ip=" + ip + " 用户登录成功");

		List<BaseMenu> roots = menuSrv.getRoots();
		Iterator<BaseMenu> ite = roots.iterator();
		while (ite.hasNext()){
			BaseMenu baseMenu = ite.next();
			if(baseMenu.getPid() != 1){
				ite.remove();
			}
		}

		ObjectMapper om = new ObjectMapper();
		String jsonStr = om.writeValueAsString(roots);
		
		return new ModelAndView("index","menus",jsonStr);
	}

}
