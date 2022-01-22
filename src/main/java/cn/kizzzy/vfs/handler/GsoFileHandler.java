package cn.kizzzy.vfs.handler;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.image.sizer.SizerHelper;
import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqfo.GsoFile;
import cn.kizzzy.qqfo.GsoFileItem;
import cn.kizzzy.qqfo.GsoFileItems;
import cn.kizzzy.vfs.IPackage;

public class GsoFileHandler extends ImageFileHandler<GsoFile> {
    
    @Override
    protected GsoFile loadImpl(IPackage pack, String path, FullyReader reader) throws Exception {
        GsoFile file = null;
        try {
            file = new GsoFile();
            file.magic_01 = reader.readIntEx();
            file.magic_02 = reader.readIntEx();
            file.major = reader.readShortEx();
            file.minor = reader.readShortEx();
            file.reserved4 = reader.readIntEx();
            file.count = reader.readIntEx();
            file.reserved6 = reader.readIntEx();
            if (file.reserved4 == 0x24) {
                file.reserved7 = reader.readIntEx();
                file.reserved8 = reader.readIntEx();
                file.reserved9 = reader.readIntEx();
            }
            file.maxWidth = reader.readIntEx();
            file.maxHeight = reader.readIntEx();
            file.pivotX = reader.readIntEx();
            file.pivotY = reader.readIntEx();
            file.reserved14 = reader.readIntEx();
            
            if (file.major != 4) {
                file.unknownCount1 = reader.readIntEx();
                file.unknowns1 = new int[file.unknownCount1];
                for (int i = 0; i < file.unknownCount1; ++i) {
                    file.unknowns1[i] = reader.readIntEx();
                }
            }
            
            if (file.major >= 3) {
                file.unknownCount2 = reader.readIntEx();
                file.unknowns2 = new GsoFile.Unknown2[file.unknownCount2];
                for (int i = 0; i < file.unknownCount2; ++i) {
                    GsoFile.Unknown2 unknown2 = new GsoFile.Unknown2();
                    unknown2.reserved01 = reader.readIntEx();
                    unknown2.reserved02 = reader.readIntEx();
                    unknown2.reserved03 = reader.readIntEx();
                    
                    file.unknowns2[i] = unknown2;
                }
            }
            
            file.items = new GsoFileItems[file.reserved6];
            for (int i = 0; i < file.reserved6; ++i) {
                int length = file.count / file.reserved6;
                
                GsoFileItems items = new GsoFileItems();
                items.items = new GsoFileItem[length];
                for (int j = 0; j < length; ++j) {
                    GsoFileItem item = new GsoFileItem();
                    item.i = i;
                    item.j = j;
                    item.file = file;
                    
                    item.reserved01 = reader.readIntEx();
                    item.offsetX = reader.readIntEx();
                    item.offsetY = reader.readIntEx();
                    item.width = reader.readIntEx();
                    item.height = reader.readIntEx();
                    item.reserved06 = reader.readIntEx();
                    item.type = reader.readIntEx();
                    item.valid = checkValid(item.width, item.height);
                    
                    if (item.valid) {
                        item.offset = reader.position();
                        item.size = SizerHelper.calc(item.type, item.width, item.height);
                        
                        reader.seek(item.size, SeekType.CURRENT);
                        
                        if (file.major == 3) {
                            item.centerX = reader.readIntEx();
                            item.centerY = reader.readIntEx();
                            item.final03 = reader.readIntEx();
                        }
                    }
                    
                    items.items[j] = item;
                }
                
                file.items[i] = items;
            }
            
            // todo rest
        } catch (Exception e) {
            LogHelper.error(String.format("failed to load gs* file : %s, %s", path, file), e);
        }
        return file;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, GsoFile data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}
