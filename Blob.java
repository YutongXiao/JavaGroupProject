import java.io.*;
import java.security.MessageDigest;
import java.util.Scanner;

/*20201207 Java小组项目任务一：
Blob类，其中含有：
数据域：
1、新建文件的哈希值key
2、新建文件的存储路径goalPath

方法：
1、Blob()--无参构造函数，内容空
2、Blob(path,goalPath)--有参构造函数，传入原文件路径变量path，和新文件存储目标路径goalPath
   函数作用：构造一个哈希为key内容为value的新文件，并将参数goalPath赋值给数据变量goalPath（有的方法需要用）
3、setFileKeyValue(path)--传入一个文件，创建一个对应key-value新文件
4、getStringValue(key)--给定key，查找对应的value值
5、getType()--返回“blob”，任务二判断文件类型用
6、fileHash(path)--计算一个给定文件的哈希值
    SHA1Checksum--计算fileHash值所用算法，工具方法
 */

public class Blob {
    //新建文件的名字，同原文件内容的哈希值，goalPath为新建文件的目标文件夹
    public static String key;
    public static String value;
    private static String goalPath;

    //无参构造方法，空
    Blob() {
    }

    //有参构造方法，给一个文件路径构造一个以文件内容的哈希值为key，内容本身为value的新文件
    //path变量为
    Blob(String path, String goalPath) throws IOException {
        key = setFileKeyValue(path);
        value = getStringValue(key);
        this.goalPath = goalPath;
    }

    //给定一个文件路径，向存储中添加对应的key-value，创建相应新文件
    public static String setFileKeyValue(String path) throws IOException {
        //创建以文件的hash为名的文件，并存储到目标地址goalPath中
        String key = fileHash(path);
        File file = new File(goalPath + key);
        file.createNewFile();

        //将原文件路径创建一个FileInputStream, 并将新建文件file作为FileOutputStream变量fos的输出目标
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(file);
        //先定义一个字节缓冲区，减少I/O次数，提高读写效率
        byte[] buffer=new byte[1024];
        int size=0;
        //从输入流fis中依次读取所有字节到buffer中，直到流的最后（size=-1）
        while((size=fis.read(buffer))!=-1){
            //将buffer中所有字节写入输出流
            fos.write(buffer, 0, size);           
        }
        //写入新文件结束，关闭输入输出流
        fis.close();
        fos.close(); 
        
        return key;
    }

    //给定字符串key，查找对应的value值
    public static String getStringValue(String key) throws FileNotFoundException {
        //新建一个已创造的key-value文件的file对象
        File file = new File(goalPath + key);

        //判断file是否存在，若不存在返回空
        if(!file.exists()){
            System.out.println("File with this key doesn't exists");
            return null;
        }
        //若存在将内容append到一个StringBuffer对象中并返回其变量stringbuffer
        else{
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            while (scan.hasNext()){
                stringbuffer.append(scan.next()).append(" ");
            }
            scan.close();
            return stringbuffer.toString();
        }        
    }

    //public static String getKey(String Value) {
    // }

    public static String getType() {
        return "Blob";
    }

    //计算一个传入文件的哈希值，文件内容的哈希值用于命名目标储存文件(也就是存储中的key)
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

    //工具方法：非直接调用，用于SHA1算法Checksum的计算
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

