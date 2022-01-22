package cn.kizzzy.vfs.handler;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;
import cn.kizzzy.qqfo.gsn.GsnFrameLoader;
import cn.kizzzy.qqfo.gsn.GsnFrameV1Loader;
import cn.kizzzy.qqfo.gsn.GsnFrameV2Loader;
import cn.kizzzy.qqfo.gsn.GsnFrameV4Loader;
import cn.kizzzy.vfs.IPackage;

import java.util.HashMap;
import java.util.Map;

public class GsnFileHandler extends StreamFileHandler<GsnFile> {
    
    private static final Map<Integer, GsnFrameLoader> loaderKvs;
    
    static {
        loaderKvs = new HashMap<>();
        loaderKvs.put(0x01, new GsnFrameV1Loader());
        loaderKvs.put(0x02, new GsnFrameV2Loader());
        loaderKvs.put(0x04, new GsnFrameV4Loader());
        loaderKvs.put(0x05, new GsnFrameV4Loader());
    }
    
    @Override
    protected GsnFile loadImpl(IPackage pack, String path, FullyReader reader) throws Exception {
        GsnFile file = null;
        try {
            file = new GsnFile();
            file.magic = reader.readString(8);
            file.major = reader.readShortEx();
            file.minor = reader.readShortEx();
            file.reserved04 = reader.readIntEx();
            file.count = reader.readIntEx();
            file.reserved06 = reader.readIntEx();
            file.reserved07 = reader.readIntEx();
            GsnFrameLoader loader = loaderKvs.get(file.major);
            if (loader == null) {
                throw new RuntimeException("unknown major: " + file.major);
            }
            
            loader.loadHeader(reader, file);
            
            file.frames = new GsnFrame[file.count];
            for (int i = 0; i < file.count; ++i) {
                file.frames[i] = loader.loadFrame(reader, file, i);
            }
        } catch (Exception e) {
            LogHelper.error(String.format("failed to load gsn file : %s, %s", path, file), e);
        }
        return file;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, GsnFile data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}
