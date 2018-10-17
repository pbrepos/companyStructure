package ru.pb.springstart.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.springstart.entity.Subdivision;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */
@Repository
@Transactional
public class SubdivisionDaoImpl implements SubdivisionDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Subdivision subdivision) throws ConstraintViolationException{
        sessionFactory.getCurrentSession().save(subdivision);
    }


    @Override
    public List<Subdivision> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subdivision> query = builder.createQuery(Subdivision.class);
        Root<Subdivision> root = query.from(Subdivision.class);
        query.select(root);
        Query<Subdivision> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Subdivision getSubdivisionById(int id) {

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Subdivision> query = criteriaBuilder.createQuery(Subdivision.class);

        Root<Subdivision> root = query.from(Subdivision.class);
        query.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        Query<Subdivision> q = session.createQuery(query);
        Subdivision subdivision = q.getSingleResult();

        return subdivision;
    }

    @Override
    public void remove(Subdivision subdivision) {

        Session session = sessionFactory.getCurrentSession();
        session.remove(subdivision);

    }

    @Override
    public void update(Subdivision subdivision) throws DataIntegrityViolationException {
        Session session = sessionFactory.getCurrentSession();

        Subdivision subdivisionUpdate = session.load(Subdivision.class, subdivision.getId());
        subdivisionUpdate.setName(subdivision.getName());
        subdivisionUpdate.setFullNameHead(subdivision.getFullNameHead());

        session.update(subdivisionUpdate);
    }
}
