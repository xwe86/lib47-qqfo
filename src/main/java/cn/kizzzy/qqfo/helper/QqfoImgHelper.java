package cn.kizzzy.qqfo.helper;

import cn.kizzzy.image.ImageCreator;
import cn.kizzzy.image.creator.BufferedImageCallback;
import cn.kizzzy.image.creator.GsoImgCreator;
import cn.kizzzy.qqfo.GsoFileItem;

import java.awt.image.BufferedImage;

public class QqfoImgHelper {
    
    private static final ImageCreator<GsoFileItem, BufferedImage> creator_1
        = new GsoImgCreator<>();
    
    public static BufferedImage toImage(GsoFileItem item) {
        return creator_1.Create(item, new BufferedImageCallback());
    }
    
    public static BufferedImage toImageFix(GsoFileItem item) {
        int maxWidth = item.file.maxWidth;
        int maxHeight = item.file.maxHeight;
        int offsetX = item.offsetX;
        int offsetY = item.offsetY;
        
        BufferedImage image = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(toImage(item), offsetX, offsetY, null);
        return image;
    }
}
