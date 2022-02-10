package cn.kizzzy.qqfo;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IStreamable;

public class GsnFrame implements IStreamable {
    
    public int width;
    public int height;
    public int type;
    
    public int index;
    public boolean valid;
    
    public GsnFile file;
    public long offset;
    public int size;
    
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
