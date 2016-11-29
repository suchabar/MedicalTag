package cz.barush.medicaltag;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.barush.medicaltag.model.Tag;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class StaticPool
{
    public static HashMap<String, List<Tag>> groupTags = new HashMap<>();
    public static String selectedGroup = null;
    public static int selectedChild = -1;
    public static Tag tagToSave = new Tag();

    public static View fragmentView;

    public static Tag createTagFromString(String textFromNFC)
    {
        String[] attributes = textFromNFC.split("\\s+");
        Tag newTag = new Tag();
        try
        {
            //NAME
            String[] name = (attributes[0]).split("(?=[A-Z])");
            if(!name[0].equals("~"))newTag.setName(name[0]);
            for (int i = 1; i < name.length; i++)newTag.setName(newTag.getName() + " " + name[i]);

            //DATE OF BIRTH
            String day = attributes[1].substring(0,2);
            String month = attributes[1].substring(2,4);
            String year = attributes[1].substring(4,8);
            newTag.setDateOfBirth(day + "/" + month + "/" + year);

            //GENDER
            newTag.setGender(Integer.parseInt(attributes[2]));

            //PREGNANT
            newTag.setPregnant(attributes[3] == "0" ? true : false);

            //STATE
            String[] state = (attributes[4]).split("(?=[A-Z])");
            if(!state[0].equals("~"))newTag.setState(state[0]);
            if(state.length == 2)newTag.setState(newTag.getState() + " " + state[1]);

            //WEIGHT
            if(!attributes[5].equals("~"))newTag.setWeight(Integer.parseInt(attributes[5]));
            //HEIGHT
            if(!attributes[6].equals("~"))newTag.setHeight(Integer.parseInt(attributes[6]));

            //BLOOD GROUP
            if(!attributes[7].equals("~"))
            {
                String bloodType = attributes[7];
                if(bloodType.contains("+"))
                {
                    bloodType = bloodType.replace("+","");
                    bloodType += " +";
                }
                else
                {
                    bloodType = bloodType.replace("-","");
                    bloodType += " -";
                }
                newTag.setBloodType(bloodType);
            }

            //DIABETES
            newTag.setHaveDiabetes(attributes[8] == "0" ? true : false);

            //ALLERGIES
            if(!attributes[9].equals("~"))newTag.setAllergies(attributes[9]);

            //MEDICAMENTS
            if(!attributes[10].equals("~"))newTag.setMedicamentsInUse(attributes[10]);

            //EMERGENCY CONTACT 1
            if(!attributes[11].equals("~"))newTag.setEmergencyContact1(attributes[11]);

            //EMERGENCY CONTACT NAME 1
            if(!attributes[12].equals("~"))newTag.setEmergencyContactTitle1(attributes[12]);

            //EMERGENCY CONTACT 2
            if(!attributes[13].equals("~"))newTag.setEmergencyContact2(attributes[13]);

            //EMERGENCY CONTACT NAME 2
            if(!attributes[14].equals("~"))newTag.setEmergencyContactTitle2(attributes[14]);

            //GROUP
            if(!attributes[15].equals("~"))newTag.setGroup(attributes[15]);
        }
       catch(Exception e)
       {
           return null;
       }
        return newTag;
    }

    public static String compressTagToString(Tag tagToCompress)
    {
        String finalCompressedTag = "";

        //NAME
        if(tagToCompress.getName() == null || tagToCompress.getName().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getName().replace(" ", "") + " ";

        //DATE OF BIRTH
        if(tagToCompress.getDateOfBirth() == null || tagToCompress.getDateOfBirth().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getDateOfBirth().replace("/", "") + " ";

        //GENDER
        finalCompressedTag += tagToCompress.getGender() + " ";

        //PREGNANT
        finalCompressedTag += tagToCompress.isPregnant() ? "0 ": "1 ";

        //STATE
        if(tagToCompress.getState() == null || tagToCompress.getState().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getState().replace(" ", "")  + " ";

        //WEIGHT
        if(tagToCompress.getWeight() == 0)finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getWeight() + " ";

        //HEIGHT
        if(tagToCompress.getHeight() == 0)finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getHeight() + " ";

        //BLOOD GROUP
        if(tagToCompress.getBloodType() == null || tagToCompress.getBloodType().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getBloodType().replace(" ", "")  + " ";

        //DIABETES
        finalCompressedTag += tagToCompress.isHaveDiabetes() ? "0 ": "1 ";

        //ALLERGIES
        if(tagToCompress.getAllergies() == null || tagToCompress.getAllergies().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getAllergies().substring(0,10).replace(" ", "")  + " ";

        //MEDICAMENTS
        if(tagToCompress.getMedicamentsInUse() == null || tagToCompress.getMedicamentsInUse().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getMedicamentsInUse().substring(0,10).replace(" ", "")  + " ";

        //EMERGENCY CONTACT 1
        if(tagToCompress.getEmergencyContact1() == null || tagToCompress.getEmergencyContact1().isEmpty())finalCompressedTag += "~ ";
        else
        {
            String con1 = tagToCompress.getEmergencyContact1();
            finalCompressedTag += con1.substring(con1.length() - 9) + " ";
        }

        //EMERGENCY CONTACT NAME 1
        if(tagToCompress.getEmergencyContactTitle1() == null || tagToCompress.getEmergencyContactTitle1().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getEmergencyContactTitle1().replace(" ", "")  + " ";

        //EMERGENCY CONTACT 2
        if(tagToCompress.getEmergencyContact2() == null || tagToCompress.getEmergencyContact2().isEmpty())finalCompressedTag += "~ ";
        else
        {
            String con2 = tagToCompress.getEmergencyContact2();
            finalCompressedTag += con2.substring(con2.length() - 9) + " ";
        }

        //EMERGENCY CONTACT NAME 2
        if(tagToCompress.getEmergencyContactTitle2() == null || tagToCompress.getEmergencyContactTitle2().isEmpty())finalCompressedTag += "~ ";
        else finalCompressedTag += tagToCompress.getEmergencyContactTitle2().replace(" ", "")  + " ";

        //GROUP
        if(tagToCompress.getGroup() == null || tagToCompress.getGroup().isEmpty())finalCompressedTag += "~";
        else finalCompressedTag += tagToCompress.getGroup();

        return finalCompressedTag;

    }
}
