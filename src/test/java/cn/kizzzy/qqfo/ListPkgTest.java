package cn.kizzzy.qqfo;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.IdxFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.IdxTreeBuilder;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;

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
        dataVfs.getHandlerKvs().put(IdxFile.class, new IdxFileHandler());
        
        IdxFile idxFile = dataVfs.load(pkgName + ".pkg", IdxFile.class);
        if (idxFile == null) {
            LogHelper.error("load pkg failed: {}", pkgName);
            return;
        }
        
        ITree tree = new IdxTreeBuilder(idxFile, new IdGenerator()).build();
        
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
    
    private static void listNodeImpl(ITree tree, String path) {
        List<Node> list = tree.listNode(path);
        list.sort(new NodeComparator());
        System.out.printf("path: %-32s, node count: %4d, list:", path, list.size());
        for (Node item : list) {
            System.out.print(" " + item.name);
        }
        System.out.println();
    }
}
