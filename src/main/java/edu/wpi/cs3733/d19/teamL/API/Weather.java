package edu.wpi.cs3733.d19.teamL.API;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/**@author Nathan
 * Gets the current weather for Worcester, MA
 *         0: summary: "Clear"
 *         1: precipProbability: 0
 *         2: visibility: 9.92
 *         3: windGust: 7.72
 *         4: precipIntensity: 0
 *         5: icon: "clear-day"
 *         6: cloudCover: 0.07
 *         7: windBearing: 301
 *         8: apparentTemperature: 57.39
 *         9: pressure: 1020.82
 *         10: dewPoint: 38.85
 *         11: ozone: 347.94
 *         12: nearestStormBearing: 210
 *         13: nearestStormDistance: 504
 *         14: temperature: 57.39
 *         15: humidity: 0.5
 *         16: time: 06-04-2019 18:42:56
 *         17: windSpeed: 6.38
 *         18: uvIndex: 0
 */
public class Weather {
    ForecastIO fio;
    FIOCurrently currently;

    public Weather(){
        getCurrently();
    }

    public void getCurrently(){
        try {
            fio = new ForecastIO("88cbab72251b626bd6cf465fc89144b9");
            if(fio == null){
                throw new NullPointerException();
            }
        } catch (Exception e){
            fio = new ForecastIO("8bbd411df62726b90761db369453bcc8");
        }
        fio.setUnits(ForecastIO.UNITS_US);
        fio.setLang(ForecastIO.LANG_ENGLISH);
        fio.getForecast("42.269478", "-71.807783");
        //System.out.println("Timezone: "+fio.getTimezone());
        currently = new FIOCurrently(fio);
    }

    /**@author Nathan
     *
     * @return Gets URL of current-weather Icon
     */
    public String getIcon(){
        String type = currently.get().getByKey("icon");
        //System.out.println("weather is printing out: "+type);
        return type;
    }

    /**@author Nathan
     *
     * @return the "actual" temp
     */
    public String getActTemp() {
        String userFriendlyTemp = currently.get().getByKey("temperature");
        if(userFriendlyTemp.contains(".")) {
            userFriendlyTemp = userFriendlyTemp.substring(0, userFriendlyTemp.indexOf("."));
        }
        return userFriendlyTemp + " \u00b0 F";
    }

    /**@author Nathan
     *
     * @return the "feel" temp
     */
    public String getFeelTemp() {
        return currently.get().getByKey("apparentTemperature");
    }
}
