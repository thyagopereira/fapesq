package com.example.demo.Services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.util.Caso;

import org.springframework.stereotype.Service;

@Service
public class AffectedService {
    private final String file_separator = System.getProperty("file.separator");
    private final String path = "dados" + file_separator;


    private LinkedList<HashMap<String, Integer>> real_database(String cep, double raio) {
        LinkedList<HashMap<String, Double>> affected_streets = affected_area(cep, raio);
        LinkedList <Caso> cases = new LinkedList<>();
        String row;
        
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path + "BaseFiltrada.csv"));

            while ((row = csvReader.readLine()) != null) {
                String[] list = row.split(",");

                if (list.length == 2 && list[1].split("/").length == 3) {
                    Caso case_ = new Caso(new SimpleDateFormat("dd/MM/yyyy").parse(list[1]), list[0]);
                    cases.add(case_);
                }
            }

            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinkedList <Caso> interested_cases = new LinkedList<>();

        for (Caso case_ : cases) {
            if (affected_streets.contains(get_coord_cep(case_.getCep()))) {
                interested_cases.add(case_);
            }
        }

        Comparator<Caso> sortByDate = (Caso case_1, Caso case_2) -> case_1.getData().compareTo(case_2.getData());
        interested_cases.sort(sortByDate);

        List<LocalDate> all_dates = generate_date_interval();
        LinkedList<LocalDate> cases_dates = new LinkedList<>();

        for (Caso elem: interested_cases) {
            cases_dates.add(elem.getData().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate());
        }

        LinkedList<LocalDate> zero_dates = new LinkedList<>();

        for (LocalDate elem: all_dates) {
            if (!cases_dates.contains(elem)) {
                zero_dates.add(elem);
            }
        }

        LinkedList<HashMap<LocalDate,Integer>> result = new LinkedList<>();

        for (LocalDate day: all_dates) {
            LocalDate yesterday = day.minus(1, ChronoUnit.DAYS);
            HashMap<LocalDate,Integer> aux = new HashMap<>();

            if(zero_dates.contains(day) && !all_dates.contains(yesterday)) {
                aux.put(day, 0);
            } else if (zero_dates.contains(day) && cases_dates.contains(yesterday)){
                aux.put(day, get_days(cases_dates, yesterday));
            } else {
                aux.put(day, get_days(cases_dates, day));
            }

            result.add(aux);
        }

        LinkedList<HashMap<String, Integer>> acumulated = get_acumulated(result);
        return acumulated;
    
    }


    private static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

    private double get_distance(HashMap <String, Double> coord1, HashMap <String, Double> coord2) {
        double lat1 =  Math.toRadians(coord1.get("lat"));
        double lng1 =  Math.toRadians(coord1.get("lng"));

        double lat2 =  Math.toRadians(coord2.get("lat"));
        double lng2 =  Math.toRadians(coord2.get("lng"));

        double dlat = lat2 - lat1;
        double dlng = lng2 - lng1;

        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlng / 2.0), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371.0;

        return c * r;
    }

    private HashMap<String, Double> get_coord_cep(String cep) {
        if (!isNumeric(cep)) {
            return new HashMap<>();
        }

        HashMap<String, Double> cord = new HashMap<>();
        String row;

        cord.put("lat", 0.0);
        cord.put("lng", 0.0);

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path + "CoordenadasFiltradas.csv"));

            while ((row = csvReader.readLine()) != null) {
                String [] records = row.split(",");

                if (records[0].equals(cep)) {
                    cord.put("lat", Double.parseDouble(records[records.length - 2]));
                    cord.put("lng", Double.parseDouble(records[records.length - 1]));
                    
                    csvReader.close();
                    return cord;
                }
            }

            csvReader.close();
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return cord;
    }

    // Função /area/{cep}/{raio}
    public LinkedList<HashMap<String, Double>> affected_area(String cep, double raio) {
        LinkedList<HashMap<String, Double>> result = new LinkedList<>();
        HashMap<String, Double> coord1 = get_coord_cep(cep);
        HashMap<String, Double> coord2;
        
        double distance;
        String row;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path + "CoordenadasFiltradas.csv"));

            while ((row = csvReader.readLine()) != null) {
                String [] records = row.split(",");
                coord2 = get_coord_cep(records[0]);
                
                if (coord2.size() != 0) {
                    distance = get_distance(coord1, coord2);
                    if (distance <= raio) {
                        result.add(coord2);
                    }
                }               
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result; 
    }

    private List<LocalDate> generate_date_interval() {
        LocalDate startDate = LocalDate.of(2020, 3, 27);
        LocalDate endDate = LocalDate.now().plusDays(1);
 
            return startDate.datesUntil(endDate)
              .collect(Collectors.toList());
    }

    private int get_days(LinkedList<LocalDate> days, LocalDate day) {
        int count = 0;
        Iterator<LocalDate> iterator = days.iterator();
        
        while (iterator.hasNext()) {
            if (iterator.next().equals(day)) {
                count += 1;
            }
        }

        return count;
    }

    private LinkedList<HashMap<String, Integer>> get_acumulated(LinkedList<HashMap<LocalDate, Integer>> cases){
        LinkedList<HashMap<String, Integer>> acumulated = new LinkedList<>();
        int [] aux = new int[cases.size()];
        
        for (int i = 0; i < cases.size(); i++) {
            aux[i] = cases.get(i).values().iterator().next();
        }

        for (int i = 1; i < aux.length; i++) {
            aux[i] = aux[i] + aux[i - 1];
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int i = 0;

        for (HashMap<LocalDate, Integer> case_ : cases) {
            HashMap<String, Integer> temp = new HashMap<>();

            for (Map.Entry<LocalDate, Integer> entry : case_.entrySet()) {
                String key = entry.getKey().format(formatter);
                int value = aux[i];              

                temp.put(key, value);
            }

            i += 1;
            acumulated.add(temp);
        }

        return acumulated;
    }

    
}
