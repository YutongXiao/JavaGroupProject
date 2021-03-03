# JavaGroupProject: Git Version Control Tool
Java group project for Java Programming Design course
Group members include:  
1. [@YutongXiao](https://github.com/YutongXiao)
2. [@brycejpt](https://github.com/brycejpt)
3. [@Mingyingjie](https://github.com/Mingyingjie)

**Content: replication of Git blob, tree and commit storage structure**  
Task 1: creation of class Blob and class KeyValue  
Task 2: creation of class Tree  
Task 3: creation of class Commit and class HEAD  
Task 4: creation of class Branch, Logs, SetTree and CommandLine


**使用方法：**  
1. 修改CommandLine类中gitCommit()方法下Commit.compareRoots中参数为源文件夹（根目录）路径  
2. 修改KeyValue类中数据域savingPath变量为键值对文件及其他指针文件的保存路径  
3. 修改Branch类中数据域repository变量为回滚生成文件的保存路径  
4. 在命令行中切换到CommandLine类所在文件夹依次键入以下代码进行交互：  
    (1) javac -encoding UTF-8 CommandLine.java  
    (2) java CommandLine XXX		(XXX为git命令，包含以下六种，可重复输入）  
    - git commit  
    - git branch "branchName"  
    - git checkout "branchName"  
    - git log  
    - git reset  
    - git reset "hash"  
