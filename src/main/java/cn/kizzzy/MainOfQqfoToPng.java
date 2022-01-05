package cn.kizzzy;

import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.qqfo.GsoFileItem;
import cn.kizzzy.qqfo.GsoFileItems;
import cn.kizzzy.qqfo.helper.QqfoImgHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ListNode;
import cn.kizzzy.vfs.ListParameter;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.GsoFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;

import java.awt.image.BufferedImage;

public class MainOfQqfoToPng {
    
    public static void main(String[] args) {
        IPackage tempKvs = new FilePackage("E:\\88Extrator\\QQFO\\Export\\objects2");
        tempKvs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage qqfoVfs = new FilePackage("E:\\88Extrator\\QQFO\\Source\\objects2");
        qqfoVfs.getHandlerKvs().put(GsoFile.class, new GsoFileHandler());
        
        ListNode root = qqfoVfs.list("magic\\magic", new ListParameter(true));
        
        listImpl(root, tempKvs, qqfoVfs);
    }
    
    private static void listImpl(ListNode node, IPackage tempKvs, IPackage qqfoVfs) {
        if (node.children != null && node.children.size() > 0) {
            for (ListNode child : node.children) {
                listImpl(child, tempKvs, qqfoVfs);
            }
        } else if (node.path.endsWith(".gso")) {
            GsoFile img = qqfoVfs.load(node.path, GsoFile.class);
            if (img != null) {
                for (GsoFileItems items : img.items) {
                    for (GsoFileItem item : items.items) {
                        if (item != null) {
                            BufferedImage image = QqfoImgHelper.toImage(item);
                            if (image != null) {
                                String fullPath = node.path.replace(".gso", String.format("-%02d-%02d.png", item.i, item.j));
                                tempKvs.save(fullPath, image);
                            }
                        }
                    }
                }
            }
        }
    }
}
