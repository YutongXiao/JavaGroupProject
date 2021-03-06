import java.io.File;
import java.util.Scanner;

public class Tree {
    //数据域
    String key ; //传入目录的key
    String value ; //传入目录的value

    //空构造方法
    Tree(){
    }

    //构造方法 传入path 建立key-value文件
    Tree(String path) throws Exception {
        key = setTreeKeyValue(path);
        value = getTreeFileValue(key);
    }
    
    //深度优先遍历，为每个文件和文件夹建立key-value文件，并返回整个文件目录的key，用于构造方法中key的赋值
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
                DirectoryString += "Blob" + "\t" + keyvalue.key +"\t"+ fs[i].getName()+ "\n";                               
            }    
            if(fs[i].isDirectory()){
                String thisDirKey = setTreeKeyValue(fs[i].getPath());				
                DirectoryString += "Tree" + "\t" + thisDirKey + "\t"+fs[i].getName()+ "\n" ;
            }
        }          
        return KeyValue.setStringKeyValue(DirectoryString);                          
    } 

    //传入一个目录的哈希key，返回其key-value文件中的内容，用于构造方法中value的赋值
    public static String getTreeFileValue(String key) throws Exception{
        File file = new File(KeyValue.savingPath + key);
        if(!file.exists()){
            System.out.println("File with this key doesn't exists");
            return null;
        }else{
            Scanner scan = new Scanner(file);
            StringBuffer stringbuffer = new StringBuffer();
            while (scan.hasNext()){
                for(int i=0; i<4; i++) { //每遍历完一条记录的文件权限信息、文件类型、文件哈希、文件名四个部分后，换行
                    stringbuffer.append(scan.next()).append(" ");
                }
                stringbuffer.append("\n"); //每打印完一条三部分记录后换行
            }
            scan.close();
            return stringbuffer.toString();
        }
    }
}
