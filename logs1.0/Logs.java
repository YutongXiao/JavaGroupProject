import java.io.File;
import java.io.PrintWriter;import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Logs {
    public static String savingPath = KeyValue.savingPath;
    public static String repository = Branch.repository;

    //logs构造方法
    //判断logs文件是否存在，若不存在，创建以HEAD为名，Commit哈希为内容的文件，若存在,更新HEAD。
    Logs(String path) throws Exception{
        //不存在则创造HEAD，放入commit哈希
        File file = new File(path + "logs");
        if (!file.exists()){
            file.createNewFile();
        }        
    }
    
    //写logs文件方法，记录每次commit的哈希和branch
    public static void Writelogs(String commit,String branch) throws Exception{
        String logsString = "";
        logsString += commit + " commitby:" + branch +"\n";
        
        File file = new File(savingPath + "logs");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter output = new FileWriter(file,true);
        output.write(logsString);
        output.close();
    }
    
    //打印全部logs
    public static void Checklogs() throws Exception{
        File file = new File(savingPath+"logs");
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[(int)file.length()];
        while (fis.read(b) != -1) {
            System.out.println(new String(b));
        }
    }
    
    //根据打印出来的commit历史，复制其中一个commit，切换到该commit，并同时切换到该commit所在分支
    public static void CheckoutCommit(String commit) throws Exception{
        File file = new File(savingPath + "logs");
        String line = "";
        if(!file.exists()){
            System.out.println("File doesn't exists");
        }
        else{
            FileReader fr = new FileReader(savingPath + "logs");
            BufferedReader br = new BufferedReader(fr);
            
            for (line = br.readLine(); !line.startsWith(commit); line = br.readLine()) {
            }
            
        }
        String branch = line.substring(line.indexOf(commit + "commitby:")+51);
        System.out.println(branch);
        Branch.checkoutbranch(branch);
        settree.ResetCommit(repository,commit);
    }
    
    public static void main(String[] args) throws Exception{
    }
        
}