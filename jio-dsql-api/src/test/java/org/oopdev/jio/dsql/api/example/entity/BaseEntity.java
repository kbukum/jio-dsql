package org.oopdev.jio.dsql.api.example.entity;


import org.hibernate.annotations.GenericGenerator;
import org.oopdev.jio.dsql.api.criteria.annotations.Ignore;

import javax.persistence.*;

/**
 * An abstract Entity implementation. All entities have to extend this class.
 * Standard fields (oid,lastupdated) will be added to your entity.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Ignore
    private static final long serialVersionUID = 1914842698571907341L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    @Ignore
    private String oid;

    @Version
    @Ignore
    private long lastUpdated;


    public BaseEntity() {
    }


    public BaseEntity(String oid, long lastUpdated) {
        this.oid = oid;
        this.lastUpdated = lastUpdated;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "oid='" + oid + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (getLastUpdated() != that.getLastUpdated()) return false;
        return getOid() != null ? getOid().equals(that.getOid()) : that.getOid() == null;
    }

    @Override
    public int hashCode() {
        int result = getOid() != null ? getOid().hashCode() : 0;
        result = 31 * result + (int) (getLastUpdated() ^ (getLastUpdated() >>> 32));
        return result;
    }
}
