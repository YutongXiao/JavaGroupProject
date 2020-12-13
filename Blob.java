public class Blob {
    String key;
    String value;
    String type;

    //无参构造方法，空
    Blob() {
    }

     //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    Blob(String path) throws Exception {
        key = KeyValue.setFileKeyValue(path);
        value = KeyValue.getBlobFileValue(key);
        type = "Blob";
    }
   
//测试方法
public static void main(String[] args) throws Exception{
        Blob blob = new Blob("C:\\Users\\annay\\Desktop\\Java小组项目\\HashTest\\test1.txt");
        System.out.println(blob.key);
        System.out.print(KeyValue.getBlobFileValue(blob.key));
    }
}

