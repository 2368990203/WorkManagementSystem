package team.work.utils.constant;


public class Global {
    /**
     * 用户信息基础字段
     * trueName\niceName\mobile\mail\qq\weChat\idCard\birthday\header\address\
     * */
    public static String USER_INFO[] = new String[]{
            "trueName","niceName","mobile",
            "mail","qq","weChat",
            "idCard","birthday","header",
            "address"
    };

    /**
     * 阿里云OSS基础字段
     * AccessKeyID\AccessKeySecret\RoleArn\TokenExpireTime\Policy
     * */
    public static String OSS[] = new String[]{
            "AccessKeyID","AccessKeySecret","RoleArn",
            "TokenExpireTime","Policy","Bucket",
            "Host","Region","Endpoint"
    };

    /**
     * 全局配置
     * websiteName\websiteTitle\copyright
     * icp\seoKeywords\seoDescription
     * statisticalCode\fileFolder\fileCapacity
     * fileMax\fileLen\basePath
     * */
    public static String CONFIG[] = new String[]{
            "websiteName","websiteTitle","copyright",
            "icp","seoKeywords","seoDescription",
            "statisticalCode","fileFolder","fileCapacity",
            "fileMax","fileLen","basePath","readPath","linuxPath",
            "platformContacts","platformEmail","platformQQ",
            "platformQRCode","platformRemark","platformAddress",
            "platformIntroduce","platformScope","platformAdvantage",
            "jpushKey","jpushSecret","smsId","smsKey","h5-url","h5-url-base",
            "t-am-s1","t-am-s2","t-am-s3","t-am-s4",
            "t-pm-s1","t-pm-s2","t-pm-s3","t-pm-s4",
            "s-am-s1","s-am-s2","s-am-s3","s-am-s4",
            "s-pm-s1","s-pm-s2","s-pm-s3","s-pm-s4"
    };

}


