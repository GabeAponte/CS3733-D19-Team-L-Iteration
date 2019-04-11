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
        fio = new ForecastIO("8bbd411df62726b90761db369453bcc8");
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
      //  System.out.println(type);
        if(type.contains("clear")){
            return "weatherIcons/sunImage.jpg";
        } else if(type.contains("rain") || type.contains("sleet")){
            return "weatherIcons/rainImage.jpg";
        } else if(type.contains("partly") || type.contains("wind")){
            return "weatherIcons/partlyCloudyImage.jpg";
        } else if(type.contains("cloudy") || type.contains("fog")){
            return "weatherIcons/cloudyImage.jpg";
        } else if(type.contains("snow")){
            return "weatherIcons/snowImage.jpg";
        } else {
            return "weatherIcons/thunderImage.jpg";
        }
    }

    /**@author Nathan
     *
     * @return the "actual" temp
     */
    public String getActTemp() {
        return currently.get().getByKey("temperature");
    }

    /**@author Nathan
     *
     * @return the "feel" temp
     */
    public String getFeelTemp() {
        return currently.get().getByKey("apparentTemperature");
    }
}
