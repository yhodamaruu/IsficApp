package com.isficuniversity.isfic.modules;

public class Student {
    private String MATRICULE;
    private String PRENOM;
    private String NOM;
    private String DATE_NAISS;
    private String ANNEE;
    private String NUM;
    private String TEL1;
    private String sexe;
    private String ID_EL;
    private String NATIONALITE;

    public Student() {
        // Constructeur vide nécessaire pour Firebase
    }

    // Getters et setters
    public String getMATRICULE() {
        return MATRICULE;
    }

    public void setMATRICULE(String MATRICULE) {
        this.MATRICULE = MATRICULE;
    }

    public String getPRENOM() {
        return PRENOM;
    }

    public void setPRENOM(String PRENOM) {
        this.PRENOM = PRENOM;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public String getDATE_NAISS() {
        return DATE_NAISS;
    }

    public void setDATE_NAISS(String DATE_NAISS) {
        this.DATE_NAISS = DATE_NAISS;
    }

    public String getANNEE() {
        return ANNEE;
    }

    public void setANNEE(String ANNEE) {
        this.ANNEE = ANNEE;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getTEL1() {
        return TEL1;
    }

    public void setTEL1(String TEL1) {
        this.TEL1 = TEL1;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getID_EL() {
        return ID_EL;
    }

    public void setID_EL(String ID_EL) {
        this.ID_EL = ID_EL;
    }

    public String getNATIONALITE() {
        return NATIONALITE;
    }

    public void setNATIONALITE(String NATIONALITE) {
        this.NATIONALITE = NATIONALITE;
    }
}
