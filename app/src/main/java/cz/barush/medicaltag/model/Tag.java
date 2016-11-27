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
    int gender;
    boolean isPregnant;
    String state;

    String insuranceInfo;
    int emergencyContact;
    int weight;
    int height;
    String bloodType;
    boolean haveDiabetes;

    String allergies;
    String medicamentsInUse;

    public Tag(String name, String dateOfBirth)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Tag()
    {

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

    public String getInsuranceInfo()
    {
        return insuranceInfo;
    }

    public void setInsuranceInfo(String insuranceInfo)
    {
        this.insuranceInfo = insuranceInfo;
    }

    public int getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(int emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

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

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
