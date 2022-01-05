package cn.kizzzy.vfs.tree;

import cn.kizzzy.qqfo.PkgFile;
import cn.kizzzy.qqfo.PkgFileItem;

public class QqfoTreeHelper {
    
    public static Root<PkgFileItem> ToPack(PkgFile pkg) {
        Root<PkgFileItem> pack = new Root<>(pkg.path);
        
        for (PkgFileItem file : pkg.fileKvs.values()) {
            Leaf<PkgFileItem> file1 = new Leaf<>();
            file1.pack = pkg.path;
            file1.path = file.path;
            file1.item = file;
            
            pack.fileKvs.put(file1.path, file1);
            
            ListFolder(pack, pack, file1);
        }
        
        return pack;
    }
    
    private static void ListFolder(Root<PkgFileItem> pack, Node<PkgFileItem> parent, Leaf<PkgFileItem> file) {
        String[] paths = file.path.split("\\\\");
        for (String path : paths) {
            Node<PkgFileItem> child = parent.children.get(path);
            if (child == null) {
                child = new Node<>(path, parent, path.contains(".") ? file : null);
                parent.children.put(path, child);
                
                pack.folderKvs.put(child.id, child);
            }
            parent = child;
        }
    }
}
