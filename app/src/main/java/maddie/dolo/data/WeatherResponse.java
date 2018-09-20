package maddie.dolo.data;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

    private String cod;
    private String message;
    private Integer cnt;
    private List<WeatherEntry> list = new ArrayList<>();

    public List<WeatherEntry> getList() {
        return list;
    }

    public void setList(List<WeatherEntry> list) {
        this.list = list;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
