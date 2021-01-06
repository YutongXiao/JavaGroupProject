/*
CommandLine类主要实现从命令行编译Java文件后的交互 通过java+命令语句实现
主要方法有：
git commit "description" / git branch xxx /git reset xxx
新加commit、切换分支及分支内回滚的功能
 */

public class CommandLine {
    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            System.out.println("invalid input length");
        } else {
            if (!args[0].equals("git")) {
                System.out.println("use \"git\" to begin");
            } else {
                switch (args[1]) {
                    case "log":
                        gitLog();
                        break;

                    case "commit":
                        gitCommit(args[2]);
                        break;

                    case "checkout":
                        gitCheckout(args[2]);
                        break;

                    case "reset":
                        if(args.length == 3) {
                            gitReset(args[2]);
                        }
                        else {
                            gitReset();
                        }
                        break;

                    default:
                        System.out.println("invalid input2");
                }
            }
        }
    }

    //用git log查看commit历史
    public static void gitLog() throws Exception {
        Logs.Checklogs();
    }

    //用git commit "description"（暂时无用) 在当前分支上创建新的commit
    public static void gitCommit(String description) throws Exception {
        Commit.compareRoots("C:\\Users\\annay\\Desktop\\Java小组项目\\HashTest\\");
    }

    //用git checkout “branchName”切换到branchName的分支
    public static void gitCheckout(String branchName) throws Exception {
        Branch.checkoutBranch(branchName);
    }

    //用git reset (40位)hash 回滚至历史中某个指定commit
    public static void gitReset(String hash) throws Exception {
        Logs.CheckoutCommit(hash);
    }

    //用git reset命令默认将当前HEAD指向branch回滚一个commit
    public static void gitReset() throws Exception {
        String branch = HEAD.getCurBranch();
        Branch.resetBranch(branch);
    }
}
