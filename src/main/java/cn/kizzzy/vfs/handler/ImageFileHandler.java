package cn.kizzzy.vfs.handler;

import java.util.HashMap;
import java.util.Map;

public abstract class ImageFileHandler<T> extends StreamFileHandler<T> {
    
    public interface IReadParam {
        int Calc(int width, int height);
    }
    
    public static class ARGBReadParam implements IReadParam {
        private int unit;
        
        public ARGBReadParam(int unit) {
            this.unit = unit;
        }
        
        public int Calc(int width, int height) {
            return width * height * unit;
        }
    }
    
    public static class DxtReadParam implements IReadParam {
        private int unit;
        
        public DxtReadParam(int unit) {
            this.unit = unit;
        }
        
        public int Calc(int width, int height) {
            return find2n(width) * find2n(height) * unit;
        }
        
        private int find2n(int target) {
            int temp = target - 1;
            for (int shift : new int[]{
                1,
                2,
                4,
                8,
                16
            }) {
                temp |= temp >> shift;
            }
            return (temp < 0) ? 1 : temp + 1;
        }
    }
    
    protected static final Map<Integer, IReadParam> readParamKvs;
    
    static {
        readParamKvs = new HashMap<>();
        readParamKvs.put(0, new ARGBReadParam(2));
        readParamKvs.put(1, new ARGBReadParam(2));
        readParamKvs.put(2, new ARGBReadParam(4));
        readParamKvs.put(3, new DxtReadParam(2));
        readParamKvs.put(4, new DxtReadParam(4));
        readParamKvs.put(5, new DxtReadParam(4));
    }
    
    protected boolean checkValid(int width, int height) {
        return width > 0 && width < 4096 && height > 0 && height < 4096;
    }
}
