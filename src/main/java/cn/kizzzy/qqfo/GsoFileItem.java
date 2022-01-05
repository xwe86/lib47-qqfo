package cn.kizzzy.qqfo;

import cn.kizzzy.io.SubStream;
import cn.kizzzy.vfs.IStreamable;

import java.io.InputStream;

public class GsoFileItem implements IStreamable {
    
    public int reserved01;
    
    public int offsetX;
    
    public int offsetY;
    
    public int width;
    
    public int height;
    
    public int reserved06;
    
    public int type;
    
    public int centerX;
    
    public int centerY;
    
    public int final03;
    
    /**
     *
     */
    public int i;
    
    /**
     *
     */
    public int j;
    
    /**
     *
     */
    public boolean valid;
    
    /**
     *
     */
    public GsoFile file;
    
    /**
     *
     */
    public int offset;
    
    /**
     *
     */
    public int size;
    
    @Override
    public IStreamable getSource() {
        return file;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.file = (GsoFile) source;
    }
    
    @Override
    public InputStream OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SubStream(getSource().OpenStream(), offset, size);
    }
    
    @Override
    public String toString() {
        return "GsoFileItem{" + "\r\n\t" +
            "i=" + i + "\r\n\t" +
            ", j=" + j + "\r\n\t" +
            ", reserved01=" + reserved01 + "\r\n\t" +
            ", offsetX=" + offsetX + "\r\n\t" +
            ", offsetY=" + offsetY + "\r\n\t" +
            ", width=" + width + "\r\n\t" +
            ", height=" + height + "\r\n\t" +
            ", reserved06=" + reserved06 + "\r\n\t" +
            ", type=" + type + "\r\n\t" +
            ", final01=" + centerX + "\r\n\t" +
            ", final02=" + centerY + "\r\n\t" +
            ", final03=" + final03 + "\r\n\t" +
            '}';
    }
}
