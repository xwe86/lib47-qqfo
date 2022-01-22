package cn.kizzzy.qqfo;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.PkgFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqfoPackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;
import cn.kizzzy.vfs.tree.QqfoTreeBuilder;

import java.util.LinkedList;
import java.util.List;

public class ExportFileTest {
    
    public static void main(String[] args) {
        String pkgName = "objects3";
        String exportRoot = "E:\\88Extrator\\QQFO\\Source\\" + pkgName;
        String dataRoot = "E:\\04Games\\Tencent\\ZYHX\\QQ幻想\\data";
        
        IPackage exportVfs = new FilePackage(exportRoot);
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(PkgFile.class, new PkgFileHandler());
        
        PkgFile pkgFile = dataVfs.load(pkgName + ".pkg", PkgFile.class);
        if (pkgFile == null) {
            LogHelper.error("load pkg failed: {}", pkgName);
            return;
        }
        
        ITree tree = new QqfoTreeBuilder(pkgFile, new IdGenerator()).build();
        
        IPackage qqfoVfs = new QqfoPackage(dataRoot, tree);
        
        List<Node> nodes = tree.listNode("");
        for (Node node : nodes) {
            listNodeImpl(node, qqfoVfs, exportVfs);
        }
    }
    
    private static void listNodeImpl(Node node, IPackage qqfoVfs, IPackage exportVfs) {
        if (node.leaf) {
            Leaf leaf = (Leaf) node;
            System.out.println("export: " + leaf.path);
            
            byte[] data = qqfoVfs.load(leaf.path, byte[].class);
            exportVfs.save(leaf.path, data);
        } else {
            List<Node> list = new LinkedList<>(node.children.values());
            list.sort(new NodeComparator());
            
            for (Node child : list) {
                listNodeImpl(child, qqfoVfs, exportVfs);
            }
        }
    }
}
