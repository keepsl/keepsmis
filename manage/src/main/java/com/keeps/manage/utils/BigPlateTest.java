package com.keeps.manage.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** 
 * <p>Title: BigPlateTest.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class BigPlateTest {
	/** 装鸡蛋的盘子，大小为5 */  
    private BlockingQueue<Object> eggs = new ArrayBlockingQueue<Object>(5);  
      
    /** 放鸡蛋 */  
    public void putEgg(Object egg) {  
        try {  
            eggs.put(egg);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
        // 下面输出有时不准确，因为与put操作不是一个原子操作  
        System.out.println("放入鸡蛋");  
    }  
      
    /** 取鸡蛋 */  
    public Object getEgg() {  
        Object egg = null;  
        try {  
            egg = eggs.take();// 从盘子开始取一个鸡蛋，如果盘子空了，当前线程阻塞  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
        // 下面输出有时不准确，因为与take操作不是一个原子操作  
        System.out.println("拿到鸡蛋");  
        return egg;  
    }  
      
    /** 放鸡蛋线程 */  
    static class AddThread extends Thread {  
        private BigPlateTest plate;  
        private Object egg = new Object();  
  
        public AddThread(BigPlateTest plate) {  
            this.plate = plate;  
        }  
  
        public void run() {  
            plate.putEgg(egg);  
        }  
    }  
  
    /** 取鸡蛋线程 */  
    static class GetThread extends Thread {  
        private BigPlateTest plate;  
  
        public GetThread(BigPlateTest plate) {  
            this.plate = plate;  
        }  
  
        public void run() {  
            plate.getEgg();  
        }  
    }  
      
    public static void main(String[] args) {  
    	BigPlateTest plate = new BigPlateTest();  
        // 先启动10个放鸡蛋线程  
        for(int i = 0; i < 10; i++) {  
            new Thread(new AddThread(plate)).start();  
        }  
        // 再启动10个取鸡蛋线程  
        for(int i = 0; i < 10; i++) {  
            new Thread(new GetThread(plate)).start();  
        }  
    }
}
