package cn.kizzzy.qqfo;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.qqfo.helper.QqfoImgHelper;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.IdxFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqfoPackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.IdxTreeBuilder;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class ExportImageTest {
    
    public static void main(String[] args) {
        String pkgName = "objects3";
        String exportRoot = "E:\\88Extrator\\QQFO\\Export\\" + pkgName;
        String dataRoot = "E:\\04Games\\Tencent\\ZYHX\\QQ幻想\\data";
        
        IPackage exportVfs = new FilePackage(exportRoot);
        exportVfs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(IdxFile.class, new IdxFileHandler());
        
        IdxFile pkgFile = dataVfs.load(pkgName + ".pkg", IdxFile.class);
        if (pkgFile == null) {
            LogHelper.error("load pkg failed: {}", pkgName);
            return;
        }
        
        ITree tree = new IdxTreeBuilder(pkgFile, new IdGenerator()).build();
        
        IPackage qqfoVfs = new QqfoPackage(dataRoot, tree);
        
        List<Node> nodes = tree.listNode("object/cc");
        for (Node node : nodes) {
            listNodeImpl(node, qqfoVfs, exportVfs);
        }
    }
    
    private static void listNodeImpl(Node node, IPackage qqfoVfs, IPackage exportVfs) {
        if (node.leaf) {
            Leaf leaf = (Leaf) node;
            if (leaf.path.contains(".gso")) {
                System.out.println("export: " + leaf.path);
                
                GsoFile img = qqfoVfs.load(leaf.path, GsoFile.class);
                if (img != null) {
                    for (GsoFileItems items : img.items) {
                        for (GsoFileItem item : items.items) {
                            if (item != null) {
                                BufferedImage image = QqfoImgHelper.toImage(item);
                                if (image != null) {
                                    String fullPath = leaf.path.replace(".gso", String.format("-%02d-%02d.png", item.i, item.j));
                                    exportVfs.save(fullPath, image);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            List<Node> list = new LinkedList<>(node.children.values());
            list.sort(new NodeComparator());
            
            for (Node child : list) {
                listNodeImpl(child, qqfoVfs, exportVfs);
            }
        }
    }
}
