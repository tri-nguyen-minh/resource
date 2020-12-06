package utils;

import dtos.ResourceDTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author TNM
 */
public class Utilities implements Serializable {

    private static final DateFormat FORMAT_FOR_PRINT = new SimpleDateFormat("MMM dd, yyyy");
    private static final DateFormat FORMAT_FOR_DB = new SimpleDateFormat("yyyy-MM-dd");

    public List<ResourceDTO> manageSearchList(List<ResourceDTO> searchlist, HashMap<String, Integer> bookingList) {
        for (int i = 0; i < searchlist.size(); i++) {
            String resourceId = searchlist.get(i).getId();
            if (bookingList.containsKey(resourceId)) {
                int quantityAvailable = searchlist.get(i).getQuantity() - bookingList.get(resourceId);
                if (quantityAvailable > 0) {
                    searchlist.get(i).setAmountAvailable(quantityAvailable);
                } else {
                    searchlist.get(i).setAmountAvailable(0);
                }
            }
        }
        return searchlist;
    }

    public boolean checkDateSwitched(String startDate, String endDate) throws Exception {
        boolean dateSwitched = false;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = format.parse(startDate);
        Date dateEnd = format.parse(endDate);
        long difference = dateEnd.getTime() - dateStart.getTime();
        if (difference <= 0) {
            dateSwitched = true;
        }
        return dateSwitched;
    }

    public String formatDateForPrint(String dateString) throws Exception {
        Date date = FORMAT_FOR_DB.parse(dateString);
        return FORMAT_FOR_PRINT.format(date);
    }

    public String formatDateForDB(Date date) {
        return FORMAT_FOR_DB.format(date);
    }
}
