package cn.kizzzy.image.creator;

import cn.kizzzy.image.PixelConverterSelector;
import cn.kizzzy.image.selecter.QqfoPixelConverterSelector;

public abstract class QqfoImgCreator<T, R> extends ImageCreatorAdapter<T, R> {
    
    protected static final PixelConverterSelector DEFAULT_SELECTOR
        = new QqfoPixelConverterSelector();
    
    public QqfoImgCreator() {
        super(DEFAULT_SELECTOR);
    }
}
