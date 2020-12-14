import java.io.File;
import java.io.PrintWriter;

public class HEAD {
    public static String savingPath = KeyValue.savingPath;

    //HEAD构造方法，传入一个commit的哈希值
    //判断HEAD文件是否存在，若不存在，创建以HEAD为名，Commit哈希为内容的文件，若存在,更新HEAD。
    HEAD(String commit) throws Exception{
        //不存在则创造HEAD，放入commit哈希
        File file = new File(savingPath + "HEAD");
        if (!HEADExists()){
            file.createNewFile();
            PrintWriter output = new PrintWriter(file);
            output.print(commit);
            output.close();
        }
        //存在则打开HEAD，更新commit哈希
        else{
            PrintWriter output = new PrintWriter(file);
            output.print(commit);
            output.close();
        }
    };
    //返回目前的Commit，若HEAD不存在则返回null
    public static String getCurCommit() throws Exception{
        if(!HEADExists()){
            return "null";
        }
        else{
            return KeyValue.getBlobFileValue("HEAD");
        }
    }

    //通过查找目前HEAD中的value，返回目前Commit指向的根目录哈希，此方法被KeyValue类的compareRoots方法调用
    public static String getCurRoot() throws Exception{
        //HEAD不存在返回字符串"null"，以参与字符串类型对比
        if(!HEADExists()){
            return "null";
        }
        //若HEAD存在则提取出HEAD中当前commit所建key-value文件中的根目录哈希（根目录信息部分如"Tree ad910b66c06991ddcfd2ce3f294eb9d130f5add8"）
        //并返回其哈希部分的值
        else{
            String value = KeyValue.getCommitFileValue(getCurCommit());
            return value.substring(value.indexOf("Tree") + 5, value.indexOf("Tree") + 45);
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
