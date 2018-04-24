[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
%]

[%script type="Class" name="dao" file="src/main/java/com/se7en/dao/[%package.namespace.name%]/impl/[%name.capitalize()%]Dao.java" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
package com.se7en.dao.[%package.namespace.name%].impl;
import com.se7en.model.[%package.namespace.name%].[%name.capitalise()%];
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.[%package.namespace.name%].I[%name.capitalise()%]Dao;

/**
 * [%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]	
 * database access obj 
 */
@Repository
public class [%name.capitalise()%]Dao extends EjbDao<[%name.capitalise()%]> implements I[%name.capitalise()%]Dao {
	
}