package team.work.utils.kit;

import team.work.utils.convert.S;
import org.apache.commons.lang3.StringUtils;
import team.work.utils.convert.S;

import java.util.ArrayList;
import java.util.List;


public class IdKit {
    private Long workerId;
    private Long datacenterId;
    private Long sequence = 0L;
    private Long twepoch = 459407593L;
    private Long workerIdBits = 5L; //节点ID长度
    private Long datacenterIdBits = 5L; //数据中心ID长度
    private Long maxWorkerId = -1L ^ (-1L << workerIdBits); //最大支持机器节点数0~31，一共32个
    private Long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //最大支持数据中心节点数0~31，一共32个
    private Long sequenceBits = 12L; //序列号12位
    private Long workerIdShift = sequenceBits; //机器节点左移12位
    private Long datacenterIdShift = sequenceBits + workerIdBits; //数据中心节点左移17位
    private Long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; //时间毫秒数左移22位
    private Long sequenceMask = -1L ^ (-1L << sequenceBits); //4095
    private Long lastTimestamp = -1L;

    private static class IdGenHolder {
        private static final IdKit instance = new IdKit();
    }

    public static IdKit getInstance(){
        return IdGenHolder.instance;
    }

    public IdKit() {
        this(0L, 0L);
    }

    public IdKit(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen(); // 获取当前毫秒数
        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp); //自旋等待到下一毫秒
            }
        } else {
            sequence = 0L; //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                                               datacenterId   workerId    sequence
        long id = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return id + 1000000000000000000L;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //   /v1/user           /v2/user

    /**
     * 常规id生成
     * */
    public static String getId(){
        String id = getInstance().nextId()+"";
        String ym = TimeKit.formatNow("yyMM");
        id = id.substring(4,19);
        return formatId(ym+id);
    }
    /**
     * 根据业务id生成id
     * */
    public static String getId(int businessId){
        String id = getInstance().nextId()+"";
        id = id.substring(3,19);
        return formatId(businessId+id);
    }
    public static String getId(Class cls){
        return getId(AnnoKit.getInstance().getTableId(cls));
    }
//    public static void main(String[] args){
//        String id = getInstance().nextId()+"";
//        String businessId = "123";
//        id = String.format("%s%s",businessId,id.substring(3,19));
//
//        System.out.println(id);
//        System.out.println(formatId(id));
//        System.out.println(formatId(id));
//        System.out.println(formatId(id));
//    }
    public static String formatId(String id){
        char[] ym = S.randomNum().toCharArray();
        char[] ids = id.toCharArray();
        List<String> item = new ArrayList<String>();
        int index = 0;
        for(int i=0;i<ids.length;i++){
            if(i>0){
                if(i%3 == 0){
                    ids[i] = ym[index];
                    index += 1;
                }
            }
            item.add(String.valueOf(ids[i]));
        }
        return StringUtils.join(item,"");
    }
}
