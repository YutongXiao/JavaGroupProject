import java.io.File;
import java.util.Scanner;

//Commit类：包含Commit对象的构造函数，函数内部初始化Commit新建文件的key, value, 根目录root和上一个commit parent
public class Commit {
    String key; //Commit对象生成文件的文件名
    String value; //Commit对象生成文件的内容
    String root; //Commit对应的根目录
    String parent; //Commit对象的前一个commit对象
    


    Commit(){}; //空构造函数

    //确定传入路径根目录和现有commit根目录哈希不同后：
    Commit(String path) throws Exception {    //构造函数，生成新Commit，传入路径，构造key, value, parent
        key = setCommitKeyValue(path); //调用KeyValue类生成Commit新文件的方法
        value = getCommitFileValue(key); //通过key找到Commit生成文件的value，包括子目录哈希和parent哈希
        root = value.substring(value.indexOf("Tree") + 5, value.indexOf("Tree") + 45); //提取出根目录的哈希
        if (!HEAD.HEADExists()) {
            parent = null;
            HEAD HEAD = new HEAD(key);
            System.out.println("First commit, HEAD file generated");
        } else {
            parent = HEAD.getCurCommit();
            HEAD HEAD = new HEAD(key);
            System.out.println("HEAD updated");
        }

        //打印出所建Commit的数据域
        System.out.println("commit key: " + key);
        System.out.println("commit value: " + "\n" + value);
        System.out.println("commit root: " + root);
        System.out.println("commit parent: " + parent);
    }
    
    public static String setCommitKeyValue(String path) throws Exception{
        String CommitString = "";
        CommitString += "Tree" + " " + Tree.setTreeKeyValue(path) + "\n"; //加根目录类型和哈希
        CommitString += "Parent " + " " + HEAD.getCurCommit();
        
        return KeyValue.setStringKeyValue(CommitString);
    }
    
    public static String getCommitFileValue(String key) throws Exception{
        File file = new File(KeyValue.savingPath + key);
        if(!file.exists()) {
            System.out.println("File with this key doesn't exists");
            return null;
        }
        else {
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            //读取根目录类型，哈希，换行，读取上一个commit类型，哈希
            stringbuffer.append(scan.next()).append(" ").append(scan.next()).append("\n");
            stringbuffer.append(scan.next()).append(" ").append(scan.next());
            scan.close();
            return stringbuffer.toString();
        }
    }
    
    //对比传入路径的根目录和HEAD指向当前commit的根目录哈希是否相等
    public static void compareRoots(String path) throws Exception{
        //相等返回相等信息
        
        String pathRoot = Tree.setTreeKeyValue(path);
        String curRoot = HEAD.getCurRoot();
        //if (setTreeKeyValue(path) == HEAD.getCurRoot()){
        if (pathRoot.equals(curRoot)){
            System.out.println("HEAD is at newest commit " + HEAD.getCurCommit());
        }
        //不等创建新的commit
        else{ Commit commit = new Commit(path); }
    }
}
