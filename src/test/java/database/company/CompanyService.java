package database.company;

public interface CompanyService {

    static LocalCompanyService getInstance() {
        return LocalCompanyService.LOCAL_COMPANY_SERVICE;
    }

    boolean getIsActiveCompany();

    String getNameCompany();

    String getDescriptionCompany();

}
