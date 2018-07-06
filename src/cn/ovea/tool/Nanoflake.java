package cn.ovea.tool;

import cn.ovea.tool.exception.NanoflakeException;

/**
 * @author QiangweiLuo
 * 用于产生分布式ID的一个类
 */
public class Nanoflake {
    // 设定一个初始纳秒时间戳
    private long initialTime = 1878547820027449L;
    // 设定的机器ID
    private long workerID;
    // 最大可用的机器ID
    private long maxWorkerID = 2048L - 1L;
    // 机器ID偏移bit
    private long workerMoveBit = 52L;

    /**
     *
     * @param workerID
     * @throws NanoflakeException
     */
    public Nanoflake(long workerID) throws NanoflakeException {
        if(workerID < 0 || workerID > maxWorkerID) throw new NanoflakeException("workerID too big or too low");
        this.workerID = workerID;
    }

    /**
     *
     * @return
     */
    private synchronized long getNanoTime(){
        return System.nanoTime();
    }

    /**
     *
     * @return
     */
    public long getNanoflakeNum(){
        long temp = 0;
        temp |= getNanoTime() - initialTime;
        workerID = workerID << workerMoveBit;
        temp |= workerID;
        return temp;
    }

    /**
     *
     * @return
     */
    public String getNanoflake(){
        return  Long.toHexString(getNanoflakeNum());
    }
}
