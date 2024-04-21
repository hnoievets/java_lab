package edu.ntudp.fit.hnoievets.java_lab.packages.parser;

import edu.ntudp.fit.hnoievets.java_lab.packages.parser.model.CarInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AutoriaParser {
    private final String URL = "https://auto.ria.com/uk/car/";
    private final String PAGE_NUMBER_QUERY_PARAMETER = "?page=";
    private final int CONNECT_TIMEOUT = 10000;
    private final int FIRST_PAGE_NUMBER = 1;

    public List<CarInfo> parseByQuery(String brand, String model, String year, int outputLimit) {
        String validQuery = validateQuery(brand, model, year);
        ArrayList<CarInfo> cars = new ArrayList<>(outputLimit);

        Document document;
        int pageNumber = 1;
        String urlWithPageQueryParameter = URL + validQuery + PAGE_NUMBER_QUERY_PARAMETER;
        String fullUrl;

        while (cars.size() != outputLimit) {
            fullUrl = urlWithPageQueryParameter + pageNumber++;

            try {
                document = Jsoup.connect(fullUrl).timeout(CONNECT_TIMEOUT).get();
            } catch (IOException e) {
                if (pageNumber == FIRST_PAGE_NUMBER) {
                    throw new RuntimeException("Can`t connect with " + fullUrl, e);
                }

                break;
            }

            Elements tickets = Objects.requireNonNull(document.getElementById("searchResults"),
                            "#searchResults is empty")
                    .getElementsByClass("ticket-item");

            for (Element ticket : tickets) {
                if (cars.size() == outputLimit) {
                    break;
                }

                cars.add(parseTicket(ticket));
            }
        }

        return cars;
    }

    private String validateQuery(String brand, String model, String year) {
        char QUERY_SEPARATOR = '/';

        String yearQuery = year.isEmpty() ? "" : "year" + QUERY_SEPARATOR + year;

        String queryPartWithModel = model.trim();
        queryPartWithModel = queryPartWithModel.isEmpty() ? "" : queryPartWithModel + QUERY_SEPARATOR;

        return brand.trim() + QUERY_SEPARATOR +
                queryPartWithModel +
                yearQuery +
                QUERY_SEPARATOR;
    }

    private CarInfo parseTicket(Element ticket) {
        final String thousandsPlace = "000";

        CarInfo carInfo = new CarInfo();

        carInfo.setGeneration(ticket.firstElementChild().dataset().get("generation-name"));
        carInfo.setCheckedVinCode(!ticket.getElementsByClass("icon-car-check").isEmpty());
        carInfo.setLink(ticket.getElementsByClass("m-link-ticket")
                .getFirst().attribute("href").getValue());

        Element content = ticket.getElementsByClass("content").getFirst();

        carInfo.setName(content.getElementsByClass("ticket-title").getFirst()
                .getElementsByTag("span").getFirst().text());

        Map<String, String> priceDataset = content.getElementsByClass("price-ticket").getFirst().dataset();
        carInfo.setPrice(Float.parseFloat(priceDataset.get("main-price")));
        carInfo.setCurrency(priceDataset.get("main-currency"));

        Element characteristic = content.getElementsByClass("characteristic").getFirst();

        String mileageText = characteristic.getElementsByClass("js-race").getFirst().text();
        try {
            carInfo.setMileage(Integer.parseInt(mileageText.split(" ")[0] + thousandsPlace));
        } catch (NumberFormatException ignored) {
            carInfo.setMileage(0);
        }

        carInfo.setLocation(characteristic.getElementsByClass("view-location").getFirst().ownText());
        carInfo.setEngine(characteristic.getElementsByAttributeValue("title", "Тип палива")
                .getFirst().parent().text());
        carInfo.setAkp(characteristic.getElementsByAttributeValue("title", "Тип коробки передач")
                .getFirst().parent().text());

        Element baseInfo = content.getElementsByClass("base_information").getFirst();

        if (carInfo.getCheckedVinCode()) {
            carInfo.setVinCode(baseInfo.getElementsByClass("label-vin")
                    .getLast().getElementsByTag("span").get(1).text());
        } else if (!baseInfo.getElementsByClass("vin-code").isEmpty()) {
            carInfo.setVinCode(baseInfo.getElementsByClass("vin-code").getFirst().text());
        }

        carInfo.setAfterAccident(!baseInfo.getElementsByClass("state").isEmpty());

        return carInfo;
    }
}
