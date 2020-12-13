import java.io.File;
import java.io.PrintWriter;
public class commit {
    
    
    String head; 
    String key;
    String value;
    String type;

    commit(){

    }

    commit(String tree)throws Exception{   
        value = KeyValue.setTreeKeyValue(tree);     //得到tree的各级keyValue
        key = KeyValue.setStringKeyValue(value);    //得到commit的key 建立keyValue
        type = "Commit";
        head = sethead(tree,key);
    }

    //creat head
    String sethead(String tree, String commitkey)throws Exception{
        File file = new File (tree);
        File commitfile = new File("E:\\KeyValues\\" + file.getName() + "Head");
        if(!commitfile.exists()){
            commitfile.createNewFile();  
        }
         //写入value
         PrintWriter output = new PrintWriter(commitfile);
         output.print(commitkey);
         output.close();
         return "E:\\KeyValues\\" + file.getName() + "Head"; //写入value         
    }
    //test
    public static void main (String[] args) throws Exception{
        new commit("E:\\素质教育");
        commit commit = new commit("E:\\素质教育");       
        System.out.println(commit.value );
        System.out.print(commit.key );
        
    }

}
