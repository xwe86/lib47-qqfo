package cn.kizzzy.vfs.handler;

public abstract class ImageFileHandler<T> extends StreamFileHandler<T> {
    
    protected boolean checkValid(int width, int height) {
        return width > 0 && width < 4096 && height > 0 && height < 4096;
    }
}
