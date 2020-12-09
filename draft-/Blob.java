import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Scanner;
import java.io.FileOutputStream;
public class Blob {
    String key;
    String value;
    String type;

    //无参构造方法，给一个文件地址构造一个以key为，内容为value的新文件
    Blob() {
    }

     //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    Blob(String path) throws Exception{
        key = setFileKeyValue(path);
        value = getFileValue(key);
        type = "Blob";
    }


    //为Tree建立blob对象
    Blob(StringBuffer DirectoryInfo) throws Exception{
        String content = value.toString();
        value = content ;
        key = setStringKeyValue(value);
        
        type = "Tree";
    }

    //设置目标存储文件的
    //public static String setKey() {
    //    return "";
    //}

    //public static String setValue() {
    //    return "";
    //}

    //给定字符串value，向存储中添加对应的key-value
    //给tree建立key-value文件所用的方法
    public static String setStringKeyValue(String value) throws Exception {
        //创建以value的hash为名的文件
        String key = StringHash(value);
        File file = new File("E:\\Blobs\\" + key);
        file.createNewFile();

        //写入value
        PrintWriter output = new PrintWriter(file);
        output.print(value);
        output.close();
        return key;
    }

    //给定文件路径，向存储中添加对应的key-value，创建相应文件，
    public  String setFileKeyValue(String path) throws Exception {
        //创建以文件的hash为名的文件        
        String key = fileHash(path);
        //System.out.print(key);
        File file = new File("E:\\Blobs\\" + key);
        file.createNewFile();

        //写入value        
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(file);
        //先定义一个字节缓冲区，减少I/O次数，提高读写效率
        byte[] buffer=new byte[1024];
        int size=0;
        while((size=fis.read(buffer))!=-1){
            fos.write(buffer, 0, size);           
        }
        fis.close();
        fos.close(); 
        
        return key;
    }

    //给定文件key，查找得到对应的value值
    public static String getFileValue(String key)throws Exception{
        File file = new File("E:\\Blobs\\" + key);
        if(!file.exists()){
            System.out.println("File with this key doesnt exists");
            return null;
        }else{
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            while (scan.hasNext()){
                stringbuffer.append(scan.next()).append("\n");
            }
            scan.close();
            return stringbuffer.toString();
        }        
    }

    //给定字符串key，查找得到对应的value值
    public String getStringValue(String key) throws Exception{
        File file = new File("E:\\Blobs\\" + key);
        if(!file.exists()){
            System.out.println("File with this key doesnt exists");
            return null;
        }else{
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            while (scan.hasNext()){
                stringbuffer.append(scan.next()).append("\n");
            }
            scan.close();
            return stringbuffer.toString();
        }        
    }

   // public static String getKey(String Value) {
    // }

    public String getType() {
        return "Blob";
    }


    //工具方法：非直接调用的类

    //valueHash 给定一个字符串，算出它的hash值
    public static String StringHash(String value) throws Exception{        
        //字符串value到 缓冲buffer
        byte[] buffer = value.getBytes();
        //使用SHA1哈希/摘要算法
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(buffer);
        //哈希值到 sha1
        byte[] sha1 = complete.digest();
        
        //打印哈希值
        String result = "";
        for (int i = 0; i < sha1.length; i++){
            result += Integer.toString(sha1[i]&0xFF,16);    //将其SHA1Checksum依次取出到result并加密转化为哈希值储存在变量result里
        }
        //System.out.println("Hash Code of the Value is: " + result); //打印文件哈希值
        return result;
    }

    //计算一个给定文件，文件内容的哈希值，用于命名目标储存文件(也就是存储中的key)
    private  String fileHash(String path){
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

    private  byte[] SHA1Checksum(InputStream is) throws Exception{
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
//测试方法
public static void main(String[] args) throws Exception{
        
/*         Blob blob = new Blob();
        System.out.print(blob.getStringValue("57b22291db30c440217479b23801561cace9ef"));
 */        
        
    }
}

