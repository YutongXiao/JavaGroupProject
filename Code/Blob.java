import java.io.File;
import java.util.Scanner;

public class Blob {
    String key; //Blob的文件名key
    String value; //Blob的文件内容value

    //无参构造方法，空
    Blob() {
    }

     //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    Blob(String path) throws Exception {
        key = KeyValue.setFileKeyValue(path);
        value = getBlobFileValue(key);
    }
    
    //给定文件key，查找得到对应的value值
    public static String getBlobFileValue(String key)throws Exception{
        File file = new File(KeyValue.savingPath + key);
        if(!file.exists()){
            System.out.println("File doesn't exists");
            return null;
        }else{
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            while (scan.hasNext()){
                stringbuffer.append(scan.next()).append(" ");
            }
            scan.close();
            return stringbuffer.toString().trim();
        }        
    }
}

