import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Blob {
    //无参构造方法，给一个文件地址构造一个以key为，内容为value的新文件
    Blob() {
    }

    //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    Blob(String path) {
    }

    //设置目标存储文件的
    //public static String setKey() {
    //    return "";
    //}

    //public static String setValue() {
    //    return "";
    //}

    //给定value，向存储中添加对应的key-value
    public static String setKeyAndValue(String value) {
        return "";
    }

    //给定key，查找得到对应的value值
    public static String getValue(String key) {
    }


   // public static String getKey(String Value) {
    // }

    public static String getType() {
        return "Blob";
    }


    //工具方法：非直接调用的类
    //计算一个给定文件的哈希值，用于命名目标储存文件(也就是存储中的key)
    private static String fileHash(String path){
        try{
            File file = new File(path);    //打开指定文件
            FileInputStream is = new FileInputStream(file);
            byte[] sha1 = SHA1Checksum(is);     //计算指定文件的SHA1Checksum
            //打印哈希值
            String result = "";
            for (int i = 0; i < sha1.length; i++){
                result += Integer.toString(sha1[i]&0xFF,16);    //将其SHA1Checksum依次取出到result并加密转化为哈希值储存在变量result里
            }
            System.out.println(path + " " + result); //打印文件哈希值
            return result;
        }

        catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    private static byte[] SHA1Checksum(InputStream is) throws Exception{
        //用于计算hash值的文件缓冲区
        byte[] buffer = new byte[1024];
        //使用SHA1哈希/摘要算法
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        int numRead = 0;
        do{
            //读取numRead字节到buffer中
            numRead = is.read(buffer);
            if (numRead > 0){
                //根据buffer[0:numRead]的内容更新hash值
                complete.update(buffer,0, numRead);
            }
            //文件已读完，退出循环
        }
        while (numRead != -1);
        //关闭输入流
        is.close();
        //返回SHA1哈希值
        return complete.digest();
    }
}

