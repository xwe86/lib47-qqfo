package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.image.sizer.QqfoSizerHelper;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;

import java.io.IOException;

public class GsnFrameV2Loader implements GsnFrameLoader {
    
    @Override
    public void loadHeader(IFullyReader reader, GsnFile file) throws IOException {
    
    }
    
    @Override
    public GsnFrame loadFrame(IFullyReader reader, GsnFile file, int index) throws IOException {
        GsnFrameV2 frame = new GsnFrameV2();
        frame.index = index;
        frame.file = file;
        
        frame.offsetX = reader.readIntEx();
        frame.offsetY = reader.readIntEx();
        frame.centerX = reader.readIntEx();
        frame.centerY = reader.readIntEx();
        frame.reserved_05 = reader.readIntEx();
        frame.reserved_06 = reader.readIntEx();
        frame.reserved_07 = reader.readIntEx();
        frame.reserved_08 = reader.readIntEx();
        frame.reserved_09 = reader.readIntEx();
        frame.reserved_10 = reader.readIntEx();
        frame.reserved_11 = reader.readIntEx();
        frame.reserved_12 = reader.readIntEx();
        frame.width = reader.readIntEx();
        frame.height = reader.readIntEx();
        frame.reserved_15 = reader.readIntEx();
        frame.type = reader.readIntEx();
        
        frame.valid = 0 < frame.width && frame.width < 4096
            && 0 < frame.height && frame.height < 4096;
        
        frame.offset = reader.position();
        frame.size = QqfoSizerHelper.calc(frame.type, frame.width, frame.height);
        
        reader.seek(frame.size, SeekType.CURRENT);
        
        frame.final_01 = reader.readIntEx();
        frame.final_02 = reader.readIntEx();
        frame.final_03 = reader.readIntEx();
        frame.final_04 = reader.readIntEx();
        frame.final_05 = reader.readIntEx();
        frame.final_06 = reader.readIntEx();
        frame.final_07 = reader.readIntEx();
        frame.final_08 = reader.readIntEx();
        
        return frame;
    }
}
