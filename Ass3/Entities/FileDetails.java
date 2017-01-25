package Entities;

public class FileDetails extends GeneralMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName,content,type;
	
	public FileDetails(String fileName,String content,String type)
	{
		this.fileName = fileName;
		this.content = content;
		this.setType(type);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
