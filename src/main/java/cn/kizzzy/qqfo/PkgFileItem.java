package cn.kizzzy.qqfo;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.io.SubStream;
import cn.kizzzy.vfs.IStreamable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PkgFileItem implements IStreamable {
    
    public int nameLength;
    
    public String path;
    
    public int reserved;
    
    public long offset;
    
    public int originSize;
    
    public int size;
    
    /**
     *
     */
    public String pkg;
    
    private IStreamable source;
    
    @Override
    public IStreamable getSource() {
        return source;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.source = source;
    }
    
    @Override
    public InputStream OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        SubStream stream = new SubStream(source.OpenStream(), offset, size);
        byte[] buffer = stream.readBytes(size);
        ByteArrayInputStream bis = new ByteArrayInputStream(ZlibHelper.uncompress(buffer));
        return bis;
    }
    
    @Override
    public String toString() {
        return "PkgFileItem{path: " + path +
            ", reserved: " + reserved +
            ", offset: " + offset +
            ", origin: " + originSize +
            ", size: " + size +
            "}";
    }
}
