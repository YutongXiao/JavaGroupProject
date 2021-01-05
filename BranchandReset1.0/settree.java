import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class settree {
    public static String savingPath = KeyValue.savingPath;
    
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

    //写blob文件
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

    //写tree文件夹
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
    
    static Boolean ResetCommit(String Mother,String commit) throws Exception{
        String tree = CommitGetTree(commit);
        return WriteTree(Mother, TreeGetContent(tree));
    }

    public static void main (String[] args) throws Exception{

//TreeGetContent 方法测试
/*          ArrayList<String> array = TreeGetContent("727506b10211bbfc0bd4aeaba36467b1f2cbbdd5") ;        
		for (int i = 0; i < array.size(); i++) {	
            System.out.println("next blob:");
            String[] arrayIn = array.get(i).split("\t");
            for (int j =0;j<3;j++){                
                System.out.println(arrayIn[j]+" ");
            }
            	 
			
        }  */

// WriteBlob 方法测试
//        System.out.print(WriteBlob("1.jpg","E:\\desktop\\NewBase\\素质教育","5e86e4d9f490c3fe0949fb0ab90a77f183ff1a6f"));

       


//WriteTree 方法测试
        //WriteTree("/Users/jiapeitong/Desktop/未命名文件夹4/",TreeGetContent("ee6e9c92dbbdd1501dc525a5016aefbbc0ef4ecd")); 
        ResetCommit("/Users/jiapeitong/Desktop/未命名文件夹4/", "5c43dc9bfdf9840761df29987c0465de2ea8cea8");



    }
}