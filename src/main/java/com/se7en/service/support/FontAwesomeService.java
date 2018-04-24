package com.se7en.service.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.se7en.common.util.StringUtil;

@Service
public class FontAwesomeService {
	
	private static List<String> icons = null;

	public List<String> getAllIconClass(HttpServletRequest req) {
		synchronized (FontAwesomeService.class) {
			if(icons == null || icons.size() == 0){
				try {
					icons = new LinkedList<String>();
					InputStream is = req.getSession().getServletContext()
							.getResourceAsStream("css/font-awesome.min.css");
					String str = IOUtils.toString(is, "utf-8");
					String[] cols = StringUtil.substringsBetween(str, ".fa-", "{");
					for (int i = 0;i < cols.length;i++) {
						cols[i] = "fa-" + cols[i];
						if(cols[i].contains(":before")){
							cols[i] = StringUtil.remove(cols[i], ":before");
						}
						if(cols[i].contains(",")){
							icons.addAll(Arrays.asList(cols[i].split(",")));
						}else{
							icons.add(cols[i]);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return icons;
	}

}
