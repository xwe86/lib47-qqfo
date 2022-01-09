package cn.kizzzy.qqfo;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.io.ByteArrayInputStreamReader;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;

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
    public FullyReader OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        FullyReader reader = new SliceFullReader(source.OpenStream(), offset, size);
        byte[] buffer = new byte[size];
        reader.read(buffer);
        return new SliceFullReader(new ByteArrayInputStreamReader(ZlibHelper.uncompress(buffer)));
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
