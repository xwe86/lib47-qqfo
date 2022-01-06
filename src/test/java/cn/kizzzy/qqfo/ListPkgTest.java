package cn.kizzzy.qqfo;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.PkgFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.LocalTree;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;
import cn.kizzzy.vfs.tree.QqfoTreeBuilder;

import java.awt.image.BufferedImage;
import java.util.List;

public class ListPkgTest {
    
    public static void main(String[] args) {
        String pkgName = "objects3";
        String dataRoot = "E:\\04Games\\Tencent\\ZYHX\\QQ幻想\\data";
        String extractRoot = "E:\\88Extrator\\QQFO\\Export\\" + pkgName;
        
        IPackage tempVfs = new FilePackage(extractRoot);
        tempVfs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(PkgFile.class, new PkgFileHandler());
        
        PkgFile pkgFile = dataVfs.load(pkgName + ".pkg", PkgFile.class);
        if (pkgFile == null) {
            LogHelper.error("load pkg failed: {}", pkgName);
            return;
        }
        
        ITree<PkgFileItem> tree = new LocalTree<>(
            new QqfoTreeBuilder(pkgFile, new IdGenerator()).build(),
            Separator.BACKSLASH_SEPARATOR_LOWERCASE
        );
        
        String[] paths = new String[]{
            "",
            "object",
            "cc",
            "object/cc",
            "object/cc/cc_cl_0101.gso",
        };
        for (String path : paths) {
            listNodeImpl(tree, path);
        }
    }
    
    private static void listNodeImpl(ITree<PkgFileItem> tree, String path) {
        List<Node<PkgFileItem>> list = tree.listNode(path);
        list.sort(new NodeComparator<>());
        System.out.printf("path: %-32s, node count: %4d, list:", path, list.size());
        for (Node<PkgFileItem> item : list) {
            System.out.print(" " + item.name);
        }
        System.out.println();
    }
}