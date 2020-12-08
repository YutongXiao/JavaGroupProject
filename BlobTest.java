import java.io.IOException;

//测试Blob类专用
public class BlobTest {
    public static void main(String[] args) throws IOException, Exception {
        //创建文件地址path 和 目标地址goalPath 的变量，可自由改动，注：有的电脑c盘貌似写不了，可换其他盘
        String path = "D:\\JavaGroupProjectTest\\HashTest\\test1.txt";
        String goalPath = "D:\\JavaGroupProjectTest\\Blobs\\";
        //将path和goalPath作为构造函数参数构造文件Blob
        Blob test = new Blob(path, goalPath);

        //找到文件的哈希值key并赋值给变量key
        String key = test.key;
        String value = test.value;

        //打印文件哈希值key和文件内容value（通过调用getStringValue）
        System.out.println("find key by passing value to StringHash(value): " + test.StringHash(value));
        System.out.println("find key by passing path to setFileKeyValue(path): " + test.setFileKeyValue(path));
        System.out.println("find value by passing key to getStringValue(key): " + test.getStringValue(key));
    }

}
