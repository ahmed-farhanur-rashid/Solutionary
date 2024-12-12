package UI;

import java.net.URL;

public class ResourceHandler {



    public static URL get (String path) {

        return ResourceHandler.class.getResource(path);
    }
}
