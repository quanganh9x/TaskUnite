package com.vbtn.taskunite.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "task")
    private Set<Plan> plans = new HashSet<>();

    @OneToMany(mappedBy = "task")
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "task_task_categories",
               joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "task_categories_id", referencedColumnName = "id"))
    private Set<TaskCategory> taskCategories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Tasker tasker;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Master master;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public Task price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public Task status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Task createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Task updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public Task deletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public Task plans(Set<Plan> plans) {
        this.plans = plans;
        return this;
    }

    public Task addPlan(Plan plan) {
        this.plans.add(plan);
        plan.setTask(this);
        return this;
    }

    public Task removePlan(Plan plan) {
        this.plans.remove(plan);
        plan.setTask(null);
        return this;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Task reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Task addReviews(Review review) {
        this.reviews.add(review);
        review.setTask(this);
        return this;
    }

    public Task removeReviews(Review review) {
        this.reviews.remove(review);
        review.setTask(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<TaskCategory> getTaskCategories() {
        return taskCategories;
    }

    public Task taskCategories(Set<TaskCategory> taskCategories) {
        this.taskCategories = taskCategories;
        return this;
    }

    public Task addTaskCategories(TaskCategory taskCategory) {
        this.taskCategories.add(taskCategory);
        taskCategory.getTasks().add(this);
        return this;
    }

    public Task removeTaskCategories(TaskCategory taskCategory) {
        this.taskCategories.remove(taskCategory);
        taskCategory.getTasks().remove(this);
        return this;
    }

    public void setTaskCategories(Set<TaskCategory> taskCategories) {
        this.taskCategories = taskCategories;
    }

    public Tasker getTasker() {
        return tasker;
    }

    public Task tasker(Tasker tasker) {
        this.tasker = tasker;
        return this;
    }

    public void setTasker(Tasker tasker) {
        this.tasker = tasker;
    }

    public Master getMaster() {
        return master;
    }

    public Task master(Master master) {
        this.master = master;
        return this;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", status=" + getStatus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
