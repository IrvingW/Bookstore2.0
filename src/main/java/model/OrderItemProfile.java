package model;

public class OrderItemProfile extends OrderItem {
    private String bookName;
    private String isbn;
    private String author;
    private String press;
    private String category1;
    private String category2;
    private String imageID;

    public OrderItemProfile(){};

    public OrderItemProfile(int orderID,int bookID,int bookAmount,int bookPrice){
        super(orderID, bookID, bookAmount, bookPrice);
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPress() {
        return press;
    }
    public void setPress(String press) {
        this.press = press;
    }
    public String getCategory1() {
        return category1;
    }
    public void setCategory1(String category1) {
        this.category1 = category1;
    }
    public String getCategory2() {
        return category2;
    }
    public void setCategory2(String category2) {
        this.category2 = category2;
    }
    public String getImageID() {
        return imageID;
    }
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
