package com.vbtn.taskunite.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Statistic.
 */
@Entity
@Table(name = "statistic")
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "completed_task")
    private Integer completedTask;

    @Column(name = "incompleted_task")
    private Integer incompletedTask;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "bonus")
    private Double bonus;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private UserInformation user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public Statistic level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public Statistic experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getCompletedTask() {
        return completedTask;
    }

    public Statistic completedTask(Integer completedTask) {
        this.completedTask = completedTask;
        return this;
    }

    public void setCompletedTask(Integer completedTask) {
        this.completedTask = completedTask;
    }

    public Integer getIncompletedTask() {
        return incompletedTask;
    }

    public Statistic incompletedTask(Integer incompletedTask) {
        this.incompletedTask = incompletedTask;
        return this;
    }

    public void setIncompletedTask(Integer incompletedTask) {
        this.incompletedTask = incompletedTask;
    }

    public Integer getRating() {
        return rating;
    }

    public Statistic rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRanking() {
        return ranking;
    }

    public Statistic ranking(Integer ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getBonus() {
        return bonus;
    }

    public Statistic bonus(Double bonus) {
        this.bonus = bonus;
        return this;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Statistic createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Statistic updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public Statistic deletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserInformation getUser() {
        return user;
    }

    public Statistic user(UserInformation userInformation) {
        this.user = userInformation;
        return this;
    }

    public void setUser(UserInformation userInformation) {
        this.user = userInformation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statistic)) {
            return false;
        }
        return id != null && id.equals(((Statistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Statistic{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", experience=" + getExperience() +
            ", completedTask=" + getCompletedTask() +
            ", incompletedTask=" + getIncompletedTask() +
            ", rating=" + getRating() +
            ", ranking=" + getRanking() +
            ", bonus=" + getBonus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
