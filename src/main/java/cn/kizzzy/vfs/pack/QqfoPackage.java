package cn.kizzzy.vfs.pack;

import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.qqfo.PkgFileItem;
import cn.kizzzy.vfs.IFileLoader;
import cn.kizzzy.vfs.IFileSaver;
import cn.kizzzy.vfs.IStrategy;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.ListNode;
import cn.kizzzy.vfs.ListParameter;
import cn.kizzzy.vfs.handler.GsoFileHandler;
import cn.kizzzy.vfs.streamable.FileStreamable;
import cn.kizzzy.vfs.tree.Leaf;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QqfoPackage extends PackageAdapter {
    
    private final ITree<PkgFileItem> tree;
    
    public QqfoPackage(String root, ITree<PkgFileItem> tree) {
        this(root, tree, null);
    }
    
    public QqfoPackage(String root, ITree<PkgFileItem> tree, IStrategy<String, Object> strategy) {
        super(root, '\\', strategy);
        this.tree = tree;
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(GsoFile.class, new GsoFileHandler());
    }
    
    @Override
    protected ListNode listImpl(String path, ListParameter param) throws Exception {
        throw new UnsupportedOperationException("pkg package is not support list operation");
    }
    
    @Override
    public boolean exist(String path) {
        return tree.getFile(path) != null;
    }
    
    @Override
    protected Object loadImpl(String path, IFileLoader<?> loader) throws Exception {
        Leaf<PkgFileItem> leaf = tree.getFile(path);
        if (leaf == null) {
            return null;
        }
        
        PkgFileItem file = leaf.item;
        if (file == null) {
            return null;
        }
        
        Path fullPath = Paths.get(root, file.pkg);
        if (file.getSource() == null) {
            file.setSource(new FileStreamable(fullPath.toString()));
        }
        
        try (InputStream stream = file.OpenStream()) {
            Object obj = loader.load(this, path, stream, file.originSize);
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
