package com.se7en.service.md;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.LiteralIntegerImpl;
import org.eclipse.uml2.uml.internal.impl.LiteralUnlimitedNaturalImpl;
import org.springframework.stereotype.Service;

@Service
public class ViewServices {

	public int adaptLowerValue(Property prop){
		int ret = 0;
		if(prop.getLower() != 0){
			ret = prop.getLower();
		}

		return ret;
	}
	
	public long adaptUpperRange(Property prop,long defaultValue){
		long ret = defaultValue;
		long upperVal = getUpperValue(prop);
		if(upperVal != 0)
			ret = upperVal;
		if(ret != 0)
			ret = ret * 10 - 1;

		return ret;
	}
	
	public int getLowerValue(Property prop){
		int ret = 0;
		if(prop.getLower() != 0){
			ret = prop.getLower();
		}

		return ret;
	}
	
	public long getUpperRange(Property prop){
		long ret = getUpperValue(prop);
		if(ret != 0)
			ret = ret * 10 - 1;

		return ret;
	}

	public long getUpperValue(Property prop) {
		long ret = 0;
		if(prop.getUpper() != 0){
			ret = prop.getUpper();
		}else{
			EList<Element> eles = prop.getOwnedElements();
			for (Element element : eles) {
				if(element instanceof LiteralUnlimitedNaturalImpl){
					LiteralUnlimitedNaturalImpl impl = (LiteralUnlimitedNaturalImpl)element;
					if(impl.getName().contains("length")){
						ret = impl.getValue();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Gets the enumeration literal integer value.
	 * 
	 * @param ele the ele
	 * @return the enumeration literal integer value
	 */
	public int getEnumerationValue(Element ele){
		int ret = 0;
		EList<Element> eles = ele.getOwnedElements();
		for(Element element : eles){
			if(element instanceof LiteralIntegerImpl){
				LiteralIntegerImpl impl = (LiteralIntegerImpl)element;
				ret = impl.getValue();
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets the enumeration text.
	 * 获取枚举文本
	 * @param ele the ele
	 * @return the enumeration text
	 */
	public String getEnumerationText(Element ele){
		String ret = "";
		EList<Comment> comments = ele.getOwnedComments();
		if(comments.size() > 0)
			ret = comments.get(0).getBody();
		return ret;
	}
	
	/**
	 * Gets the class comment.
	 * 获取类描述
	 * @param cls the cls
	 * @return the class comment
	 */
	public String getClassComment(ClassImpl cls){
		String ret = "";
		EList<Comment> coms = cls.getOwnedComments();
		if(CollectionUtils.isNotEmpty(coms)){
			ret = coms.get(0).getBody();
		}
		return ret;
	}
	
	/**
	 * Gets the property comment.
	 * 获取属性描述
	 * @param prop the prop
	 * @return the property comment
	 */
	public String getPropertyComment(Property prop){
		String ret = "";
		EList<Comment> coms = prop.getOwnedComments();
		if(CollectionUtils.isNotEmpty(coms)){
			ret = coms.get(0).getBody();
		}
		
		return ret;
	}
	
	/**
	 * Gets the id by field name.
	 *
	 * @param fldName the fld name
	 * @return the id by field name
	 */
	public String getJqueryIdByFieldName(String fldName){
		return fldName.replaceAll("\\.", "\\\\\\\\.");
	}

	/**
	 * 字段名转换为页面标签名
	 * @param propName
	 * @return
     */
	public String transPropertyName2TagId(String propName){
		return propName.replaceAll("\\.", "_");
	}

	/**
	 * 字段名转换为页面标签名
	 * @param propName
	 * @return
	 */
	public String transTagId2PropertyName(String tagId){
		return tagId.replaceAll("_", ".");
	}
}
