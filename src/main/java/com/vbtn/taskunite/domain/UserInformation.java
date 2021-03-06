package com.vbtn.taskunite.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserInformation.
 */
@Entity
@Table(name = "user_information")
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "user")
    private Set<Address> workingAddresses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications = new HashSet<>();

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Tasker tasker;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Master master;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Statistic statistic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public UserInformation gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public UserInformation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public UserInformation phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public UserInformation status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserInformation createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserInformation updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public UserInformation deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getUser() {
        return user;
    }

    public UserInformation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Address> getWorkingAddresses() {
        return workingAddresses;
    }

    public UserInformation workingAddresses(Set<Address> addresses) {
        this.workingAddresses = addresses;
        return this;
    }

    public UserInformation addWorkingAddresses(Address address) {
        this.workingAddresses.add(address);
        address.setUser(this);
        return this;
    }

    public UserInformation removeWorkingAddresses(Address address) {
        this.workingAddresses.remove(address);
        address.setUser(null);
        return this;
    }

    public void setWorkingAddresses(Set<Address> addresses) {
        this.workingAddresses = addresses;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public UserInformation reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public UserInformation addReviews(Review review) {
        this.reviews.add(review);
        review.setUser(this);
        return this;
    }

    public UserInformation removeReviews(Review review) {
        this.reviews.remove(review);
        review.setUser(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public UserInformation notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public UserInformation addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setUser(this);
        return this;
    }

    public UserInformation removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setUser(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Tasker getTasker() {
        return tasker;
    }

    public UserInformation tasker(Tasker tasker) {
        this.tasker = tasker;
        return this;
    }

    public void setTasker(Tasker tasker) {
        this.tasker = tasker;
    }

    public Master getMaster() {
        return master;
    }

    public UserInformation master(Master master) {
        this.master = master;
        return this;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public UserInformation statistic(Statistic statistic) {
        this.statistic = statistic;
        return this;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInformation)) {
            return false;
        }
        return id != null && id.equals(((UserInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
            "id=" + getId() +
            ", gender=" + getGender() +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", status=" + getStatus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
