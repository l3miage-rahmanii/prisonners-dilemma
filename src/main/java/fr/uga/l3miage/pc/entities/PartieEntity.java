package fr.uga.l3miage.pc.entities;


import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartieEntity {
    private String id;
    private String status;
    private Integer nbTours;
    private int idJoueur1;
    private int idJoueur2;
    private List<CoupEnum> coupsJoueur1;
    private List<CoupEnum> coupsJoueur2;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private StrategieEnum strategieJoueur;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNbTours(Integer nbTours) {
        this.nbTours = nbTours;
    }

    public void setIdJoueur1(int idJoueur1) {
        this.idJoueur1 = idJoueur1;
    }

    public void setIdJoueur2(int idJoueur2) {
        this.idJoueur2 = idJoueur2;
    }

    public void setCoupsJoueur1(List<CoupEnum> coupsJoueur1) {
        this.coupsJoueur1 = coupsJoueur1;
    }

    public void setCoupsJoueur2(List<CoupEnum> coupsJoueur2) {
        this.coupsJoueur2 = coupsJoueur2;
    }

    public void setScoreJoueur1(int scoreJoueur1) {
        this.scoreJoueur1 = scoreJoueur1;
    }

    public void setScoreJoueur2(int scoreJoueur2) {
        this.scoreJoueur2 = scoreJoueur2;
    }

    public void setStrategieJoueur(StrategieEnum strategieJoueur) {
        this.strategieJoueur = strategieJoueur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNbTours() {
        return nbTours;
    }

    public String getStatus() {
        return status;
    }

    public int getIdJoueur2() {
        return idJoueur2;
    }

    public int getIdJoueur1() {
        return idJoueur1;
    }

    public List<CoupEnum> getCoupsJoueur1() {
        return coupsJoueur1;
    }

    public List<CoupEnum> getCoupsJoueur2() {
        return coupsJoueur2;
    }

    public int getScoreJoueur1() {
        return scoreJoueur1;
    }

    public int getScoreJoueur2() {
        return scoreJoueur2;
    }

    public StrategieEnum getStrategieJoueur() {
        return strategieJoueur;
    }


}
