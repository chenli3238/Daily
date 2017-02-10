package com.wqy.daily.model;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by wqy on 17-2-6.
 */
@Entity(
        nameInDb = "Event",
        active = true,
        generateConstructors = false,
        generateGettersSetters = false
)
public class Event {

    @Id(autoincrement = true)
    private Long id;

    @Transient
    public static final int ENDLESS = -1;

    @NonNull
    private String title;

    private String desc;

    @Convert(converter = DayTimeConverter.class, columnType = String.class)
    private DayTime punchTime;

    @Convert(converter = PriorityConverter.class, columnType = Integer.class)
    private Priority priority;

    /**
     * 目标打卡次数, -1 表示不限次数。
     */
    private int aim;

    private float keepRate;

    private boolean keepDuration;

    private boolean finished;

    private boolean deleted;

    private float score;

    private boolean remindme;

    @Transient
    private Reminder reminder;

    @ToMany(referencedJoinProperty = "eventId")
    @OrderBy(value = "date ASC")
    private List<Punch> punches;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1542254534)
    private transient EventDao myDao;

    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DayTime getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(DayTime punchTime) {
        this.punchTime = punchTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isKeepDuration() {
        return keepDuration;
    }

    public boolean getKeepDuration() {
        return keepDuration;
    }

    public void setKeepDuration(boolean keepDuration) {
        this.keepDuration = keepDuration;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isRemindme() {
        return remindme;
    }

    public boolean getRemindme() {
        return remindme;
    }

    public void setRemindme(boolean remindme) {
        this.remindme = remindme;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public float getKeepRate() {
        return keepRate;
    }

    public void setKeepRate(float keepRate) {
        this.keepRate = keepRate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setEndless() {
        aim = ENDLESS;
    }

    public int getAim() {
        return aim;
    }

    public void setAim(int aim) {
        this.aim = aim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEndless() {
        return aim == ENDLESS;

    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1824928532)
    public List<Punch> getPunches() {
        if (punches == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PunchDao targetDao = daoSession.getPunchDao();
            List<Punch> punchesNew = targetDao._queryEvent_Punches(id);
            synchronized (this) {
                if (punches == null) {
                    punches = punchesNew;
                }
            }
        }
        return punches;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1764356859)
    public synchronized void resetPunches() {
        punches = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1459865304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }

    public enum Priority {
        UNKNOWN(0),

        LOW(1),

        MEDIUM(2),

        HIGH(3);

        private int value;

        Priority(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public static class PriorityConverter implements PropertyConverter<Priority, Integer> {

        @Override
        public Priority convertToEntityProperty(Integer databaseValue) {
            if (Priority.HIGH.value == databaseValue) {
                return Priority.HIGH;
            } else if (Priority.MEDIUM.value == databaseValue) {
                return Priority.MEDIUM;
            } else if (Priority.LOW.value == databaseValue) {
                return Priority.LOW;
            } else {
                return Priority.UNKNOWN;
            }
        }

        @Override
        public Integer convertToDatabaseValue(Priority entityProperty) {
            return entityProperty.value;
        }
    }

    public static class DayTimeConverter implements PropertyConverter<DayTime, String> {

        @Override
        public DayTime convertToEntityProperty(String databaseValue) {
            return DayTime.fromString(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(DayTime entityProperty) {
            return entityProperty.toString();
        }
    }
}
