import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author segarci & albamig
 */

public class WordList implements Cloneable{
	private char letter;
	private ArrayList<Integer> positions;
	private ArrayList<WordList> wordLists;

    /**
     * Constructor de la clase WordList
     *
     * @param letter caracter al que representa el objeto
     * @param positions lista que contiene las posiciones en las que se encuentra el texto
     * @param wordLists caracteres que le siguen en el texto
     */
	public WordList(char letter, ArrayList<Integer> positions, ArrayList<WordList> wordLists){
		this.letter = letter;
		this.positions = positions;
		this.wordLists = wordLists;
	}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Setter de wordLists
     * @param wordLists Lista de caracteres que siguen al caracter objeto
     */
    private void setWordLists(ArrayList<WordList> wordLists) {
        this.wordLists = wordLists;
    }

    private void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }




    /**
     * Getter de letter
     * @return letter
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Getter de positions
     * @return positions
     */
    public ArrayList<Integer> getPositions() {
        return positions;
    }

    /**
     * Getter de wordLists
     * @return wordLists
     */
    public ArrayList<WordList> getWordLists() {
        return wordLists;
    }


    /**
     * El método newInstance genera un ArrayList formado por objetos WordList
     *
     * @param strText Texto a partir del cual se generara el arbol
     * @param refi Nivel de profundidad que tendra el arbol
     * @return listLetter un ArrayList<WordList>
     */
	public static ArrayList<WordList> newInstance(StringBuilder strText, int refi){
		ArrayList<WordList> listLetter = new ArrayList<WordList>();
		char charLetter;
		WordList letter;

        //Genera el primer nivel del arbol
        for (int position = 0; position < strText.length(); position++){
            charLetter = strText.charAt(position);
            letter = new WordList(charLetter,new ArrayList<Integer>(), null);

            if (!letter.belongs(listLetter,position)){
                listLetter.add(letter);
            }
        }
        /*

        if (refi > 1){

            //Genera los n siguientes niveles
            for(int i = 0; i < listLetter.size(); i++)
                listLetter.get(i).addLetter(strText, refi -1, WordList.cloneList(listLetter));
        }

        */
        while (refi > 0){

            System.out.println("Hola");
            for(int i = 0; i < listLetter.size(); i++) {

                try {
                    System.out.println("Hola");

                    listLetter.get(i).addLetter(strText, refi - 1, WordList.cloneList(listLetter));


                } catch (NullPointerException ignore){}
            }
            refi--;
        }

		return  listLetter;
	}

    /**
     *
     * El metodo belongs es un metodo booleano, que indica si el objeto
     * pertenece o no a la lista que se le pasa como parametro
     * En el caso de que si pertenezca se le anade un nuevo elemento al atributo posicion
     *
     * @param arrayList lista a la que puede que pertenezca el objeto Wordlist
     * @param position posicion en el texto de la letra Wordlist
     * @return Resultado booleano en funcion de si el objeto pertene o no a la lista
     */
    private boolean belongs(ArrayList<WordList> arrayList,int position){
        for (int i = 0; i<arrayList.size(); i++){
            if ( this.getLetter() == arrayList.get(i).getLetter()){
                arrayList.get(i).getPositions().add(position);
                return true;
            }
        }
        this.getPositions().add(position);
        return false;
    }

    /**
     * El metodo addLetter genera un nivel mas de profundidad en el arbol
     * y se llama a si mismo recursivamente mientras refi sea mayor que 0
     * @param strText Texto a partir del cual se generara el arbol
     * @param refi Nivel de profundidad que tendra el arbol
     */
    private void addLetter(StringBuilder strText, int refi, ArrayList<WordList> listLetter) {

        char charLetter;

        this.setWordLists(listLetter);

        for (int a = 0; a < this.getPositions().size(); a++) {
            try {

                charLetter = strText.charAt(this.getPositions().get(a)+1);

                for (int i = 0; i<listLetter.size(); i++){
                    if ( charLetter == listLetter.get(i).getLetter()){
                        listLetter.get(i).getPositions().add(this.getPositions().get(a) + 1);
                    }
                }

            } catch (StringIndexOutOfBoundsException e){}
        }


    }

    public static ArrayList<WordList> cloneList(ArrayList<WordList> list) {
        ArrayList<WordList> clone = new ArrayList<WordList>(list.size());
        WordList wordList;
        for(WordList item: list) {


            if (item.getPositions().isEmpty()){
                clone.add(null);
            } else {

                try {
                    wordList = ((WordList) item.clone());
                    wordList.setPositions(new ArrayList<Integer>());

                    clone.add(wordList);

                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }


            }

        }
        return clone;
    }
}
