package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;
import cn.kizzzy.vfs.IStreamable;

public class GsnFrameV1 implements GsnFrame {
    public int reserved_01;
    public int reserved_02;
    public int reserved_03;
    public int width;
    public int height;
    public int reserved_06;
    public int type;
    
    public int index;
    public boolean valid;
    
    public GsnFile file;
    public long offset;
    public int size;
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getType() {
        return type;
    }
    
    @Override
    public boolean isValid() {
        return valid;
    }
    
    @Override
    public IStreamable getSource() {
        return file;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.file = (GsnFile) source;
    }
    
    @Override
    public FullyReader OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SliceFullReader(getSource().OpenStream(), offset, size);
    }
}
