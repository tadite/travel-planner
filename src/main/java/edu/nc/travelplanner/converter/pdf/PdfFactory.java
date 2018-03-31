package edu.nc.travelplanner.converter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import edu.nc.travelplanner.dto.afterPickTree.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class PdfFactory {

    private final String FONT_PATH = System.getProperty("user.dir") + "/front_end/src/fonts/pt-sans-caption.ttf";
    private final String IMAGE_PATH = System.getProperty("user.dir") + "/front_end/src/img/james-donaldson-365418-unsplash.jpg";

    private PdfPCellEvent cellEvent;
    private BaseFont baseFont;
    private Font font;

    {
        this.cellEvent = new RoundedCell();
        try{
            this.baseFont = BaseFont.createFont(FONT_PATH, "cp1251", BaseFont.EMBEDDED);
            this.font = new Font(baseFont);
        } catch (IOException | DocumentException e){}
    }

    public byte[] createPdf(TravelDto travelDto) throws IOException, DocumentException{
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Image image = Image.getInstance(IMAGE_PATH);
        image.scaleAbsolute(PageSize.A4);
        image.setAbsolutePosition(0, 0);
        writer.setPageEvent(new ImageBackgroundEvent(image));

        PdfPTable outerTable = new PdfPTable(1);

        PdfPTable commonInfo = getCommonInfoTable(travelDto);
        outerTable.addCell(getCell(commonInfo));

        if (travelDto.getHotel() != null){
            PdfPTable hotel = getHotelsTable(travelDto.getHotel());
            outerTable.addCell(getCell(hotel));
        }

        if (travelDto.getTwoWayFlight() != null){
            PdfPTable airTickets = new PdfPTable(1);
            FlightDto flightFrom = travelDto.getTwoWayFlight().getFlightFrom();
            FlightDto flightTo = travelDto.getTwoWayFlight().getFlightTo();
            if (flightFrom != null){
                PdfPTable flightFromTable = getAirTicketsTable(flightFrom, "Путь туда");
                airTickets.addCell(getCell(flightFromTable));
            }
            if (flightTo != null){
                PdfPTable flightToTable = getAirTicketsTable(flightTo, "Путь обратно");
                airTickets.addCell(getCell(flightToTable));
            }
            PdfPTable priceTable = getAirTicketsPrice(travelDto.getTwoWayFlight());
            airTickets.addCell(getCell(priceTable));
            outerTable.addCell(getCell(airTickets));
        }

        if (travelDto.getExcursions().size() != 0){
            int id = 0;
            for (ExcursionDto excursion: travelDto.getExcursions()){
                id++;
                PdfPTable excursionTable = getExcursionTable(excursion, id);
                outerTable.addCell(getCell(excursionTable));
            }
        }

        if (travelDto.getCarRent() != null){
            PdfPTable carTable = getCarTable(travelDto.getCarRent());
            outerTable.addCell(getCell(carTable));
        }

        document.add(outerTable);
        document.close();
        byte[] pdf = baos.toByteArray();
        return pdf;
    }

    private PdfPTable getCommonInfoTable(TravelDto travelDto){
        PdfPTable table = new PdfPTable(2);

        PdfPCell header = getCell("Общая информация");
        header.setColspan(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        table.addCell(getCell("Название путешествия"));
        table.addCell(getCell(travelDto.getName()));
        table.addCell(getCell("Страна отправления"));
        table.addCell(getCell(travelDto.getFromCheckpoint().getCountryName()));
        table.addCell(getCell("Город отправления"));
        table.addCell(getCell(travelDto.getFromCheckpoint().getCityName()));
        table.addCell(getCell("Страна назначения"));
        table.addCell(getCell(travelDto.getToCheckpoint().getCountryName()));
        table.addCell(getCell("Город назначения"));
        table.addCell(getCell(travelDto.getToCheckpoint().getCityName()));
        table.addCell(getCell("Дата отъезда"));
        table.addCell(getCell(travelDto.getDateStart()));
        table.addCell(getCell("Дата приезда"));
        table.addCell(getCell(travelDto.getDateEnd()));
        table.addCell(getCell("Количество человек"));
        table.addCell(getCell(travelDto.getNumberOfPersons()));
        table.addCell(getCell("Бюджет"));
        table.addCell(getCell(travelDto.getBudgetType()));

        return table;
    }

    private PdfPTable getHotelsTable(HotelDto hotelDto){
        PdfPTable table = new PdfPTable(2);

        PdfPCell header = getCell("Проживание");
        header.setColspan(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        table.addCell(getCell("Отель"));
        table.addCell(getCell(hotelDto.getName()));
        table.addCell(getCell("Адрес"));
        table.addCell(getCell(hotelDto.getAddress()));
        table.addCell(getCell("Стоимость"));
        table.addCell(getCell(hotelDto.getPrice()));
        table.addCell(getCell("Период"));
        table.addCell(getCell(hotelDto.getPricePeriod()));
        table.addCell(getCell("Информация"));
        table.addCell(getCell(hotelDto.getPriceInfo()));

        return table;
    }

    private PdfPTable getAirTicketsTable(FlightDto flight, String header){
        PdfPTable table = new PdfPTable(2);

        PdfPCell headerFrom = getCell(header);
        headerFrom.setColspan(2);
        headerFrom.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerFrom);

        table.addCell(getCell("Самолет"));
        table.addCell(getCell(flight.getAircraft()));
        table.addCell(getCell("Авиакомпания"));
        table.addCell(getCell(flight.getCompanyName()));
        table.addCell(getCell("Аэропорт вылета"));
        table.addCell(getCell(flight.getDepartureName()));
        table.addCell(getCell("Аэропорт прилета"));
        table.addCell(getCell(flight.getArrivalName()));
        table.addCell(getCell("Класс обслуживания"));
        table.addCell(getCell(flight.getClassType()));
        table.addCell(getCell("Дата вылета"));
        table.addCell(getCell(flight.getDepartureDate()));
        table.addCell(getCell("Время вылета"));
        table.addCell(getCell(flight.getDepartureTime()));
        table.addCell(getCell("Время в пути"));
        table.addCell(getCell(flight.getTimeInPath()));

        return table;
    }

    private PdfPTable getAirTicketsPrice(TwoWayFlightDto ticket){
        PdfPTable table = new PdfPTable(2);

        PdfPCell header = getCell("Билеты");
        header.setColspan(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        table.addCell(getCell("Общая стоимость"));
        table.addCell(getCell(ticket.getPrice()));

        return table;
    }

    private PdfPTable getExcursionTable(ExcursionDto excursion, int id){
        PdfPTable table = new PdfPTable(2);

        PdfPCell header = getCell("Экскурсия №" + id);
        header.setColspan(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        table.addCell(getCell("Название"));
        table.addCell(getCell(excursion.getName()));
        table.addCell(getCell("Описание"));
        table.addCell(getCell(excursion.getDescription()));
        table.addCell(getCell("Цена"));
        table.addCell(getCell(excursion.getPrice()));
        table.addCell(getCell("Продолжительность"));
        table.addCell(getCell(excursion.getTime()));

        return table;
    }

    private PdfPTable getCarTable(CarRentDto car){
        PdfPTable table = new PdfPTable(2);

        PdfPCell header = getCell("Аренда автомобиля");
        header.setColspan(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        table.addCell(getCell("Марка авто"));
        table.addCell(getCell(car.getName()));
        table.addCell(getCell("Срок аренды"));
        table.addCell(getCell(car.getPricePeriod()));
        table.addCell(getCell("Стоимость"));
        table.addCell(getCell(car.getPrice()));
        table.addCell(getCell("Количество пассажиров"));
        table.addCell(getCell(car.getSeats()));
        table.addCell(getCell("Количество дверей"));
        table.addCell(getCell(car.getDoors()));
        table.addCell(getCell("Тип коробки передач"));
        table.addCell(getCell(car.getTransmission()));
        table.addCell(getCell("Класс"));
        table.addCell(getCell(car.getClassType()));
        table.addCell(getCell("Пробег"));
        table.addCell(getCell(car.getMileage()));

        return table;
    }

    private PdfPCell getCell(String label){
        PdfPCell cell = new PdfPCell(new Phrase(label, this.font));
        cell.setUseVariableBorders(true);
        cell.setBorderColorTop(new BaseColor(255, 255, 255));
        cell.setBorderColorRight(new BaseColor(255, 255, 255));
        cell.setBorderColorLeft(new BaseColor(255, 255, 255));
        cell.setBorderColorBottom(BaseColor.GRAY);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setNoWrap(false);
        cell.setMinimumHeight(15f);
        cell.setBorderWidth(1f);

        return cell;
    }

    private PdfPCell getCell(PdfPTable table){
        PdfPCell cell = new PdfPCell(table);
        cell.setCellEvent(this.cellEvent);
        //cell.setBorderColor(new BaseColor(102, 120, 177));
        //cell.setBorderWidth(2f);

        return cell;
    }
}
