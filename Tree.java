import java.io.File;
import java.util.Scanner;

public class Tree {
    //数据域
    String key ;
    String value ;
    String type;

    //方法
    Tree(){
    }

    //构造方法 传入path 建立key-value文件
    Tree(String path) throws Exception {
        key = setTreeKeyValue(path);
        value = getTreeFileValue(key);
        type = "Tree";
    }
    
    //dfs方法为每个文件、文件夹建立 key-value，
    public static String setTreeKeyValue(String path) throws Exception{
        String DirectoryString = "";
        // 该字符串中存储文件夹下 files 结构， 如：
        /*  Tree  8826db7b1c619caeb18d355f853f84d6161dbcd0	1-大数据与人工智能》讲座听后感
            Blob  5e86e4d9f490c3fe949fbab9a77f183ff1a6f	1.jpg
            Blob  17594c5ae3133196f9bf6d6bb63debf93c725	10月10日《素质教育与前沿技术课程》讲座现场（3303教室）报名.xlsx
            Blob  fa96ebc2de647999e21b1f74a0351611ade87	课程考核.txt */
        File dir = new File(path);
        File[] fs = dir.listFiles();        
        for(int i = 0; i < fs.length; i++){
            if(fs[i].isFile()){                
                KeyValue keyvalue = new KeyValue(fs[i].getPath());  
                DirectoryString += "100644 Blob" + "  " + keyvalue.key +"\t"+ fs[i].getName()+ "\n";
            }    
            if(fs[i].isDirectory()){
                String thisDirKey = setTreeKeyValue(fs[i].getPath());				
                DirectoryString += "040000 Tree" + "  " + thisDirKey + "\t"+fs[i].getName()+ "\n" ;
            }
        }          
        return KeyValue.setStringKeyValue(DirectoryString);                          
    } 
    
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


    
    //测试方法
    public static void main (String[] args) throws Exception{
         
        Tree tree = new Tree("C:\\Users\\annay\\Desktop\\Java小组项目\\HashTest");
        System.out.println(tree.value);
        System.out.println("Root: " + tree.key);
    }
}

