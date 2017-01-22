package Entities;

public class Review extends GeneralMessage
{
	private static final long serialVersionUID = 1L;
	private OrderedBook reviewBook;
	private String keyword,review,signature;
	private int isApproved,ReviewID;
	public String query;
	
	public Review(){}
	
	public Review(OrderedBook reviewBook,String keyword,String review,String signature, int isApproved,int reviewID)
	{
		this.reviewBook = reviewBook;
		this.keyword = keyword;
		this.review = review;
		this.signature = signature;
		this.isApproved = isApproved;
		this.setReviewID(reviewID);
	}
	public OrderedBook getReviewBook() {
		return reviewBook;
	}
	
	public void setReviewBook(OrderedBook reviewBook) {
		this.reviewBook = reviewBook;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public String getKeyword() { 
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getIsApproved() {
		return isApproved;
	}
	
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getReviewID() {
		return ReviewID;
	}
	public void setReviewID(int reviewID) {
		ReviewID = reviewID;
	}
	@Override
	public String toString()
	{
		return "A review for " + reviewBook.getTitle() +" written by " + signature;		
	}
}
