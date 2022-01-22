package cn.kizzzy.qqfo;

import cn.kizzzy.vfs.IStreamable;

public interface GsnFrame extends IStreamable {
    
    int getWidth();
    
    int getHeight();
    
    int getType();
    
    boolean isValid();
}
