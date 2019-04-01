package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class WeatherRequest extends ASIARequest {
    private String id;
    private String date;
    private String aggregator;
    private String weatherParam;
    private String offset;

    public WeatherRequest(String id, String date, String aggregator, String weatherParam, String offset) {
        this.id = id;
        this.date = date;
        this.aggregator = aggregator;
        this.weatherParam = weatherParam;
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherRequest that = (WeatherRequest) o;
        return id.equals(that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(aggregator, that.aggregator) &&
                Objects.equals(weatherParam, that.weatherParam) &&
                Objects.equals(offset, that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, aggregator, weatherParam, offset);
    }
}
