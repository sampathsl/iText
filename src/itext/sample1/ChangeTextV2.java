package itext.sample1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpProcessor;

public class ChangeTextV2 {

	public static void main(String[] args) throws IOException, DocumentException 
	{
		
		String inFile = "G:\\PDF-001.pdf";
		String inFile1 = "G:\\PDF-001-changed-v2.pdf";
		Rectangle rect = new Rectangle(50f,550f,500f,445f);
		RenderFilter[] filter = {new RegionTextRenderFilter(rect)};
		TextExtractionStrategy strategy;
		StringBuilder sb = new StringBuilder();
		PdfReader reader = new PdfReader(inFile);
		
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
		    sb.append(PdfTextExtractor.getTextFromPage(reader, i, strategy));
		}
		
		System.out.println(sb.toString());
		
		processPDF(inFile,inFile1);
		
	}
	
	public static void processPDF(String src, String dest) throws IOException, DocumentException 
	{
		
		PdfReader reader = new PdfReader(src);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	    Font fontNormal = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
	    Rectangle rect = new Rectangle(50f,550f,500f,445f);
	    List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<PdfCleanUpLocation>();
	    cleanUpLocations.add(new PdfCleanUpLocation(1, rect, BaseColor.WHITE));
	    
	    PdfContentByte cb = stamper.getOverContent(1);
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(140f, 490f, 500f, 445f);
		Paragraph pz = new Paragraph(new Phrase(0,"HERE IS THE DYNAMIC TEXT", fontNormal));
		ct.addElement(pz);
		ct.go();
	    
	    PdfCleanUpProcessor cleaner = new PdfCleanUpProcessor(cleanUpLocations, stamper);
	    cleaner.cleanUp();
	    stamper.close();
	    reader.close();
	    
	}

}
