[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
%]

[%script type="Class" name="model" file="src/main/java/com/se7en/model/[%package.namespace.name%]/[%name.capitalize()%].java" post="replaceAll("[\r\n]{2,}","\r\n").trim()"%]
package com.se7en.model.[%package.namespace.name%];

[%for (getAllAttributes()){%]
	[%if (type.filter("PrimitiveType") != null){%]
		[%if (type.name.lowerCase().contains("string")){%]
		[%}else if(type.name.lowerCase().contains("int")){%]
		[%}else if(type.name.lowerCase().contains("boolean")){%]
		[%}else if(type.name.lowerCase().contains("date")){%]
import java.util.Date;
		[%}else if(type.name.lowerCase().contains("datetime")){%]
import java.util.Date;		
		[%}else if(type.name.lowerCase().contains("byte")){%]
		[%}else if(type.name.lowerCase().contains("char")){%]
		[%}else if(type.name.lowerCase().contains("short")){%]
		[%}else if(type.name.lowerCase().contains("long")){%]
		[%}else if(type.name.lowerCase().contains("float")){%]
		[%}else if(type.name.lowerCase().contains("double")){%]
		[%}else if(type.name.lowerCase().contains("blob")){%]
import java.sql.Blob;
		[%}else if(type.name.lowerCase().contains("clob")){%]
import java.sql.Clob;
		[%}%]
	[%--关联字段--%]
	[%}else if(type.filter("Class") != null){%]
import com.se7en.model.[%type.package.namespace.name%].[%type.name%];
		[%if (clientDependency.name == "1-*"){%]
import java.util.LinkedHashSet;
		[%}else if (clientDependency.name == "*-1"){%]
		[%}else if (clientDependency.name == "1-1"){%]
		[%}else if (clientDependency.name == "*-*"){%]
		[%}%]		
	[%}else{%]
	[%}%]
[%}%]
[%if (superClass != null){%]
import com.se7en.model.[%superClass.name.capitalize()%];
[%}%]


/**
 * [%name.capitalize()%] entity
 */
[%if (superClass != null){%]
public class [%name.capitalize()%] extends [%superClass.name.capitalize()%] implements java.io.Serializable {
[%}else{%]
public class [%name.capitalize()%] implements java.io.Serializable {
[%}%] 

	public final static [%name.capitalize()%] EMPTY_[%name.toUpperCase()%] = new [%name.capitalize()%]();

	// Fields
	[%for (attribute){%]
		[%--基本类型--%]
		[%if (type.filter("PrimitiveType") != null){%]
			[%if (type.name.lowerCase().contains("string")){%]
	private String [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("int")){%]
	private Integer [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("boolean")){%]
	private Integer [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("date")){%]
	private Date [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("datetime")){%]
	private Date [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]		
			[%}else if(type.name.lowerCase().contains("byte")){%]
	private Byte [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("char")){%]
	private String [%name.firstLower()%];		[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("short")){%]
	private Short [%name.firstLower()%];		[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("long")){%]
	private Long [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("float")){%]
	private Float [%name.firstLower()%];		[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("double")){%]
	private Double [%name.firstLower()%];		[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("blob")){%]
	private Blog [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if(type.name.lowerCase().contains("clob")){%]
	private String [%name.firstLower()%];		[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}%]
		[%}else if(type.filter("Enumeration") != null){%]
	private Integer [%name.firstLower()%];			[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]	
		[%--关联字段--%]
		[%}else if(type.filter("Class") != null){%]
			[%--组合映射--%]
			[%if (clientDependency.name == "1-*"){%]
	private Set<[%type.name%]> [%name.firstLower()%]s = new LinkedHashSet<[%type.name%]>();	[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if (clientDependency.name == "*-1"){%]
	private [%type.name%] [%name.firstLower()%];	[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if (clientDependency.name == "1-1"){%]
	private [%type.name%] [%name.firstLower()%];	[%if (ownedComment != null){%]//[%ownedComment.body%][%}%]
			[%}else if (clientDependency.name == "*-*"){%]
			[%}%]
		[%}else{%]
		[%}%]
	[%}%]

	// Property accessors
	[%for (attribute){%]
		[%if (type.filter("PrimitiveType") != null){%]
			[%if (type.name.lowerCase().contains("string")){%]
	public String get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](String [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}else if(type.name.lowerCase().contains("int")){%]
	public Integer get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Integer [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("boolean")){%]
	public Integer get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Integer [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}else if(type.name.lowerCase().contains("date")){%]
	public Date get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Date [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}else if(type.name.lowerCase().contains("datetime")){%]
	public Date get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Date [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("byte")){%]
	public Byte get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Byte [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("char")){%]
	public String get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](String [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("short")){%]
	public Short get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Short [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}		
			[%}else if(type.name.lowerCase().contains("long")){%]
	public Long get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Long [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("float")){%]
	public Float get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Float [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}		
			[%}else if(type.name.lowerCase().contains("double")){%]
	public Double get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Double [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}		
			[%}else if(type.name.lowerCase().contains("blob")){%]
	public Blob get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Blob [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
			[%}else if(type.name.lowerCase().contains("clob")){%]
	public String get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](String [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}%]
		[%}else if(type.filter("Enumeration") != null){%]
	public Integer get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%](Integer [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}	
		[%--关联字段--%]
		[%}else if(type.filter("Class") != null){%]
			[%--组合映射--%]
			[%if (clientDependency.name == "1-*"){%]
	public Set<[%type.name%]> get[%name.capitalize()%]s() {
		return [%name.firstLower()%]s;
	}

	public void set[%name.capitalize()%]s(Set<[%type.name%]> [%name.firstLower()%]s) {
		this.[%name.firstLower()%]s = [%name.firstLower()%]s;
	}
			[%}else if (clientDependency.name == "*-1"){%]
	public [%type.name%] get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%]([%type.name%] [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}else if (clientDependency.name == "1-1"){%]
	public [%type.name%] get[%name.capitalize()%]() {
		return [%name.firstLower()%];
	}

	public void set[%name.capitalize()%]([%type.name%] [%name.firstLower()%]) {
		this.[%name.firstLower()%] = [%name.firstLower()%];
	}
			[%}else if (clientDependency.name == "*-*"){%]
			[%}%]
		[%}else{%]
		[%}%]
	[%}%]
	
	/** default constructor */
	public [%name.capitalize()%]() {
	}

}