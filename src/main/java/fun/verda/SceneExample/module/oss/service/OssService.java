package fun.verda.SceneExample.module.oss.service;


import java.io.IOException;


public interface OssService {

    void fileUpload(String object, String filename) throws IOException;


    void fileUpload(String bucket, String object, String filename) throws IOException;

    String getObjectUrl();
}
