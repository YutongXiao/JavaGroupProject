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
        value = KeyValue.getTreeFileValue(key);
        type = "Tree";
    }


    
    //测试方法
    public static void main (String[] args) throws Exception{
         
        Tree tree = new Tree("C:\\Users\\annay\\Desktop\\Java小组项目\\HashTest");
        System.out.println(tree.value);
        System.out.println("Root: " + tree.key);
    }
}
