package main.java.dao;

import main.java.entity.PackageType;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PackageTypeDAO {


    static public List<PackageType> getTypeInfo(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("from PackageType");

        List<PackageType> listOfTypeInfo = query.list();

        return listOfTypeInfo;
    }

    static public void updatePackageType(int packTypeId, String size, String weight, String price){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        PackageType packageType = session.get(PackageType.class, packTypeId);

        packageType.setSize(size);
        packageType.setWeight(weight);
        packageType.setPrice(price);

        session.update(packageType);

        session.getTransaction().commit();
    }
}
