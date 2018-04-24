package com.se7en.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.se7en.common.util.entity.Folder;
/**
 * 树工具
 * @author dl
 */
public final class FileUtil {
	
	private static Map<String,List<Folder>> folders = new HashMap<String,List<Folder>>();
	
	private FileUtil(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * 枚举磁盘
	 * @return
	 */
	public static List<Folder> enumDisk(){
		final List<Folder> result = new ArrayList<Folder>();
		final File[] disks = File.listRoots();
		for(File disk : disks){
			final String id = Guid.generate();
    		String path = disk.getPath();
			result.add(new Folder(id,"0",new String(path.substring(0, 1)),path));
		}
		return result;
	}
	
	/**
	 * 枚举根目录下所有非隐藏磁盘文件夹
	 */
	public static List<Folder> enumAllFolders(){
		List<Folder> result = new ArrayList<Folder>();
		File[] disks = File.listRoots();
		for(File disk : disks){
			String id = Guid.generate();
			result.add(new Folder(id,"0",new String(disk.getPath().substring(0, 1)),disk.getPath()));
    		enumFolders(disk.getPath(),id,result);
		}
		return result;
	}
	
	/**
	 * 只枚举下一级文件夹
	 * @param path
	 * @param pid guid
	 * @return
	 */
	public static List<Folder> enumNextFolders(final String path,final String pid){
		final List<Folder> result = new ArrayList<Folder>();
		final File file = new File(path);
		final File[] childFiles = file.listFiles();
		if(ArrayUtils.isEmpty(childFiles)){
			return result;
		}
		for (File child : childFiles) {
			if(child.isHidden()){
	    		continue;
	    	}else if (child.isDirectory()) {
	    		final String id = Guid.generate();
	    		result.add(new Folder(id,pid,child.getName(),child.getPath()));
	        }else if(child.isFile()){
	            continue;
	        }
		}
		return result;
	}
	
	/**
	 * 枚举指定路径下所有非隐藏文件夹(路径)
	 * @param path
	 * @param pid
	 * @return
	 */
	public static List<Folder> enumFolders(final String path){
		List<Folder> result = Collections.emptyList();
		if(folders.get(path) != null){
			result = folders.get(path);
		}else{
			result = new ArrayList<Folder>();
			String pid = Guid.generate();
			enumFolders(path,pid,result);
			folders.put(path, result);
		}
		return result;
	}
	
	/**
	 * Enum next files.
	 * 枚举下一级非隐藏文件列表
	 * @param path the path
	 * @return the list
	 */
	public static List<com.se7en.common.util.entity.File> enumNextFiles(final String path){
		final List<com.se7en.common.util.entity.File> result = new ArrayList<com.se7en.common.util.entity.File>();
		final File folder = new File(path);
		final File[] childFiles = folder.listFiles();
		if(ArrayUtils.isEmpty(childFiles)){
			return result;
		}
		for (File child : childFiles) {
			if(child.isHidden()){
	    		continue;
	    	}else if (child.isDirectory()) {
	    		continue;
	        }else if(child.isFile()){
	        	final com.se7en.common.util.entity.File file = new com.se7en.common.util.entity.File();
	        	final String fileName = child.getName();
	        	file.setExtName(FilenameUtils.getExtension(fileName));
	        	file.setName(FilenameUtils.getBaseName(fileName));
	        	file.setPath(file.getPath());
	        	result.add(file);
	        }
		}
		Collections.sort(result);	//根据文件名排序
		return result;
	}
	
	/**
	 * 枚举指定路径下所有非隐藏文件夹(路径)
	 * @param path
	 * @param pid
	 * @return
	 */
	public static void enumFolders(final String path,final String pid,final List<Folder> list){
		if(StringUtil.isBlank(path)){
			return;
		}
		final File file = new File(path);
		final File[] childFiles = file.listFiles();
	    if (childFiles == null) {
	        return;
	    }
	    for (File child : childFiles) {
	    	if(child.isHidden()){
	    		continue;
	    	}else if (child.isDirectory()) {
	    		final String id = Guid.generate();
	    		list.add(new Folder(id,pid,child.getName(),child.getPath()));
	    		enumFolders(child.getPath(),id,list);
	        }else if(child.isFile()){
	            continue;
	        }
	    }
	}
	
	/**
	 * 根据某一路径递归向下查询指定文件名的文件
	 * @param fileName
	 * @param path
	 * @return
	 */
	public static File findFile(final String fileName,final String path){
		File ret = null;
		
		final List<File> nextFolders = new LinkedList<File>(); 
		final File rootFolder = new File(path);
		for(final File file : rootFolder.listFiles()){
			if(file.isHidden()){
				continue;
			}else if(file.isFile()){
				if(file.getName().equals(fileName)){
					ret = file;
					return ret;
				}
			}else if(file.isDirectory()){
				nextFolders.add(file);
			}
		}
		for (final File file : nextFolders) {
			final String nextPath = file.getPath();
			ret = findFile(fileName,nextPath);
			if(ret != null){
				break;
			}
		}
		
		return ret;
	}
}
