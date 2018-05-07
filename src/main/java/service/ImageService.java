package service;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;

public interface ImageService extends BaseService{
    GridFSDBFile showImage(String imageID);
    Boolean uploadUserImage(int userID, File userImage);
    Boolean uploadBookImage(int bookID, File bookImage);
}
