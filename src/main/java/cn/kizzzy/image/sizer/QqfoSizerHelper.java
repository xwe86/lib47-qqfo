package cn.kizzzy.image.sizer;

import cn.kizzzy.image.Sizer;

import java.util.HashMap;
import java.util.Map;

public class QqfoSizerHelper {
    
    protected static final Map<Integer, Sizer> sizerKvs;
    
    static {
        sizerKvs = new HashMap<>();
        sizerKvs.put(0, new ARGBSizer(2));
        sizerKvs.put(1, new ARGBSizer(2));
        sizerKvs.put(2, new ARGBSizer(4));
        sizerKvs.put(3, new DxtSizer(2));
        sizerKvs.put(4, new DxtSizer(1));
        sizerKvs.put(5, new DxtSizer(1));
    }
    
    public static int calc(int type, int width, int height) {
        Sizer sizer = sizerKvs.get(type);
        if (sizer != null) {
            return sizer.calc(width, height);
        }
        return 0;
    }
}
