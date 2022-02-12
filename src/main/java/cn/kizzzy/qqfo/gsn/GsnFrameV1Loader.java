package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.image.sizer.QqfoSizerHelper;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;

import java.io.IOException;

public class GsnFrameV1Loader implements GsnFrameLoader {
    
    @Override
    public void loadHeader(IFullyReader reader, GsnFile file) throws IOException {
        file.reserved08 = reader.readIntEx();
        file.reserved09 = reader.readIntEx();
        file.reserved10 = reader.readIntEx();
        file.reserved11 = reader.readIntEx();
        file.reserved12 = reader.readIntEx();
        file.reserved13 = reader.readIntEx();
        file.reserved14 = reader.readIntEx();
        file.reserved15 = reader.readIntEx();
        file.reserved16 = reader.readIntEx();
    }
    
    @Override
    public GsnFrame loadFrame(IFullyReader reader, GsnFile file, int index) throws IOException {
        GsnFrameV1 frame = new GsnFrameV1();
        frame.index = index;
        frame.file = file;
        
        frame.reserved_01 = reader.readIntEx();
        frame.reserved_02 = reader.readIntEx();
        frame.reserved_03 = reader.readIntEx();
        frame.width = reader.readIntEx();
        frame.height = reader.readIntEx();
        frame.reserved_06 = reader.readIntEx();
        frame.type = reader.readIntEx();
        
        frame.valid = 0 < frame.width && frame.width < 4096
            && 0 < frame.height && frame.height < 4096;
        
        frame.offset = reader.position();
        frame.size = QqfoSizerHelper.calc(frame.type, frame.width, frame.height);
        
        reader.seek(frame.size, SeekType.CURRENT);
        
        return frame;
    }
}
