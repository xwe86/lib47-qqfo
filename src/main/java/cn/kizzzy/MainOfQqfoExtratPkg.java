package cn.kizzzy;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.qqfo.GsoFileItem;
import cn.kizzzy.qqfo.GsoFileItems;
import cn.kizzzy.qqfo.PkgFile;
import cn.kizzzy.qqfo.PkgFileItem;
import cn.kizzzy.qqfo.helper.QqfoImgHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.GsoFileHandler;
import cn.kizzzy.vfs.handler.PkgFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqfoPackage;
import cn.kizzzy.vfs.tree.LocalTree;
import cn.kizzzy.vfs.tree.QqfoTreeHelper;

import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MainOfQqfoExtratPkg {
    
    public static void main(String[] args) {
        String pkgName = "objects3";
        
        IPackage tempVfs = new FilePackage("E:\\88Extrator\\QQFO\\Export\\" + pkgName);
        tempVfs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage dataVfs = new FilePackage("E:\\04Games\\Tencent\\ZYHX\\QQ幻想\\data");
        dataVfs.getHandlerKvs().put(PkgFile.class, new PkgFileHandler());
        
        PkgFile pkgFile = dataVfs.load(pkgName + ".pkg", PkgFile.class);
        if (pkgFile == null) {
            LogHelper.error("load pkg failed: {}", pkgName);
            return;
        }
        
        ITree<PkgFileItem> tree = new LocalTree<>(QqfoTreeHelper.ToPack(pkgFile));
        
        IPackage qqfoVfs = new QqfoPackage("E:\\04Games\\Tencent\\ZYHX\\QQ幻想\\data", tree);
        qqfoVfs.getHandlerKvs().put(GsoFile.class, new GsoFileHandler());
        
        List<PkgFileItem> list = new LinkedList<>();
        
        for (PkgFileItem item : pkgFile.fileKvs.values()) {
            if (item.path.contains(".gso")) {
                list.add(item);
            }
        }
        
        list.sort(Comparator.comparing(x -> x.path));
        
        for (PkgFileItem item : list) {
            GsoFile img = qqfoVfs.load(item.path, GsoFile.class);
            if (img != null) {
                for (GsoFileItems items : img.items) {
                    for (GsoFileItem item2 : items.items) {
                        if (item2 != null) {
                            BufferedImage image = QqfoImgHelper.toImage(item2);
                            if (image != null) {
                                String fullPath = item.path.replace(".gso", String.format("-%02d-%02d.png", item2.i, item2.j));
                                tempVfs.save(fullPath, image);
                            }
                        }
                    }
                }
            }
        }
    }
}
