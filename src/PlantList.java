/*
Vytvoření seznamu květin

    Vytvoř třídu pro ukládání seznamu pokojových květin.
    Jako atribut bude mít kolekci, uchovávající objekty s informacemi o květinách.
    Přidej metody pro
        - přidání nové květiny,
        - získání květiny na zadaném indexu
        - odebrání květiny ze seznamu

Načtení ze souboru

    Přidej do seznamu květin metodu pro načtení květin ze souboru.
        V případě chybného vstupu vyhoď výjimku.

    !!!!!!
    Přidej do seznamu květin metodu pro uložení aktualizovaného seznamu do souboru.
    !!!!!!

Ověření

    Načti seznam květin ze souboru kvetiny.txt.

    Soubor si stáhni celý! Při kopírování obsahu IntelliJ nahradí tabulátory za mezery a načtení nebude fungovat správně!

    Vypiš na obrazovku informace o zálivce pro všechny květiny.
    Přidej dvě nové květiny do seznamu. Jednu květinu odeberte.
    Ulož seznam květin do nového souboru a ověřte, že je jeho obsah správný. Výsledný soubor by měl vypadat takto:

            Fialka 1 Popis fialky - je fialová a hezká   3   2021-05-12  2021-01-01
            Vánoční hvězda bez poznámky      4   2021-05-10  2021-04-01
            Bazalka v kuchyni        3   2021-09-04  2021-09-04

    Vyzkoušej opětovné načtení vygenerovaného souboru.

Poškozený soubor 1

    Vyzkoušej, že se aplikace bude chovat správně při načtení vadného souboru kvetiny-spatne-datum.txt.

    Soubor si stáhni celý! Při kopírování obsahu IntelliJ nahradí tabulátory za mezery a načtení nebude fungovat správně!

Poškozený soubor 2

    Vyzkoušej, že se aplikace bude chovat správně při načtení vadného souboru kvetiny-spatne-frekvence.txt

    Soubor si stáhni celý! Při kopírování obsahu IntelliJ nahradí tabulátory za mezery a načtení nebude fungovat správně!
*/

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class PlantList {

    // METODA pro zápis do souboru
    public void exportToFile (String filename, String delimiter) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            // Vezmi postupně každou položku ze seznamu plantList:
            if (plantList.isEmpty()) {
                throw new PlantException("Seznam květin je prázdný, není co zapisovat do výstupního souboru " + filename + " \n");
            }
            for (Plant plant : plantList) {                             //  getList()
                writer.println( plant.getName() + delimiter +
                                plant.getNotes() + delimiter +
                                plant.getFrequencyOfWatering() + delimiter +
                                plant.getWatering() + delimiter +
                                plant.getPlanted()
                              );
            }
        }
        catch (IOException e) {
            System.err.println("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
        catch (PlantException e) {
            System.err.println("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }

    // METODA pro čtení ze souboru
    public static PlantList importFromFile(String filename, String delimiter) throws PlantException {
        // 1. přečíst řádky ze souboru
        // 2. Rozdělit řádky na jednotlivé položky a převést je na odpovídající datový typ
        // 3. Sestavit z jednotlivých proměnných objekty

    PlantList result = new PlantList();

        // Pomocná proměnná typu String pro uchování načteného řádku z textového souboru
        String line = "";
        // Pomocná proměnná typu int pro počítání načtených řádků
        int pocRad = 0;
        // 1. Čtení ze souboru přes třídu Scanner - pomocí try-with-resources
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                // Přečteme řádek ze souboru a pro jeho další zpracování jej uložíme do pomocné proměnné
                line = scanner.nextLine();
                pocRad++;
                System.out.println("Načten " + pocRad + " řádek:" + line);      // ----- pomocný výpis na obrazovku při ladění aplikace
                // 2. Rozdělíme řádek podle nalezených odělovacích znaků (zde konkrétně tabelátorů) na jednotlivé položky a vracíme pole (array) řetězců
                String[] items = line.split(delimiter);
                // Pokud není počet prvků pole správný (má jich být 5), tak vyhodíme vyjímku kde uvedeme popis chyby
                if (items.length != 5) {
                    throw new PlantException("Na řádku: " + line + " je špatný počet položek: " + items.length + "\n");
                }
                // 3. Sestavíme z jednotlivých proměnných objekty
                // Uložíme položky pole do proměnných typu String (pokud nebyla vyhozena vyjímka pro nesprávný počet položek na zpracovávaném řádku - to by se neprovedlo nic)
                String name = items[0];             // První  položka: název rostliny
                String notes = items[1];            // Druhá  položka: poznámky
                String frequencyAsText = items[2];  // Třetí  položka: bězná frekvence zálivky
                String wateringAsText = items[3];   // Čtvrta položka: datum poslední zálivky
                String plantedAsText = items[4];    // Pátá   položka: datum kdy byla zasazena
                // Pokračujeme tak, že převedeme položky pole, jež jsou typu String na správný datový typ
                // String name;                                                 // název rostliny - není potřeba konverze (String je správný datový typ)
                // String notes;                                                // poznámky - není potřeba konverze (String je správný datový typ)
                LocalDate planted = LocalDate.parse(plantedAsText);             // Převede text na datum určující datum zasazení květiny
                LocalDate watering = LocalDate.parse(wateringAsText);           // Převede text na datum určující datum poslední zálivky
                int frequencyOfWatering = Integer.parseInt(frequencyAsText);    // Převedeme text na integer určující běžnou frekvenci zálivky
                // Vytvoříme objekt třídy Plant (z proměnných získaných převodem položek z pole řetězců na správné datové typy)
                Plant plant = new Plant(name, notes, planted, watering, frequencyOfWatering);
                // Přidáme nově vytvořený objekt třídy Plant do kolekce obsahující objekty třídy Plant
                result.addPlant(plant);
            }
        // Zachytáváme vyjímky
        // Výjímka vznikla, pokud textový soubor nebyl nalezen (a je obsloužena vyhozením další vyjímky - jedná se o vyjímku z námi vytvořené třídy PlantException)
        } catch (FileNotFoundException e) {
            // Oznámíme popis chyby tak, že vyhodíme výjímku přes námi vytvořenou třídu vyjímek PlantException
            throw new PlantException("Soubor " + filename + " nebyl nalezen \n" + e.getLocalizedMessage() + "\n");
        // Výjímka vzniklá, pokud se nepodařil převod řetězce na datum (a je obsloužena vyhozením další vyjímky - jedná se o vyjímku z námi vytvořené třídy PlantException)
        } catch (DateTimeParseException e) {
            // Oznámíme popis chyby tak, že vyhodíme výjímku přes námi vytvořenou třídu vyjímek PlantException
            throw new PlantException("Špatný formát datumu na řádku: " + line + " \n" + e.getLocalizedMessage() + "\n");
        // Výjimka indikuje pokus o převod řetězce na některý číselný typ v případě že řetězec nemá patřičný typ
        } catch (NumberFormatException e) {
            // Oznámíme popis chyby tak, že vyhodíme výjímku přes námi vytvořenou třídu vyjímek PlantException
            throw new PlantException("Špatný formát čísla na řádku: " + line + " \n" + e.getLocalizedMessage() + "\n");
        }

        // Předaání-předání výsledku
        return result;

    }

    // Vytvoření pole pro ukládání jednotlivývh květin
    List<Plant> plantList = new ArrayList<>();

    // Přidání prvku-květiny do seznamu
    public void addPlant(Plant newPlant) {
        plantList.add(newPlant); }

    // Odebrání prvku-květiny do seznamu
    public void removePlant(Plant plant) {
        plantList.remove(plant);
    }

    // Získání prvku-květiny na zadaném indexu
    public Plant getPlantFromIndex (int indexOfPlant) throws PlantException {

        // Ošetření výskytu nenaplněného pole objektů
        if (plantList.size() == 0) {
            throw new PlantException("Pole které má obsahovat seznam květin je prázdné !");
        }

        // Ošetření chybně zadané hodnoty indexu odkazující mimo rozsah pole
        try {
            return plantList.get(indexOfPlant);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            throw new PlantException("Prvek nenalezen, index " + indexOfPlant + " odkazuje mimo rozsah pole [0 .. "+ plantList.size() +"]" + e.getLocalizedMessage() + "\n");
        }
    }

    // předávám KOPII atribut - seznamu květinu !!!!!
    public List<Plant> getList() {
        return new ArrayList<>(plantList);
    }

    // předávám počet prvků pole - seznamu květinu !!!!!
    public int rangeOfPlanList() {
        return plantList.size();
    }
}