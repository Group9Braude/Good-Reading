package Entities;
/**
 * This class holds all the necessary information about reviews
 * @author Eran Simhon
 *
 */
public class Review extends GeneralMessage
{
	private static final long serialVersionUID = 1L;
	private OrderedBook reviewBook;
	private String keyword,review,signature;
	private int isApproved,ReviewID;
	public String query;
	/**
	 * Construct a review
	 */
	public Review(){}
	/**
	 * Construct a review
	 * @param reviewBook the book the review is written about
	 * @param keyword keywords for searching the review
	 * @param review the contents of the review itself
	 * @param signature the signature of the reader
	 * @param isApproved if the review has been approved
	 * @param reviewID the review ID
	 */
	
	public Review(OrderedBook reviewBook,String keyword,String review,String signature, int isApproved,int reviewID)
	{
		this.reviewBook = reviewBook;
		this.keyword = keyword;
		this.review = review;
		this.signature = signature;
		this.isApproved = isApproved;
		this.setReviewID(reviewID);
	}
	/**
	 * 
	 * @param reviewBook the book the review is written about
	 * @param keyword keywords for searching the review
	 * @param review the contents of the review itself
	 * @param signature the signature of the reader
	 * @param isApproved if the review has been approved
	 */
	public Review(OrderedBook reviewBook,String keyword,String review,String signature, int isApproved)
	{
		this.reviewBook = reviewBook;
		this.keyword = keyword;
		this.review = review;
		this.signature = signature;
		this.isApproved = isApproved;
	}
	/**
	 * Get the review book
	 * @return the review book
	 */
	public OrderedBook getReviewBook() {
		return reviewBook;
	}
	/**
	 * Set the review book
	 * @param reviewBook the new review book
	 */
	public void setReviewBook(OrderedBook reviewBook) {
		this.reviewBook = reviewBook;
	}
	/**
	 * get the contents of the review
	 * @return the contents of the review
	 */
	public String getReview() {
		return review;
	}
	
	/**
	 * Set the content of the review
	 * @param review
	 */
	public void setReview(String review) {
		this.review = review;
	}
	/**
	 * Get the keywords used for searching this review
	 * @return the string containing the keywords
	 */
	public String getKeyword() { 
		return keyword;
	}
	/**
	 * set new keywords
	 * @param keyword the new keywords
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * Return the approval status of this review
	 * @return the approval status
	 */
	public int getIsApproved() {
		return isApproved;
	}
	/**
	 * Set the approval status of this book
	 * @param isApproved
	 */
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	/**
	 * Get the signature of the writer
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * Set the signature of the writer
	 * @param signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * Get the review ID
	 * @return the review ID
	 */
	public int getReviewID() {
		return ReviewID;
	}
	/**
	 * Set the review ID
	 * @param reviewID the new reivew ID
	 */
	public void setReviewID(int reviewID) {
		ReviewID = reviewID;
	}
	/**
	 * This methods returns a string representing the current review
	 */
	@Override
	public String toString()
	{
		return "A review for " + reviewBook.getTitle() +" written by " + signature;		
	}
}
