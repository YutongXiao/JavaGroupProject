public class BranchTest {
    //测试方法:传入一个路径到KeyValue类的compareRoots方法里，方法会比较路径哈希与当前HEAD目前存储commit根目录哈希是否相等
    //相等则打印已最新，若HEAD不存在或不相等则创建新的commit对象（创建过程中会自动更新HEAD文件目前指向）
    public static void main(String[] args) throws Exception {
        //part1:提交commit
        //Commit.compareRoots("/Users/jiapeitong/Desktop/未命名文件夹 3");
        //part2:新建及切换分支
        //Branch testBranch = new Branch("testBranch");
        //Branch.checkoutbranch("testbranch");
        //Branch.checkoutbranch("main");
        //part3:分支回滚
        //Branch.resetBranch("testBranch");
        //Branch.resetBranch("main");
        //part4:通过logs文件检查commit历史，切换到某一commit，同时切换到该commit所在分支
        //Logs.Checklogs();
        //Logs.CheckoutCommit("c5d32836aeec25800ed6d8c7e0e65b24f0c9e47e");
    }
}
