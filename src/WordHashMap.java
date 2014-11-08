import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by garciparedes on 7/11/14.
 */
public class WordHashMap {
    private ArrayList<Integer> positionList;
    private HashMap<Character, WordHashMap> hashList;
    private static HashMap<Character, WordHashMap> defaultHashList = new HashMap<Character, WordHashMap>();


    public WordHashMap(ArrayList<Integer> newPositionList, HashMap<Character, WordHashMap> newHashList) {
        this.positionList = newPositionList;
        this.hashList = newHashList;
    }

    public static HashMap<Character, WordHashMap> getDefaultHashList() {

        HashMap<Character, WordHashMap> clone = new HashMap<Character, WordHashMap>();

        for (Map.Entry<Character, WordHashMap> entry : defaultHashList.entrySet())
        {
            /*
            Para quitar toda optimizacion aqui se deberia generar el valor,
            es decir: new WordHashMap(new ArrayList<Integer>(), null)
            en vez de solo en el caso de que estuviera en el texto (linea 120)
             */
            clone.put(entry.getKey(),null);

        }

        return clone;
    }
    public ArrayList<Integer> getPositionList() {
        return positionList;
    }
    public HashMap<Character, WordHashMap> getHashList() {
        return hashList;
    }
    public void setHashList(HashMap<Character, WordHashMap> hashList) {
        this.hashList = hashList;
    }

    public static HashMap<Character, WordHashMap> newInstance(StringBuilder strText, int refi) {

        HashMap<Character, WordHashMap> newHashList = new HashMap<Character, WordHashMap>();

        ArrayList<Integer> newPositionList;

        char charLetter;


        //Genera el primer nivel del arbol
        for (int position = 0; position < strText.length(); position++) {
            charLetter = strText.charAt(position);
            if (newHashList.containsKey(charLetter)) {
                newHashList.get(charLetter).getPositionList().add(position);
            } else {
                newPositionList = new ArrayList<Integer>();
                newPositionList.add(position);
                newHashList.put(charLetter, new WordHashMap(newPositionList, null));
                defaultHashList.put(charLetter, new WordHashMap(null,null));
            }
        }

        if (refi > 1) {

            for (Map.Entry<Character, WordHashMap> entry : newHashList.entrySet())
            {
                entry.getValue().addLevel(refi-1, strText);
            }

        }

        return newHashList;
    }

    public void addLevel(int refi, StringBuilder stringBuilder){
        HashMap<Character, WordHashMap> newHashMap = getDefaultHashList();
        char charLetter;
        this.setHashList(newHashMap);


        for (int i = 0 ; i < this.getPositionList().size() ; i++){
            try {

                charLetter = stringBuilder.charAt(this.getPositionList().get(i)+1);

                if(newHashMap.get(charLetter) == null){
                    newHashMap.put(charLetter,new WordHashMap(new ArrayList<Integer>(), null));
                }

                this.getHashList().get(charLetter).getPositionList().add(this.getPositionList().get(i) + 1);

            } catch (StringIndexOutOfBoundsException ignored){}

        }
        if (refi > 0){

            for (Map.Entry<Character, WordHashMap> entry : newHashMap.entrySet())
            {
                if (entry.getValue() != null)
                    entry.getValue().addLevel(refi-1, stringBuilder);
            }


        }

    }

}
