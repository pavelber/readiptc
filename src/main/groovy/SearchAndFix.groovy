import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.iptc.IptcDirectory

import static groovy.io.FileType.FILES

int keywords = 0x219
int name = 0x205
int caption = 0x278

new File('D:\\stock').eachFileRecurse(FILES) {
    if (it.name.toLowerCase().endsWith('.jpg')) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(it);
            Directory directory = metadata.getFirstDirectoryOfType(IptcDirectory)
            if (directory == null) {
                println it
                it.delete()
            } else {
                def n = directory.getString(name)
                def c = directory.getString(caption)
                if (
                (n == null || n.length() == 0) &&
                        (c == null || c.length() == 0)) {
                    println it

                    it.delete()
                }
                if (
                (n == null || n.length() == 0) ||
                        (c == null || c.length() == 0)) {
                    println it

                    it.delete()
                }

                def array = directory.getStringArray(keywords)
                if (array == null || array.length < 10) {
                    println it

                    it.delete()
                }

            }
        } catch (Exception e) {
            //e.printStackTrace()
            println "BAD: $it"
            it.delete()
        }


    }
}