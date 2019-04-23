package edu.wpi.cs3733.d19.teamL.Reports;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportThread extends Thread {
    int type;

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
        else if (type == 2) {
            LocalDateTime cool = LocalDateTime.now();
            System.out.println(cool.toString());
            String cool2 = "2019-04-22T19:33:34.360";
            LocalDateTime cool3 = LocalDateTime.parse(cool2);
        }
        Thread.currentThread().stop();
    }
}
