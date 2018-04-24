package com.se7en.common.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The Class ThreadUtil. 线程工具类
 */
public final class ThreadUtil {

	private ThreadUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	/**
	 * Jvm all runngin thread. 获取jvm当前运行线程
	 * 
	 * @return the thread[]
	 */
	public static Thread[] jvmAllRunnginThread() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;

		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		// 激活的线程数加倍
		final int estimatedSize = topGroup.activeCount() * 2;
		final Thread[] slackList = new Thread[estimatedSize];
		// 获取根线程组的所有线程
		final int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		return list;
	}

	/**
	 * Format threads info. 线程信息格式化
	 * 
	 * @param threads
	 *            the threads
	 * @return the jSON object
	 */
	public static Map<String,Object> formatThreadsInfo(Thread[] threads) {
		Map<String,Object> result = new HashMap<>();
		List<Map<String,Object>> ary = new LinkedList<>();
		for (Thread thread : threads) {
			Map<String,Object> thdJson = new HashMap<>();
			String name = thread.getName();
			thdJson.put("id", thread.getId());
			thdJson.put(
					"title",
					name.length() < 20 ? name : new String(name
							.substring(0, 20)));
			thdJson.put("name", thread.getName());
			thdJson.put("priority", thread.getPriority());
			thdJson.put("threadGroup", thread.getThreadGroup().getName());
			thdJson.put("isAlive", thread.isAlive());
			thdJson.put("isDaemon", thread.isDaemon());
			thdJson.put("isInterrupted", thread.isInterrupted());
			ary.add(thdJson);
		}
		result.put("thread", ary);
		return result;
	}

	/**
	 * To string.
	 * 线程字符串
	 * @param thread the thread
	 * @return the string
	 */
	public static String toString(Thread thread) {
		ToStringBuilder builder = new ToStringBuilder(thread);
		return builder.append("id: ", thread.getId())
				.append("name: ", thread.getName()).toString();
	}
}
