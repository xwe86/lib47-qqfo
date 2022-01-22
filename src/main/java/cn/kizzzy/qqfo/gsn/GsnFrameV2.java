package cn.kizzzy.qqfo.gsn;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsnFrame;
import cn.kizzzy.vfs.IStreamable;

public class GsnFrameV2 implements GsnFrame {
    public int offsetX;
    public int offsetY;
    public int centerX;
    public int centerY;
    public int reserved_05;
    public int reserved_06;
    public int reserved_07;
    public int reserved_08;
    public int reserved_09;
    public int reserved_10;
    public int reserved_11;
    public int reserved_12;
    public int width;
    public int height;
    public int reserved_15;
    public int type;
    public int final_01;
    public int final_02;
    public int final_03;
    public int final_04;
    public int final_05;
    public int final_06;
    public int final_07;
    public int final_08;
    
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
