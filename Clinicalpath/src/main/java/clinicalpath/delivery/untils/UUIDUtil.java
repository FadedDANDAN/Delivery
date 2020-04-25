package clinicalpath.delivery.untils;

import java.util.UUID;

public class UUIDUtil {
    public static synchronized String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
