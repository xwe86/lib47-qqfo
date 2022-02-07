package cn.kizzzy.vfs.handler;

import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.qqfo.PkgFile;
import cn.kizzzy.qqfo.PkgFileItem;
import cn.kizzzy.vfs.IPackage;

import java.nio.charset.Charset;

public class PkgFileHandler extends StreamFileHandler<PkgFile> {
    
    @Override
    protected PkgFile loadImpl(IPackage pack, String path, FullyReader reader) throws Exception {
        PkgFile pkg = new PkgFile(path);
        pkg.magic = reader.readIntEx();
        pkg.indexCount = reader.readUnsignedIntEx();
        pkg.indexPosition = reader.readUnsignedIntEx();
        pkg.indexSize = reader.readUnsignedIntEx();
        
        reader.skip(pkg.indexPosition - 16);
        
        for (int i = 0; i < pkg.indexCount; ++i) {
            PkgFileItem file = new PkgFileItem();
            file.pkg = pkg.path;
            
            file.nameLength = reader.readUnsignedShortEx();
            file.path = reader.readString(file.nameLength, Charset.forName("GB2312")).toLowerCase();
            file.reserved = reader.readIntEx();
            file.offset = reader.readUnsignedIntEx();
            file.originSize = reader.readIntEx();
            file.size = reader.readIntEx();
            
            pkg.fileKvs.put(file.path, file);
        }
        return pkg;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, PkgFile data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}
