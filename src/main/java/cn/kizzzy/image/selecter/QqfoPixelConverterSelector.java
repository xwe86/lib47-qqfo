package cn.kizzzy.image.selecter;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.converter.ARGB1555PixelConverter;
import cn.kizzzy.image.converter.ARGB4444PixelConverter;
import cn.kizzzy.image.converter.ARGB8888PixelConverter;
import cn.kizzzy.image.converter.DXT1PixelConverter;
import cn.kizzzy.image.converter.DXT3PixelConverter;
import cn.kizzzy.image.converter.DXT5PixelConverter;
import cn.kizzzy.image.selector.DefaultPixelConverterSelector;

import java.util.HashMap;
import java.util.Map;

public class QqfoPixelConverterSelector extends DefaultPixelConverterSelector {
    
    public QqfoPixelConverterSelector() {
        super(initKvs());
    }
    
    private static Map<Integer, PixelConverter> initKvs() {
        Map<Integer, PixelConverter> converterKvs = new HashMap<>();
        converterKvs.put(0x00, new ARGB1555PixelConverter());
        converterKvs.put(0x01, new ARGB4444PixelConverter());
        converterKvs.put(0x02, new ARGB8888PixelConverter());
        converterKvs.put(0x03, new DXT1PixelConverter());
        converterKvs.put(0x04, new DXT3PixelConverter());
        converterKvs.put(0x05, new DXT5PixelConverter());
        return converterKvs;
    }
}
