package cn.kizzzy.image.sizer;

import cn.kizzzy.image.Sizer;

import java.util.HashMap;
import java.util.Map;

public class QqfoSizerHelper {
    
    protected static final Map<Integer, Sizer> sizerKvs;
    
    static {
        sizerKvs = new HashMap<>();
        sizerKvs.put(0, new ARGB1555Sizer());
        sizerKvs.put(1, new ARGB4444Sizer());
        sizerKvs.put(2, new ARGB8888Sizer());
        sizerKvs.put(3, new Dxt1Sizer());
        sizerKvs.put(4, new Dxt3Sizer());
        sizerKvs.put(5, new Dxt5Sizer());
    }
    
    public static int calc(int type, int width, int height) {
        Sizer sizer = sizerKvs.get(type);
        if (sizer != null) {
            return sizer.calc(width, height);
        }
        return 0;
    }
}
