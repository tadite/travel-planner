package edu.nc.travelplanner.converter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import edu.nc.travelplanner.dto.afterPickTree.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class PdfFactory {

    private final String FONT = System.getProperty("user.dir") + "/pdf/font/pt-sans-caption.ttf";
    private final String IMAGE = System.getProperty("user.dir") + "/pdf/image/";
    private final String TABLE_STYLE = System.getProperty("user.dir") + "/pdf/style/questions.component.css";

    private BaseFont baseFont;
    private Font font;

    {
        try{
            this.baseFont = BaseFont.createFont(FONT, "cp1251", BaseFont.EMBEDDED);
            this.font = new Font(baseFont);
        } catch (IOException | DocumentException e){}
    }

    public byte[] createPdf(TravelDto travelDto) throws IOException, DocumentException{
        Document document = new Document();
        document.setMargins(75, 30, 70, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        String tableOpen = "<table>";
        String tableClose = "</table>";

        document.open();

        /*Image image = Image.getInstance(IMAGE + "james-donaldson-365418-unsplash.jpg");
        image.scaleAbsolute(PageSize.A4);
        image.setAbsolutePosition(0, 0);
        writer.setPageEvent(new ImageBackgroundEvent(image));*/

        Image navHeader = Image.getInstance(IMAGE + "navbar.jpg");
        navHeader.setAbsolutePosition(0, 800);
        writer.setPageEvent(new ImageBackgroundEvent(navHeader));

        Image icon = Image.getInstance(IMAGE + "icon.png");
        icon.scaleAbsolute(40, 40);
        icon.setAbsolutePosition(20, 800);
        writer.setPageEvent(new ImageBackgroundEvent(icon));

        writer.setPageEvent(new CustomHeader(this.font));

        StringBuilder table = new StringBuilder();
        table.append("<div class=\"result-table-block\">");

        table.append(tableOpen);
        table.append(getCommonInfoTable(travelDto));
        table.append(tableClose);

        if (travelDto.getHotel() != null){
            table.append(tableOpen);
            table.append(getHotelsTable(travelDto.getHotel()));
            table.append(tableClose);
        }

        if (travelDto.getTwoWayFlight() != null){
            FlightDto flightFrom = travelDto.getTwoWayFlight().getFlightFrom();
            FlightDto flightTo = travelDto.getTwoWayFlight().getFlightTo();
            if (flightTo != null){
                table.append(tableOpen);
                table.append(getAirTicketsTable(flightTo, "Путь туда"));
                table.append(tableClose);
            }
            if (flightFrom != null){
                table.append(tableOpen);
                table.append(getAirTicketsTable(flightFrom, "Путь обратно"));
                table.append(tableClose);
            }
            table.append(tableOpen);
            table.append(getAirTicketsPrice(travelDto.getTwoWayFlight()));
            table.append(tableClose);
        }

        if (travelDto.getExcursions().size() != 0){
            int id = 0;
            for (ExcursionDto excursion: travelDto.getExcursions()){
                id++;
                table.append(tableOpen);
                table.append(getExcursionTable(excursion, id));
                table.append(tableClose);
            }
        }

        if (travelDto.getCarRent() != null){
            table.append(tableOpen);
            table.append(getCarTable(travelDto.getCarRent()));
            table.append(tableClose);
        }
        table.append("</div>");

        CSSResolver resolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(TABLE_STYLE));
        resolver.addCss(cssFile);

        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register(FONT);
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(resolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser parser = new XMLParser(worker, Charset.forName("UTF-8"));
        parser.parse(new ByteArrayInputStream(table.toString().getBytes()));

        document.close();
        byte[] pdfFile = baos.toByteArray();
        return pdfFile;
    }

    private StringBuffer getCommonInfoTable(TravelDto travelDto){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag("Общая информация")));
        sb.append(wrapTrTag(wrapTdTag("Название путешествия")
                + wrapTdTag(travelDto.getName())));
        sb.append(wrapTrTag(wrapTdTag("Страна отправления")
                + wrapTdTag(travelDto.getFromCheckpoint().getCountryName())));
        sb.append(wrapTrTag(wrapTdTag("Город отправления")
                + wrapTdTag(travelDto.getFromCheckpoint().getCityName())));
        sb.append(wrapTrTag(wrapTdTag("Страна назначения")
                + wrapTdTag(travelDto.getToCheckpoint().getCountryName())));
        sb.append(wrapTrTag(wrapTdTag("Город назначения")
                + wrapTdTag(travelDto.getToCheckpoint().getCityName())));
        sb.append(wrapTrTag(wrapTdTag("Дата отъезда")
                + wrapTdTag(travelDto.getDateStart())));
        sb.append(wrapTrTag(wrapTdTag("Дата приезда")
                + wrapTdTag(travelDto.getDateEnd())));
        sb.append(wrapTrTag(wrapTdTag("Количество человек")
                + wrapTdTag(travelDto.getNumberOfPersons())));
        sb.append(wrapTrTag(wrapTdTag("Бюджет")
                + wrapTdTag(travelDto.getBudgetType())));

        return sb;
    }

    private StringBuffer getHotelsTable(HotelDto hotelDto){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag("Проживание")));

        sb.append(wrapTrTag(wrapTdTag("Отель")
                + wrapTdTag(hotelDto.getName())));
        sb.append(wrapTrTag(wrapTdTag("Адрес")
                + wrapTdTag(hotelDto.getAddress())));
        sb.append(wrapTrTag(wrapTdTag("Стоимость")
                + wrapTdTag(hotelDto.getPrice())));
        sb.append(wrapTrTag(wrapTdTag("Период")
                + wrapTdTag(hotelDto.getPricePeriod())));
        sb.append(wrapTrTag(wrapTdTag("Информация")
                + wrapTdTag(hotelDto.getPriceInfo())));

        return sb;
    }

    private StringBuffer getAirTicketsTable(FlightDto flight, String header){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag(header)));

        sb.append(wrapTrTag(wrapTdTag("Самолет")
                + wrapTdTag(flight.getAircraft())));
        sb.append(wrapTrTag(wrapTdTag("Авиакомпания")
                + wrapTdTag(flight.getCompanyName())));
        sb.append(wrapTrTag(wrapTdTag("Аэропорт вылета")
                + wrapTdTag(flight.getDepartureName())));
        sb.append(wrapTrTag(wrapTdTag("Аэропорт прилета")
                + wrapTdTag(flight.getArrivalName())));
        sb.append(wrapTrTag(wrapTdTag("Класс обслуживания")
                + wrapTdTag(flight.getClassType())));
        sb.append(wrapTrTag(wrapTdTag("Дата вылета")
                + wrapTdTag(flight.getDepartureDate())));
        sb.append(wrapTrTag(wrapTdTag("Время вылета")
                + wrapTdTag(flight.getDepartureTime())));
        sb.append(wrapTrTag(wrapTdTag("Время в пути")
                + wrapTdTag(flight.getTimeInPath())));

        return sb;
    }

    private StringBuffer getAirTicketsPrice(TwoWayFlightDto ticket){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag("Билеты")));

        sb.append(wrapTrTag(wrapTdTag("Общая стоимость")
                + wrapTdTag(ticket.getPrice())));

        return sb;
    }

    private StringBuffer getExcursionTable(ExcursionDto excursion, int id){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag("Экскурсия №" + id)));

        sb.append(wrapTrTag(wrapTdTag("Название")
                + wrapTdTag(excursion.getName())));
        sb.append(wrapTrTag(wrapTdTag("Описание")
                + wrapTdTag(excursion.getDescription())));
        sb.append(wrapTrTag(wrapTdTag("Цена")
                + wrapTdTag(excursion.getPrice())));
        sb.append(wrapTrTag(wrapTdTag("Продолжительность")
                + wrapTdTag(excursion.getTime())));

        return sb;
    }

    private StringBuffer getCarTable(CarRentDto car){
        StringBuffer sb = new StringBuffer();

        sb.append(wrapTrTag(wrapCaptionTag("Аренда автомобиля")));

        sb.append(wrapTrTag(wrapTdTag("Марка авто")
                + wrapTdTag(car.getName())));
        sb.append(wrapTrTag(wrapTdTag("Срок аренды")
                + wrapTdTag(car.getPricePeriod())));
        sb.append(wrapTrTag(wrapTdTag("Стоимость")
                + wrapTdTag(car.getPrice())));
        sb.append(wrapTrTag(wrapTdTag("Количество пассажиров")
                + wrapTdTag(car.getSeats())));
        sb.append(wrapTrTag(wrapTdTag("Количество дверей")
                + wrapTdTag(car.getDoors())));
        sb.append(wrapTrTag(wrapTdTag("Тип коробки передач")
                + wrapTdTag(car.getTransmission())));
        sb.append(wrapTrTag(wrapTdTag("Класс")
                + wrapTdTag(car.getClassType())));
        sb.append(wrapTrTag(wrapTdTag("Пробег")
                + wrapTdTag(car.getMileage())));

        return sb;
    }

    private String wrapCaptionTag(String label){
        return "<td colspan=\"2\"><div class=\"caption\">" + label + "</div></td>";
    }

    private String wrapTdTag(String label){
        return "<td>" + label + "</td>";
    }

    private String wrapTrTag(String label){
        return "<tr>" + label + "</tr>";
    }
}
