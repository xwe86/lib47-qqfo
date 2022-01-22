package cn.kizzzy.vfs.tree;

import cn.kizzzy.qqfo.PkgFile;
import cn.kizzzy.qqfo.PkgFileItem;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;

public class QqfoTreeBuilder extends TreeBuilder {
    
    private final PkgFile pkgFile;
    
    public QqfoTreeBuilder(PkgFile pkgFile, IdGenerator idGenerator) {
        super(Separator.BACKSLASH_SEPARATOR_LOWERCASE, idGenerator);
        this.pkgFile = pkgFile;
    }
    
    @Override
    public ITree build() {
        Root root = new Root(idGenerator.getId(), pkgFile.path);
        for (PkgFileItem item : pkgFile.fileKvs.values()) {
            listImpl(root, root, item);
        }
        return new Tree(root, separator);
    }
    
    private void listImpl(Root root, Node parent, PkgFileItem item) {
        String[] names = separator.split(item.path);
        for (String name : names) {
            Node child = parent.children.get(name);
            if (child == null) {
                if (name.contains(".")) {
                    Leaf leaf = new Leaf(idGenerator.getId(), name, root.name, item.path, item);
                    root.fileKvs.put(leaf.path, leaf);
                    child = leaf;
                } else {
                    child = new Node(idGenerator.getId(), name);
                }
                root.folderKvs.put(child.id, child);
                parent.children.put(name, child);
            }
            parent = child;
        }
    }
}
