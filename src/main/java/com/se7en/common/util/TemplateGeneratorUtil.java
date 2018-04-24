package com.se7en.common.util;

import com.se7en.service.md.ModelEngine;
import com.se7en.service.md.TemplateStringUtil;
import com.se7en.service.md.ViewUtil;
import org.apache.velocity.VelocityContext;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;

import java.io.File;

/**
 * 代码生成类
 * Created by ZhangShuzheng on 2017/1/10.
 */
public final class TemplateGeneratorUtil {

    private TemplateGeneratorUtil(){
    }

    private static ModelEngine modelEngine = new ModelEngine();

    // Model模板路径
    private static String model_vm = "/template/Model.vm";
    private static String modelmapper_vm = "/template/ModelMapper.vm";
    private static String idao_vm = "/template/Idao.vm";
    private static String dao_vm = "/template/Dao.vm";
    private static String sql_vm = "/template/Sql.vm";
    private static String service_vm = "/template/Service.vm";
    private static String controller_vm = "/template/Controller.vm";
    private static String detail_jsp_vm = "/template/Detail_Jsp.vm";
    private static String detail_js_vm = "/template/Detail_Js.vm";
    private static String manage_jsp_vm = "/template/Manage_Jsp.vm";
    private static String manage_js_vm = "/template/Manage_Js.vm";

    /**
     * 根据模板生成generatorConfig.xml文件
     *
     * @param project        项目模块
     * @param package_name  包名
     */
    public static void generator(
            String project,
            String package_name,
            String model
            ) throws Exception {

        model_vm = TemplateGeneratorUtil.class.getResource(model_vm).getPath().replaceFirst("/", "");
        modelmapper_vm = TemplateGeneratorUtil.class.getResource(modelmapper_vm).getPath().replaceFirst("/", "");
        sql_vm = TemplateGeneratorUtil.class.getResource(sql_vm).getPath().replaceFirst("/", "");
        idao_vm = TemplateGeneratorUtil.class.getResource(idao_vm).getPath().replaceFirst("/", "");
        dao_vm = TemplateGeneratorUtil.class.getResource(dao_vm).getPath().replaceFirst("/", "");
        service_vm = TemplateGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
        controller_vm = TemplateGeneratorUtil.class.getResource(controller_vm).getPath().replaceFirst("/", "");
        detail_jsp_vm = TemplateGeneratorUtil.class.getResource(detail_jsp_vm).getPath().replaceFirst("/", "");
        manage_jsp_vm = TemplateGeneratorUtil.class.getResource(manage_jsp_vm).getPath().replaceFirst("/", "");
        //初始化uml
        File absolute = new File("");
        String absolutePath = absolute.getAbsolutePath();
        project = "se7en-port";
        package_name = "com.se7en";
        String umlPath = "file://" + absolutePath + "/src/main/resources/engine.uml";
        modelEngine.setUmlPath(umlPath);
        //初始化基本信息
        ClassImpl ci = modelEngine.getClass("BtsdHmmState");
        Model domain = ci.getModel();
        String className = ci.getName();
        String domainName = domain.getName();
        //初始化模板信息
        VelocityContext context = new VelocityContext();
        context.put("StringUtil", StringUtil.class);
        context.put("TemplateStringUtil", TemplateStringUtil.class);
        context.put("ViewUtil", ViewUtil.class);
        context.put("modelEngine", modelEngine);
        context.put("class", ci);
        context.put("className", className);
        context.put("domainName", domainName);

        createModel(package_name, absolutePath, className, domainName, context);

        createModelMapper(package_name, absolutePath, className, domainName, context);

        createIdao(package_name, absolutePath, className, domainName, context);

        createDao(package_name, absolutePath, className, domainName, context);

        createService(package_name, absolutePath, className, domainName, context);

        createController(package_name, absolutePath, className, domainName, context);

        createDetailJsp(package_name, absolutePath, className, domainName, context);

        createDetailJs(package_name, absolutePath, className, domainName, context);

        createManageJsp(package_name, absolutePath, className, domainName, context);

        createManageJs(package_name, absolutePath, className, domainName, context);

        createSql(absolutePath, className, context);

    }

    private static void createModel(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成Model ==========");
        String modelPath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/model";
        String modelFile = modelPath + "/" + domainName + "/" + className + ".java";
        File mf = new File(modelFile);
        if(mf.exists() == true){
            mf.delete();
            System.out.println("删除原Model " + modelFile);
        }
        VelocityUtil.generate(model_vm, modelFile, context);
        System.out.println("生成Model" + modelFile);
        System.out.println("========== 结束生成Model ==========");
    }

    private static void createModelMapper(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成ModelMapper ==========");
        String modelMapperPath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/model";
        String modelMapperFile = modelMapperPath + "/" + domainName + "/" + className + ".hbm.xml";
        File mmf = new File(modelMapperFile);
        if(mmf.exists() == true){
            mmf.delete();
            System.out.println("删除原ModelMapper " + modelMapperFile);
        }
        VelocityUtil.generate(modelmapper_vm, modelMapperFile, context);
        System.out.println("生成ModelMapper" + modelMapperFile);
        System.out.println("========== 结束生成ModelMapper ==========");
    }

    private static void createIdao(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成IDao ==========");
        String idaoPath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao";
        String idaoFile = idaoPath + "/" + domainName + "/I" + className + "Dao.java";
        File idaof = new File(idaoFile);
        if(idaof.exists() == true){
            idaof.delete();
            System.out.println("删除原IDao " + idaoFile);
        }
        VelocityUtil.generate(idao_vm, idaoFile, context);
        System.out.println("生成IDao" + idaoFile);
        System.out.println("========== 结束生成IDao ==========");
    }

    private static void createDao(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成Dao ==========");
        String daoPath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao";
        String daoFile = daoPath + "/" + domainName + "/impl/" + className + "Dao.java";
        File daof = new File(daoFile);
        if(daof.exists() == true){
            daof.delete();
            System.out.println("删除原Dao " + daoFile);
        }
        VelocityUtil.generate(dao_vm, daoFile, context);
        System.out.println("生成Dao" + daoFile);
        System.out.println("========== 结束生成Dao ==========");
    }

    private static void createService(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成Service ==========");
        String servicePath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service";
        String serviceFile = servicePath + "/" + domainName + "/" + className + "Service.java";
        File servicef = new File(serviceFile);
        if(servicef.exists() == true){
//            servicef.delete();
//            System.out.println("删除原Service " + serviceFile);
        }
        VelocityUtil.generate(service_vm, serviceFile, context);
        System.out.println("生成Service" + serviceFile);
        System.out.println("========== 结束生成Service ==========");
    }

    private static void createController(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成Control ==========");
        String controlPath = absolutePath + "/" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/control";
        String controlFile = controlPath + "/" + domainName + "/" + className + "Controller.java";
        File controllerf = new File(controlFile);
        if(controllerf.exists() == true){
//            controllerf.delete();
//            System.out.println("删除原Controller " + controlFile);
        }
        VelocityUtil.generate(controller_vm, controlFile, context);
        System.out.println("生成Controller" + controlFile);
        System.out.println("========== 结束生成Controller ==========");
    }

    private static void createDetailJsp(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成DetailJsp ==========");
        String detailJspPath = absolutePath + "/" + "/src/main/webapp/jsp/" + domainName;
        File path = new File(detailJspPath);
        if(path.exists() == false){
            path.mkdir();
        }
        detailJspPath = detailJspPath + "/" + className.toLowerCase();
        path = new File(detailJspPath);
        if(path.exists() == false){
            path.mkdir();
        }
        String detailJspFile = detailJspPath + "/detail.jsp";
        File detailJspf = new File(detailJspFile);
        if(detailJspf.exists() == true){
//            detailJspf.delete();
//            System.out.println("删除原DetailJsp " + detailJspFile);
        }
        VelocityUtil.generate(detail_jsp_vm, detailJspFile, context);
        System.out.println("生成DetailJsp" + detailJspFile);
        System.out.println("========== 结束生成DetailJsp ==========");
    }

    private static void createManageJsp(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成ManagerJsp ==========");
        String manageJspPath = absolutePath + "/" + "/src/main/webapp/jsp/" + domainName;
        File path = new File(manageJspPath);
        if(path.exists() == false){
            path.mkdir();
        }
        manageJspPath = manageJspPath + "/" + className.toLowerCase();
        path = new File(manageJspPath);
        if(path.exists() == false){
            path.mkdir();
        }
        String manageJspFile = manageJspPath + "/manage.jsp";
        File manageJspf = new File(manageJspFile);
        if(manageJspf.exists() == true){
//            manageJspf.delete();
//            System.out.println("删除原ManageJsp " + manageJspFile);
        }
        VelocityUtil.generate(manage_jsp_vm, manageJspFile, context);
        System.out.println("生成ManageJsp" + manageJspFile);
        System.out.println("========== 结束生成ManageJsp ==========");
    }

    private static void createDetailJs(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成DetailJs ==========");
        String detailJsPath = absolutePath + "/" + "/src/main/webapp/jsp/" + domainName;
        File path = new File(detailJsPath);
        if(path.exists() == false){
            path.mkdir();
        }
        detailJsPath = detailJsPath + "/" + className.toLowerCase();
        path = new File(detailJsPath);
        if(path.exists() == false){
            path.mkdir();
        }
        String detailJsFile = detailJsPath + "/detail.js";
        File detailJsf = new File(detailJsFile);
        if(detailJsf.exists() == true){
            detailJsf.delete();
            System.out.println("删除原DetailJs " + detailJsFile);
        }
        VelocityUtil.generate(detail_js_vm, detailJsFile, context);
        System.out.println("生成DetailJs" + detailJsFile);
        System.out.println("========== 结束生成DetailJs ==========");
    }

    private static void createManageJs(String package_name, String absolutePath, String className, String domainName, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成ManagerJs ==========");
        String manageJsPath = absolutePath + "/" + "/src/main/webapp/jsp/" + domainName;
        File path = new File(manageJsPath);
        if(path.exists() == false){
            path.mkdir();
        }
        manageJsPath = manageJsPath + "/" + className.toLowerCase();
        path = new File(manageJsPath);
        if(path.exists() == false){
            path.mkdir();
        }
        String manageJsFile = manageJsPath + "/manage.js";
        File manageJsf = new File(manageJsFile);
        if(manageJsf.exists() == true){
            manageJsf.delete();
            System.out.println("删除原ManageJs " + manageJsFile);
        }
        VelocityUtil.generate(manage_js_vm, manageJsFile, context);
        System.out.println("生成ManageJs" + manageJsFile);
        System.out.println("========== 结束生成ManageJs ==========");
    }

    private static void createSql(String absolutePath, String className, VelocityContext context) throws Exception {
        System.out.println("========== 开始生成Sql ==========");
        String sqlPath = absolutePath + "/doc/sql";
        String sqlFile = sqlPath + "/" + className + ".sql";
        File sqlf = new File(sqlFile);
        if(sqlf.exists() == true){
            sqlf.delete();
            System.out.println("删除原Sql " + sqlFile);
        }
        VelocityUtil.generate(sql_vm, sqlFile, context);
        System.out.println("生成Sql" + sqlFile);
        System.out.println("========== 结束生成Sql ==========");
    }

    // 递归删除非空文件夹
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }

    public static void main(String[] args) {
        try {
            generator(null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
