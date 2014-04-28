package beans.file;

import java.io.Serializable;

public class FileBaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contributorUsername;
	
	private String fileName;
	
	private String filePath;
	
	private int fileSize;
	
	
	/**
	 * 
	 */
	public FileBaseInfo()
	{
		
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getContributorUsername()
	{
		return contributorUsername;
	}
	
	/**
	 * 
	 * @param contributorUsername
	 */
	public void setContributorUsername(String contributorUsername)
	{
		this.contributorUsername = contributorUsername;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getFileName()
	{
		return fileName;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getFilePath()
	{
		return filePath;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getFileSize()
	{
		return fileSize;
	}
	
	/**
	 * 
	 * @param fileSize
	 */
	public void setFileSize(int fileSize)
	{
		this.fileSize = fileSize;
	}
	
}
