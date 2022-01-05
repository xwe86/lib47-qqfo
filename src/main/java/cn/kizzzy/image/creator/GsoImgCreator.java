package cn.kizzzy.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.io.DataInputStreamEx;
import cn.kizzzy.qqfo.GsoFileItem;

import java.io.InputStream;

public class GsoImgCreator<R> extends QqfoImgCreator<GsoFileItem, R> {
    
    @Override
    protected R CreateImpl(GsoFileItem item, Callback<R> callback) throws Exception {
        PixelConverter converter = selector.select(item.type);
        if (converter != null && item.valid) {
            try (InputStream temp = item.OpenStream()) {
                try (DataInputStreamEx reader = new DataInputStreamEx(temp)) {
                    int[] buffer = readPixel(reader, converter, item.width, item.height);
                    return callback.invoke(buffer, item.width, item.height);
                }
            }
        }
        return null;
    }
}
