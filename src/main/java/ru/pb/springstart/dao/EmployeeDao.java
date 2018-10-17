package ru.pb.springstart.dao;

import ru.pb.springstart.entity.Employee;
import ru.pb.springstart.entity.Subdivision;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 15.10.18.
 * pbarmenkov@gmail.com
 */
public interface EmployeeDao {

    void save(Employee employee);

    void update(Employee employee);

    void remove(Employee employee);

    Employee getById(int id);

    List<Employee> getListByPage(int page, int recordOnPage, int subdivisionId, String orderBy);

    int getAllRecords(int subdivisionId);

}
