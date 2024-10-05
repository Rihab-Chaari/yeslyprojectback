package com.java.demo.springboot.models;

public class InscriptionRequest {


    private Long etudiantId;
    private Long coursId;
    public Long getEtudiantId() {
        return etudiantId;
    }
    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }
    public Long getCoursId() {
        return coursId;
    }
    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }
    public InscriptionRequest(Long etudiantId, Long coursId) {
        this.etudiantId = etudiantId;
        this.coursId = coursId;
    }
    public InscriptionRequest() {
    }

    

}
