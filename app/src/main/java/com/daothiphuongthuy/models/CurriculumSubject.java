package com.daothiphuongthuy.models;

public class CurriculumSubject {
    private String stt;
    private String code;
    private String name;
    private String credits;
    private String semester;

    public CurriculumSubject(String stt, String code, String name, String credits, String semester) {
        this.stt = stt;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.semester = semester;
    }

    public String getStt() { return stt; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCredits() { return credits; }
    public String getSemester() { return semester; }
}
