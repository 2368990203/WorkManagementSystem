package team.work.utils.tool;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class LDAPUtil {
    private static final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String AUTHENTICATION = "simple";
    private static LdapContext ctx = null;
    private static final Control[] connCtls = null;

    //学校LDAP认证服务器
    private static final String URL = "ldap://10.240.1.105:389/";//服务器地址
    private static final String BASEDN = "dc=gxun,dc=edu,dc=cn";  // 根
    private static final String root = "cn=Admin,dc=gxun,dc=edu,dc=cn"; // 管理员
    private static final String pwd = "GxunNicLDAP";// 管理员密码

    //启航LDAP认证服务器
    /*
    private static final String URL = "ldap://ldap.gxun.club:389/";//服务器地址
    private static final String BASEDN = "dc=gxun,dc=club";  // 根
    private static final String root = "cn=qihsoft,dc=gxun,dc=club"; // 管理员
    private static final String pwd = "QihsoftLDAP";// 管理员密码
    */


    //连接认证
    private static void connect() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
        env.put(Context.PROVIDER_URL, URL + BASEDN);
        env.put(Context.SECURITY_AUTHENTICATION, AUTHENTICATION);
        env.put(Context.SECURITY_PRINCIPAL, root);
        env.put(Context.SECURITY_CREDENTIALS, pwd);

        try {
            ctx = new InitialLdapContext(env, connCtls);
            System.out.println("LDAP服务器连接成功");

        } catch (javax.naming.AuthenticationException e) {
            System.out.println("LDAP服务器连接失败：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("LDAP服务器连接出错：");
            e.printStackTrace();
        }

    }


    //查询指定用户
    public static void findLdap() {

        try {
            if (ctx != null) {
                System.out.println("查询用户liwen信息");

                LdapContext obj = (LdapContext) ctx.lookup("cn=liwen,ou=soft");
                Attributes answer = obj.getAttributes("");
                String sn = String.valueOf(answer.get("sn").getAll().next());
                System.out.println("学号: " + sn);
                System.out.println("输出liwen详细信息开始");
                for (NamingEnumeration ae = answer.getAll(); ae.hasMore(); ) {
                    Attribute attr = (Attribute) ae.next();
                    System.out.println("attribute: " + attr.getID());
                    for (NamingEnumeration e = attr.getAll(); e.hasMore();
                         System.out.println("value: " + e.next()))
                        ;

                }
                System.out.println("输出liwen详细信息结束");
            }

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    //查询所有用户
    public static void readLdap() {

        Map<String, String> map = new HashMap<String, String>();

        try {
            if (ctx != null) {
                System.out.println("遍历soft分组用户开始");

                NamingEnumeration<NameClassPair> list = ctx.list("ou=soft");
                while (list.hasMore()) {
                    NameClassPair ncp = list.next();
                    String cn = ncp.getName();
                    if (cn.indexOf("=") != -1) {
                        int index = cn.indexOf("=");
                        cn = cn.substring(index + 1, cn.length());
                        //将用户基本信息存入Map
                        map.put(cn, ncp.getNameInNamespace());
                    }
                }
            }

            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println("Key:" + entry.getKey());
                System.out.println("Value:" + entry.getValue());
            }
            System.out.println("遍历soft分组用户结束");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

    }

    //关闭认证服务器连接
    public static void closeLdap() {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        connect();
        findLdap();
        readLdap();
        closeLdap();
    }


}
