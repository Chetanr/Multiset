package generation;


import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class dataGeneration
{
    public dataGeneration()
    {
        randonDataGenerator();
    }

    public void randonDataGenerator() {

        List<String> list= new ArrayList<String>();

        String random = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random rand = new Random();
        String create1 = "create s1";
        String create2 = "create s2";

        StringBuilder temp = new StringBuilder();


        temp.append(create1.concat("\n"));


        //for creating add statements for inserting 5 elements
        for (int i = 1; i <= 5; i++)
        {
            int index = rand.nextInt(32); //because length of random is 32 letters
            temp = temp.append("add s1 ".concat(String.valueOf(random.charAt(index))));
            temp = temp.append("\n");
        }

        //for creating removeOne statements
        for (int i = 1; i <= 5; i++)
        {
            int index = rand.nextInt(32); //because length of random is 32 letters
            temp = temp.append("removeOne s1 ".concat(String.valueOf(random.charAt(index))));
            temp = temp.append("\n");
        }

        temp = temp.append("print s1 \n");

        //for creating add statements for inserting 5 elements
        for (int i = 1; i <= 5; i++)
        {
            int index = rand.nextInt(32); //because length of random is 32 letters
            temp = temp.append("add s1 ".concat(String.valueOf(random.charAt(index))));
            temp = temp.append("\n");
        }

        //for creating add statements for inserting 5 elements
        for (int i = 1; i <= 5; i++)
        {
            int index = rand.nextInt(32); //because length of random is 32 letters
            temp = temp.append("add s2 ".concat(String.valueOf(random.charAt(index))));
            temp = temp.append("\n");
        }

        temp = temp.append("intersect s1 s2 sU \n");


        try {
            FileWriter fw = new FileWriter("testCase.txt");
            fw.write(temp.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}