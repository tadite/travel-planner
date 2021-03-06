package edu.nc.travelplanner.model.response;

import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.Row;
import edu.nc.travelplanner.model.response.elements.*;

import java.util.*;

public class ViewResponseBuilder {

    private class ViewElementsIndexCounter {
        private int titleIndex = 1;
        private int checkboxIndex = 1;
        private int radioboxIndex = 1;
        private int checkboxBlockIndex = 1;
        private int radioboxBlockIndex = 1;
        private int textboxIndex = 1;
        private int datepickerIndex = 1;
        private int dropdownlistIndex = 1;
        private int tableIndex = 1;

        public int getTitleIndex() {
            return titleIndex++;
        }

        public int getCheckboxIndex() {
            return checkboxIndex++;
        }

        public int getCheckboxBlockIndex() {
            return checkboxBlockIndex++;
        }

        public int getRadioboxIndex() {
            return radioboxIndex++;
        }

        public int getRadioboxBlockIndex() {
            return radioboxBlockIndex++;
        }

        public int getTextboxIndex() {
            return textboxIndex++;
        }

        public int getDatepickerIndex() {
            return datepickerIndex++;
        }

        public int getDropdownlistIndex() {
            return dropdownlistIndex++;
        }

        public int getTableIndex() {
            return dropdownlistIndex++;
        }
    }

    class ViewElementsPostfixHolder {
        private String titlePostfix = "title";
        private String checkboxPostfix = "checkbox";
        private String radioboxPostfix = "radiobox";
        private String checkboxesBlockPostfix = "block";
        private String radioboxesBlockPostfix = "block";
        private String textboxPostfix = "textbox";
        private String datepickerPostfix = "datepicker";
        private String dropdownlistPostfix = "dropdownlist";
        private String tablePostfix = "table";
    }

    ViewElementsIndexCounter indexCounter = new ViewElementsIndexCounter();
    ViewElementsPostfixHolder elementsPostfixHolder = new ViewElementsPostfixHolder();


    private ViewResponse viewResponse = new ViewResponse();


    public ViewResponseBuilder addTitleElement(String id, String data) {
        viewResponse.addElement(new TitleViewElement(elementsPostfixHolder.titlePostfix + indexCounter.getTitleIndex() + "." + id, data));
        return this;
    }

    public ViewResponseBuilder addCheckboxes(Map<String, String> options) {
        int currentCheckboxBlockIndex = indexCounter.getCheckboxBlockIndex();
        options.forEach((key, value) -> viewResponse.addElement(
                new CheckViewElement(elementsPostfixHolder.checkboxesBlockPostfix + currentCheckboxBlockIndex + "." +
                        elementsPostfixHolder.checkboxPostfix + indexCounter.getCheckboxIndex() + "." + key, value)));
        return this;
    }

    public ViewResponseBuilder addRadioboxes(Map<String, String> options) {
        int currentRadioboxBlockIndex = indexCounter.getRadioboxBlockIndex();
        options.forEach((key, value) -> viewResponse.addElement(
                new RadioViewElement(elementsPostfixHolder.radioboxesBlockPostfix + currentRadioboxBlockIndex + "." +
                        elementsPostfixHolder.radioboxPostfix + indexCounter.getRadioboxIndex() + "." + key, value)));
        return this;
    }

    public ViewResponseBuilder addTextbox(String id, String data) {
        viewResponse.addElement(new TextInputViewElement(elementsPostfixHolder.textboxPostfix + indexCounter.getTextboxIndex() + "." + id, data));
        return this;
    }

    public ViewResponseBuilder addDropdownList(String id, Map<String, String> options) {
        int currentIndex = indexCounter.getDropdownlistIndex();
        Map<String, String> newOptions = new HashMap<>();
        options.forEach((k, v) -> newOptions.put(elementsPostfixHolder.dropdownlistPostfix + currentIndex + "." + k, v));
        viewResponse.addElement(new DropDownListViewElement(elementsPostfixHolder.dropdownlistPostfix + currentIndex + "." + id, newOptions));
        return this;
    }

    public ViewResponseBuilder addDatePicker(String id, String data) {
        viewResponse.addElement(new DatePickerViewElement(elementsPostfixHolder.datepickerPostfix + "." + indexCounter.getDatepickerIndex() + "." + id, data));
        return this;
    }

    public ViewResponseBuilder addTable(String id, List<Row> rows, List<Row> columnsDefsRowList, List<String> links, Boolean multiPick, Boolean canPick, Boolean oneLine) {
        /*Row columnsDefsRow = new Row();
        columnDefs.forEach((key, value) -> {
            if (value != "") columnsDefsRow.addColumn(new Column(key, value));
        });
*/
        viewResponse.addElement(new TableViewElement(elementsPostfixHolder.tablePostfix + "." + indexCounter.getTableIndex() + "." + id, rows, columnsDefsRowList, links, multiPick, canPick, oneLine));
        return this;
    }

    public String getDatePickerName(String id, String data, Integer index) {
        return elementsPostfixHolder.datepickerPostfix + "." + index + "." + id;
    }

    public ViewResponse build() {
        return viewResponse;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
