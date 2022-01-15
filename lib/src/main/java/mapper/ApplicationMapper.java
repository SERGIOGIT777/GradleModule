package mapper;


import entity.Application;

import java.util.List;
import java.util.Random;

public class ApplicationMapper {
    private static final Integer ID = 1;
    private static final Integer FIRSTNAME_INDEX = 2;
    private static final Integer LASTNAME_INDEX = 3;
    private static final Integer AGE_INDEX = 4;
    private static final Integer ADDRESS_INDEX = 5;

    public Application toUpdateEntity(List<String> args) {
        var application = new Application();
        application.setFirstname(args.get(FIRSTNAME_INDEX));
        application.setLastname(args.get(LASTNAME_INDEX));
        application.setAge(Integer.parseInt(args.get(AGE_INDEX)));
        application.setAddress(args.get(ADDRESS_INDEX));
        application.setStatus(Application.STATUS_NEW);
        return application;
    }

    public Application toCreateEntity(List<String> args){
        var application = new Application();
        application.setId(new Random().nextInt(1000));
        application.setFirstname(args.get(1));
        application.setLastname(args.get(2));
        application.setAge(Integer.parseInt(args.get(3)));
        application.setAddress(args.get(4));
        application.setStatus(Application.STATUS_NEW);

     return application;
    }
}
