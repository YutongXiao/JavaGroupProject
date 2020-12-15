import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Scanner;
import java.io.FileOutputStream;
public class KeyValue {
    String key;
    String value;

    //无参构造方法，给一个文件地址构造一个以key为，内容为value的新文件
    KeyValue() {
    }

    //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    KeyValue(String path) throws Exception{
        key = setFileKeyValue(path);
        value = getFileValue(key);

    }


/*     //为Tree建立key Value对象
    KeyValue(StringBuffer DirectoryInfo) throws Exception{
        String content = value.toString();
        value = content ;
        key = setStringKeyValue(value);
        
        
    } */

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
        File file = new File("E:\\KeyValues\\" + key);
        file.createNewFile();

        //写入value
        PrintWriter output = new PrintWriter(file);
        output.print(value);
        output.close();
        return key;
    }

    //给定文件路径，向存储中添加对应的key-value，创建相应文件，
    public static String setFileKeyValue(String path) throws Exception {
        //创建以文件的hash为名的文件        
        String key = fileHash(path);
        //System.out.print(key);
        File file = new File("E:\\KeyValues\\" + key);
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

    //dfs方法为每个文件、文件夹建立 key-value，
    public static String setTreeKeyValue(String path) throws Exception{
        String DirectoryString = "";
        // 该字符串中存储文件夹下 files 结构
            /*  Tree  8826db7b1c619caeb18d355f853f84d6161dbcd0	1-大数据与人工智能》讲座听后感
                Blob  5e86e4d9f490c3fe949fbab9a77f183ff1a6f	1.jpg
                Blob  17594c5ae3133196f9bf6d6bb63debf93c725	10月10日《素质教育与前沿技术课程》讲座现场（3303教室）报名.xlsx
                Blob  fa96ebc2de647999e21b1f74a0351611ade87	课程考核.txt */
        File dir = new File(path);
        File[] fs = dir.listFiles();
        for(int i = 0; i < fs.length; i++){
            if(fs[i].isFile()){
                KeyValue keyvalue = new KeyValue(fs[i].getPath());
                DirectoryString += "Blob" + "  " + keyvalue.key +"\t"+ fs[i].getName()+ "\n";
            }
            if(fs[i].isDirectory()){
                String thisDirKey = setTreeKeyValue(fs[i].getPath());
                DirectoryString += "Tree" + "  " + thisDirKey + "\t"+fs[i].getName()+ "\n" ;
            }
        }
        return KeyValue.setStringKeyValue(DirectoryString);
    }
    //
    //
    //
    //给定文件key，查找得到对应的value值
    public static String getFileValue(String key)throws Exception{
        File file = new File("E:\\KeyValues\\" + key);
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
    public static String getStringValue(String key) throws Exception{
        File file = new File("E:\\KeyValues\\" + key);
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




    //工具方法：非直接调用的类

    private static String fileHash(String path){
        try{
            File file = new File(path);    //打开指定文件
            FileInputStream is = new FileInputStream(file);
            byte[] sha1 = SHA1Checksum(is);     //计算指定文件的SHA1Checksum
            //打印哈希值            
            //System.out.println(path + " " + result); //打印文件哈希值
            return printhash (sha1);
        }

        catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    //valueHash 给定一个字符串，算出它的hash值
    public static String StringHash(String value) throws Exception{
        //字符串value到 缓冲buffer
        byte[] buffer = value.getBytes();
        //使用SHA1哈希/摘要算法
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(buffer);
        //哈希值到 sha1
        byte[] sha1 = complete.digest();
        //System.out.println("Hash Code of the Value is: " + result); //打印文件哈希值
        return printhash (sha1);
    }
    private static String printhash (byte[] sha1){
        //打印哈希值
        String result = "";
        int n = sha1.length;
        for (int i = 0; i < n; i++){
            String append = Integer.toString(sha1[i] & 0xFF, 16);
            if (append.length() < 2){
                result += "0" + append;
            }else{
                result += append;
            }
        }
        return result;
    }
    //计算一个给定文件，文件内容的哈希值，用于命名目标储存文件(也就是存储中的key)


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
    //测试方法
    public static void main(String[] args) throws Exception{
        
/*         Blob blob = new Blob();
        System.out.print(blob.getStringValue("57b22291db30c440217479b23801561cace9ef"));
 */

    }
}

