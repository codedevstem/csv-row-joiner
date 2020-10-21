package no.cds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        Scanner sc;
        try {
            sc = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getLocalizedMessage());
            return;
        }
        sc.useDelimiter("\n");   //sets the delimiter pattern
        Map<String, Integer> map = new HashMap<>();
        while(sc.hasNext()) {
            String[] line = sc.next().split(",");
            String vendor = line[0];
            String count = line[1];
            map.put(vendor, Integer.valueOf(count.strip()));
        }
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> i : map.entrySet()) {
            ArrayList<String> reqsList = new ArrayList<>(Arrays.asList(i.getKey().split(" ")));
            Integer value = 0;
            String newKey = i.getKey();
            for (Map.Entry<String, Integer> j : result.entrySet()) {
                ArrayList<String> qualList = new ArrayList<>(Arrays.asList(j.getKey().split(" ")));
                qualList.retainAll(reqsList);
                double matchPercentage =  (double) qualList.size() / (double) reqsList.size() * 100.0;
                if (i.getKey().equals(j.getKey())) continue;
                if (matchPercentage >= 50) {
                    value = j.getValue();
                    newKey = j.getKey();
                    break;
                }
            }
            result.put(newKey, i.getValue() + value);
        }
        Map<String, Integer> sortedResult = sortByValue(result);
        List<String> resultList = new ArrayList<>();
        sortedResult.forEach((key, value) -> {
            resultList.add(key + "," + value);
        });
        Collections.reverse(resultList);
        givenDataArray_whenConvertToCSV_thenOutputCreated(resultList);
        sc.close();
    }

    public static void givenDataArray_whenConvertToCSV_thenOutputCreated(List<String> resultList) {
        File csvOutputFile = new File("./result/result.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            resultList
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.out.println("could not write to file " + e.getLocalizedMessage());
            exit(0);
        }
    }

    public static Map<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(hm.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
