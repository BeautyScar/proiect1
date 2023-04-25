package musicinterpretor;

import java.io.File;
import java.io.FileNotFoundException;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import java.util.Scanner;



public class MusicInterpretor {

    
    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println("Please insert the path of the file you want to convert and press enter.");
        //Scanner scaner = new Scanner(System.in);
        //String s = scaner.nextLine();
        String s = "C:\\Users\\Gnegnel\\Desktop\\Mozart-main\\output\\txt\\02.txt";
        String pattern = convertSequence(s);
        Player player = new Player();
        player.play(pattern);



        //System.out.println(scaner.nextLine());
        //System.out.println(s);
        
        
        
    }
    public static String convertNote(String note){
        if(note.endsWith("]")){
            note = note.replace("]", "");
        }
        
        if(note.contains("meter")){
            note = note.replaceFirst("\\Wmeter<\"\\d/\\d\">", "");
        }
        note = note.toUpperCase();
        note = note.replace("\u0026", "b");
        
        if(note.matches("\\w\\d.*")){
            note = note.replaceFirst("\\d", Integer.toString(Integer.parseInt(""+ note.charAt(1)) + 4));
            note = note.substring(0, 3).concat(String.valueOf(1.0/Integer.parseInt(note.substring(3))));
        }else{
            if(note.matches("\\w\\w.*") || note.matches("\\w\\W.*")){
                if(note.matches("\\w\\w\\d.*") || note.matches("\\w\\W\\d.*")){
                    note = note.replaceFirst("\\d", Integer.toString(Integer.parseInt(""+ note.charAt(2)) + 4));
                    note = note.substring(0, 4).concat(String.valueOf(1.0/Integer.parseInt(note.substring(4))));
                }else{
                    if(note.matches("\\w\\w\\w\\d.*") || note.matches("\\w\\W\\W\\d.*")){
                        note = note.replaceFirst("\\d", Integer.toString(Integer.parseInt(""+ note.charAt(3)) + 4));
                        note = note.substring(0, 5).concat(String.valueOf(1.0/Integer.parseInt(note.substring(5))));
                    }
                } 
            }
        }
        return note;
    }
    
    public static String[] getChordNotes(String chord){
        String[] notes = chord.split(",");
        for(int i = 0; i < notes.length; i++){
            String note = notes[i];
            note = note.replace("{", "");
            note = note.replace("}", "");
            note = convertNote(note);
            notes[i] = note;
        }
        return notes;
    }
    
    public static String convertSequence(String path) throws FileNotFoundException{
        File file = new File(path);
        Scanner scaner = new Scanner(file);
        String s = "";
        String pattern = "";
        while(scaner.hasNextLine()){
            s += scaner.nextLine() + "\n";
        }
        String[][] sheet;
        String[] rows = s.split("\n");
        for (String row : rows) {
            String[] notes = row.split(" ");
            if(notes.length > 1){
                for(String note : notes){
                    
                    if(note.length() > 1){
                        
                        if(note.length() <= 8){
                            note = convertNote(note);
                            
                            pattern += note + " ";
                            System.out.print(note + ", " + note.length() + "\n");
                        }else{
                            String[] chord = getChordNotes(note);
                            
                            for(String nt : chord){
                                pattern += nt + " ";
                                System.out.print(nt + ", " + nt.length() + "\n");
                            }
                        }
                        
                    }
                }
                System.out.print("\n\n");
            }
        }
        
        System.out.println(s);
        System.out.println(pattern);
        
        return pattern;
    }
    
}
