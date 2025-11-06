package db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "University")
public class UniversityEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String country;

    public UniversityEntity() {
    }

    public UniversityEntity(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return id +
                ". name:'" + name + '\'' +
                ", country: '" + country + '\'';
    }
}
