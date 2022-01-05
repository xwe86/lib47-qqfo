package cn.kizzzy.qqfo;

import cn.kizzzy.vfs.IStreamable;

import java.util.Arrays;

public class GsoFile implements IStreamable {
    
    public static class Unknown2 {
        public int reserved01;
        public int reserved02;
        public int reserved03;
        
        @Override
        public String toString() {
            return "Unknown1{" +
                "reserved01=" + reserved01 +
                ", reserved02=" + reserved02 +
                ", reserved03=" + reserved03 +
                '}';
        }
    }
    
    public int magic_01;
    public int magic_02;
    public int major;
    public int minor;
    public int reserved4;
    
    public int count;
    public int reserved6;
    public int reserved7;
    public int reserved8;
    public int reserved9;
    public int maxWidth;
    public int maxHeight;
    public int pivotX;
    public int pivotY;
    public int reserved14;
    public int unknownCount1;
    public int[] unknowns1;
    
    public int unknownCount2;
    public Unknown2[] unknowns2;
    
    public GsoFileItems[] items;
    
    private IStreamable source;
    
    @Override
    public IStreamable getSource() {
        return source;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.source = source;
    }
    
    @Override
    public String toString() {
        return "GsoFile{" + "\r\n\t" +
            "magic_01=" + magic_01 + "\r\n\t" +
            ", magic_02=" + magic_02 + "\r\n\t" +
            ", major=" + major + "\r\n\t" +
            ", minor=" + minor + "\r\n\t" +
            ", reserved4=" + reserved4 + "\r\n\t" +
            ", count=" + count + "\r\n\t" +
            ", reserved6=" + reserved6 + "\r\n\t" +
            ", reserved7=" + reserved7 + "\r\n\t" +
            ", reserved8=" + reserved8 + "\r\n\t" +
            ", reserved9=" + reserved9 + "\r\n\t" +
            ", maxWidth=" + maxWidth + "\r\n\t" +
            ", maxHeight=" + maxHeight + "\r\n\t" +
            ", reserved12=" + pivotX + "\r\n\t" +
            ", reserved13=" + pivotY + "\r\n\t" +
            ", reserved14=" + reserved14 + "\r\n\t" +
            ", unknownCount1=" + unknownCount1 + "\r\n\t" +
            ", unknowns1=" + Arrays.toString(unknowns1) + "\r\n\t" +
            ", unknownFlag2=" + unknownCount2 + "\r\n\t" +
            ", unknown2=" + Arrays.toString(unknowns2) + "\r\n\t" +
            ", items=" + Arrays.toString(items) + "\r\n" +
            '}';
    }
}
