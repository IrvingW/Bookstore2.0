package service.imp;

import com.mongodb.gridfs.GridFSDBFile;
import dao.ImageDao;
import service.ImageService;

import java.io.File;

public class ImageServiceImpl extends BaseServiceImpl implements ImageService {
    private ImageDao imageDao;

    public ImageDao getImageDao() {
        return imageDao;
    }

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public GridFSDBFile showImage(String imageID) {
        return this.imageDao.getImageById(imageID);
    }

    @Override
    public Boolean uploadBookImage(int bookID, File bookImage) {
        return null;
    }

    @Override
    public Boolean uploadUserImage(int userID, File userImage) {
        return null;
    }
}
