package cn.kizzzy.vfs.pack;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.qqfo.PkgFileItem;
import cn.kizzzy.vfs.IFileLoader;
import cn.kizzzy.vfs.IFileSaver;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.GsoFileHandler;
import cn.kizzzy.vfs.streamable.FileStreamable;
import cn.kizzzy.vfs.tree.Leaf;

public class QqfoPackage extends PackageAdapter {
    
    private final ITree<PkgFileItem> tree;
    
    public QqfoPackage(String root, ITree<PkgFileItem> tree) {
        super(root);
        this.tree = tree;
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(GsoFile.class, new GsoFileHandler());
    }
    
    @Override
    public boolean exist(String path) {
        return tree.getLeaf(path) != null;
    }
    
    @Override
    protected Object loadImpl(String path, IFileLoader<?> loader) throws Exception {
        Leaf<PkgFileItem> leaf = tree.getLeaf(path);
        if (leaf == null) {
            return null;
        }
        
        PkgFileItem file = leaf.item;
        if (file == null) {
            return null;
        }
        
        String fullPath = FILE_SEPARATOR.combine(root, file.pkg);
        if (file.getSource() == null) {
            file.setSource(new FileStreamable(fullPath));
        }
        
        try (FullyReader reader = file.OpenStream()) {
            Object obj = loader.load(this, path, reader, file.originSize);
            if (obj instanceof IStreamable) {
                ((IStreamable) obj).setSource(file);
            }
            return obj;
        }
    }
    
    @Override
    protected <T> boolean saveImpl(String path, T data, IFileSaver<T> saver) throws Exception {
        return false;
    }
}
