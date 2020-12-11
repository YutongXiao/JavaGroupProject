import java.io.*;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;import java.nio.file.*;


public class Tree {
	public static String key;
	public static String value;
	private static String goalPath;
	
	Tree(){		
	}
	
	Tree(String k, String v,String goalPath) throws IOException {
		key= k;
		value=v;
		this.goalPath=goalPath;
	}
	
	static HashMap<String,String> site = new HashMap<String,String>();//全局变量储存文件键值对用于递归时找到特定文件key和value
	
	public static String setDirectoryKeyValue(String path) throws IOException {
		File dir = new File(path);
		File[] fs = dir.listFiles();
		
		String s="";//将文件夹中的value写入一个字符串，用于计算最后的key，并且生成对应的value
		for(int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory()) {	
				
				String j = setDirectoryKeyValue(path + File.separator + fs[i].getName());//递归遍历文件夹内容并返回该文件夹key
				Tree testtree = new Tree(j, site.get(j), goalPath);
				s = s+"Tree "+j+" : "+fs[i].getName()+"\n";//写入value
			}
			if(fs[i].isFile()) {
				Blob testblob = new Blob(fs[i].getPath(), goalPath);
				s = s+"Blob "+testblob.key+" : "+fs[i].getName()+"\n";//写入value
			}
			
		}
		String k = StringtoHash(s);//计算key
		site.put(k,s);//将该文件夹key和value放入hashmap用于外层递归建立对应的tree和blob
		File f=new File(goalPath+File.separator+key);//新建一个文件对象，如果不存在则创建一个该文件
		
		FileWriter fw;
		try {
			fw=new FileWriter(f);
			fw.write(s);//将字符串写入到指定的路径下的文件中
			fw.close();
			} catch (IOException e) { e.printStackTrace(); }
		
		return k;//返回文件夹key
		
	}
	
	//对字符串进行哈希用于计算文件夹哈希值
	private static String StringtoHash(String s){
		try{
			InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));    
			byte[] sha1 = SHA1Checksum(is);     //计算指定文件的SHA1Checksum
			//打印哈希值
			String result = "";
			for (int i = 0; i < sha1.length; i++){
				result += Integer.toString(sha1[i]&0xFF,16);    //将其SHA1Checksum依次取出到result并加密转化为哈希值储存在变量result里
			}
			
			return result;
		}
		
		catch (Exception e){
			e.printStackTrace();
			return "Error";
		}
	}	
	
	
	
	//工具方法：非直接调用，用于SHA1算法Checksum的计算
	private static byte[] SHA1Checksum(InputStream is) throws Exception{
		//用于计算hash值的文件缓冲区
		byte[] buffer = new byte[1024];
		//使用SHA1哈希/摘要算法
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		int numRead = 0;
		do{
			//读取numRead字节到buffer中
			numRead = is.read(buffer);
			if (numRead > 0){
				//根据buffer[0:numRead]的内容更新hash值
				complete.update(buffer,0, numRead);
			}
			//文件已读完，退出循环
		}
		while (numRead != -1);
		//关闭输入流
		is.close();
		//返回SHA1哈希值
		return complete.digest();
	}
	
	public static void main(String[] args) throws IOException{
		String i = setDirectoryKeyValue("/Users/jiapeitong/Desktop/未命名文件夹 3");
		Tree testtree = new Tree(i, site.get(i), "/Users/jiapeitong/Desktop/未命名文件夹 3");
		String key = testtree.key;
		String value = testtree.value;
		
		//打印文件哈希值key和文件内容value（通过调用getStringValue）
		System.out.println("key: " + key);
		System.out.print("value: " + value);
	}
}