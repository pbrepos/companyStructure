package ru.pb.springstart.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.pb.springstart.entity.Office;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */
@Repository
public class OfficeDaoImpl implements OfficeDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Office office) throws ConstraintViolationException {
        sessionFactory.getCurrentSession().save(office);
    }


    @Override
    public List<Office> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Office> query = builder.createQuery(Office.class);
        Root<Office> root = query.from(Office.class);
        query.select(root);
        Query<Office> q = session.createQuery(query);
        return q.getResultList();
    }


    @Override
    public Office getOfficeById(int id) {

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Office> query = criteriaBuilder.createQuery(Office.class);

        Root<Office> root = query.from(Office.class);
        query.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        Query<Office> q = session.createQuery(query);
        Office office = q.getSingleResult();
        return office;
    }

    @Override
    public void remove(Office office) {

        Session session = sessionFactory.getCurrentSession();
        session.remove(office);
    }

    @Override
    public void update(Office office) throws DataIntegrityViolationException {
        Session session = sessionFactory.getCurrentSession();

        Office officeUpdate = session.load(Office.class, office.getId());
        officeUpdate.setName(office.getName());
        officeUpdate.setAddress(office.getAddress());

        session.update(officeUpdate);
    }
}
