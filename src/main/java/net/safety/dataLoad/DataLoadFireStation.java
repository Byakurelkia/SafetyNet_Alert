package net.safety.dataLoad;

import com.jsoniter.any.Any;
import net.safety.model.FireStation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataLoadFireStation {

    private final static DataLoadInit allData = new DataLoadInit();
    Any allDataFromJSON = allData.readerFileJSON();

    public DataLoadFireStation() throws IOException {
    }

    public Set<FireStation> allFireStations(){
        Any allFireStations = allDataFromJSON.get("firestations");
        Set<FireStation> fireStationSet = new HashSet<>();
        allFireStations.forEach(f->
                fireStationSet.add(
                        new FireStation(
                                f.get("address").toString(),
                                f.get("station").toInt()
                        )
                )
        );
        return fireStationSet;
    }

}
