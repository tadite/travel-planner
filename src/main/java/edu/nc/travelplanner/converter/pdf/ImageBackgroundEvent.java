package edu.nc.travelplanner.converter.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ImageBackgroundEvent extends PdfPageEventHelper {

    private Image image;

    public ImageBackgroundEvent(Image image){
        this.image = image;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            writer.getDirectContentUnder().addImage(this.image);
        } catch (DocumentException e){
            e.printStackTrace();
        }
    }
}
