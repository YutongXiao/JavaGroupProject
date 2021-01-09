import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class Branch {
    String name; //分支名
    public static String savingPath = KeyValue.savingPath;
    public static String repository = "请将此变量修改为回滚生成文件的保存路径";

    //生成名为name的文件，获取当前分支指向commit写入文件
    Branch(String branch) throws Exception{
        name = branch;
        File file = new File(savingPath + branch);
        if (!BranchExists(branch)){
            file.createNewFile();
            System.out.println("new branch \"" + branch + "\" created");
        }
        else{
            System.out.println("The Branch "+branch+" already exists.");
        }
    };
    
    //切换分支，HEAD储存当前分支名
    public static void checkoutBranch(String branch) throws Exception{
        if (!BranchExists(branch)){
            System.out.println("Branch doesn't exist.");
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
    
    //回滚，在当前分支回滚，Branch指向前一个Commit，并恢复文件
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
    
    
    //判断名为name的分支是否存在
    public static boolean BranchExists(String name){
        File file = new File(savingPath + name);
        if (!file.exists()){
            return false;
        }
        else{
            return true;
        }
    }
    
    //返回当前Branch储存的commit的哈希值
    public static String getBranchCommit(String branch) throws Exception{
        return Blob.getBlobFileValue(branch);
    }
    
    //返回当前HEAD指向commit
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
