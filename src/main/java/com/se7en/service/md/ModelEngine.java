package com.se7en.service.md;
	
import java.lang.Class;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.se7en.common.util.StringUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.DataTypeImpl;
import org.eclipse.uml2.uml.internal.impl.EnumerationImpl;
import org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service
public class ModelEngine {

	private static Logger log = LoggerFactory.getLogger(ModelEngine.class);

	public final static String moduleName = "md";

	private final static String RELATIVE_SIGN = "\\.";

	private String umlPath = null;
	
	private Model model = null;
	
	private Map<String,org.eclipse.uml2.uml.Package> pkgCache = null;
	
	private Map<String,ClassImpl> clsCache = null;
	
	@javax.annotation.Resource
	private StringServices strSrv;

	public void setUmlPath(String umlPath) {
		this.umlPath = umlPath;
	}

	public Model getModel(){
		String umlPath = null;
		if(StringUtil.isNotEmpty(this.umlPath)){
			umlPath = this.umlPath;
		}else{
			umlPath = "file:/" + getUMLPath() + "engine.uml";
		}
		if(model == null){
			model = getModel(umlPath);
		}
		return model;
	}

	private Model getModel(String umlPath) {
		URI baseUri = URI.createURI(umlPath);

		ResourceSet resourceSet = new ResourceSetImpl();

		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", UMLResource.Factory.INSTANCE);

		Resource resource = resourceSet.getResource( baseUri, true );

		return (Model)EcoreUtil.getObjectByType( resource.getContents(), UMLPackage.Literals.MODEL );
	}
	
	public String getUMLPath(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
		ServletContext servletContext = webApplicationContext.getServletContext();

		String path = servletContext.getRealPath("/");
//		path = path.substring(0, path.indexOf("WebContent"));
		path += "WEB-INF/classes/";

		return path;
	}
	
	/**
	 * Gets the package.
	 * 根据包名获取包
	 * @param pkgName the pkg name
	 * @return the package
	 */
	public org.eclipse.uml2.uml.Package getPackage(String pkgName){
		if(pkgCache == null){
			pkgCache = new HashMap<String,org.eclipse.uml2.uml.Package>();
			Model model = getModel();
			EList<org.eclipse.uml2.uml.Package> pkgs = model.getNestedPackages();
			for (org.eclipse.uml2.uml.Package pkg : pkgs) {
				pkgCache.put(pkg.getName(), pkg);
			}
		}
		return pkgCache.get(pkgName);
	}
	
	/**
	 * Gets the cls.
	 * 根据反射类获取
	 * @param cls the cls
	 * @return the cls
	 */
	public ClassImpl getClass(Class<?> cls){
		String clsName = cls.getSimpleName();
		
		return getClass(clsName);
	}
	
	/**
	 * Gets the cls.
	 * 根据类名获取类
	 * @param clsName the cls name
	 * @return the cls
	 */
	public ClassImpl getClass(String clsName){
		if(clsCache == null){
			clsCache = new HashMap<String,ClassImpl>();
			Model model = getModel();
			EList<org.eclipse.uml2.uml.Package> pkgs = model.getNestedPackages();
			for (org.eclipse.uml2.uml.Package pkg : pkgs) {
		    	EList<Element> eles = pkg.allOwnedElements();
		    	for (Element ele : eles) {
		    		if(ele instanceof ClassImpl){
		    			ClassImpl cls = (ClassImpl)ele;
		    			String name = cls.getName();
		    			if(!clsCache.containsKey(name)) {
		    				clsCache.put(name, cls);
		    			}
		    			cacheSuperClass(cls);
		    		}
				}
			}
		}
		return clsCache.get(clsName);
	}
	
	/**
	 * Gets the property cls.
	 * 获取实体属性对应
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return the property cls
	 */
	public ClassImpl getRelativePropertyCls(String clsName,String propName){
		Property prop = getProperty(clsName, propName);
		
		return getPropertyCls(prop);
	}
	
	/**
	 * Gets the property type.
	 * 获取字段类型
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return the property type
	 */
	public Type getPropertyType(String clsName,String propName){
		Type type = null;
		Property prop = getProperty(clsName, propName);
		if(prop != null)
			type = prop.getType();
		return type;
	}
	
	/**
	 * Gets the last relative cls.
	 * 根据属性名称获取最后一个关联属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return the last relative cls
	 */
	public ClassImpl getLastRelativeCls(String clsName,String propName){
		int lastPos = propName.lastIndexOf(".");
		String lastClsName = propName.substring(0, lastPos);
		String lastFldName = propName.substring(lastPos + 1);
		Property prop = getProperty(clsName, lastClsName);
		if(isClassProperty(prop)){
			Property fld = getProperty(getPropertyCls(prop).getName(), lastFldName);
			if(isClassProperty(fld)){
				return getPropertyCls(fld);
			}
		}
		return getPropertyCls(prop);
	}

	public ClassImpl getPropertyCls(Property prop) {
		return (ClassImpl)prop.getType();
	}
	
	/**
	 * Gets the relative property first cls.
	 * 获取关联字段第一个关联class
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return the relative property first cls
	 */
	public ClassImpl getRelativePropertyFirstCls(String clsName,String propName){
		ClassImpl ci = null;
		if(propName.contains(".")){		//关联字段
			String propNickName = propName.substring(0, propName.indexOf("."));
			ci = getRelativePropertyCls(clsName, propNickName);
		}
		return ci;
	}
	
	public void cacheSuperClass(ClassImpl cls){
		if(cls.getSuperClasses().size() > 0){
			EList<org.eclipse.uml2.uml.Class> superClss = cls.getSuperClasses(); 
			for (org.eclipse.uml2.uml.Class c : superClss) {
				String name = c.getName();
				if(!clsCache.containsKey(name)) {
    				clsCache.put(name, (ClassImpl)c);
				}
				cacheSuperClass((ClassImpl)c);
			}
		}
	}

	public EList<Property> getAllPropertys(String clsName){
		ClassImpl cls = getClass(clsName);
		return cls.getAllAttributes();
	}

	public EList<Property> getOwnerPropertys(String clsName){
		ClassImpl cls = getClass(clsName);
		return cls.getAttributes();
	}
	
	/**
	 * Gets the property.
	 * 获取属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return the property
	 */
	public Property getProperty(String clsName,String propName){
		ClassImpl cls = getClass(clsName);
		Property prop = null;
		//
		if(propName.contains(".")){		//关联字段
//			String fldClsName = clsName.substring(0, propName.indexOf("."));
			ClassImpl fldCls = getRelativePropertyFirstCls(clsName, propName);
			String fldClsName = fldCls.getName();
			prop = getProperty(fldClsName, propName.substring(propName.indexOf(".") + 1));
		}else{		//
			prop = cls.getAttribute(propName, null);
		}
		//
		//从父类获取
		if(prop == null){
			EList<org.eclipse.uml2.uml.Class> elist = cls.getSuperClasses();
			for (org.eclipse.uml2.uml.Class c : elist) {
				prop = getProperty(c.getName(),propName);
			}
		}
		
		return prop;
	}

	/**
	 * 获取属性，如果存在关联属性，获取第一个关联类属性
	 * @param clsName
	 * @param propName
     * @return
     */
	public Property getFirstProperty(String clsName,String propName){
		String firstPropName = propName.split(RELATIVE_SIGN)[0];

		return getProperty(clsName, firstPropName);
	}
	
	/**
	 * Exist property.
	 * 是否存在属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if successful
	 */
	public boolean existProperty(String clsName,String propName){
		Property prop = getProperty(clsName,propName);
		
		if(prop == null)
			return false;
		return true;
	}
	
	/**
	 * Checks if is primarty property.
	 * 判断是否是原生类型属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if is primarty property
	 */
	public boolean isPrimartyProperty(String clsName,String propName){
		return judgePropertyType(clsName, propName, PrimitiveTypeImpl.class);
	}
	
	/**
	 * Checks if is primarty property.
	 * 判断是否是原生类型属性
	 * @return true, if is primarty property
	 */
	public boolean isPrimartyProperty(Property prop){
		return judgePropertyType(prop, PrimitiveTypeImpl.class);
	}
	
	/**
	 * Checks if is class property.
	 * 判断是否是引用类型属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if is class property
	 */
	public boolean isClassProperty(String clsName,String propName){
		return judgePropertyType(clsName, propName, ClassImpl.class);
	}

	/**
	 * Checks if is class property.
	 * 判断(如果存在关联属性,取第一个关联属性)是否是引用类型属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if is class property
	 */
	public boolean isClassFirstProperty(String clsName,String propName){
		return judgePropertyType(clsName, propName.split(RELATIVE_SIGN)[0], ClassImpl.class);
	}
	
	/**
	 * Checks if is class property.
	 * 判断是否是引用类型属性
	 * @return true, if is class property
	 */
	public boolean isClassProperty(Property prop){
		return judgePropertyType(prop, ClassImpl.class);
	}
	
	/**
	 * Checks if is enumeration property.
	 * 判断是否是枚举类型属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if is enumeration property
	 */
	public boolean isEnumerationProperty(String clsName,String propName){
		return judgePropertyType(clsName, propName, Enumeration.class);
	}
	
	/**
	 * Checks if is enumeration property.
	 * 判断是否是枚举类型属性
	 * @return true, if is enumeration property
	 */
	public boolean isEnumerationProperty(Property prop){
		return judgePropertyType(prop, Enumeration.class);
	}
	
	/**
	 * Checks if is enumeration property.
	 * 判断是否是DataType类型属性
	 * @return true, if is enumeration property
	 */
	public boolean isDataTypeProperty(Property prop){
		return judgePropertyType(prop, DataTypeImpl.class);
	}
	
	/**
	 * Checks if is enumeration property.
	 * 判断是否是DataType类型属性
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if is enumeration property
	 */
	public boolean isDataTypeProperty(String clsName,String propName){
		return judgePropertyType(clsName, propName, DataTypeImpl.class);
	}
	
	/**
	 * Judge property type.
	 * 判断属性类型
	 * @param clsName the cls name
	 * @param propName the prop name
	 * @return true, if successful
	 */
	private boolean judgePropertyType(String clsName, String propName, Class<? extends Type> type) {
		Property prop = getProperty(clsName,propName);
		
		return judgePropertyType(prop,type);
	}
	
	/**
	 * Judge property type.
	 * 判断属性类型
	 * @param type the type
	 * @return true, if successful
	 */
	private boolean judgePropertyType(Property prop, Class<? extends Type> type) {
		boolean ret = false;
		
		if(prop != null){
			if(type.isInstance(prop.getType())){
				ret = true;
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets the operation.
	 * 获取操作集
	 * @param cls the cls
	 * @param optName the opt name
	 * @return the operation
	 */
	public Operation getOperation(Class<?> cls, String optName){
		String clsName = cls.getSimpleName();
		
		return getOperation(clsName, optName);
	}
	
	/**
	 * Gets the operation.
	 * 获取操作集
	 * @param clsName the cls name
	 * @param optName the opt name
	 * @return the operation
	 */
	public Operation getOperation(String clsName,String optName){
		Operation ret = null;
		
		ClassImpl ci = getClass(clsName);
		ret = ci.getOperation(optName, null, null);

		return ret;
	}

	/**
	 * 获取枚举类型映射
	 * @param peop
	 * @return
	 */
	public Map<String,String> getEnumerationMap(Property peop){
		EnumerationImpl ei = (EnumerationImpl)(peop.getType());
		List<EnumerationLiteral> listerals = ei.getOwnedLiterals();
		Map<String,String> enumMap = new HashMap<>(listerals.size());
		for(EnumerationLiteral literal : listerals){
			String enumText = literal.getName();
			String enumValue = literal.getSpecification().stringValue();
			enumMap.put(enumValue,enumText);
		}

		return enumMap;
	}

	/**
	 * 清空缓存
	 */
	public void clearCache(){
		this.model = null;
		this.pkgCache = null;
		this.clsCache = null;
	}
}
