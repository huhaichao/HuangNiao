package com.sy.huangniao.common.Util;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	// 最小长度
    protected static final int MIN_LENGTH = 18;
    // 顺序号最大数
    protected long seqMaxVal = 9990;
    // 顺序号部分长度
    protected int seqValLen = 4;
    // 生产的随机码最大数
    protected int randomMaxVal = 9999;
    protected int randomValLen = 4;
    // 用于生成随机数的种子，避免在多服务器下生成重复的号。
    // 用于生成随机数的种子，随机2位id
    static protected int APPSERVERID =0;
    // 12位日期格式
    protected SimpleDateFormat dateTimeFormater = new SimpleDateFormat("yyMMddHHmmss");
    // 顺序号计数器
    protected static AtomicLong globalCount = new AtomicLong(APPSERVERID);
    private IdGenerator(){};
    private static  IdGenerator  idGenerator = new IdGenerator();
    /**
     * 单例模式
     * @return
     */
    public static IdGenerator  getInstance(){
    	return idGenerator;
    }
    
	//id生成器
	public String generate() {

        long seq = globalCount.incrementAndGet();
        if (seq >= seqMaxVal) {
            globalCount.set(APPSERVERID);
            seq = globalCount.incrementAndGet();
        }
        String date = null;

        date = dateTimeFormater.format(System.currentTimeMillis());// 12位日期
        String randStr = "";
        if (randomMaxVal > 0) {
            Random r = new Random();
            int rInt = r.nextInt(this.randomMaxVal);
            randStr = String.format("%0" + this.randomValLen + "d", rInt);// 随机数
        }

        String seqStr = String.format("%0" + this.seqValLen + "d", seq);// 顺序号

        return date + seqStr+randStr;
    }


    public  static  void main(String[] args){
        System.out.println(IdGenerator.getInstance().generate());
        System.out.println(IdGenerator.getInstance().generate());
        System.out.println(IdGenerator.getInstance().generate());
        System.out.println(IdGenerator.getInstance().generate());
        System.out.println(IdGenerator.getInstance().generate());
    }

}
