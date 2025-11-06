package db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class UniversityDatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:jokes.db";
    private Dao<UniversityEntity, Integer> universityDao;

    public void init() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
        universityDao = DaoManager.createDao(connectionSource, UniversityEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, UniversityEntity.class);
    }

    public void saveUniversity(UniversityEntity university) throws SQLException {
        universityDao.create(university);
    }

    public List<UniversityEntity> getAllUniversities() throws SQLException {
        return universityDao.queryForAll();
    }
}
