import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class FastDFS {

    @Test
    public void test() throws IOException, MyException {
        ClientGlobal.init("properties/Client.conf");
        // 3、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        // 4、创建一个TrackerServer对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5、声明一个StorageServer对象，null。
        StorageServer storageServer = null;
        // 6、获得StorageClient对象。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7、直接调用StorageClient对象方法上传文件即可。
        String[] strings = storageClient.upload_file("G:\\images\\2.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }

    }

}
