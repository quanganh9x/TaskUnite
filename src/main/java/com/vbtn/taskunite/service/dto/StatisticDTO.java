package com.vbtn.taskunite.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vbtn.taskunite.domain.Statistic} entity.
 */
public class StatisticDTO implements Serializable {

    private Long id;

    private Integer level;

    private Integer experience;

    private Integer completedTask;

    private Integer incompletedTask;

    private Integer rating;

    private Integer ranking;

    private Double bonus;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate deletedAt;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(Integer completedTask) {
        this.completedTask = completedTask;
    }

    public Integer getIncompletedTask() {
        return incompletedTask;
    }

    public void setIncompletedTask(Integer incompletedTask) {
        this.incompletedTask = incompletedTask;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userInformationId) {
        this.userId = userInformationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatisticDTO statisticDTO = (StatisticDTO) o;
        if (statisticDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statisticDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatisticDTO{" +
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
            ", user=" + getUserId() +
            "}";
    }
}
