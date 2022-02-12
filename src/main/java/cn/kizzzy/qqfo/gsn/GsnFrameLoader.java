package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;

import java.io.IOException;

public interface GsnFrameLoader {
    
    void loadHeader(IFullyReader reader, GsnFile file) throws IOException;
    
    GsnFrame loadFrame(IFullyReader reader, GsnFile file, int index) throws IOException;
}
