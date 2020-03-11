import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Game {

    int randomNumber = (int) (Math.random() * 25) + 1;
    String numeFilm;
    String newNumeFilm;
    String vectorCaractere = "";
    String vectorMemorareCaractere = "";
    int nrGresite = 0;
    boolean gameRunning = true, eOk, eOk1;
    boolean literaValida = true, literaDejaIntrod = false;

    Game() {
    }

    public void chooseTitle() {
        try {
            File file = new File("numeFilme.txt");
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < randomNumber; i++)
                numeFilm = scanner.nextLine();

            newNumeFilm = numeFilm.replaceAll("[a-zA-Z]", "_");
        } catch (FileNotFoundException exception) {
            System.out.println("Fisier invalid!");
            gameRunning = false;
        }
    }

    public boolean aMaiFostIntrod(char c) {
        int currIndex = vectorMemorareCaractere.indexOf(c);
        return currIndex > -1;
    }

    public void verif(char litera) {
        do {
            eOk = false;
            literaValida = true;
            literaDejaIntrod = false;
            if (litera < 'a' || litera > 'z') {
                literaValida = false;
                eOk1 = false;
                break;
            }
            if (!aMaiFostIntrod(litera)) {
                vectorMemorareCaractere += litera;
                eOk = true;
            } else {
                literaDejaIntrod = true;
                break;
            }

        }
        while (!eOk);

        int index = numeFilm.indexOf(litera);

        //verificare litera + afisare
        if (literaValida && !literaDejaIntrod)
            if (index != -1)
                while (index >= 0) {
                    char[] numeFilmChar = newNumeFilm.toCharArray();
                    numeFilmChar[index] = litera;
                    newNumeFilm = String.valueOf(numeFilmChar);
                    index = numeFilm.indexOf(litera, index + 1);
                }
            else {
                nrGresite++;
                vectorCaractere = vectorCaractere + " " + litera;
            }
    }

    public boolean checkIfWon() {
        if (newNumeFilm.indexOf('_') == -1) return true;
        else return false;
    }

    public String getMovieName() {
        return newNumeFilm;
    }

    public int getNrGresite() {
        return this.nrGresite;
    }

    public boolean getLiteraValida() {
        return literaValida;
    }

    public boolean getLiteraDejaIntrod() {
        return literaDejaIntrod;
    }

    public String getVectorCaract() {
        return "Ai nimerit " + "(" + nrGresite + ")" + " litere gresite: " + vectorCaractere;
    }

}