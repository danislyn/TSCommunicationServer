package server.reflection;

import java.io.Serializable;

import beans.file.FileBaseInfo;


public class Call implements Serializable{
	
	private String className;
	
	private String methodName;
	
	private Class[] paramTypes;
	
	private Object[] params;
	
	private Object result;
	
	private int type;
	
	private FileBaseInfo fileBaseInfo;
	
	private boolean tag;
	
	
	/**
	 * 
	 */
	public Call()
	{
		
	}
	
	/**
	 * 普通调用请求构造
	 * @param className
	 * @param methodName
	 * @param paramTypes
	 * @param params
	 */
	public Call(String className, String methodName, Class[] paramTypes, Object[] params)
	{
		this.className = className;
		
		this.methodName = methodName;
		
		this.paramTypes = paramTypes;
		
		this.params = params;
		
		this.result = null;
		
		this.type = 0;
		
		this.fileBaseInfo = null;
		
		this.tag = false;
	}
	
	/**
	 * 文件传输请求构造
	 * @param className
	 * @param methodName
	 * @param type 1为上传   2为下载
	 * @param fileBaseInfo
	 */
	public Call(String className, String methodName, int type, FileBaseInfo fileBaseInfo)
	{
		this.className = className;
		
		this.methodName = methodName;
		
		this.paramTypes = new Class[]{FileBaseInfo.class};
		
		this.params = new Object[]{fileBaseInfo};
		
		this.result = null;
		
		this.type = type;

		this.fileBaseInfo = fileBaseInfo;

		this.tag = false;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 
	 * @return Class[]
	 */
	public Class[] getParamTypes() {
		return paramTypes;
	}

	/**
	 * 
	 * @param paramTypes
	 */
	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	/**
	 * 
	 * @return Object[]
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * 
	 * @param params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return FileBaseInfo
	 */
	public FileBaseInfo getFileBaseInfo() {
		return fileBaseInfo;
	}
	
	/**
	 * 
	 * @param fileBaseInfo
	 */
	public void setFileBaseInfo(FileBaseInfo fileBaseInfo) {
		this.fileBaseInfo = fileBaseInfo;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean getTag() {
		return tag;
	}
	
	/**
	 * 
	 * @param tag
	 */
	public void setTag(boolean tag) {
		this.tag = tag;
	}

}
