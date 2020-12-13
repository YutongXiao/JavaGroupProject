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
        key = KeyValue.setTreeKeyValue(path);
        value = KeyValue.getFileValue(key);
        type = "Tree";
        
    }



    //测试方法
    public static void main (String[] args) throws Exception{       
        /* Tree tree = new Tree("E:\\素质教育");       
        System.out.print(tree.value);
        System.out.print("Root: " + tree.key);  */ 
    }
}