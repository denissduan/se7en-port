package com.se7en.control;

import com.se7en.common.util.AESUtil;
import com.se7en.common.util.StringUtil;
import com.se7en.model.base.BaseUser;
import com.se7en.service.base.BaseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Controller
public class LoginController extends AbstractControl {

	public final static String SESSION_USER_ID = "userId";
	public final static String SESSION_USERNAME = "username";

	@Resource
	private BaseUserService userService;

	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest req) throws Exception {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
			return null;
		}

		String aesPassword = AESUtil.AESEncode(password);
		BaseUser user = userService.getByUsername(username);
		if(user.getPassword().equals(aesPassword) == false){
			return null;
		}
		//设置session
		HttpSession session = req.getSession();
		session.setAttribute(SESSION_USER_ID, user.getId());
		session.setAttribute(SESSION_USERNAME, user.getName());

		return new ModelAndView("redirect:index.do");
	}

	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest req) throws Exception {
		//清除session
		HttpSession session = req.getSession();
		session.removeAttribute(SESSION_USER_ID);
		session.removeAttribute(SESSION_USERNAME);

		return new ModelAndView("redirect:index.do");
	}

}
