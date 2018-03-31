package edu.nc.travelplanner.converter.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

public class RoundedCell implements PdfPCellEvent{

    @Override
    public void cellLayout(PdfPCell pdfPCell, Rectangle rectangle, PdfContentByte[] pdfContentBytes) {
        PdfContentByte cb = pdfContentBytes[PdfPTable.LINECANVAS];
        cb.roundRectangle(
                rectangle.getLeft() + 1.5f, rectangle.getBottom() + 1.5f, rectangle.getWidth() - 3,
                rectangle.getHeight() - 3, 4);
        cb.stroke();
        cb.setColorStroke(new BaseColor(102, 120, 177));
        /*PdfContentByte cb1 = pdfContentBytes[PdfPTable.BACKGROUNDCANVAS];
        cb1.saveState();
        PdfGState state = new PdfGState();
        state.setFillOpacity(0.7f);
        cb1.setGState(state);
        cb1.setColorFill(BaseColor.WHITE);
        cb1.fill();
        cb1.restoreState();*/
    }
}
