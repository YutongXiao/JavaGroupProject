import java.io.File;
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
        value = Blob.getFileValue(key);
        type = "Tree";
    }

    //dfs方法为每个文件、文件夹建立 key-value，
    public String setTreeKeyValue(String path) throws Exception{
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
                Blob blob = new Blob(fs[i].getPath());  
                DirectoryString += "Blob" + "  " + blob.key +"\t"+ fs[i].getName()+ "\n";                               
            }    
            if(fs[i].isDirectory()){
                String thisDirKey = setTreeKeyValue(fs[i].getPath());				
                DirectoryString += "Tree" + "  " + thisDirKey + "\t"+fs[i].getName()+ "\n" ;
            }
        }          
        return Blob.setStringKeyValue(DirectoryString);                          
    } 
    
    //测试方法
    public static void main (String[] args) throws Exception{
        Tree tree = new Tree("E:\\素质教育");       
        System.out.print(tree.value);
        System.out.print("Root: " + tree.key);
    }
}
