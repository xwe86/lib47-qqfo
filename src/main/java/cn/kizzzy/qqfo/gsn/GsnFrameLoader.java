package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;

import java.io.IOException;

public interface GsnFrameLoader {
    
    void loadHeader(IFullyReader reader, GsnFile file) throws IOException;
    
    GsnFrame loadFrame(IFullyReader reader, GsnFile file, int index) throws IOException;
    
    default boolean checkValid(int width, int height) {
        return width > 0 && width < 4096 && height > 0 && height < 4096;
    }
}
