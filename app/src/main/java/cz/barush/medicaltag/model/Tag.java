package cz.barush.medicaltag.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class Tag
{
    String group;

    String name;
    String dateOfBirth;
    //0 - woman, 1 - man, -1 - nothing
    int gender = -1;
    boolean isPregnant;
    String state;

    //String insuranceInfo;
    int weight;
    int height;
    String bloodType;
    boolean haveDiabetes;

    String allergies;
    String medicamentsInUse;

    String emergencyContactTitle1;
    String emergencyContact1;
    String emergencyContactTitle2;
    String emergencyContact2;

    public Tag(String name)
    {
        this.name = name;
    }

    public Tag()
    {

    }

    public Tag(Tag scannedTag)
    {
        this.group = scannedTag.group;
        this.name = scannedTag.name;
        this.dateOfBirth = scannedTag.dateOfBirth;
        this.gender = scannedTag.gender;
        this.isPregnant = scannedTag.isPregnant;
        this.state = scannedTag.state;
        this.weight = scannedTag.weight;
        this.height = scannedTag.height;
        this.bloodType = scannedTag.bloodType;
        this.haveDiabetes = scannedTag.haveDiabetes;
        this.allergies = scannedTag.allergies;
        this.medicamentsInUse = scannedTag.medicamentsInUse;
        this.emergencyContactTitle1 = scannedTag.emergencyContactTitle1;
        this.emergencyContact1 = scannedTag.emergencyContact1;
        this.emergencyContactTitle2 = scannedTag.emergencyContactTitle2;
        this.emergencyContact2 = scannedTag.emergencyContact2;
    }

    public String getGroup()
    {
        return group;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public boolean isPregnant()
    {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant)
    {
        isPregnant = pregnant;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

//    public String getInsuranceInfo()
//    {
//        return insuranceInfo;
//    }
//
//    public void setInsuranceInfo(String insuranceInfo)
//    {
//        this.insuranceInfo = insuranceInfo;
//    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getBloodType()
    {
        return bloodType;
    }

    public void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    public boolean isHaveDiabetes()
    {
        return haveDiabetes;
    }

    public void setHaveDiabetes(boolean haveDiabetes)
    {
        this.haveDiabetes = haveDiabetes;
    }

    public String getAllergies()
    {
        return allergies;
    }

    public void setAllergies(String allergies)
    {
        this.allergies = allergies;
    }

    public String getMedicamentsInUse()
    {
        return medicamentsInUse;
    }

    public void setMedicamentsInUse(String medicamentsInUse)
    {
        this.medicamentsInUse = medicamentsInUse;
    }

    public String getEmergencyContactTitle1()
    {
        return emergencyContactTitle1;
    }

    public void setEmergencyContactTitle1(String emergencyContactTitle1)
    {
        this.emergencyContactTitle1 = emergencyContactTitle1;
    }

    public String getEmergencyContactTitle2()
    {
        return emergencyContactTitle2;
    }

    public void setEmergencyContactTitle2(String emergencyContactTitle2)
    {
        this.emergencyContactTitle2 = emergencyContactTitle2;
    }
//
//    public String getEmergencyContactTitle3()
//    {
//        return emergencyContactTitle3;
//    }
//
//    public void setEmergencyContactTitle3(String emergencyContactTitle3)
//    {
//        this.emergencyContactTitle3 = emergencyContactTitle3;
//    }

    public String getEmergencyContact1()
    {
        return emergencyContact1;
    }

    public void setEmergencyContact1(String emergencyContact1)
    {
        this.emergencyContact1 = emergencyContact1;
    }

    public String getEmergencyContact2()
    {
        return emergencyContact2;
    }

    public void setEmergencyContact2(String emergencyContact2)
    {
        this.emergencyContact2 = emergencyContact2;
    }

//    public String getEmergencyContact3()
//    {
//        return emergencyContact3;
//    }
//
//    public void setEmergencyContact3(String emergencyContact3)
//    {
//        this.emergencyContact3 = emergencyContact3;
//    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }



}
