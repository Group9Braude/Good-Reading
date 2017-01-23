package Entities;

public class FileDetails extends GeneralMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName,content;
	
	public FileDetails(String fileName,String content)
	{
		this.fileName = fileName;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
