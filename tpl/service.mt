[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
%]

[%script type="Class" name="service" file="src/main/java/com/se7en/service/[%package.namespace.name%]/[%name.capitalize()%]Service.java" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
[%getAllAttributes().put("attrs")%][%name.firstLower().put("varName")%][%name.lowerCase().put("nickName")%]
package com.se7en.service.[%package.namespace.name%];
import java.util.List;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.se7en.common.util.StringUtil;
import com.se7en.service.AbstractEjbService;
import com.se7en.web.vo.PageView;
import com.se7en.web.vo.QueryView;
import com.se7en.model.[%package.namespace.name%].[%name.capitalise()%];
import com.se7en.dao.[%package.namespace.name%].I[%name.capitalise()%]Dao;
[%if (superClass != null && superClass.name == "BaseHierarchy"){%]
import com.se7en.service.BaseHierarchyService;
[%}%]

/**
 * [%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]
 * [%name%] service
 */
@Service
[%if (superClass != null && superClass.name == "BaseHierarchy"){%]
public class [%name.capitalise()%]Service extends [%superClass.name.capitalize()%]Service<[%name.capitalise()%]> {
[%}else{%]
public class [%name.capitalise()%]Service extends AbstractEjbService<[%name.capitalise()%]> {
[%}%]

	@Resource
	private I[%name.capitalise()%]Dao dao;
	
	@Override
	public I[%name.capitalise()%]Dao getDao() {
	
		return dao;
	}
	
	public List<[%name.capitalise()%]> queryAll(){
		
		return dao.queryAll();
	}
	
	public PageView pageViewQueryS([%name.capitalise()%] [%name.firstLower()%],QueryView view){
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" [%get("nickName")%].id ");
		[%for (ownedOperation){%]
			[%if (name.lowerCase().contains("list")){%]
				[%for (ownedParameter){%]
					[%push()%]
						[%for (get("attrs")){%]
							[%if (peek().name == name){%]
								[%if (type.filter("Class") != null) {%]
									[%--获取查询字段--%]
									[%if (ownedElement.filter("LiteralString").length > 0) {%]
										[%for (ownedElement.filter("LiteralString")){%]
											[%if ((self().name.toLowerCase().contains("searchfield")) || (self().name.toLowerCase().contains("searchfld"))){%]
												[%--带字段别名--%]
												[%if (self().value != null){%]
		sql.append(" ,[%type.name.lowerCase()%].[%self().value.getFieldNameFromPropertyName()%] as [%type.name.lowerCase()%][%self().value%] ");
												[%}else{%]
		sql.append(" ,[%type.name.lowerCase()%].name as [%type.name.lowerCase()%]name ");									
												[%}%]
											[%}else{%]
		sql.append(" ,[%type.name.lowerCase()%].name as [%type.name.lowerCase()%]name ");									
											[%}%]
										[%}%]
									[%}else{%]
		sql.append(" ,[%type.name.lowerCase()%].name as [%type.name.lowerCase()%]name ");									
									[%}%]
								[%}else{%]
		sql.append(" ,[%get("nickName")%].[%name%] as [%get("nickName")%][%name%] ");
								[%}%]
							[%}else{%]
							[%}%]
						[%}%]
					[%pop()%]	
				[%}%]
			[%}else{%]
			[%}%]
		[%}%]
		sql.append(" from [%name.getTableNameFromClassName()%] [%get("nickName")%] ");
		[%--查看是否存在表关联--%]
		[%for (ownedOperation){%]
			[%if (name.lowerCase().contains("list")){%]
				[%for (ownedParameter){%]
					[%push()%]
						[%for (get("attrs")){%]
							[%if (peek().name == name){%]
								[%if (type.filter("Class") != null) {%]
		sql.append(" left join [%type.name.getTableNameFromClassName()%] [%type.name.lowerCase()%] on [%get("nickName")%].[%name%]_id = [%type.name.lowerCase()%].id ");
								[%}%]
							[%}else{%]
							[%}%]
						[%}%]
					[%pop()%]	
				[%}%]
			[%}else{%]
			[%}%]
		[%}%]

		sql.append(" where 1 = 1 ");
		[%for (ownedOperation){%]
			[%if (name.lowerCase().contains("search")){%]
				[%for (ownedParameter){%]
					[%push()%]
						[%for (get("attrs")){%]
							[%if (peek().name == name){%]
								[%if (type.filter("Class") != null) {%]
		if([%get("varName")%].get[%name.capitalize()%]().getId() != null){
		    sql.append(" and [%name%].id = ? ");
		    params.add([%get("varName")%].get[%name.capitalise()%]().getId());
		}										
								[%}else if (type.name.toLowerCase().contains("string")){%]
		if(StringUtil.isNotBlank([%get("varName")%].get[%name.capitalize()%]())){
		    sql.append(" and [%get("nickName")%].[%name%] like ? ");
		    params.add("%" + [%get("varName")%].get[%name.capitalise()%]() + "%");
		}
								[%}else if(type.name.toLowerCase().contains("int")){%]
		if([%get("varName")%].get[%name.capitalize()%]() != null){
		    sql.append(" and [%get("nickName")%].[%name%] = ? ");
		    params.add([%get("varName")%].get[%name.capitalise()%]());
		}
								[%}else if(type.name.toLowerCase().contains("boolean")){%]
		if([%get("varName")%].get[%name.capitalize()%]() != null){
		    sql.append(" and [%get("nickName")%].[%name%] = ? ");
		    params.add([%get("varName")%].get[%name.capitalise()%]());
		}
								[%}else if(type.name.toLowerCase().contains("date")){%]
		if([%get("varName")%].get[%name.capitalize()%]() != null){
		    sql.append(" and [%get("nickName")%].[%name%] = ? ");
		    params.add([%get("varName")%].get[%name.capitalise()%]());
		}
								[%}%]
							[%}else{%]
							[%}%]
						[%}%]
					[%pop()%]	
				[%}%]
			[%}else{%]
			[%}%]
		[%}%]
		return getPageQueryService().pageQueryS(sql.toString(), params.toArray(), view, dao);
	}

}