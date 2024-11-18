package com.afsal.project2.dao;


import com.afsal.project2.entity.Employee;
import com.afsal.project2.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private SessionFactory sessionFactory;

    public void deleteById(Integer id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, id);
        Employee employee = product.getEmployee();
        session.delete(product);
        session.delete(employee);
        transaction.commit();
        session.close();
    }

    public void deleteAll(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Product").executeUpdate();
        session.createQuery("DELETE FROM Employee").executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public Product isPresent(Product product) {
        Session session = sessionFactory.openSession();
        Query query= session.createQuery("FROM Product WHERE clientId=:clientId AND skuCode=:skuCode");
        query.setParameter("clientId", product.getClientId());
        query.setParameter("skuCode", product.getSkuCode());
        Product product1 = (Product) query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return product1;
    }

    public Product isPresentEmployee(Product product){
        Session session = sessionFactory.openSession();
        Query query =session.createQuery("FROM Product WHERE employee.empId=:empId");
        query.setParameter("empId", product.getEmployee().getEmpId());
        Product product1 = (Product) query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return product1;
    }
}
