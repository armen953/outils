package fr.armen.outils.models;

import org.springframework.web.multipart.MultipartFile;

public class TestForm {

    private String nom;

    private String prenom;

    private String fileFormat;

    private MultipartFile fichier;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public MultipartFile getFichier() {
        return fichier;
    }

    public void setFichier(MultipartFile fichier) {
        this.fichier = fichier;
    }

    @Override
    public String toString() {
        return "TestForm [fichier=" + fichier + ", fileFormat=" + fileFormat + ", nom=" + nom + ", prenom=" + prenom + "]";
    }
    
}
