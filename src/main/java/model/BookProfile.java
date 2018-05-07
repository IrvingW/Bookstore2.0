package model;

import java.io.File;
import java.util.List;

public class BookProfile {
    private int bookID;
    private String bookName;
    private String isbn;
    private String author;
    private String press;
    private String category1;
    private String category2;
    private String profileID;         // 书的详细信息在monggodb中的id
    private String imageID;           // 书的图片在monggodb中的图片
    private Integer price;
    private Integer amount;
    private String intro;

    private List<String> otherPictureIDList;
    private File coverPicture;
    private String coverPictureFileName;
    private String coverPictureContentType;
    private File[] otherPicture;
    private String[] otherPictureFileName;
    private String[] otherPictureContentType;

    /* ==================================================== */

    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
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
    public String getProfileID() {
        return profileID;
    }
    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }
    public String getImageID() {
        return imageID;
    }
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public List<String> getOtherPictureIDList() {
        return otherPictureIDList;
    }
    public void setOtherPictureIDList(List<String> otherPictureIDList) {
        this.otherPictureIDList = otherPictureIDList;
    }
    public File getCoverPicture() {
        return coverPicture;
    }
    public void setCoverPicture(File coverPicture) {
        this.coverPicture = coverPicture;
    }
    public File[] getOtherPicture() {
        return otherPicture;
    }
    public void setOtherPicture(File[] otherPicture) {
        this.otherPicture = otherPicture;
    }

}
