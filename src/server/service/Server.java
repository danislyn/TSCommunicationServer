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
	
	//������Žӿں���Ӧ��ʵ��
	private Map<String, Object> interfaceInstance;
	
	//��������ļ������Ͷ�Ӧ��directory
	private Map<String, String> directoryInstance;
	
	
	/**
	 * ���캯��
	 */
	public Server(){}
	
	/**
	 * ָ���ӿ�Map���ļ�Ŀ¼Map�Ĺ��캯��
	 * @param interfaceInstance
	 * @param directoryInstance
	 */
	public Server(Map<String, Object> interfaceInstance, Map<String, String> directoryInstance){
		this.interfaceInstance = interfaceInstance;
		this.directoryInstance = directoryInstance;
	}
	
	
	/**
	 * ��ʼ����Ľӿ�
	 */
	public void startService(){
		try{
			//��ʼ������˵�socket�����÷���˿�Ϊ8090
			ServerSocket serverSocket = new ServerSocket(8091);
			
			System.out.println("Server started, waiting connection......");
			
			while(true){
				//�ͻ��˵�socket
				Socket socket = serverSocket.accept();
				
				//����һ�����߳�
				new Thread(new HandlingThread(socket)).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	//һ���̳�Runnable���ڲ���
	public class HandlingThread implements Runnable{
		
		private Socket mySocket;

		//���캯��
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
				
				//�ӿͻ��˶�ȡ��Call����
				Call call;
				
				while(true){
					call = (Call) ois.readObject();
					
					if(call != null){
						//��ͨ��������
						if(call.getType() == 0)
						{
							System.out.println(call.getClassName());
						
							//����invoke()�ӿڣ�����һ��result��ֵ��Callʵ��
							call = invoke(call);
						
							//�Ѵ��н����Call���󷵻ظ��ͻ���
							oos.writeObject(call);
							oos.reset();
						}
						//�ļ��ϴ�����**********************************************
						else if(call.getType() == 1)
						{
							System.out.println("File submission request......");
							
							FileBaseInfo fileBaseInfo = call.getFileBaseInfo();
							//��ʼ����ʱ����
							byte[] fileBytes = new byte[1024];
							int length = 0;
							int count = fileBaseInfo.getFileSize();
							
							//��ʼ���ļ���Ϣ
							String directory = directoryInstance.get(call.getMethodName());
							String fileName = fileBaseInfo.getFileName();
							
							//����Ǹ���ͷ���Զ������ļ���
							if(call.getMethodName().equals("uploadPersonalImage"))
							{
								fileName = "image_" + fileBaseInfo.getContributorUsername() + ".png";
								fileBaseInfo.setFileName(fileName);
							}
							
							//exception...
							java.io.FileOutputStream fos = new FileOutputStream(new File(directory + fileName));
							fileBaseInfo.setFilePath(directory + fileName);
							
							//��Ӧ�ͻ���
							call.setTag(true);
							oos.writeObject(call);
							oos.reset();
							call.setTag(false);
							
							System.out.println ("��ʼ��������...");

							while(count>0 && (length = is.read(fileBytes,0,fileBytes.length))>0)
							{
								count--;
								
								fos.write(fileBytes);
								fos.flush();
							}
							System.out.println ("��ɽ���");
							System.out.println("Received FileSize: " + fileBaseInfo.getFileSize() + "KB");
				   							
							fos.close();
				
							//����invoke()�ӿڣ�����һ��result��ֵ��Callʵ��
							call = invoke(call);		
							//��Ӧ�ͻ���
							oos.writeObject(call);
							oos.reset();
						}
						//�ļ���������**********************************************
						else if(call.getType() == 2)
						{			
							//��ʼ����ʱ����
							int length=0;
							byte[] sendBytes=new byte[1024];
							int count = 0;
							
							//exception...
							File file = new File(call.getFileBaseInfo().getFilePath());						
							   
							java.io.FileInputStream fis = new FileInputStream(file);
									
							System.out.println ("��ʼ��������...");
							
							while((length = fis.read(sendBytes, 0, sendBytes.length))>0)
							{
								count++;
								os.write(sendBytes);
								os.flush();							
							}
												
							fis.close();
							
							System.out.println ("��ɷ���");
							System.out.println("Sent FileSize: " + count +"KB");
							
							//��Ӧ�ͻ���
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
	 * ����ָ������ض������Ľӿڣ���Ҫ����JAVA������Ƶ�ʵ��
	 */
	@SuppressWarnings("finally")
	public Call invoke(Call call){
		Object result = null;  //���ؽ��
		
		String className = call.getClassName();  //Ҫ���÷���������
		String methodName = call.getMethodName();  //Ҫ���÷�����
		Object[] params = call.getParams();  //����
		Class[] paramTypes = call.getParamTypes();  //��������
		
		//System.out.println(className);
		System.out.println(methodName);
		
		try {
			//�õ�һ���������ֵ����ʵ��
			Class classType = Class.forName(className);
			
			//���ݷ������Ͳ������͵õ�����
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
