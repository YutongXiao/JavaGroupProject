import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class Branch {
    String name; //分支名
    public static String savingPath = KeyValue.savingPath;
    public static String repository = "C:\\Users\\annay\\Desktop\\Java小组项目\\SetTree\\";

    //HEAD构造方法，传入一个commit的哈希值
    //判断HEAD文件是否存在，若不存在，创建以HEAD为名，Commit哈希为内容的文件，若存在,更新HEAD。
    Branch(String branch) throws Exception{
        //不存在则创造HEAD，放入commit哈希
        name = branch;
        File file = new File(savingPath + branch);
        if (!BranchExists(branch)){
            file.createNewFile();
            System.out.println("new branch \"" + branch + "\" created");
        }
        //存在则打开HEAD，更新commit哈希
        else{
            System.out.println("The Branch "+branch+" already exists.");
        }
    };
    
    //切换分支
    public static void checkoutBranch(String branch) throws Exception{
        if (!BranchExists(branch)){
            Branch newBranch = new Branch(branch);
            checkoutBranch(branch);
        }
        else{
            String curcommit = getCurCommit();
            HEAD HEAD = new HEAD(branch);
            String commit = getBranchCommit(branch);
            if(commit.length()!=0){
                SetTree.ResetCommit(repository,commit);
                System.out.println("switched to branch " + branch);
            }
            else {
                updateBranch(branch, curcommit);
                System.out.println("switched to branch " + branch);
            }
            
        }
    }
    
    //回滚
    public static void resetBranch(String branch) throws Exception{
        if (!BranchExists(branch)){
            System.out.println("Branch doesn't exists");
        }
        else{
            String value = Commit.getCommitFileValue(getCurCommit());
            String commit = value.substring(value.indexOf("Parent") + 7, value.indexOf("Parent") + 47);
            updateBranch(branch, commit);
            System.out.println("successfully reset to commit " + commit + " at branch " + branch);
            SetTree.ResetCommit(repository,commit);
        }
    }
    
    //更新Branch所储存的commit的哈希
    public static void updateBranch(String branch,String commit) throws Exception{
        File file = new File(savingPath + branch);
        PrintWriter output = new PrintWriter(file);
        output.print(commit);
        output.close();
    }
    
    
    //判断HEAD文件是否存在，存在则返回true，不存在返回false
    public static boolean BranchExists(String name){
        File file = new File(savingPath + name);
        if (!file.exists()){
            return false;
        }
        else{
            return true;
        }
    }
    
    //返回某分支储存commit
    public static String getBranchCommit(String branch) throws Exception{
        return Blob.getBlobFileValue(branch);
    }
    
    //返回当前所在commit
    public static String getCurCommit() throws Exception{
        String branch = HEAD.getCurBranch();
        String commit = getBranchCommit(branch);
        return commit;
    }
    
    public static String getCurRoot() throws Exception{
        //HEAD不存在返回字符串"null"，以参与字符串类型对比
        if(!HEAD.HEADExists()){
            return "null";
        }
        //若HEAD存在则提取出HEAD中当前commit所建key-value文件中的根目录哈希（根目录信息部分如"Tree ad910b66c06991ddcfd2ce3f294eb9d130f5add8"）
        //并返回其哈希部分的值
        else{
            String value = Commit.getCommitFileValue(getCurCommit());
            return value.substring(value.indexOf("Tree") + 5, value.indexOf("Tree") + 45);
        }
    }
    
}
