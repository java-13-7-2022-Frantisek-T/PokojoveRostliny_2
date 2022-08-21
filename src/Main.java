import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Arrays.deepToString;

public class Main {

//  public static final String FILENAME = "kvetiny-spatne-frekvence.txt";
//  public static final String FILENAME = "kvetiny-spatne-datum.txt";
    public static final String FILENAME = "kvetiny.txt";

    public static final String FILENAME_OUT = "kvetiny_2.txt";

    public static final String DELIMITER = "\t";

    public static void main(String[] args) {


        System.out.println("************************( S T A R T )************************");
        // Načteme obsah textového souboru (včetně ošetření vzniku možných chyb
        PlantList plantList = null;
        try {
            plantList = PlantList.importFromFile(FILENAME, DELIMITER);
        }
        catch (PlantException e) {
            System.err.println("Soubor " + FILENAME + " se nepodařiol správně načíst !\n" + e.getLocalizedMessage());
        }
        System.out.println("************************( KONEC NAČÍTÁNÍ DAT Z TEXTOVÉHO SOUBORU )************************");

        try {
            if (plantList == null) {
                System.err.println("************************( SEZNAM KVĚTIN NENÍ VYTVOŘEN - PROGRAM SKONČÍ )************************");
        }
        else {
            Plant plant_4 = new Plant("První květina", "Poznámka", LocalDate.parse("2022-08-01"), LocalDate.parse("2022-08-03"), 1);
            Plant plant_3 = new Plant("Druhá květina", LocalDate.parse("2022-08-08"), 2);
            Plant plant_1 = new Plant("Třetí květina");
            Plant plant_0 = new Plant("");  // CO S TÍM? teoreticky může být prázdný řetězec správné, logoicky nesmysl
            // Přidání několika objektů do seznamu
            plantList.addPlant(plant_4);
            plantList.addPlant(plant_3);
            plantList.addPlant(plant_1);
            plantList.addPlant(plant_0);

            Plant plant_6 = new Plant("Divná květina", "Poznámka: sázena jako 6", LocalDate.parse("2022-08-16"), LocalDate.parse("2022-08-18"), 10);
            Plant plant_5 = new Plant("Divná květina", "Poznámka: sázena jako 5", LocalDate.parse("2022-08-16"), LocalDate.parse("2022-08-18"), 10);
            plantList.addPlant(plant_6);
            plantList.addPlant(plant_5);

            plantList.addPlant(plant_0);
            }
        }
        // Ošetření vyhozených vyjímek které mohly vzniknout v předchozím bloku programového kódu
        // Použil jsem vlastní třídu výjímek
        // Uznávám že popis "Chyby !" není nic moc ....
        catch (PlantException e) {
            System.err.println("Chyby !! " + e.getLocalizedMessage());
        }
        catch (NullPointerException e) {
            System.err.println("Chyby !!!!! " + e.getLocalizedMessage());
        }

        if (plantList != null) {
            for (Plant plant : plantList.getList()) {
                System.out.println( plant.getName() + " " +
                                    plant.getNotes() + " " +
                                    plant.getPlanted() + " " +
                                    plant.getFrequencyOfWatering() + " " +
                                    plant.getWatering()
                                   );
            }
            System.out.println("************************( VYPSÁN ROZŠÍŘENÝ SEZNAM KVĚTIN )************************");

            // Ke zkoušení rušení prvku kolekce (toho posledního bez názvu)
            try {
                int i = plantList.rangeOfPlanList() - 1;
                plantList.removePlant(plantList.getPlantFromIndex(i));
            }
            // Ošetření vyhozených vyjímek které mohly vzniknout v předchozím bloku programového kódu
            // Použil jsem vlastní třídu výjímek
            catch (PlantException e) {
                System.err.println("Chyby! " + e.getLocalizedMessage());
            }

            for (Plant plant : plantList.getList()) {
                System.out.println( plant.getName() + " " +
                                    plant.getNotes() + " " +
                                    plant.getPlanted() + " " +
                                    plant.getFrequencyOfWatering() + " " +
                                    plant.getWatering()
                                   );
            }
            System.out.println("************************( VYPSÁN SEZNAM KVĚTIN PO SMAZÁNÍ POSLEDNÍHHO PRVKU SERZNAMU )************************");

            // Pomocná proměnná
            Plant plant = new Plant("");

            // Zkoušení zadávání indexu mimo rozsah pole
            try {
                // Pomocná proměnná
                // index prvku, který chci z kolekce přečíst (a vypsat na monitor)
                int i = 3;
                plant = plantList.getPlantFromIndex(i);
                System.out.println( "Index prvku: " + i + " Popis prvku: " +
                                    plant.getName() + " " +
                                    plant.getNotes() + " " +
                                    plant.getPlanted() + " " +
                                    plant.getFrequencyOfWatering() + " " +
                                    plant.getWatering()
                                  );

            }
            catch (PlantException | IndexOutOfBoundsException e) {
                System.err.println("Prvek pole se nepodařilo načíst! \n" + e.getLocalizedMessage());
            }
            System.out.println("************************( VYPSÁNÍ JEDNOHO PRVKU ZE SEZNAMU KVĚTIN )************************");

            // KOSTRBATÉ PROVEDENÍ V CYKLU WHILE
            // Pomocná proměnná pro počítadlo v cyklus while
            int i = 0;
            // Výpis listu květin s číslem indexu a datem doporučené zálivky
            while (i < plantList.rangeOfPlanList()) {
                try {
                    plant = plantList.getPlantFromIndex(i);
                    System.out.println(i + 1 + " " + plant.getWateringInfo());
                }
                catch (PlantException e) {
                    System.err.println("Prvek pole se nepodařilo načíst! \n" + e.getLocalizedMessage());
                }
                finally {
                    // Zvyšování počitadla průchodů v cyklu o 1
                    // Sice v případě výjimky by se mělo z cyklu vyskočit ale raději volím tuto cestu jak se nezacyklit
                    i++;
                }
            }
            System.out.println("************************( VYPSÁNÍ DOPORUČENÉHO DATA DALŠÍ ZÁLIVKY - while )************************");

            // SNAD O NĚCO ZAJÍMAVĚJŠÍ PROVEDENÍ PŘES ListIteraror
            for (ListIterator<Plant> it = plantList.getList().listIterator(); it.hasNext(); ) {
                Plant plant1 = it.next();
                System.out.println(" " + plant1.getWateringInfo());
            }
            System.out.println("************************( VYPSÁNÍ DOPORUČENÉHO DATA DALŠÍ ZÁLIVKY - ListIterator )************************");

            // SNAD ELEGANTNĚJŠÍ PROVEDENÍ PŘES CYKLUS FOREACH
            for (Plant plant1 : plantList.getList()) {
                System.out.println(" - " + plant1.getWateringInfo());
            }
            System.out.println("************************( VYPSÁNÍ DOPORUČENÉHO DATA DALŠÍ ZÁLIVKY - foreach )************************");

            // DOPLNĚNO (dle návrhu lektora) - do seznamu různých způsobů výpisu kolekce ještě megakrátký zápis s využítím Stream API:
            plantList.getList().forEach(plant1 -> System.out.println(" -- " + plant1.getWateringInfo()));
            System.out.println("************************( VYPSÁNÍ DOPORUČENÉHO DATA DALŠÍ ZÁLIVKY - Stream API )************************");



        // NOVÉ - úkoly z lekce 06
        //  ZÁPIS DO SOUBORU
        //  Zapíšeme seznam rostlin do výstupního textového souboru:
            try {
                plantList.exportToFile (FILENAME_OUT, DELIMITER);
            }
            catch (PlantException e) {
                System.err.println("Soubor " + FILENAME_OUT + " zápis dat se nepodařil !\n" + e.getLocalizedMessage());
            }
            System.out.println("------------------------------( KONEC ZÁPISU DAT DO TEXTOVÉHO SOUBORU )------------------------------");

            // TŘÍDĚNÍ KOLEKCE
            // Vytvořím proměnnou plantsArray
            System.out.println("------------------------------( seznam květin převedu na kolekci )------------------------------");
            List<Plant> plantsArray = new ArrayList<>(plantList.getList());
            // Kolekci setřídíme podle názvů květin vzestupně
            System.out.println("------------------------------( kolekci setřídím VZESTUPNĚ podle názvů květin )------------------------------");
            Collections.sort(plantsArray);
            // Kolekci vypíšeme na konzoli
            System.out.println("------------------------------( vzestupně setřídenou kolekci (podle názvu) vypíšu na konzoli )------------------------------");
            System.out.println(deepToString(new List[]{plantsArray}).toString());
            // Kolekci setřídíme podle názvů květin sestupně
            System.out.println("------------------------------( kolekci setřídím SESTUPNĚ podle názvů květin )------------------------------");
            Collections.sort(plantsArray, Collections.reverseOrder());
            // Kolekci vypíšeme na konzoli
            System.out.println("------------------------------( sestupně setřídenou kolekci (podle názvu) vypíšu na konzoli )------------------------------");
            System.out.println(deepToString(new List[]{plantsArray}).toString());

            // Kolekci setřídíme podle data zálivky vzestupně
            System.out.println("------------------------------( kolekci setřídím VZESTUPNĚ podle data zálivky )------------------------------");
            Collections.sort(plantsArray, new PlantDateOfWateringComparator());
            // Kolekci vypíšeme na konzoli
            System.out.println("------------------------------( vzestupně setřídenou kolekci (podle data zálivky) vypíšu na konzoli )------------------------------");
            System.out.println(deepToString(new List[]{plantsArray}).toString());
            // Kolekci setřídíme podle data zálivky sestupně
            System.out.println("------------------------------( kolekci setřídím SESTUPNĚ podle data zálivky )------------------------------");
            Collections.sort(plantsArray, new PlantDateOfWateringComparator().reversed());
            // Kolekci vypíšeme na konzoli
            System.out.println("------------------------------( sestupně setřídenou kolekci (podle data zálivky) vypíšu na konzoli )------------------------------");
            System.out.println(deepToString(new List[]{plantsArray}).toString());

            // Kdy se zalévaly rostliny?
            // Zjisti a vypiš datumy, kdy jsi zaléval(a) naposledy některou rostlinu. Pokud některý den neprobíhala zálivka, dané datum nevypisuj.
            // Řešíme pouze data poslední zálivky. O předchozím zalévání nemáme nic uložené, takže předchozí zalévání nemůžeme do výpisu zarhnout.
            // Potřebuji vytvořit množinu (Set) datumů, kdy proběhla zálivka
            Set<LocalDate> set = new TreeSet<>();
            // Přenesu do množiny datumy, kdy proběhla zálivka
            plantList.getList().forEach(plant1 -> set.add(plant1.getWatering()));
            // Množinu vypíšeme na konzoli
            System.out.println("------------------------------( DATUMY kdy proběhly zálivky (jednotlivých květin) vypíšu na konzoli )------------------------------");
            System.out.println(set.toString());


            // Je datum v posledním týdnu?   !!!!!!!!!!!!!!!!!!!
            // Pokud ano, vypíši jej na konzoli
            // Přenesu do množiny datumy, kdy proběhla zálivka
            plantList.getList().forEach(plant1 -> set.add(plant1.getWatering()));
            // Pokud pokud to bylo před více jak sedmi dny, vymažu je z množiny
            set.removeIf(datum -> ChronoUnit.DAYS.between(datum, LocalDate.now()) >= 7);
            // Množinu vypíšeme na konzoli
            System.out.println("------------------------------( DATUMY ne starší 7 dní, kdy proběhly zálivky (jednotlivých květin) - vypíšu na konzoli )------------------------------");
            System.out.println(set.toString());
            // Asi by to šlo udělat elegantněji, ale mně se to do testování nepodařilo vnutit do foreach,
            // tak jsem to jen tupě z množiny datumů zálivek vyházel ---- prostě tahle cesta do Říma asi není ta nejkratší ....

            // SNAD JSEM NA NIC NEZAPOMNĚL ....

        }
    }
}