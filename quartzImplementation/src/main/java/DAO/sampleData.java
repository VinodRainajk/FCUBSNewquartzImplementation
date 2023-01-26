package DAO;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class sampleData {

    public List<Integer> loadData()
    {
        System.out.println("Sending Data ");
        List<Integer> dataprepared = new ArrayList<>();
        for(int idx =0; idx < 100 ; idx++)
        {
            dataprepared.add(idx);
        }
        return dataprepared;
    }
}
