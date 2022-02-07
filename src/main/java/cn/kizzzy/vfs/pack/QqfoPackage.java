package cn.kizzzy.vfs.pack;

import cn.kizzzy.qqfo.GsnFile;
import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.GsnFileHandler;
import cn.kizzzy.vfs.handler.GsoFileHandler;

public class QqfoPackage extends IdxPackage {
    
    public QqfoPackage(String root, ITree tree) {
        super(root, tree);
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(GsoFile.class, new GsoFileHandler());
        handlerKvs.put(GsnFile.class, new GsnFileHandler());
    }
}
