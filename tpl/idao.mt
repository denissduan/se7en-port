[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
%]

[%script type="Class" name="idao" file="src/main/java/com/se7en/dao/[%package.namespace.name%]/I[%name.capitalize()%]Dao.java" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
package com.se7en.dao.[%package.namespace.name%];
import com.se7en.model.[%package.namespace.name%].[%name.capitalise()%];
import com.se7en.dao.IEjbDao;

/**
 * [%if (ownedComment.body.isNotEmpty()){%][%ownedComment.body%][%}%]	
 * database access obj interface 
 */
public interface I[%name.capitalise()%]Dao extends IEjbDao<[%name.capitalize()%]> {

}