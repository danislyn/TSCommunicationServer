package server.service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import beans.file.FileBaseInfo;

import server.reflection.Call;



public class Server {
	
	//用来存放接口和相应的实例
	private Map<String, Object> interfaceInstance;
	
	//用来存放文件操作和对应的directory
	private Map<String, String> directoryInstance;
	
	
	/**
	 * 构造函数
	 */
	public Server(){}
	
	/**
	 * 指定接口Map和文件目录Map的构造函数
	 * @param interfaceInstance
	 * @param directoryInstance
	 */
	public Server(Map<String, Object> interfaceInstance, Map<String, String> directoryInstance){
		this.interfaceInstance = interfaceInstance;
		this.directoryInstance = directoryInstance;
	}
	
	
	/**
	 * 开始服务的接口
	 */
	public void startService(){
		try{
			//初始化服务端的socket，设置服务端口为8090
			ServerSocket serverSocket = new ServerSocket(8091);
			
			System.out.println("Server started, waiting connection......");
			
			while(true){
				//客户端的socket
				Socket socket = serverSocket.accept();
				
				//启动一个新线程
				new Thread(new HandlingThread(socket)).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	//一个继承Runnable的内部类
	public class HandlingThread implements Runnable{
		
		private Socket mySocket;

		//构造函数
		public HandlingThread(){}
		
		public HandlingThread(Socket socket){
			this.mySocket = socket;
		}
		
		
		@Override
		public void run() {
			
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			
			try {			
				InputStream is = mySocket.getInputStream();
				ois = new ObjectInputStream(is);

				OutputStream os = mySocket.getOutputStream();
				oos = new ObjectOutputStream(os);
				
				//从客户端读取的Call对象
				Call call;
				
				while(true){
					call = (Call) ois.readObject();
					
					if(call != null){
						//普通调用请求
						if(call.getType() == 0)
						{
							System.out.println(call.getClassName());
						
							//调用invoke()接口，返回一个result赋值的Call实例
							call = invoke(call);
						
							//把带有结果的Call对象返回给客户端
							oos.writeObject(call);
							oos.reset();
						}
						//文件上传请求**********************************************
						else if(call.getType() == 1)
						{
							System.out.println("File submission request......");
							
							FileBaseInfo fileBaseInfo = call.getFileBaseInfo();
							//初始化临时变量
							byte[] fileBytes = new byte[1024];
							int length = 0;
							int count = fileBaseInfo.getFileSize();
							
							//初始化文件信息
							String directory = directoryInstance.get(call.getMethodName());
							String fileName = fileBaseInfo.getFileName();
							
							//如果是个人头像，自动分配文件名
							if(call.getMethodName().equals("uploadPersonalImage"))
							{
								fileName = "image_" + fileBaseInfo.getContributorUsername() + ".png";
								fileBaseInfo.setFileName(fileName);
							}
							
							//exception...
							java.io.FileOutputStream fos = new FileOutputStream(new File(directory + fileName));
							fileBaseInfo.setFilePath(directory + fileName);
							
							//响应客户端
							call.setTag(true);
							oos.writeObject(call);
							oos.reset();
							call.setTag(false);
							
							System.out.println ("开始接收数据...");

							while(count>0 && (length = is.read(fileBytes,0,fileBytes.length))>0)
							{
								count--;
								
								fos.write(fileBytes);
								fos.flush();
							}
							System.out.println ("完成接收");
							System.out.println("Received FileSize: " + fileBaseInfo.getFileSize() + "KB");
				   							
							fos.close();
				
							//调用invoke()接口，返回一个result赋值的Call实例
							call = invoke(call);		
							//响应客户端
							oos.writeObject(call);
							oos.reset();
						}
						//文件下载请求**********************************************
						else if(call.getType() == 2)
						{			
							//初始化临时变量
							int length=0;
							byte[] sendBytes=new byte[1024];
							int count = 0;
							
							//exception...
							File file = new File(call.getFileBaseInfo().getFilePath());						
							   
							java.io.FileInputStream fis = new FileInputStream(file);
									
							System.out.println ("开始发送数据...");
							
							while((length = fis.read(sendBytes, 0, sendBytes.length))>0)
							{
								count++;
								os.write(sendBytes);
								os.flush();							
							}
												
							fis.close();
							
							System.out.println ("完成发送");
							System.out.println("Sent FileSize: " + count +"KB");
							
							//响应客户端
							//call.setTag(true);
							//oos.writeObject(call);
							//oos.reset();
						}

					}
				}//end while
				
			} catch (EOFException e) {
				// TODO: handle exception
				
				try {
					mySocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				
			} catch (Exception e) {
				
				//e.printStackTrace();
				
				/*try{
					ois.reset();
					oos.reset();
				}catch(Exception e1){
					e1.printStackTrace();
				}*/
				
			}
		}//end run
		
	}
	
	
		
	/**
	 * 调用指定类的特定方法的接口，主要负责JAVA反射机制的实现
	 */
	@SuppressWarnings("finally")
	public Call invoke(Call call){
		Object result = null;  //返回结果
		
		String className = call.getClassName();  //要调用方法的类名
		String methodName = call.getMethodName();  //要调用方法名
		Object[] params = call.getParams();  //参数
		Class[] paramTypes = call.getParamTypes();  //参数类型
		
		//System.out.println(className);
		System.out.println(methodName);
		
		try {
			//得到一个给定名字的类的实例
			Class classType = Class.forName(className);
			
			//根据方法名和参数类型得到方法
			Method method = classType.getMethod(methodName, paramTypes);
			
			if(interfaceInstance.get(className)==null){
				result = "class not found";
			}else{
				result = method.invoke(interfaceInstance.get(className), params);
				System.out.println("result...." + result);
			}
		} catch (Exception e) {
			result = "exception";
			e.printStackTrace();
		}finally{
			call.setResult(result);
			System.out.println(result);
			return call;
		}
	}
	

}
