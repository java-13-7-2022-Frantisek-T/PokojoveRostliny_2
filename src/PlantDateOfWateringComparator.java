import java.util.Comparator;

// Tato konstrukce přes vytvoření samostatné třídy umožňuje mít libovolný počet variant použitelných pro porovnávání
public class PlantDateOfWateringComparator implements Comparator<Plant> {

    // Opět musím definovat jak porovnávat (prostě odečtu datumy)
    @Override
    public int compare(Plant o1, Plant o2) {
        return o1.getWatering().compareTo(o2.getWatering());
    }
}
