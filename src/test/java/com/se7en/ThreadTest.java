package com.se7en;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

	public Map<String,Map<String,Object>> container = new HashMap<String,Map<String,Object>>();
	
	private void init(){
		container.clear();
		for(int i = 0;i < 100;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			for(int j = 0;j < 10;j++){
				map.put(String.valueOf(j), i + "" + j);
			}
			container.put(String.valueOf(i), map);
		}
	}
	
	public void test(){
		init();
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future> rets = new ArrayList<Future>();
		for(int i = 0;i < 100;i++){
//			es.execute(new Innder(i));
			rets.add(es.submit(new Innder1(i)));
		}
		es.shutdown();
		try {
			for (Future future : rets) {
				System.out.println(future.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread(){
			public void run() {
				ds.f();
			};
		}.start();
		ds.g();
	}
	
	class Innder implements Runnable{
		private int index;
		
		public Innder(int index){
			this.index = index;
		}
		
		@Override
		public void run() {
			Object obj = container.get(String.valueOf(index));
//			System.out.println(obj == null);
			System.out.println(obj);
		}
	}
	
	class Innder1 implements Callable{
		private int index;
		
		public Innder1(int index){
			this.index = index;
		}

		@Override
		public Object call() throws Exception {
			Object obj = container.get(String.valueOf(index));
//			System.out.println(obj == null);
			System.out.println(obj);
			return index;
		}
		
	}
	
}

class DualSynch{
	private Object syncObject = new Object();
	public synchronized void f(){
		for(int i = 0;i < 5;i++){
			System.out.println("f()");
			Thread.yield();
		}
	}
	public void g(){
		synchronized (syncObject) {
			for(int i = 0;i < 5;i++){
				System.out.println("g()");
				Thread.yield();
			}
		}
	}
}
