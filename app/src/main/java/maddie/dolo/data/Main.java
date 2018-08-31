package maddie.dolo.data;

public class Main {

    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Integer humidity;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;

    }

    public void setTemp_max(Double tempMin) {
        this.temp_min = tempMin;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTempMax(Double tempMax) {
        this.temp_max = tempMax;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
