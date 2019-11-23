package com.vbtn.taskunite.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tasker.
 */
@Entity
@Table(name = "tasker")
public class Tasker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne
    @JoinColumn(unique = true)
    private UserInformation user;

    @OneToMany(mappedBy = "tasker")
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "tasker")
    private Set<Address> workingAddresses = new HashSet<>();

    @OneToMany(mappedBy = "tasker")
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tasker_task_categories",
               joinColumns = @JoinColumn(name = "tasker_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "task_categories_id", referencedColumnName = "id"))
    private Set<TaskCategory> taskCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Tasker price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public Tasker status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Tasker createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Tasker updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public Tasker deletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserInformation getUser() {
        return user;
    }

    public Tasker user(UserInformation userInformation) {
        this.user = userInformation;
        return this;
    }

    public void setUser(UserInformation userInformation) {
        this.user = userInformation;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Tasker schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public Tasker addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setTasker(this);
        return this;
    }

    public Tasker removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setTasker(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<Address> getWorkingAddresses() {
        return workingAddresses;
    }

    public Tasker workingAddresses(Set<Address> addresses) {
        this.workingAddresses = addresses;
        return this;
    }

    public Tasker addWorkingAddresses(Address address) {
        this.workingAddresses.add(address);
        address.setTasker(this);
        return this;
    }

    public Tasker removeWorkingAddresses(Address address) {
        this.workingAddresses.remove(address);
        address.setTasker(null);
        return this;
    }

    public void setWorkingAddresses(Set<Address> addresses) {
        this.workingAddresses = addresses;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Tasker tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Tasker addTasks(Task task) {
        this.tasks.add(task);
        task.setTasker(this);
        return this;
    }

    public Tasker removeTasks(Task task) {
        this.tasks.remove(task);
        task.setTasker(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<TaskCategory> getTaskCategories() {
        return taskCategories;
    }

    public Tasker taskCategories(Set<TaskCategory> taskCategories) {
        this.taskCategories = taskCategories;
        return this;
    }

    public Tasker addTaskCategories(TaskCategory taskCategory) {
        this.taskCategories.add(taskCategory);
        taskCategory.getTaskers().add(this);
        return this;
    }

    public Tasker removeTaskCategories(TaskCategory taskCategory) {
        this.taskCategories.remove(taskCategory);
        taskCategory.getTaskers().remove(this);
        return this;
    }

    public void setTaskCategories(Set<TaskCategory> taskCategories) {
        this.taskCategories = taskCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tasker)) {
            return false;
        }
        return id != null && id.equals(((Tasker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tasker{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", status=" + getStatus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
