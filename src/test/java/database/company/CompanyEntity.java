package database.company;

public class CompanyEntity {
    private final boolean isActiveCompany;
    private final String nameCompany;
    private final String descriptionCompany;

    public CompanyEntity(boolean isActiveCompany, String nameCompany, String descriptionCompany) {
        this.isActiveCompany = isActiveCompany;
        this.nameCompany = nameCompany;
        this.descriptionCompany = descriptionCompany;
    }

    public boolean isActiveCompany() {
        return isActiveCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public String getDescriptionCompany() {
        return descriptionCompany;
    }
}
