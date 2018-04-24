package com.se7en.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se7en.model.AjaxResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.se7en.service.GridService;

@Controller
@RequestMapping("/grid")
public class GridControl extends AbstractControl {
	
	@Resource
	private GridService gridSrv;

	/**
	 * 保存单元格值
	 * @param req
	 * @param res
	 * @param id
	 * @param tableName
	 * @param name
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCell.do")
	public @ResponseBody
	AjaxResult saveCell(HttpServletRequest req,
						HttpServletResponse res, String id, String tableName, String name, String value) throws Exception {
		boolean sign = gridSrv.saveCell(id, tableName, name, value);
		
		return ajaxCallback(sign);
	}
	
}
