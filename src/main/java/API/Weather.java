package API;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Weather {
    public Weather(){}

    public String getCurrently(int i) {
        ForecastIO fio = new ForecastIO("8bbd411df62726b90761db369453bcc8");
        fio.setUnits(ForecastIO.UNITS_US);
        fio.setLang(ForecastIO.LANG_ENGLISH);
        fio.getForecast("42.269478", "-71.807783");
        System.out.println("Latitude: "+fio.getLatitude());
        System.out.println("Longitude: "+fio.getLongitude());
        System.out.println("Timezone: "+fio.getTimezone());
        FIOCurrently currently = new FIOCurrently(fio);
        //Print currently data
        System.out.print("\nCurrently\n");
        String[] f = currently.get().getFieldsArray();
        //f[5] is for icon
        //f[5] + currently.get().getByKey(f[5])
        return f[i] + currently.get().getByKey(f[i]);
    }
}
