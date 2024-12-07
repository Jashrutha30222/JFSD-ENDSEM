package com.klu.LabExam;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World");
         StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();
        Session session = sf.openSession();
        Transaction transaction;

       
        Department department1 = new Department();
        department1.setName("CSE");
        department1.setLocation("R AND D BLOCK");
        department1.setHodName("M.Jashrutha");

        transaction = session.beginTransaction();
        session.save(department1);
        transaction.commit();
        System.out.println("Inserted Department data.");

        
        Department department2 = new Department();
        department2.setName("ECE");
        department2.setLocation("Arts block");
        department2.setHodName("Thrushitha");

        transaction = session.beginTransaction();
        session.save(department2);
        transaction.commit();
        System.out.println("inserted data.");

        
        Department retrievedDept = session.find(Department.class, 1);
        if (retrievedDept != null) {
            System.out.println("Retrieved : ID = " + retrievedDept.getDepartmentId() + ", Name = " + retrievedDept.getName());
        }

       
        Department deptToDelete = session.find(Department.class, 2);  
        if (deptToDelete != null) {
            transaction = session.beginTransaction();
            session.delete(deptToDelete);
            transaction.commit();
            System.out.println("Delete Department with ID = 2.");
        } else {
            System.out.println("No Department found with ID = 2.");
        }

       
        Criteria criteria = session.createCriteria(Department.class);
        criteria.add(Restrictions.gt("departmentId", 1));
        List<Department> departments = criteria.list();
        for (Department dept : departments) {
            System.out.println("Department: ID = " + dept.getDepartmentId() + ", Name = " + dept.getName());
        }

        
        Query<Department> query = session.createQuery("select D from Department D where D.departmentId > 1", Department.class);
        List<Department> deptList = query.list();
        for (Department dept : deptList) {
            System.out.println("Department: ID = " + dept.getDepartmentId() + ", Name = " + dept.getName());
        }

       
        session.close();
        sf.close();
    }
}
