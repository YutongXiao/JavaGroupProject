import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class SetTree {
    public static String savingPath = KeyValue.savingPath; //所有键值对文件存放的路径
    
    //从commit中提取tree
    static String CommitGetTree(String commit) throws Exception{
        String value = Commit.getCommitFileValue(commit);
        String tree = value.substring(value.indexOf("Tree") + 5, value.indexOf("Tree") + 45);
        return tree;
    }

    //获取tree的value中的 blob tree的信息
    static ArrayList<String> TreeGetContent(String FileKey)throws Exception{  
        //FileKey为树的KeyValue文件     
        // 使用ArrayList来存储每行读取到的字符串       
		ArrayList<String> TreeContent = new ArrayList<>();		
		FileReader fr = new FileReader(savingPath+FileKey);
		BufferedReader bf = new BufferedReader(fr);
		String str;
		// 按行读取字符串
		while ((str = bf.readLine()) != null) {
			TreeContent.add(str);
		}
		bf.close();
		fr.close();
        return TreeContent;
    }

    //通过原文件名、写入路径和文件对应键值文件写回文件。
    static Boolean WriteBlob(String FileName,String Mother,String FileKey)throws Exception{
        File file = new File(Mother +File.separator+ FileName);
        file.createNewFile();
        //写入value        
        FileInputStream fis = new FileInputStream(savingPath+ FileKey);
        FileOutputStream fos = new FileOutputStream(file);
        //先定义一个字节缓冲区，减少I/O次数，提高读写效率
        byte[] buffer=new byte[1024];
        int size=0;
        while((size=fis.read(buffer))!=-1){
            fos.write(buffer, 0, size);           
        }
        fis.close();
        fos.close();   
        return true;    
    }

    //通过原文件名、写入路径和文件对应键值文件写回文件夹。
    static Boolean WriteTree(String Mother, ArrayList<String> TreeContent)throws Exception{		
		// 对ArrayList中存储的字符串进行处理
		int length = TreeContent.size();	
		for (int i = 0; i < length; i++) {	         
            String[] FileInfo = TreeContent.get(i).split("\t");	

            if(FileInfo[0].equals("Blob")){
                WriteBlob(FileInfo[2],Mother,FileInfo[1]);
            }
            else if(FileInfo[0].equals("Tree")){
                File file = new File(Mother +File.separator+ FileInfo[2]);
                file.mkdir();
                WriteTree(Mother +File.separator+ FileInfo[2],TreeGetContent(FileInfo[1]));
            }	         
        }
        return true;
    }

    //传入路径和commit，调用上述的方法重写Commit到路径。
    static Boolean ResetCommit(String Mother,String commit) throws Exception{
        String tree = CommitGetTree(commit);
        return WriteTree(Mother, TreeGetContent(tree));
    }
}