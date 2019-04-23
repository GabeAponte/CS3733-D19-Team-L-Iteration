package edu.wpi.cs3733.d19.teamL.Reports;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.scene.Parent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class ReportThread extends Thread {
    int type;
    String requestType;

    public ReportThread(int type){
        this.type = type;
    }


    public void run(){
        if(type == 1) {
            try {
                pathReportAccess p = new pathReportAccess();
                //Complete Pathfinding report
                Singleton single = Singleton.getInstance();
                ArrayList<Location> toAddSearch = new ArrayList<Location>();
                ArrayList<Location> toAddNoSearch = new ArrayList<Location>();

                for (Location l : single.lookup.values()) {
                    //sleep(1);
                    if (toAddSearch.size() < 5) {
                        if (toAddSearch.size() == 0) {
                            toAddSearch.add(l);
                        } else {
                            boolean hotFix = false; // if its not added in earlier, add it in last
                            for (int j = 0; j < toAddSearch.size(); j++) {
                                if (p.getNumSearched(l.getLongName()) > p.getNumSearched(toAddSearch.get(j).getLongName())) {
                                    //System.out.println("ADDING: size < 10");
                                    toAddSearch.add(j, l);
                                    hotFix = true;
                                    break;
                                }
                            }
                            if (!hotFix) {
                                toAddSearch.add(l);
                            }
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            if (p.getNumSearched(l.getLongName()) > p.getNumSearched(toAddSearch.get(i).getLongName())) {
                                //System.out.println("ADDING: size > 10");
                                toAddSearch.add(i, l);
                                toAddSearch.remove(5);
                                break;
                            }
                        }
                    }
                }
                for (Location l : single.lookup.values()) {
                    //sleep(1);
                    if (toAddNoSearch.size() < 5) {
                        if (toAddNoSearch.size() == 0) {
                            toAddNoSearch.add(l);
                        } else {
                            boolean hotFix = false; // if its not added in earlier, add it in last
                            for (int j = 0; j < toAddNoSearch.size(); j++) {
                                if (p.getNumTraveledTo(l.getLongName()) > p.getNumTraveledTo(toAddNoSearch.get(j).getLongName())) {
                                    //System.out.println("ADDING: size < 10");
                                    toAddNoSearch.add(j, l);
                                    hotFix = true;
                                    break;
                                }
                            }
                            if (!hotFix) {
                                toAddNoSearch.add(l);
                            }
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            if (p.getNumTraveledTo(l.getLongName()) > p.getNumTraveledTo(toAddNoSearch.get(i).getLongName())) {
                                //System.out.println("ADDING: size > 10");
                                toAddNoSearch.add(i, l);
                                toAddNoSearch.remove(5);
                                break;
                            }
                        }
                    }
                }

                List<BarGraphChartData> noSearchList = new ArrayList<BarGraphChartData>();
                for (int k = 0; k < 5; k++) {
                    noSearchList.add(new BarGraphChartData("Locations- Searched", toAddSearch.get(k).getLongName(), p.getNumSearched(toAddSearch.get(k).getLongName())));
                }
                List<BarGraphChartData> searchList = new ArrayList<BarGraphChartData>();
                for (int x = 0; x < 5; x++) {
                    searchList.add(new BarGraphChartData("Locations- Traveled To", toAddNoSearch.get(x).getLongName(), p.getNumTraveledTo(toAddNoSearch.get(x).getLongName())));
                }

                ArrayList<Integer> counts = p.getTypeCounts();
                List<PieChartData> pieChartData = new ArrayList<PieChartData>();
                pieChartData.add(new PieChartData("search", counts.get(0)));
                pieChartData.add(new PieChartData("poi", counts.get(1)));
                pieChartData.add(new PieChartData("selected", counts.get(2)));

                System.out.println("LOOPS COMPLETE");
                File f = new File("PathFindStats.jrxml");
                JasperReport jasperReport = null;
                String filePath = f.getAbsolutePath().replace('\\', '/');
                jasperReport = JasperCompileManager.compileReport(filePath);
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("CHART_DATASET", new JRBeanCollectionDataSource(noSearchList));
                paramMap.put("PIE_CHART", new JRBeanCollectionDataSource(pieChartData));
                paramMap.put("CHART_DATASET_NOSEARCH", new JRBeanCollectionDataSource(searchList));
                List<Object> data = new ArrayList<Object>();
                data.add(noSearchList);
                data.add(pieChartData);
                data.add(searchList);
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, dataSource);
                String outputPath = "outputPDF.pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                PdfReader reader = new PdfReader("outputPDF.pdf");
                reader.close();
                String path;
                PdfStamper stamper;
                reader.selectPages("1");
                stamper = new PdfStamper(reader, new FileOutputStream("PathFindReport.pdf"));
                stamper.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (type == 2) { //general service request report
            try {
                int count = 0;
                requestReportAccess ra = new requestReportAccess();
                ArrayList<serviceRequestReportData> completeList = new ArrayList<serviceRequestReportData>();
                while (count < ra.countRecords()) { //get all the report data for service requests
                    ArrayList<String> data = ra.getItems(count);
                    serviceRequestReportData d = new serviceRequestReportData(data.get(0),
                            data.get(1), data.get(2), data.get(3), data.get(4), data.get(5));
                    completeList.add(d);
                    count++;
                }
                //data acquired. Execute report generation
                HashMap<String, Integer> typeCountHash = new HashMap<String, Integer>(); //count for each type
                HashMap<String, Integer> locationCountHash = new HashMap<String, Integer>();
                HashMap<String, String> monthHash = new HashMap<String, String>();
                HashMap<String, Integer> specificMonthHash = new HashMap<String, Integer>();
                ArrayList<String> type = new ArrayList<String>();
                //fill the hash tables
                for (serviceRequestReportData s : completeList) {
                    //by type section
                    if (!typeCountHash.containsKey(s.getType())) {
                        typeCountHash.put(s.getType(), 1);
                        type.add(s.getType());
                    } else {
                        int old = typeCountHash.get(s.getType());
                        typeCountHash.replace(s.getType(), old, old + 1);
                    }
                    //by month section
                    LocalDateTime t = LocalDateTime.parse(s.getTimeOfRequest());
                    String monthName = t.getMonth().toString();
                    String x = monthName + "_" + s.getType();
                    if (!specificMonthHash.containsKey(x)) {
                        specificMonthHash.put(x, 1);
                        if (monthName.equals("APRIL")) {
                            monthHash.put(x, "April");
                        } else if (monthHash.equals("MARCH")) {
                            monthHash.put(x, "March");
                        } else if (monthHash.equals("FEBRUARY")) {
                            monthHash.put(x, "February");
                        }
                    } else {
                        int old = specificMonthHash.get(x);
                        specificMonthHash.replace(x, old, old + 1);
                    }
                    //overall month counters
                    if (!locationCountHash.containsKey(s.getLocation())) {
                        locationCountHash.put(s.getLocation(), 1);
                    } else {
                        int oldloc = locationCountHash.get(s.getLocation());
                        locationCountHash.replace(s.getLocation(), oldloc, oldloc + 1);
                    }
                }

                //sort the data (location)
                HashMap<String, Integer> sortedLocations = sortByValue(locationCountHash);
                int numLocations = 0;
                List<BarGraphChartData> locationData = new ArrayList<BarGraphChartData>();
                for (Map.Entry<String, Integer> lEntry : sortedLocations.entrySet()) {
                    if (numLocations < 5) {
                        locationData.add(new BarGraphChartData("Location", lEntry.getKey(), lEntry.getValue()));
                        numLocations++;
                    }

                }
                List<BarGraphChartData> monthTypeData = new ArrayList<BarGraphChartData>();
                for (Map.Entry<String, String> monthHashData : monthHash.entrySet()) {
                    String key = monthHashData.getKey();
                    String justType = key.substring(key.indexOf("_")+1);

                    monthTypeData.add(new BarGraphChartData(justType, monthHash.get(key), specificMonthHash.get(key)));
                }
                List<PieChartData> typeData = new ArrayList<PieChartData>();
                for (String p : typeCountHash.keySet()) {
                    typeData.add(new PieChartData(p, typeCountHash.get(p)));
                }

                //build chart- series = month, category = type, value = value
                System.out.println("LOOPS COMPLETE");
                File f = new File("ServiceRequestOverview.jrxml");
                JasperReport jasperReport = null;
                String filePath = f.getAbsolutePath().replace('\\', '/');
                jasperReport = JasperCompileManager.compileReport(filePath);
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("REPORTS_PER_WEEK", new JRBeanCollectionDataSource(monthTypeData));
                paramMap.put("PIE_CHART_TYPE", new JRBeanCollectionDataSource(typeData));
                paramMap.put("TOP_10_LOCATIONS", new JRBeanCollectionDataSource(locationData));

                List<Object> data = new ArrayList<Object>();
                data.add(monthTypeData);
                data.add(typeData);
                data.add(locationData);
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, dataSource);
                String outputPath = "outputPDF2.pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                PdfReader reader = new PdfReader("outputPDF2.pdf");
                reader.close();
                String path;
                PdfStamper stamper;
                reader.selectPages("1");
                stamper = new PdfStamper(reader, new FileOutputStream("ServiceRequestOverview.pdf"));
                stamper.close();
                reader.close();
                System.out.println("SUCCESS");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (type == 3) {
            int count = 0;
            requestReportAccess ra = new requestReportAccess();
            ArrayList<serviceRequestReportData> completeList = new ArrayList<serviceRequestReportData>();
            while (count < ra.countRecords()) { //get all the report data for service requests
                ArrayList<String> data = ra.getItems(count);
                if (data.get(3).equals(requestType)) {
                    serviceRequestReportData d = new serviceRequestReportData(data.get(0),
                            data.get(1), data.get(2), data.get(3), data.get(4), data.get(5));
                    completeList.add(d);
                }
                count++;
            }
            HashMap<String, Integer> employeeHash = new HashMap<String, Integer>();
            HashMap<String, Integer> specificType = new HashMap<String, Integer>();
            int countMeter = completeList.size();
            for (serviceRequestReportData s : completeList) {
                if (!specificType.containsKey(s.getSpecificType())) {
                    specificType.put(s.getSpecificType(), 1);
                }
                else {
                    int old = specificType.get(s.getSpecificType());
                    specificType.replace(s.getSpecificType(), old, old+1);
                }
                if (!employeeHash.containsKey(s.getAssignedToEID())) {
                    employeeHash.put(s.getAssignedToEID(), 1);
                }
                else {
                    int old = employeeHash.get(s.getAssignedToEID());
                    employeeHash.replace(s.getAssignedToEID(), old, old+1);
                }
            }
            List<PieChartData> specificTypeData = new ArrayList<PieChartData>();
            for (String p: specificType.keySet()) {
                specificTypeData.add(new PieChartData(p, specificType.get(p)));
            }
            List<PieChartData> employeeData = new ArrayList<PieChartData>();
            for (String p: employeeHash.keySet()) {
                employeeData.add(new PieChartData(p, employeeHash.get(p)));
            }


        }


        Thread.currentThread().stop();
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    //found online- geeks4geeks- sorts hashmap
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
