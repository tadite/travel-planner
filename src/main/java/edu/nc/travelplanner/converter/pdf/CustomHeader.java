package edu.nc.travelplanner.converter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class CustomHeader extends PdfPageEventHelper{

    private Font font;

    public CustomHeader(Font font){
        this.font = font;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        this.font.setColor(BaseColor.WHITE);
        Phrase header = new Phrase("TRAVEL PLANNER", this.font);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                header,
                70, 815, 0);
    }
}
