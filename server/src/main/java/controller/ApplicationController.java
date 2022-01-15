package controller;


import dao.ApplicationDAO;
import entity.Application;
import mapper.ApplicationMapper;

import java.sql.Connection;
import java.util.List;

public class ApplicationController implements IController {
    private Connection connection;

    @Override
    public void setDatabaseConnection(Connection connection) {
        this.connection = connection;
    }
    @Override

    public void process(List<String> arguments) {
        var dao = new ApplicationDAO(this.connection);
        switch (arguments.get(0).replace("application/", "")) {
            case "generate" -> {
                var mapper = new ApplicationMapper();
                for (int i = 0; i < 15; i++) {
                    dao.save(mapper.toCreateEntity(arguments));
                }
            }
            case "list" -> {
                var status = Integer.parseInt(arguments.get(1));
                if (!Application.STATUSES.contains(status)) {
                    throw new IllegalArgumentException("wrong status: " + status);
                }

                var entities = dao.findAll();
                entities.stream().filter(e -> e.getStatus() == status || status == -1).forEach(System.out::println);
//                for (int i = 0; i < entities.size(); i++) {
//                    if (status == -1 || entities.get(i).getStatus() == status) {
//                        System.out.println(entities.get(i));
//                    }
//                }
            }
            case "save" -> {
                Application application;
                var mapper = new ApplicationMapper();
                if (arguments.size() == 7) {
                    application = mapper.toUpdateEntity(arguments);
                    var list = dao.findAll();
                    dao.deleteFile();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(Integer.parseInt(arguments.get(1)))) {
                            list.remove(i);
                            list.add(application);
                        }
                        dao.save(list.get(i));
                    }


                } else {
                    application = mapper.toCreateEntity(arguments);
                    dao.save(application);
                }


            }
            case "done" -> {
            }
        }

    }
}
