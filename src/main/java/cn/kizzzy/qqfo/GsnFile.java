package cn.kizzzy.qqfo;

import cn.kizzzy.qqfo.gsn.GsnFrameV2;
import cn.kizzzy.qqfo.gsn.GsnFrameV4;
import cn.kizzzy.vfs.IStreamable;

public class GsnFile implements IStreamable {
    
    public String magic;
    public int major;
    public int minor;
    public int reserved04;
    
    public int count;
    public int reserved06;
    public int reserved07;
    public int reserved08;
    public int reserved09;
    public int reserved10;
    public int reserved11;
    public int reserved12;
    public int reserved13;
    public int reserved14;
    public int reserved15;
    public int reserved16;
    
    public GsnFrame[] frames;
    
    private IStreamable source;
    
    @Override
    public IStreamable getSource() {
        return source;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.source = source;
    }
}
