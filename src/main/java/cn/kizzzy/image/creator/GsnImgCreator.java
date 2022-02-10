package cn.kizzzy.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.io.DataInputStreamEx;
import cn.kizzzy.qqfo.GsnFrame;

import java.io.InputStream;

public class GsnImgCreator<R> extends QqfoImgCreator<GsnFrame, R> {
    
    @Override
    protected R CreateImpl(GsnFrame frame, Callback<R> callback) throws Exception {
        PixelConverter converter = selector.select(frame.type);
        if (converter != null && frame.valid) {
            try (InputStream temp = frame.OpenStream()) {
                try (DataInputStreamEx reader = new DataInputStreamEx(temp)) {
                    int[] buffer = readPixel(reader, converter, frame.width, frame.height);
                    return callback.invoke(buffer, frame.width, frame.height);
                }
            }
        }
        
        return null;
    }
}
