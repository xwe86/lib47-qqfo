package cn.kizzzy.qqfo;

import java.util.HashMap;
import java.util.Map;

public class PkgFile {
    
    public final String path;
    
    public int magic;
    
    public long indexCount;
    
    public long indexPosition;
    
    public long indexSize;
    
    public final Map<String, PkgFileItem> fileKvs
        = new HashMap<>();
    
    public PkgFile(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return "Q3Pkg{" +
            "path='" + path + '\'' +
            ", magic=" + magic +
            ", indexCount=" + indexCount +
            ", indexPosition=" + indexPosition +
            ", indexSize=" + indexSize +
            '}';
    }
}
