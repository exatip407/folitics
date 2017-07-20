package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Category
 * @author Abhishek Patel
 */
@Entity
@Table(name = "category")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotNull(message = "error.category.name.notNull")
    @Size(min = 1, max = 255, message = "error.category.name.size")
    private String name;

    @Column(nullable = false, length = 255)
    @NotNull(message = "error.category.description.notNull")
    @Size(min = 1, max = 255, message = "error.category.description.size")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "error.category.editTime.notNull")
    private Timestamp editTime;

    @Column(nullable = true)
    private Long editedBy;

    @Column(nullable = false)
    @NotNull(message = "error.category.createTime.notNull")
    private Timestamp createTime;

    @Column(nullable = false)
    @NotNull(message = "error.category.createdBy.notNull")
    private Long createdBy;

    @Column(nullable = false, length = 25, columnDefinition = "enum('Category','SubCategory','Indicator')")
    @NotNull(message = "error.category.type.notNull")
    private String type;

    @Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
    @NotNull(message = "error.category.status.notNull")
    private String status;

    @ManyToMany
    @Cascade(value = CascadeType.DETACH)
    @JoinTable(name = "CategoryHierarchy", joinColumns = @JoinColumn(name = "parentId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "childId", referencedColumnName = "id"))
    private List<Category> childs;

    @ManyToMany
    @Cascade(value = CascadeType.DETACH)
    @JoinTable(name = "CategoryHierarchy", joinColumns = @JoinColumn(name = "childId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "parentId", referencedColumnName = "id"))
    private List<Category> parents;

    @OneToMany(mappedBy = "category")
    private Set<Opinion> opinions;
    
/*    @OneToMany(mappedBy="category")
    private Set<IndicatorData> IndicatorData;*/

    public Category() {
        setTimestamp(DateUtils.getSqlTimeStamp());
        setEdited(DateUtils.getSqlTimeStamp());
        setStatus(ComponentState.ACTIVE.getValue());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String category) {
        this.name = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getEdited() {
        return this.editTime;
    }

    public void setEdited(Timestamp edited) {
        this.editTime = edited;
    }

    public Timestamp getTimestamp() {
        return this.createTime;
    }

    public void setTimestamp(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Long getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Long editedBy) {
        this.editedBy = editedBy;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Category> getChilds() {
        return childs;
    }

    public void setChilds(List<Category> childs) {
        this.childs = childs;
    }

    public List<Category> getParents() {
        return parents;
    }

    public void setParents(List<Category> parents) {
        this.parents = parents;
    }

    public void addParent(Category parent) throws Exception {
        try {
            parents.add(parent);
        } catch (NullPointerException e) {
            throw new MessageException("Category object found null while adding it to the parent list in Category");
        } catch (Exception e) {
            throw new Exception("Exception occured while adding parent in parent list of Category");
        }
    }

    public void removeParent(Category parent) {
        if (parent != null) {
            parents.remove(parent);
        }
    }

    public void addChild(Category child) {
        if (child != null) {
            childs.add(child);
        }
    }

    public void removeChild(Category child) {
        if (child != null) {
            childs.remove(child);
        }
    }

    public Set<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(Set<Opinion> opinions) {
        this.opinions = opinions;
    }

    public void addOpinion(Opinion opinion) {
        if (opinion != null) {
            opinions.add(opinion);
        }
    }

    public void removeOpinion(Opinion opinion) {
        if (opinion != null) {
            opinions.remove(opinion);
        }
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Category))
            return false;

        final Category a = (Category) object;

        if (id != null && a.getId() != null) {
            return id.equals(a.getId());
        }
        return false;
    }

/*	*//**
	 * @return the indicatorData
	 *//*
	public Set<IndicatorData> getIndicatorData() {
		return IndicatorData;
	}

	*//**
	 * @param indicatorData the indicatorData to set
	 *//*
	public void setIndicatorData(Set<IndicatorData> indicatorData) {
		IndicatorData = indicatorData;
	}*/
}
