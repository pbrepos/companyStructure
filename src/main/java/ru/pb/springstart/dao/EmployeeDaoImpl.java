package ru.pb.springstart.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.pb.springstart.entity.Employee;
import ru.pb.springstart.entity.Subdivision;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 15.10.18.
 * pbarmenkov@gmail.com
 */


@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    SessionFactory sessionFactory;


    @Override
    public void save(Employee employee) throws ConstraintViolationException {
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
    }

    @Override
    public void update(Employee employee) throws DataIntegrityViolationException {

        Session session = sessionFactory.getCurrentSession();

        Employee employeeUpdate = session.load(Employee.class, employee.getId());
        employeeUpdate.setFullName(employee.getFullName());
        employeeUpdate.setEmail(employee.getEmail());
        employeeUpdate.setDateBirth(employee.getDateBirth());
        employeeUpdate.setPhone(employee.getPhone());
        session.update(employeeUpdate);

    }

    @Override
    public void remove(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(employee);
    }

    @Override
    public Employee getById(int id) {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = query.from(Employee.class);
        query.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        Query<Employee> q = session.createQuery(query);
        Employee employee = q.getSingleResult();

        return employee;

    }

    @Override
    public List<Employee> getListByPage(int page, int recordOnPage, int subdivisionId, String orderBy) {

        Session session = sessionFactory.getCurrentSession();

        StringBuilder queryString = new StringBuilder("From Employee as em WHERE em.subdivision = " + subdivisionId);
        queryString.append(" order by em." + orderBy);
        Query query = session.createQuery(queryString.toString());

        query.setFirstResult(page * recordOnPage);
        query.setMaxResults(recordOnPage);

        List<Employee> employeesList = query.list();

        return employeesList;
    }

    @Override
    public int getAllRecords(int subdivisionId) {

        Session session = sessionFactory.getCurrentSession();

        StringBuilder queryString = new StringBuilder("From Employee as em WHERE em.subdivision = " + subdivisionId);
        Query query = session.createQuery(queryString.toString());
        List<Employee> employeesList = query.list();

        return employeesList.size();
    }
}
