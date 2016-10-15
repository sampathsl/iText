package itext.sample1;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ChangeText {
	
	public static final String SRC = "G:\\PDF-001.pdf";
    public static final String DEST = "G:\\PDF-001-changed-v1.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException 
    {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        processPDF(SRC, DEST);
    }
	
    public static void processPDF(String src, String dest) throws IOException, DocumentException 
    {
        PdfReader reader = new PdfReader(src);
        PdfDictionary dict = reader.getPageN(1);
        PdfObject object = dict.getDirectObject(PdfName.CONTENTS);
        
        if (object instanceof PRStream) 
        {
            PRStream stream = (PRStream)object;
            byte[] data = PdfReader.getStreamBytes(stream);
            String dd = new String(data);
            dd = dd.replace("0123456789", "0121212121212");
            dd = dd.replace("EEE:", "Our Ref:");
            dd = dd.replace("WR", "IT TEST");
            dd = dd.replace("2016", "2020");
            stream.setData(dd.getBytes());
        }
        
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
    
}
