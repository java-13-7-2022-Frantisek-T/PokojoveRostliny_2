
/*
Ošetření nesprávného vstupu
Vytvoř novou třídu výjimek s názvem PlantException. Bude potomkem (extends) třídy Exception.
Ošetři zadávání frekvence zálivky — pokud je parametrem 0 nebo záporné číslo, systém vyhodí výjimku třídy PlantException s vhodným popisem.
Obdobně ošetřete zadávání data poslední zálivky — nesmí být starší než datum zasazení rostliny.
 */

public class PlantException extends Exception {
    public PlantException(String message) {
        super(message);
    }
}
