public class Blob {
    String key;
    String value;
    String type;

    //无参构造方法，给一个文件地址构造一个以key为，内容为value的新文件
    Blob() {
    }

    //有参构造方法，给一个文件地址构造一个以哈希为key，内容为value的新文件
    Blob(String path) throws Exception {
        key = KeyValue.setFileKeyValue(path);
        value = KeyValue.getFileValue(key);
        type = "Blob";
    }

    public String getType() {
        return "Blob";
    }



    //测试方法
    public static void main(String[] args) throws Exception{
        
/*         Blob blob = new Blob("E:\\局外人.txt");
        System.out.print(KeyValue.getStringValue(blob.key));
          */

    }
}

