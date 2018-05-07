package dao;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;

public interface ImageDao extends BaseDao {
    GridFSDBFile getImageById(String id);
    String saveImage(File image);
    Boolean deleteImageById(String id);
}
