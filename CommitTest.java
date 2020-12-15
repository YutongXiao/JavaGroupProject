
public class CommitTest {
    //测试方法:传入一个路径到KeyValue类的compareRoots方法里，方法会比较路径哈希与当前HEAD目前存储commit根目录哈希是否相等
    //相等则打印已最新，若HEAD不存在或不相等则创建新的commit对象（创建过程中会自动更新HEAD文件目前指向）
    public static void main(String[] args) throws Exception {
        Commit.compareRoots("/Users/jiapeitong/Desktop/未命名文件夹 3");
    }
}

