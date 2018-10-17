package ru.pb.springstart.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.springstart.dao.EmployeeDao;
import ru.pb.springstart.entity.Employee;
import ru.pb.springstart.entity.Subdivision;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 15.10.18.
 * pbarmenkov@gmail.com
 */


@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    @Override
    public void save(Employee employee) throws ConstraintViolationException {
        employeeDao.save(employee);
    }

    @Transactional
    @Override
    public void update(Employee employee) throws DataIntegrityViolationException {
        employeeDao.update(employee);
    }

    @Transactional
    @Override
    public void remove(Employee employee) {
        employeeDao.remove(employee);
    }

    @Transactional
    @Override
    public Employee getById(int id) {
        return employeeDao.getById(id);
    }

    @Transactional
    @Override
    public List<Employee> getListByPage(int page, int recordOnPage, int subdivisionId, String orderBy) {
        return employeeDao.getListByPage(page, recordOnPage, subdivisionId, orderBy);
    }

    @Transactional
    @Override
    public int getAllRecords(int subdivisionId) {
        return employeeDao.getAllRecords(subdivisionId);
    }
}
