package itext.sample2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class DynamicTable {
	
	public static final String SRC = "G:\\PDF-002.pdf";
    public static final String DEST = "G:\\PDF-002-changed-v1.pdf";

	public static void main(String[] args) throws DocumentException, IOException 
	{
		DynamicTable dt = new DynamicTable();
		PdfReader reader = new PdfReader(SRC);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
		
		Quote quote = new Quote();
		quote.setValue1(new BigDecimal("20.50"));
		quote.setValue2(new BigDecimal("50.20"));
		quote.setValue3(new BigDecimal("100.80"));
		quote.setValue4(new BigDecimal("10.75"));
		
		List<Option> optionList = new ArrayList<Option>();
		
		Option o1 = new Option();
		o1.setSampleText1("O1 VAL1");
		o1.setSampleText2(new BigDecimal("10.60"));
		o1.setSampleText3("O1 VAL2");
		o1.setSampleText4(new BigDecimal("40.10"));
		o1.setSampleText5(new BigDecimal("4.90"));
		
		Option o2 = new Option();
		o2.setSampleText1("O2 VAL1");
		o2.setSampleText2(new BigDecimal("15.60"));
		o2.setSampleText3("O2 VAL2");
		o2.setSampleText4(new BigDecimal("50.10"));
		o2.setSampleText5(new BigDecimal("1.90"));
		
		Option o3 = new Option();
		o3.setSampleText1("O3 VAL1");
		o3.setSampleText2(new BigDecimal("19.60"));
		o3.setSampleText3("O3 VAL2");
		o3.setSampleText4(new BigDecimal("45.10"));
		o3.setSampleText5(new BigDecimal("5.90"));
		
		optionList.add(o1);
		optionList.add(o2);
		optionList.add(o3);
		
		PdfContentByte cb = stamper.getOverContent(1);
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(50f,550f,500f,40f);
		ct.addElement(dt.createTable(quote,optionList));
		ct.go();
		
		stamper.close();
        reader.close();
		
	}
	
	public PdfPTable createTable(Quote q,List<Option> optionList) throws DocumentException 
	{

		// create columns of the table
		PdfPTable table = new PdfPTable(optionList.size() + 1);

		// set the width of the table to 100% of page
		table.setWidthPercentage(100);
		
		// add headers to the table
		PdfPCell cell1 = createHeaderCell("Description");
		table.addCell(cell1);
		
		// set column width array
		float[] fArray = new float[optionList.size() + 1];
		fArray[0] = 1f;
		
		for (int i = 1; i <= optionList.size(); i++) {
			// add headers to the table
			PdfPCell cellNew = createHeaderCell("Solution "+String.format("%02d", i)+" \r\nOptimum usage");
			table.addCell(cellNew);
			fArray[i] = 0.7f;
		}

		// set relative columns width
		table.setWidths(fArray);

		for (int i = 1; i <= 6 ; i++) {

			if (i == 1) {
				
				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw1. Raw1 Raw1 \r\nRaw1 Raw1 \r\n(Raw1)");
				table.addCell(cellRaw);
				
				for (int j = 0; j < optionList.size() ; j++) {
					table.addCell(createValueCell(
							q.getValue1() != null ? String.valueOf(q.getValue1()) : ""));
				}

			} 
			else if (i == 2) {
				
				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw2 Raw2 Raw2 Raw2 \r\nRaw2 Raw2. \r\n(Raw2) (Raw2.)");
				table.addCell(cellRaw);
				
				for (Iterator iteratorOption = optionList.iterator(); iteratorOption.hasNext();) {
					Option optionExisting = (Option) iteratorOption.next();
					table.addCell(createValueCell(
							optionExisting.getSampleText1() != null ? String.valueOf(optionExisting.getSampleText1()) : ""));
				}
				
			} 
			else if (i == 3) {
				
				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw3 Raw3. Raw3 Raw3 \r\nRaw3 zzz/zzzz (Raw3)");
				table.addCell(cellRaw);
				
				for (Iterator iteratorOption = optionList.iterator(); iteratorOption.hasNext();) {
					Option optionExisting = (Option) iteratorOption.next();
					String value = "";
					if(optionExisting.getSampleText2() != null && q.getValue2() != null){
						value = String.valueOf(q.getValue2().subtract(optionExisting.getSampleText2()));
					}
					table.addCell(createValueCell(value));
				}
				
			} 
			else if (i == 4) {

				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw4 Raw4. Raw4 Raw4 \r\n(Raw4)");
				table.addCell(cellRaw);
				
				for (int j = 0; j < optionList.size() ; j++) {
					table.addCell(createValueCell(
							q.getValue3() != null ? "Rs. " + String.format("%,.2f", q.getValue3().setScale(2, RoundingMode.CEILING)) : ""));
				}
				
				
				
			} 
			else if (i == 5) {
				
				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw5 Raw5. Raw5 Raw5 (Raw5)");
				table.addCell(cellRaw);
				
				for (Iterator iteratorOption = optionList.iterator(); iteratorOption.hasNext();) {
					Option optionExisting = (Option) iteratorOption.next();
					String value = "";
					if(optionExisting.getSampleText4() != null && q.getValue3() != null){
						value = String.valueOf(q.getValue3().subtract(optionExisting.getSampleText4()));
					}
					table.addCell(createValueCellWithBoldFont(
							!value.equalsIgnoreCase("") ? "Rs. " + String.format("%,.2f", 
									new BigDecimal(value).multiply(new BigDecimal("22")).setScale(2, RoundingMode.CEILING)) : ""));
				}
				
			} 
			else if (i == 6) {
				
				// add initial raw column to the table
				PdfPCell cellRaw = createValueCell("Raw6. Raw6 Raw6");
				table.addCell(cellRaw);
				
				for (Iterator iteratorOption = optionList.iterator(); iteratorOption.hasNext();) {
					Option optionExisting = (Option) iteratorOption.next();
					BigDecimal value = null;
					if(optionExisting.getSampleText5()!= null && q.getValue4() != null){
						value = q.getValue4().subtract(optionExisting.getSampleText5());
					}
					
					String saving = "";
					if(value != null && isNegative(value)){
						saving = "Rs. " + String.format("%,.2f", value.multiply(new BigDecimal("22").setScale(2, RoundingMode.CEILING)));
					}
					else{
						saving = "Rs. 0.00";
					}
					
					table.addCell(createValueCellWithBoldFont(saving));
				}
				
			}

		}

		return table;
	}
	
	private boolean isNegative(BigDecimal b)
    {
        return b.signum() == -1;
    }

	public PdfPCell createHeaderCell(String header) 
	{
		
		// font
		Font headerFont = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);

		// create header cell
		PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);

		// set style
		cell.setColspan(1);
		cell.setBackgroundColor(new BaseColor(204, 227, 249));
		cell.setMinimumHeight(30f);
		cell.setBorderWidth(1f);

		return cell;
		
	}

	public PdfPCell createValueCell(String text) 
	{
		// font
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

		// create cell
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// set style
		cell.setColspan(1);
		cell.setBackgroundColor(new BaseColor(199, 249, 214));
		cell.setMinimumHeight(45f);
		cell.setBorderWidth(1f);

		return cell;
		
	}
	
	public PdfPCell createValueCellWithBoldFont(String text) 
	{
		
		// font
		Font font = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);

		// create cell
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// set style
		cell.setColspan(1);
		cell.setBackgroundColor(new BaseColor(199, 249, 214));
		cell.setMinimumHeight(45f);
		cell.setBorderWidth(1f);

		return cell;
		
	}

	public PdfPCell createCustomRedValueCell(Option optionExisting) 
	{
		
		Font dataRedFont = FontFactory.getFont(BaseFont.HELVETICA,
				"Cp1252", BaseFont.EMBEDDED, 9, 0, BaseColor.RED,
				true);
		Font font = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
		Phrase pz = new Phrase("", font);

		pz.add(new Chunk(String.valueOf(optionExisting.getSampleText3())));
		pz.add(new Chunk(" \r\n Expandable \r\n up to 3 kW  ", dataRedFont));
		PdfPCell cell = new PdfPCell(pz);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(1);
		cell.setBackgroundColor(new BaseColor(199, 249, 214));
		cell.setMinimumHeight(40f);
		cell.setBorderWidth(1f);

		return cell;
		
	}

	public PdfPCell createLabelCell(String text) 
	{
		// font
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

		// create cell
		PdfPCell cell = new PdfPCell(new Phrase(text, font));

		// set style
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderWidth(1f);

		return cell;
	}
	
}

class Quote {
	
	private BigDecimal value1;
	private BigDecimal value2;
	private BigDecimal value3;
	private BigDecimal value4;
	
	public BigDecimal getValue1() 
	{
		return value1;
	}
	
	public void setValue1(BigDecimal value1) 
	{
		this.value1 = value1;
	}
	
	public BigDecimal getValue2() 
	{
		return value2;
	}
	
	public void setValue2(BigDecimal value2) 
	{
		this.value2 = value2;
	}
	
	public BigDecimal getValue3() 
	{
		return value3;
	}
	
	public void setValue3(BigDecimal value3) 
	{
		this.value3 = value3;
	}
	
	public BigDecimal getValue4() 
	{
		return value4;
	}
	
	public void setValue4(BigDecimal value4) 
	{
		this.value4 = value4;
	}
	
}


class Option {
	
	private String sampleText1;
	private BigDecimal sampleText2;
	private String sampleText3;
	private BigDecimal sampleText4;
	private BigDecimal sampleText5;
	
	public String getSampleText1() 
	{
		return sampleText1;
	}
	
	public void setSampleText1(String sampleText1) 
	{
		this.sampleText1 = sampleText1;
	}
	public BigDecimal getSampleText2() {
		return sampleText2;
	}
	public void setSampleText2(BigDecimal sampleText2) {
		this.sampleText2 = sampleText2;
	}
	
	public String getSampleText3() 
	{
		return sampleText3;
	}
	
	public void setSampleText3(String sampleText3) 
	{
		this.sampleText3 = sampleText3;
	}
	
	public BigDecimal getSampleText4() 
	{
		return sampleText4;
	}
	
	public void setSampleText4(BigDecimal sampleText4) 
	{
		this.sampleText4 = sampleText4;
	}
	
	public BigDecimal getSampleText5() 
	{
		return sampleText5;
	}
	
	public void setSampleText5(BigDecimal sampleText5) 
	{
		this.sampleText5 = sampleText5;
	}
	
}