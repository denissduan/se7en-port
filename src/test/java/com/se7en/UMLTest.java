package com.se7en;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPackage.Literals;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Test;

import com.se7en.service.md.ModelEngine;

public class UMLTest {
	
	private static final ResourceSet RESOURCE_SET = new ResourceSetImpl();

	@Test
	public void test() throws Exception{
	    // This line needs to be here, to force running of the UMLPackage
	    //  static initializer, otherwise loading the meta-model will return null
		/*UMLPackage.eINSTANCE.getName();
	    
	    Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
	      UMLResource.FILE_EXTENSION,
	      UMLResource.Factory.INSTANCE
	    );
		
	    final String umlProfile = "news.uml";
	    final URL url = UMLTest.class.getClassLoader().getResource( umlProfile );
	    final URL url = UMLTest.class.getResource(umlProfile);
	    String baseUrl = url.toString();
	    baseUrl = baseUrl.substring( 0, baseUrl.length() - umlProfile.length() );
	    registerPathmaps( URI.createURI( umlProfile ));

	    Model umlMetamodel = (Model) load( URI.createURI( UMLResource.UML_METAMODEL_URI ) );
	    System.out.println( "umlMetamodel = " + umlMetamodel );

	    final Model sampleModel = UMLFactory.eINSTANCE.createModel();
	    sampleModel.setName( "Sample Model" );

	    final Profile sampleProfile = UMLFactory.eINSTANCE.createProfile();
	    sampleProfile.setName( "Sample Profile" );


	    // The model should be added to a resource before creating/applying stereotypes
	    //  otherwise EMF won't persist the created stereotypes
	    final File outputFile = new File( "sample_model.uml" );
	    final URI outputUri = URI.createFileURI( outputFile.getAbsolutePath() );
	    final Resource resource = RESOURCE_SET.createResource( outputUri );
	    resource.getContents().add( sampleModel );
	    resource.getContents().add( sampleProfile );

	    final Stereotype ejbStereo = sampleProfile.createOwnedStereotype( "EJB" );
	    extendMetaclass( umlMetamodel, sampleProfile, UMLPackage.Literals.CLASS.getName(), ejbStereo );

	    sampleProfile.define();

	    final Package samplePackage = sampleModel.createNestedPackage( "sample" );
	    samplePackage.applyProfile( sampleProfile );

	    final Class sampleClass = samplePackage.createOwnedClass( "TimeEntry", false );
	    sampleClass.applyStereotype( ejbStereo );    

	    resource.save( null );*/
	    String rootPath = "file:\\" + System.getProperty("user.dir") + "\\news.uml";
//		String rootPath = System.getProperty("user.dir") + "/base.uml";
		URI baseUri = URI.createURI(rootPath);
		
		URIConverter.URI_MAP.put(URI.createURI( UMLResource.LIBRARIES_PATHMAP ), baseUri.appendSegment( "libraries" ).appendSegment( "" ));
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.METAMODELS_PATHMAP ), baseUri.appendSegment( "metamodels" ).appendSegment( "" ));
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.PROFILES_PATHMAP ), baseUri.appendSegment( "profiles" ).appendSegment( "" ));
	    
	    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("UML.metamodel.uml", UMLPackage.eINSTANCE);
	    Resource resource = RESOURCE_SET.getResource( URI.createURI( UMLResource.UML_METAMODEL_URI ), true );
	    
	    Model umlMetamodel = (Model)(Package) EcoreUtil.getObjectByType( resource.getContents(), Literals.PACKAGE );
	    System.out.println( umlMetamodel == null);
	}
	
	protected static Package load( URI uri )
		    throws Exception
	  {
	    System.out.println( "uri = " + uri );
	    Resource resource = RESOURCE_SET.getResource( uri, true );
	    return (Package) EcoreUtil.getObjectByType( resource.getContents(), Literals.PACKAGE );
	  }
	
	protected static void registerPathmaps( URI baseUri )
	  {
	    System.out.println( "baseUri = " + baseUri );
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.LIBRARIES_PATHMAP ), baseUri.appendSegment( "libraries" ).appendSegment( "" ));
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.METAMODELS_PATHMAP ), baseUri.appendSegment( "metamodels" ).appendSegment( "" ));
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.PROFILES_PATHMAP ), baseUri.appendSegment( "profiles" ).appendSegment( "" ));
	  }
	
	@Test
	public void test2(){
		// create resource set and resource
		try{
			String path = "file:\\" + System.getProperty("user.dir") + "\\base.uml";
			ResourceSet resourceSet = new ResourceSetImpl();
			// Register XML resource factory
	
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
			new XMIResourceFactoryImpl());
	
			URI uri =
			org.eclipse.emf.common.util.URI.createURI(path);
			Resource resource = resourceSet.createResource(uri);
			TreeIterator ti = resource.getAllContents();
			while (ti.hasNext()){
			System.out.println(ti.getClass().getSimpleName());
			}
		}// end of try
		catch (NullPointerException e){
			e.getStackTrace();
		}
	}
	
	@Test
	public void test3(){
		String path = "file:\\" + System.getProperty("user.dir") + "\\news.uml";
		URI modelURI = URI.createURI(path);

		ResourceSet resourceSet = new ResourceSetImpl();
		
		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", UMLResource.Factory.INSTANCE);
		resourceSet.getResource(URI.createURI(path), true);
		
		Resource resource = resourceSet.createResource(modelURI);
		
		TreeIterator ti = resource.getAllContents();
		while (ti.hasNext()){
		System.out.println(ti.getClass().getSimpleName());
		}
	}
	
	@Test
	public void test4(){
		String rootPath = "file:\\" + System.getProperty("user.dir") + "\\base.uml";
		URI baseUri = URI.createURI(rootPath);
		
//		URIConverter.URI_MAP.put(URI.createURI( UMLResource.LIBRARIES_PATHMAP ), baseUri.appendSegment( "libraries" ).appendSegment( "" ));
//	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.METAMODELS_PATHMAP ), baseUri.appendSegment( "metamodels" ).appendSegment( "" ));
//	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.PROFILES_PATHMAP ), baseUri.appendSegment( "profiles" ).appendSegment( "" ));
	    
	    RESOURCE_SET.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
	    RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", UMLResource.Factory.INSTANCE);
	    RESOURCE_SET.getResource(baseUri, true);
	    
	    Resource resource = RESOURCE_SET.getResource( URI.createURI(rootPath), true );
	    
//	    System.out.println(resource.getContents());
//		Resource resource = resourceSet.createResource(modelURI);
	    
	   /* Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(rootPath, UMLPackage.eINSTANCE);
	    Resource resource = RESOURCE_SET.getResource( URI.createURI(rootPath), true );*/
	    
	    Model umlMetamodel = (Model)EcoreUtil.getObjectByType( resource.getContents(), Literals.MODEL );
	    
	    EList<org.eclipse.uml2.uml.Package> pkgs = umlMetamodel.getNestedPackages();
	    for (org.eclipse.uml2.uml.Package pkg : pkgs) {
	    	EList<Element> eles = pkg.allOwnedElements();
	    	for (Element ele : eles) {
	    		if(ele instanceof ClassImpl){
	    			System.out.println(((ClassImpl) ele).getName());
	    		}
			}
		}
	}
	
	@Test
	public void serviceTest(){
		ModelEngine me = new ModelEngine();
		System.out.println(me.getClass("BaseUnit"));
		System.out.println(me.getProperty("BaseUnit", "phone"));
	}
	
	@Test
	public void pathTest(){
		File file = new File("");
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
