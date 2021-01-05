import java.io.File;
import java.io.PrintWriter;

public class HEAD {
    public static String savingPath = KeyValue.savingPath;

    //HEAD构造方法，传入一个branch
    //判断HEAD文件是否存在，若不存在，创建以HEAD为名，分支名为内容的文件，若存在,更新HEAD。
    HEAD(String branch) throws Exception{
        //不存在则创造HEAD，放入commit哈希
        File file = new File(savingPath + "HEAD");
        if (!HEADExists()){
            file.createNewFile();
        }
        PrintWriter output = new PrintWriter(file);
        output.print(branch);
        output.close();
        
        
    };
    //返回目前的HEAD，若HEAD不存在则返回null
    public static String getCurBranch() throws Exception{
        if(!HEADExists()){
            return "null";
        }
        else{
            return Blob.getBlobFileValue("HEAD");
        }
    }


    //判断HEAD文件是否存在，存在则返回true，不存在返回false
    public static boolean HEADExists(){
        File file = new File(savingPath + "HEAD");
        if (!file.exists()){
            return false;
        }
        else{return true;}
    }
}
